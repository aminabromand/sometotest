package com.sometotest.eagent.server;

import com.sometotest.eagent.handler.MyeAgentHandler;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class MyeAgentServer
{

	private Server server;

	public MyeAgentServer(){
		server = new Server(8080);

		ContextHandler context = new ContextHandler("/");
		context.setContextPath("/");
		context.setHandler(new MyeAgentHandler());

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { context });

		server.setHandler(contexts);
	}

	public void startServer() throws Exception{
		server.start();
		server.join();
	}
}
