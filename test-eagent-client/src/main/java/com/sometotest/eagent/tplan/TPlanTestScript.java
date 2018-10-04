package com.sometotest.eagent.tplan;


import com.sometotest.eagent.activiti.ActivitiBase;
import com.sometotest.eagent.tplan.desktop.DesktopPage;
import com.sometotest.eagent.tplan.desktop.TextFilePage;
import com.tplan.robot.scripting.DefaultJavaTestScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TPlanTestScript extends DefaultJavaTestScript{
	private final static Logger logger = LoggerFactory.getLogger( TPlanTestScript.class );

	private String returnValue;

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

		returnValue = my_text_file_page.getText();
		logger.info( "==============================================================================" );
		logger.info( "!RESULT: the text that was read is >> " + my_text_file_page.getText() + " << !" );
		logger.info( "==============================================================================" );
		my_text_file_page.closeTextFile();
	}

	public String getReturnValue(){
		return returnValue;
	}
}
