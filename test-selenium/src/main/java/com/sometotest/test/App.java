package com.sometotest.test;


import com.sometotest.test.selenium_test.SeleniumTest;

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
        System.out.print( "Choose your test (1 = 'gmx', 2 = 'valuemation create', 3 = 'desktop test', 4 = 'selenium infos', 5 = 'valuemation change', 6 = 'valuemation list'): " );
        testSelection = Integer.valueOf(scanner.nextLine());


        if (testSelection == 1) {
            SeleniumTest myTest = new SeleniumTest("https://www.google.de");
            myTest.doGMX();
        } else if (testSelection == 2) {
            SeleniumTest myTest = new SeleniumTest("This string is not used !?");
            myTest.doValuemation();
        } else if (testSelection == 3) {
            SeleniumTest myTest = new SeleniumTest("This string is not used !?");
            myTest.doDesktopTest();
        } else if (testSelection == 4) {
            SeleniumTest myTest = new SeleniumTest("This string is not used !?");
            myTest.writeSeleniumInfos();
        } else if (testSelection == 5) {
            SeleniumTest myTest = new SeleniumTest("This string is not used !?");
            System.out.println( "" );
            System.out.print( "shorttext: ");
            String shorttext = scanner.nextLine();
            System.out.println( "" );
            System.out.print( "description: ");
            String description = scanner.nextLine();
            myTest.doValuemationChange( shorttext, description );
        } else if (testSelection == 6) {
            SeleniumTest myTest = new SeleniumTest("This string is not used !?");
            myTest.doValuemationProcessList(  );
        }

        scanner.close();

    }
}
