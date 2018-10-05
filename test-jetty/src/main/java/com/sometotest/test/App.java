package com.sometotest.test;

import com.sometotest.test.jetty.CustomJettyServer;
import com.sometotest.test.jetty.CustomJsonJettyServer;

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
			CustomJettyServer.startServer();
			CustomJsonJettyServer.startServer();
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
    }
}
