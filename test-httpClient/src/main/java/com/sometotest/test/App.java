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
		System.out.print( "Choose your http post (1 = 'multipartFormData', 2 = 'application/json'): " );
		testSelection = Integer.valueOf(scanner.nextLine());

		HttpClientUtilsTest myTester = new HttpClientUtilsTest();

		if (testSelection == 1) {
			myTester.doPost();
		} else if (testSelection == 2) {
			myTester.doPostJson();
		}

		scanner.close();




	}


}
