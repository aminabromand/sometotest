package com.sometotest.test.jetty;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class JsonJettyServer extends AbstractHandler
{
	@Override
	public void handle( String target,
					Request baseRequest,
					HttpServletRequest request,
					HttpServletResponse response ) throws IOException,
					ServletException
	{
		// Declare response encoding and types
		response.setContentType("text/html; charset=utf-8");

		// Declare response status code
		response.setStatus(HttpServletResponse.SC_OK);

		// Write back response
		response.getWriter().println("Method: "+baseRequest.getMethod());
		response.getWriter().println("ContentType: "+baseRequest.getContentType());
		response.getWriter().println("ContextPath: "+baseRequest.getContextPath());
		response.getWriter().println("RequestURI: "+baseRequest.getRequestURI());
		response.getWriter().println("OriginalURI: "+baseRequest.getOriginalURI());
		response.getWriter().println("HttpURI: "+baseRequest.getHttpURI());



		Map<String, String[]> params = baseRequest.getParameterMap();
		if ( params.size() > 0 ) {
			response.getWriter().println("Parameter:");
			for ( String key : params.keySet() ) {
				response.getWriter().print("key: "+key + " ");
				String[] values = params.get( key );
				if ( values.length > 0 ) {
					for ( String value : values ) {
						response.getWriter().println("value: "+value);
					}
				}
			}
		} else {
			response.getWriter().println("Parameter: None");
		}


		Enumeration<String> headerNames = baseRequest.getHeaderNames();
		if ( headerNames.hasMoreElements() ) {
			response.getWriter().println("Headers");
			while ( headerNames.hasMoreElements() ) {
				String header = headerNames.nextElement();
				response.getWriter().print("header: "+header+" ");
				String value = baseRequest.getHeader( header );
				response.getWriter().println("value: "+value);
			}
		} else {
			response.getWriter().println("Headers: None");
		}

		response.getWriter().println("HttpInput: " + baseRequest.getHttpInput());
		response.getWriter().println("HttpFields" + baseRequest.getHttpFields());



		Enumeration<String> attributeNames = baseRequest.getAttributeNames();
		if ( attributeNames.hasMoreElements() ) {
			response.getWriter().println("Attributes: ");
			while ( attributeNames.hasMoreElements() ) {
				String attribute = attributeNames.nextElement();
				response.getWriter().print("attribute: "+attribute+" ");
				Object value = baseRequest.getAttribute( attribute );
				response.getWriter().println("value: "+value.toString());
			}
		} else {
			response.getWriter().println("Attributes: None");
		}

		if ( isMultipartRequest( baseRequest ) ) {
			Collection<Part> parts = baseRequest.getParts();
			Iterator<Part> partsIterator = parts.iterator();
			Part part;

			int i = 0;
			if ( partsIterator.hasNext() ) {
				while ( partsIterator.hasNext() ) {
					part = partsIterator.next();
					i++;
					response.getWriter().println( "i: " + i);
					response.getWriter().print( "part name: " + part.getName() );
					String filename = part.getSubmittedFileName();
					if ( filename != null ) {
						part.write( filename );
						response.getWriter().println( ", file name: " + part.getSubmittedFileName() );
					} else {
						response.getWriter().println( ", part: " + baseRequest.getParameter( part.getName() ) );
					}
				}

			}
		}

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
//		byte[] decodedValue = Base64.getDecoder().decode( file1String.getBytes( "UTF-8" ) );
		System.out.println("decoded value length: " + decodedValue.length);

		String outputFilePath = JsonJettyServer.class.getProtectionDomain().getCodeSource().getLocation().getPath()
						+ File.separator + ".." + File.separator + "resources" + File.separator ;
		File outputFile = new File( outputFilePath + "pic38.png" );
		outputFile.createNewFile();


		try (FileOutputStream fos = new FileOutputStream(outputFile)) {
			fos.write(decodedValue);
		}


		// Inform jetty that this request has now been handled
		baseRequest.setHandled(true);
	}

	public static final String MULTIPART_FORMDATA_TYPE = "multipart/form-data";
	public static boolean isMultipartRequest(ServletRequest request) {
		return request.getContentType() != null
						&& request.getContentType().startsWith(MULTIPART_FORMDATA_TYPE);
	}

	public static void startServer() throws Exception
	{

		System.out.println( System.getProperty("java.io.tmpdir") );
		System.out.println( "jetty.home: " + System.getProperty("jetty.home") );
		System.out.println( "jetty.base: " + System.getProperty("jetty.base") );

		Server server = new Server(8081);

		JsonJettyServer myJettyServer = new JsonJettyServer();




		MultipartConfigInjectionHandler multipartConfigInjectionHandler =
						new MultipartConfigInjectionHandler();

		HandlerCollection collection = new HandlerCollection();
		collection.addHandler(myJettyServer);


		multipartConfigInjectionHandler.setHandler(collection);

		server.setHandler(multipartConfigInjectionHandler);
//		server.setHandler( myJettyServer );


		server.start();
		server.join();
	}
}
