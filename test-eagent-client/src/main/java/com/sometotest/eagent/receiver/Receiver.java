package com.sometotest.eagent.receiver;

import com.sometotest.eagent.core.AgentClient;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class Receiver{

	private AgentClient agentClient;
	private Server jettyServer;

	public Receiver( AgentClient agentClient ){
		this.agentClient = agentClient;
		jettyServer = new Server( Integer.parseInt( agentClient.getPort() ) );

		ContextHandler context = new ContextHandler("/process/start");
		context.setHandler( new ProcessStartHandler( agentClient ) );

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers( new Handler[] { context } );

		jettyServer.setHandler( contexts );
	}

	public void startServer() throws Exception{
		jettyServer.start();
		jettyServer.join();
	}

	public void stopServer() throws Exception {
		jettyServer.stop();
	}
}
