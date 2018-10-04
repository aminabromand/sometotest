package com.sometotest.eagent.receiver;

import com.sometotest.eagent.core.AgentServer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class Receiver{

	private Server jettyServer;

	public Receiver( AgentServer agentServer ){
		jettyServer = new Server(8010);

		ContextHandler context = new ContextHandler("/client/register");
		//context.setContextPath("/client/register");
		context.setHandler( new ClientRegistrationHandler( agentServer ) );

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
