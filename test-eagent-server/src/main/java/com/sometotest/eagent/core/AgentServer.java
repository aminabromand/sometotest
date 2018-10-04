package com.sometotest.eagent.core;

import com.sometotest.eagent.gui.AgentServerGUI;
import com.sometotest.eagent.receiver.Receiver;
import com.sometotest.eagent.sender.Sender;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AgentServer{

	private Properties serverProperties;

	private String ipaddress;
	private String port;

	private ClientEntry myClient;

	private Sender senderModule;
	private Receiver receiverModule;
	private AgentServerGUI myGUI;

	public AgentServer() throws UnknownHostException{
		loadProperties( File.separator + "AgentServer.properties" );
		ipaddress = InetAddress.getLocalHost().getHostAddress();
		port = serverProperties.getProperty( "core.receiver_port" );

		senderModule = new Sender();
		receiverModule = new Receiver( this );
		myGUI = new AgentServerGUI( this );
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
		serverProperties = new Properties();
		InputStream properties_input_stream = this.getClass().getResourceAsStream( propertiesLocation );
		try{
			serverProperties.load( properties_input_stream );
		} catch( IOException ioex ) {
			ioex.printStackTrace();
		}
	}

	public String getPort() {
		return port;
	}

	public String toString() {
		return "Server @" + ipaddress +":" + port;
	}

	public Receiver getReceiverModule() {
		return receiverModule;
	}

	public Sender getSenderModule() {
		return senderModule;
	}

	public void startProcess() {
		try{
			senderModule.sendStartProcess( myClient );
		} catch(InterruptedException e){
			e.printStackTrace();
		} catch(ExecutionException e){
			e.printStackTrace();
		} catch(TimeoutException e){
			e.printStackTrace();
		}
	}

	public void addClient( ClientEntry clientEntry ) {
		myClient = clientEntry;
	}
	public ClientEntry getClients() { return myClient; }
}
