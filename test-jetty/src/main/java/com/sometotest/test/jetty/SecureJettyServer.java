package com.sometotest.test.jetty;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.security.Constraint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class SecureJettyServer extends AbstractHandler
{
	@Override
	public void handle( String target,
					Request baseRequest,
					HttpServletRequest request,
					HttpServletResponse response ) throws IOException
	{
		// Declare response encoding and types
		response.setContentType("text/html; charset=utf-8");

		// Declare response status code
		response.setStatus(HttpServletResponse.SC_OK);

		// Write back response
		response.getWriter().println("<h1>Hallo =)</h1>");
		response.getWriter().println("Method: "+baseRequest.getMethod());
		response.getWriter().println("ContentType: "+baseRequest.getContentType());
		response.getWriter().println("ContextPath: "+baseRequest.getContextPath());
		response.getWriter().println("RequestURI: "+baseRequest.getRequestURI());
		response.getWriter().println("OriginalURI: "+baseRequest.getOriginalURI());
		response.getWriter().println("HttpURI: "+baseRequest.getHttpURI());
		response.getWriter().println("HttpInput: " + baseRequest.getHttpInput());
		response.getWriter().println("HttpFields" + baseRequest.getHttpFields());

		baseRequest.setHandled(true);

		System.out.println("secure request handled!");
	}

	private void handleJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data = "";
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = request.getReader();

		String line;
		int i = 0;
		while ((line = reader.readLine()) != null) {
			i++;
			System.out.println("line no: " + i);
			builder.append(line);
		}
		data = builder.toString();

		JsonObject jsonObject = (new JsonParser()).parse(data).getAsJsonObject();
		String file1String = jsonObject.getAsJsonArray( "files" ).get( 0 ).getAsJsonObject().get("file1").getAsString();


		byte[] decodedValue = Base64.getMimeDecoder().decode(file1String.getBytes( "UTF-8" ));
		System.out.println("decoded value length: " + decodedValue.length);

		String outputFilePath = SecureJettyServer.class.getProtectionDomain().getCodeSource().getLocation().getPath()
						+ File.separator + ".." + File.separator + "resources" + File.separator ;
		File outputFile = new File( outputFilePath + "output.png" );
		outputFile.createNewFile();


		try (FileOutputStream fos = new FileOutputStream(outputFile)) {
			fos.write(decodedValue);
		}
	}


	public static void startServer() throws Exception
	{

		System.out.println( System.getProperty("java.io.tmpdir") );
		System.out.println( "jetty.home: " + System.getProperty("jetty.home") );
		System.out.println( "jetty.base: " + System.getProperty("jetty.base") );

		Server server = new Server(8081);

		SecureJettyServer myJettyServer = new SecureJettyServer();

		HandlerCollection collection = new HandlerCollection();
		collection.addHandler(myJettyServer);


		String jetty_base = SecureJettyServer.class.getProtectionDomain().getCodeSource().getLocation().getPath()
						+ File.separator + ".." + File.separator + "resources" + File.separator ;

		// === test-realm.xml ===
		HashLoginService loginService = new HashLoginService();
		loginService.setName("Test Realm");
		loginService.setConfig(jetty_base + "etc/realm.properties");
		loginService.setHotReload(false);
		server.addBean(loginService);


		// A security handler is a jetty handler that secures content behind a
		// particular portion of a url space. The ConstraintSecurityHandler is a
		// more specialized handler that allows matching of urls to different
		// constraints. The server sets this as the first handler in the chain,
		// effectively applying these constraints to all subsequent handlers in
		// the chain.
		ConstraintSecurityHandler security = new ConstraintSecurityHandler();
		server.setHandler(security);

		// This constraint requires authentication and in addition that an
		// authenticated user be a member of a given set of roles for
		// authorization purposes.
		Constraint constraint = new Constraint();
		constraint.setName("auth");
		constraint.setAuthenticate(true);
		constraint.setRoles(new String[] { "user", "admin" });

		// Binds a url pattern with the previously created constraint. The roles
		// for this constraing mapping are mined from the Constraint itself
		// although methods exist to declare and bind roles separately as well.
		ConstraintMapping mapping = new ConstraintMapping();
		mapping.setPathSpec("/*");
		mapping.setConstraint(constraint);

		// First you see the constraint mapping being applied to the handler as
		// a singleton list, however you can passing in as many security
		// constraint mappings as you like so long as they follow the mapping
		// requirements of the servlet api. Next we set a BasicAuthenticator
		// instance which is the object that actually checks the credentials
		// followed by the LoginService which is the store of known users, etc.
		security.setConstraintMappings(Collections.singletonList(mapping));
		security.setAuthenticator(new BasicAuthenticator());
		security.setLoginService(loginService);


		security.setHandler(collection);
//		server.setHandler(collection);
		server.setHandler( security );


		server.start();
		server.join();
	}
}
