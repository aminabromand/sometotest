package org.activiti;

import org.activiti.activiti_test.ActivitiTest;

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

		ActivitiTest myActivitiTest = new ActivitiTest();
		myActivitiTest.do_activiti();
	}
}
