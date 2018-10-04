package com.sometotest.eagent;

import com.sometotest.eagent.server.MyeAgentServer;

/**
 * Hello world!
 *
 */

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        MyeAgentServer myeAgentServer = new MyeAgentServer();

        try {
            myeAgentServer.startServer();
        } catch( Exception ex ) {
            ex.printStackTrace();
        }

    }
}
