package com.sometotest.eagent.gui;

import com.sometotest.eagent.sender.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class SenderWorker extends SwingWorker<Integer, String>{

	private final static Logger logger = LoggerFactory.getLogger( ButtonListener.class );

	private Sender mySender;

	SenderWorker( Sender senderModule )
	{
		this.mySender = senderModule;
	}

	protected Integer doInBackground() throws Exception {
		try {
			logger.info( "starting sender" );
			mySender.startClient();
		} catch (Exception e) {
			logger.info( "stopping sender" );
			mySender.stopClient();
		}
		return 0;
	}
}