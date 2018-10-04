package com.sometotest.test;

import com.sometotest.test.jetty.CustomJettyServer;

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
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
    }
}
