package com.sometotest.eagent.gui;

import com.sometotest.eagent.receiver.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class ReceiverWorker extends SwingWorker<Integer, String>{

	private final static Logger logger = LoggerFactory.getLogger( ButtonListener.class );

	private Receiver myReceiver;

	ReceiverWorker( Receiver receiverModule )
	{
		this.myReceiver = receiverModule;
	}

	protected Integer doInBackground() throws Exception {
		try {
			logger.info( "starting receiver" );
			myReceiver.startServer();
		} catch (InterruptedException e) {
			logger.info( "stopping receiver" );
			myReceiver.stopServer();
		}
		return 0;
	}
}
