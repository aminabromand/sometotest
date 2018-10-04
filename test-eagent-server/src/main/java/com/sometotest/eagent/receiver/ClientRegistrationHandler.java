package com.sometotest.eagent.receiver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sometotest.eagent.core.AgentServer;
import com.sometotest.eagent.core.ClientEntry;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientRegistrationHandler extends AbstractHandler
{
	private final static Logger logger = LoggerFactory.getLogger( ClientRegistrationHandler.class );

	private AgentServer agentServer;

	public ClientRegistrationHandler( AgentServer agentServer ) {
		super();
		this.agentServer = agentServer;
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

		String client_ip = baseRequest.getParameter( "client_ip" );
		String client_port = baseRequest.getParameter( "client_port" );

		ClientEntry clientEntry = new ClientEntry( client_ip, client_port );
		agentServer.addClient( clientEntry );
		logger.info( "client connected" );


		// Write back response
		response.getWriter().println("<h1>Hello World</h1>");
		response.getWriter().println("<h2>" + agentServer.getClients().toString() + "</h2>");

		// Inform jetty that this request has now been handled
		baseRequest.setHandled(true);
	}
}
