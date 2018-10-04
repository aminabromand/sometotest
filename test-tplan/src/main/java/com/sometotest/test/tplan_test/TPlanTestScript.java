package com.sometotest.test.tplan_test;

import com.sometotest.test.tplan_test.desktop.DesktopPage;
import com.sometotest.test.tplan_test.desktop.TextFilePage;
import com.tplan.robot.scripting.DefaultJavaTestScript;

public class TPlanTestScript extends DefaultJavaTestScript{
	public void test() {

		DesktopPage my_desktop_page = new DesktopPage( getContext() );
		if ( !my_desktop_page.getPrepared() ) {
			System.out.println( "Desktop blurry" );
			return;
		}

		TextFilePage my_text_file_page = my_desktop_page.openTextFile();
		if ( !my_text_file_page.getPrepared() ) {
			System.out.println( "TextFile blurry" );
			return;
		}
		System.out.println( "==============================================================================" );
		System.out.println( "!RESULT: the text that was read is >> " + my_text_file_page.getText() + " << !" );
		System.out.println( "==============================================================================" );
		my_text_file_page.closeTextFile();
	}
}
