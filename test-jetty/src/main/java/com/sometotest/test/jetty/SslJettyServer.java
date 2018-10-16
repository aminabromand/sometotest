package com.sometotest.test.jetty;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

public class SslJettyServer extends AbstractHandler
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

		System.out.println("ssl request handled!");
	}




	public static void startServer() throws Exception
	{


		Server server = new Server();

		SslJettyServer myJettyServer = new SslJettyServer();

		HandlerCollection collection = new HandlerCollection();
		collection.addHandler(myJettyServer);


		String jetty_base = SslJettyServer.class.getProtectionDomain().getCodeSource().getLocation().getPath()
						+ File.separator + ".." + File.separator + "resources" + File.separator ;


		// HTTP Configuration
		HttpConfiguration http_config = new HttpConfiguration();
		http_config.setSecureScheme("https");
		//http_config.setSecurePort(8043); - this is not working somehow



//		// === jetty-http.xml ===
//		ServerConnector http = new ServerConnector(server,
//						new HttpConnectionFactory(http_config));
//		http.setPort(8080);
//		http.setIdleTimeout(30000);
//		server.addConnector(http);



		// === jetty-https.xml ===
		// SSL Context Factory
		// generating keystore: http://www.eclipse.org/jetty/documentation/current/configuring-ssl.html
		SslContextFactory sslContextFactory = new SslContextFactory();
		sslContextFactory.setKeyStorePath(jetty_base + "etc/keystore");
		sslContextFactory.setKeyStorePassword("abcdef9h");
		sslContextFactory.setKeyManagerPassword("abcdef9h");
		sslContextFactory.setTrustStorePath(jetty_base + "etc/keystore");
		sslContextFactory.setTrustStorePassword("abcdef9h");
		sslContextFactory.setExcludeCipherSuites("SSL_RSA_WITH_DES_CBC_SHA",
						"SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA",
						"SSL_RSA_EXPORT_WITH_RC4_40_MD5",
						"SSL_RSA_EXPORT_WITH_DES40_CBC_SHA",
						"SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
						"SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");

		// SSL HTTP Configuration
		HttpConfiguration https_config = new HttpConfiguration(http_config);
		https_config.addCustomizer(new SecureRequestCustomizer());

		// SSL Connector
		ServerConnector sslConnector = new ServerConnector(server,
						new SslConnectionFactory(sslContextFactory,HttpVersion.HTTP_1_1.asString()),
						new HttpConnectionFactory(https_config));
		sslConnector.setPort(8443);
		server.addConnector(sslConnector);



		server.setHandler(collection);


		server.start();
		server.join();
	}
}
