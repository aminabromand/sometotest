package com.sometotest.test;

import com.sometotest.test.file_test.FileTest;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

//        SeleniumTest myTest = new SeleniumTest("https://www.google.de");
//        myTest.doGMX();

        FileTest myFileTest = new FileTest();
        myFileTest.do_file_test();
    }
}
