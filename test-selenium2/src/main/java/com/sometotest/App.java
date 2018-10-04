package com.sometotest;

import com.sometotest.selenium.SeleniumTest;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        SeleniumTest myTest = new SeleniumTest();
        myTest.writeSeleniumInfos();
    }
}
