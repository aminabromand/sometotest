package com.sometotest.test;

import com.sometotest.test.jetty.JsonJettyServer;
import com.sometotest.test.jetty.MultipartDataJettyServer;
import com.sometotest.test.jetty.SecureJettyServer;
import com.sometotest.test.jetty.SslJettyServer;

import java.util.Scanner;

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

			int testSelection;
			Scanner scanner = new Scanner(System.in);

			System.out.println( "Choose your http handler");
			System.out.println ("* 'multipartFormData' => type 1");
			System.out.println( "* 'application/json' => type 2" );
			System.out.println( "* 'with Basic Auth' => type 3" );
			System.out.println( "* 'with ssl/tls' => type 4" );

			System.out.print( "type your selection number: " );

			testSelection = Integer.valueOf(scanner.nextLine());
			scanner.close();


			if (testSelection == 1) {
				MultipartDataJettyServer.startServer();
			} else if (testSelection == 2) {
				JsonJettyServer.startServer();
			} else if (testSelection == 3) {
				SecureJettyServer.startServer();
			} else if (testSelection == 4) {
				SslJettyServer.startServer();
			}


		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
    }
}
