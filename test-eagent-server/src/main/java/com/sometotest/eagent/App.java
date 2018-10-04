package com.sometotest.eagent;

import com.sometotest.eagent.core.AgentServer;
import com.sometotest.eagent.gui.AgentServerGUI;

import javax.swing.SwingUtilities;
import java.net.UnknownHostException;

public class App
{

	public static void main( String[] args )
	{
		System.out.println( "Hello World!" );

		try{
			AgentServer myServer = new AgentServer();
			myServer.start();
		} catch(UnknownHostException e){
			e.printStackTrace();
		} catch( Exception e ) {
			e.printStackTrace();
		}

	}
}
