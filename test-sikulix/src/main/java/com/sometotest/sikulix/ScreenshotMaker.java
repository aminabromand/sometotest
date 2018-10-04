package com.sometotest.sikulix;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

public class ScreenshotMaker{

	public static void main( String[] args ){

		Screen s = new Screen();

		try{

			ScreenImage userCapture = s.userCapture();
			userCapture.save( "/Users/amin/Work/sometotest/test-sikulix/src/main/resources/imgs/input/", "screenshot" );
			s.find( userCapture.getFile() ).highlight( 2 );

		} catch(FindFailed findFailed){
			findFailed.printStackTrace();
		}
	}

}
