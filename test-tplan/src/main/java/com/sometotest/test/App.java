package com.sometotest.test;

import com.sometotest.test.tplan_test.TPlanTestScript;
import com.sometotest.test.tplan_test.tplan.TPlanScriptRunner;

import java.net.URISyntaxException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "I do TPlan!" );

        try{

            TPlanTestScript my_script = new TPlanTestScript();
            TPlanScriptRunner my_runner = new TPlanScriptRunner();
            my_runner.runScript( my_script );

        } catch (URISyntaxException ex){
            System.out.println("Something went wrong.");
        }

        System.out.println( "I did TPlan!" );
    }
}
