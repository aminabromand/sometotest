package com.sometotest.test;

import com.sometotest.test.http_client.HttpClientUtils;
import com.sometotest.test.http_client.HttpClientUtilsTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

		HttpClientUtilsTest myTester = new HttpClientUtilsTest();
		myTester.doPost();


	}


}
