package com.sometotest.eagent.gui;

import com.sometotest.eagent.activiti.ActivitiBase;
import com.sometotest.eagent.core.AgentClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AgentClientGUI extends JFrame {

	private final static Logger logger = LoggerFactory.getLogger( AgentClientGUI.class );

	private AgentClient myClient;
	private SenderWorker senderWorker;
	private ReceiverWorker receiverWorker;
	private ActivitiWorker activitiWorker;

	private JButton jButton1 = new JButton( "Start Client" );
	private JButton jButton2 = new JButton( "Start Process" );
	private JButton jButton3 = new JButton( "jButton3" );
	private JButton jButton4 = new JButton( "jButton4" );
	private JButton jButton5 = new JButton( "jButton5" );

	private JTextArea jTextArea = new JTextArea( "" );

	private ButtonListener bl;

	public AgentClientGUI( AgentClient myClient ) {
		this.myClient = myClient;
		this.bl = new ButtonListener( this );
		initComponents();
	}

	private void initComponents () {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLayout( new java.awt.BorderLayout() );


		JPanel jPanel1 = new JPanel();
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
		//this.add(jButton2, java.awt.BorderLayout.PAGE_END);
		// this.add(jButton3, java.awt.BorderLayout.LINE_START);
		this.add(jTextArea, java.awt.BorderLayout.CENTER);
		// this.add(jButton5, java.awt.BorderLayout.LINE_END);

		pack();

		this.setSize(500,500);
	}

	void start() {
		logger.info( "client start signal received" );
		senderWorker = new SenderWorker( myClient.getSenderModule() );
		receiverWorker = new ReceiverWorker( myClient.getReceiverModule() );
		activitiWorker = new ActivitiWorker( myClient.getActivitiModule() );
		try{
			receiverWorker.addPropertyChangeListener( new PropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent event) {

					switch (event.getPropertyName()) {
						case "state":
							switch ((SwingWorker.StateValue) event.getNewValue()) {
								case DONE:
									jButton1.setText( "Start Client" );
									jTextArea.append( ":> Client halted\n" );
									break;

								case STARTED:
									jButton1.setText( "Stop Client" );
									jTextArea.append( ":> Client started\n" );
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
			activitiWorker.execute();

			sendServerRegistration();

		} catch(Exception e){
			e.printStackTrace();
		}
	}

	void stop() {
		logger.info( "server stop signal received" );
		senderWorker.cancel( true );
		receiverWorker.cancel(true);
		activitiWorker.cancel( true );
	}

	private void sendServerRegistration() {
		myClient.sendServerRegistration();
	}

}
