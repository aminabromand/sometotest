package com.sometotest.eagent.receiver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sometotest.eagent.core.AgentClient;
import com.sometotest.eagent.sender.Sender;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessStartHandler extends AbstractHandler
{
	private final static Logger logger = LoggerFactory.getLogger( Sender.class );

	private AgentClient agentClient;

	public ProcessStartHandler( AgentClient agentClient ) {
		super();
		this.agentClient = agentClient;
	}

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
		response.getWriter().println("<h2>Process started on: <u>" + agentClient.toString() + "</u></h2>");

		logger.info( "Process started on: " + agentClient.toString() );
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put( "selenium.geckodriver_path", agentClient.getProperty( "selenium.geckodriver_path" ) );
		agentClient.getActivitiModule().createProcessInstance( "writeEmailTest", variables );

		// Inform jetty that this request has now been handled
		baseRequest.setHandled( true );
	}
}