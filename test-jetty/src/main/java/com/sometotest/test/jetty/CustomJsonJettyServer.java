package com.sometotest.test.jetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

public class CustomJsonJettyServer extends AbstractHandler
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
		response.getWriter().println("ContextPath"+baseRequest.getContextPath());
		response.getWriter().println("RequestURI"+baseRequest.getRequestURI());
		response.getWriter().println("OriginalURI"+baseRequest.getOriginalURI());
		response.getWriter().println("HttpURI"+baseRequest.getHttpURI());



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

		Server server = new Server(8081);

		CustomJsonJettyServer myJettyServer = new CustomJsonJettyServer();




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
