package com.sometotest.eagent.sender;

import com.sometotest.eagent.core.AgentClient;
import com.sometotest.eagent.core.ServerEntry;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Sender{

	private final static Logger logger = LoggerFactory.getLogger( Sender.class );

	private HttpClient httpClient;

	public Sender() {
		httpClient = new HttpClient();
	}

	public void startClient() throws Exception{
		httpClient.start();
	}

	public void stopClient() throws Exception{
		httpClient.stop();
	}

	public void sendServerRegistration( ServerEntry myServer, String ipaddress, String port )
					throws InterruptedException, ExecutionException, TimeoutException{
		ContentResponse response = httpClient.POST( myServer.getURL() + "/client/register/" )
										.param("client_ip", ipaddress )
										.param("client_port", port )
										.send();
		logger.info( "Server Registration Response: " + response.getContentAsString() );
	}

}
