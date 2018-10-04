package com.sometotest.eagent.sender;

import com.sometotest.eagent.core.ClientEntry;
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

	public void sendStartProcess( ClientEntry myClient )
					throws InterruptedException, ExecutionException, TimeoutException{
		logger.info( "sending Start Process to: " + myClient.getURL() );
		ContentResponse response = httpClient.POST( myClient.getURL() + "/process/start/" )
						.send();
		logger.info( "Client Start Process Response: " + response.getContentAsString() );
	}

}
