package com.sometotest.eagent;

import com.sometotest.eagent.core.AgentClient;

import java.net.UnknownHostException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        try {
			AgentClient myClient = new AgentClient();
			myClient.start();
		} catch(UnknownHostException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}

	}
}
