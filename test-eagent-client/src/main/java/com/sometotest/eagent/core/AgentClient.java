package com.sometotest.eagent.core;

import com.sometotest.eagent.activiti.ActivitiBase;
import com.sometotest.eagent.gui.AgentClientGUI;
import com.sometotest.eagent.receiver.Receiver;
import com.sometotest.eagent.sender.Sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AgentClient{

	private final static Logger logger = LoggerFactory.getLogger( AgentClient.class );

	private Properties clientProperties;

	private String ipaddress;
	private String port;

	private ServerEntry myServer;

	private Sender senderModule;
	private Receiver receiverModule;
	private ActivitiBase activitModule;
	private AgentClientGUI myGUI;

	public AgentClient() throws UnknownHostException{
		loadProperties( File.separator + "conf" + File.separator + "AgentClient.properties" );


		try(final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ipaddress = socket.getLocalAddress().getHostAddress();
		} catch(SocketException e){
			e.printStackTrace();
			ipaddress = InetAddress.getLocalHost().getHostAddress();
		}
		logger.info( "client ip address: " + ipaddress );

		port = clientProperties.getProperty( "core.receiver_port" );

		myServer = new ServerEntry( clientProperties.getProperty( "core.server_ip" ), clientProperties.getProperty( "core.server_port" ) );

		senderModule = new Sender();
		receiverModule = new Receiver( this );
		activitModule = new ActivitiBase();
		myGUI = new AgentClientGUI( this );
	}

	public void start() throws Exception{
		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				myGUI.setVisible ( true );
			}
		});
	}

	public void stop() throws Exception{
	}

	private void loadProperties( String propertiesLocation ){
		clientProperties = new Properties();

		try{
			File containing_file = new File( this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI() );
			String temp_out_file_location = containing_file.getParentFile().getPath() + File.separator + "resources" + File.separator + propertiesLocation;
			File temp_out_file = new File( temp_out_file_location );

			InputStream properties_input_stream;
			if ( temp_out_file.exists() ) {
				//InputStream properties_input_stream = new FileInputStream( new File( this.getClass().getResource( propertiesLocation ).getPath() ) );
				properties_input_stream = new FileInputStream( temp_out_file );
			} else {
				properties_input_stream = this.getClass().getResourceAsStream( propertiesLocation );
			}

			clientProperties.load( properties_input_stream );
		} catch( IOException ioex ) {
			ioex.printStackTrace();
		} catch(URISyntaxException e){
			e.printStackTrace();
		}
	}

	public String getPort() {
		return port;
	}

	public String toString() {
		return "Client @" + ipaddress +":" + port;
	}

	public String getProperty( String key ) {
		return clientProperties.getProperty( key );
	}

	public Receiver getReceiverModule() {
		return receiverModule;
	}

	public Sender getSenderModule() {
		return senderModule;
	}

	public ActivitiBase getActivitiModule() {
		return activitModule;
	}

	public void sendServerRegistration() {
		try{
			senderModule.sendServerRegistration( myServer, ipaddress, port );
		} catch(InterruptedException e){
			e.printStackTrace();
		} catch(ExecutionException e){
			e.printStackTrace();
		} catch(TimeoutException e){
			e.printStackTrace();
		}
	}
}
