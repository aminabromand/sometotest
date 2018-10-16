package com.sometotest.test;

import com.sometotest.test.http_client.HttpClientUtils;
import com.sometotest.test.http_client.HttpClientUtilsTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		int testSelection;

		System.out.println( "Hello World!" );

		Scanner scanner = new Scanner(System.in);

		System.out.println( "Choose your http post");
		System.out.println ("* 'multipartFormData' => type 1");
		System.out.println( "* 'application/json' => type 2" );
		System.out.println( "* 'with Basic Auth' => type 3" );
		System.out.println( "* 'with ssl/tls' => type 4" );

		System.out.print( "type your selection number: " );

		testSelection = Integer.valueOf(scanner.nextLine());

		HttpClientUtilsTest myTester = new HttpClientUtilsTest();

		if (testSelection == 1) {
			myTester.doPost();
		} else if (testSelection == 2) {
			myTester.doPostJson();
		} else if (testSelection == 3) {
			myTester.doPostSecure();
		} else if (testSelection == 4) {
			myTester.doPostSsl();
		}

		scanner.close();




	}


}
