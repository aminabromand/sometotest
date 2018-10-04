package com.sometotest.eagent.gui;

import com.sometotest.eagent.core.AgentServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AgentServerGUI extends javax.swing.JFrame {

	private final static Logger logger = LoggerFactory.getLogger( AgentServerGUI.class );

	private AgentServer myServer;
	private SenderWorker senderWorker;
	private ReceiverWorker receiverWorker;

	private javax.swing.JButton jButton1 = new javax.swing.JButton( "Start Server" );
	private javax.swing.JButton jButton2 = new javax.swing.JButton( "Start Process" );
	private javax.swing.JButton jButton3 = new javax.swing.JButton( "jButton3" );
	private javax.swing.JButton jButton4 = new javax.swing.JButton( "jButton4" );
	private javax.swing.JButton jButton5 = new javax.swing.JButton( "jButton5" );

	private javax.swing.JTextArea jTextArea = new javax.swing.JTextArea( "" );

	private ButtonListener bl;

	public AgentServerGUI( AgentServer myServer ) {
		this.myServer = myServer;
		this.bl = new ButtonListener( this );
		initComponents();
	}

	private void initComponents () {

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setLayout( new java.awt.BorderLayout() );


		javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
		//jButton1 = new javax.swing.JButton( "jButton1" );
		jPanel1.setLayout( new java.awt.GridLayout(10, 10) );
		//jPanel1.add(jButton1);


		this.add(jPanel1, java.awt.BorderLayout.CENTER);

		jButton1.addActionListener( bl );
		jButton2.addActionListener( bl );
		jButton3.addActionListener( bl );
		jButton4.addActionListener( bl );
		jButton5.addActionListener( bl );

		this.add(jButton1, java.awt.BorderLayout.PAGE_START);
		this.add(jButton2, java.awt.BorderLayout.PAGE_END);
		// this.add(jButton3, java.awt.BorderLayout.LINE_START);
		this.add(jTextArea, java.awt.BorderLayout.CENTER);
		// this.add(jButton5, java.awt.BorderLayout.LINE_END);

		pack();

		this.setSize(500,500);
	}

	void start() {
		logger.info( "server start signal received" );
		senderWorker = new SenderWorker( myServer.getSenderModule() );
		receiverWorker = new ReceiverWorker( myServer.getReceiverModule() );
		try{
			receiverWorker.addPropertyChangeListener( new PropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent event) {

					switch (event.getPropertyName()) {
						case "state":
							switch ((SwingWorker.StateValue) event.getNewValue()) {
								case DONE:
									jButton1.setText( "Start Server" );
									jTextArea.append( ":> Server halted\n" );
									break;

								case STARTED:
									jButton1.setText( "Stop Server" );
									jTextArea.append( ":> Server started\n" );
									break;

								case PENDING:
									break;

							}
							break;
					}
				}
			});
			senderWorker.execute();
			receiverWorker.execute();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	void stop() {
		logger.info( "server stop signal received" );
		senderWorker.cancel( true );
		receiverWorker.cancel(true);
	}

	void startProcess() {
		myServer.startProcess();
	}


}
