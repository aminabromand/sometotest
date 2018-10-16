package com.sometotest.test.jetty;


import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

public class MultipartDataJettyServer extends AbstractHandler
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
		response.getWriter().println("<h1>Hello World</h1>");
		response.getWriter().println("<table border='1'>");
		response.getWriter().println("<tr><th>Meta</th><th>Method</th><th>ContentType</th><th>ContextPath</th><th>RequestURI</th><th>OriginalURI</th><th>HttpURI</th></tr>");



		response.getWriter().println("<tr><td></td><td>"+baseRequest.getMethod()
						+"</td><td>"+baseRequest.getContentType()
						+"</td><td>"+baseRequest.getContextPath()
						+"</td><td>"+baseRequest.getRequestURI()
						+"</td><td>"+baseRequest.getOriginalURI()
						+"</td><td>"+baseRequest.getHttpURI()
						+"</td></tr>"
						+"<tr><td>.</td></tr>");
		//response.getWriter().println("<h1>Hello World</h1>");


		Cookie[] cookies = baseRequest.getCookies();
		if ( cookies != null && cookies.length > 0 ) {
			response.getWriter().println("<tr><th>Cookies</th>");
			for ( Cookie cookie : cookies ) {
				response.getWriter().println("<td>"+cookie.toString()+"</td>"+"<td>"+cookie.getName()+"</td>"+"<td>"+cookie.getValue()+"</td>");
			}
			response.getWriter().println("</tr>"+"<tr><td>.</td></tr>");
		} else {
			response.getWriter().println("<tr><th>No Cookies found !</th></tr>"+"<tr><td>.</td></tr>");
		}

		Map<String, String[]> params = baseRequest.getParameterMap();
		if ( params.size() > 0 ) {
			response.getWriter().println("<tr><th>Parameter</th></tr><tr>");
			for ( String key : params.keySet() ) {
				response.getWriter().println("<th>"+key+"</th>");
				String[] values = params.get( key );
				if ( values.length > 0 ) {
					for ( String value : values ) {
						response.getWriter().println("<td>"+value+"</td>");
					}
				}
			}
			response.getWriter().println("</tr>"+"<tr><td>.</td></tr>");
		} else {
			response.getWriter().println("<tr><th>No Parameter found !</th></tr>"+"<tr><td>.</td></tr>");
		}


		Enumeration<String> headerNames = baseRequest.getHeaderNames();
		if ( headerNames.hasMoreElements() ) {
			response.getWriter().println("<tr><th>Headers</th></tr><tr>");
			while ( headerNames.hasMoreElements() ) {
				String header = headerNames.nextElement();
				response.getWriter().println("<th>"+header+"</th>");
				String value = baseRequest.getHeader( header );
				response.getWriter().println("<td colspan='6'>"+value+"</td>");
				response.getWriter().println("</tr>");
			}
			response.getWriter().println("<tr><td>.</td></tr>");
		} else {
			response.getWriter().println("<tr><th>No Headers found !</th></tr>"+"<tr><td>.</td></tr>");
		}

		response.getWriter().println("<tr><th>HttpInput</th><td>" + baseRequest.getHttpInput() + "</td></tr>"+"<tr><td>.</td></tr>");
		//baseRequest.getHttpFields();
		response.getWriter().println("<tr><th>HttpFields</th><td>" + baseRequest.getHttpFields() + "</td></tr>"+"<tr><td>.</td></tr>");


		response.getWriter().println("<tr><th>Param: testparam</th><td>" + baseRequest.getParameter( "testparam" ) + "</td></tr>");
		response.getWriter().println("<tr><th>Param: task</th><td>" + baseRequest.getParameter( "task" ) + "</td></tr>"+"<tr><td>.</td></tr>");


		Enumeration<String> attributeNames = baseRequest.getAttributeNames();
		if ( attributeNames.hasMoreElements() ) {
			response.getWriter().println("<tr><th>Headers</th></tr><tr>");
			while ( attributeNames.hasMoreElements() ) {
				String attribute = attributeNames.nextElement();
				response.getWriter().println("<th>"+attribute+"</th>");
				Object value = baseRequest.getAttribute( attribute );
				response.getWriter().println("<td colspan='6'>"+value.toString()+"</td>");
				response.getWriter().println("</tr>");
			}
			response.getWriter().println("<tr><td>.</td></tr>");
		} else {
			response.getWriter().println("<tr><th>No Attributes found !</th></tr>"+"<tr><td>.</td></tr>");
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


		response.getWriter().println("</table>");

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

		Server server = new Server(8080);

		MultipartDataJettyServer myJettyServer = new MultipartDataJettyServer();




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
