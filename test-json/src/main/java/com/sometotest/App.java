package com.sometotest;

import com.sometotest.json.JsonTest;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

		JsonTest jsonTest = new JsonTest();

		jsonTest.doJson();

    }
}
