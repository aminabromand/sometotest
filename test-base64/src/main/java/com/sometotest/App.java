package com.sometotest;

import com.sometotest.base64.Base64Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String inputFilePath = App.class.getProtectionDomain().getCodeSource().getLocation().getPath()
						+ File.separator + ".." + File.separator + "resources";


		Base64Test base64Test = new Base64Test();
		try{
			base64Test.run( new String[]{ inputFilePath } );
		} catch(IOException e){
			e.printStackTrace();
		}

	}
}
