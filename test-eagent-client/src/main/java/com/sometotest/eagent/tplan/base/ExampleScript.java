package com.sometotest.eagent.tplan.base;

import com.tplan.robot.scripting.DefaultJavaTestScript;
import com.tplan.robot.scripting.StopRequestException;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ExampleScript extends DefaultJavaTestScript{


	public void test() {
		try {
//			setTemplateDir(getVariableAsString( "_PROJECT_TEMPLATE_DIR" ));
//			setOutputDir(getVariableAsString( "_PROJECT_REPORT_UNIQUE_DIR" ));
//			report( new File( "results.xml" ), "" );
			// Develop the automation code in here
			System.out.println("hola!!!");

			File[] testme = new File[2];
			testme[0] = new File("/Users/amin/Work/testfile/out_text_file_2.png");
			testme[1] = new File("/Users/amin/Work/testfile/text_file.png");

			takeScreenshot( "test" );

			compareTo(testme, "search2", 50.0f);

			System.out.println( "Compare To Exit: " + getContext().getExitCode() );

			Point textFile = getContext().getSearchHitClickPoint();

			mouseDoubleClick( textFile );

			System.out.println( "hier jetzt so" );

			exit( 0 );


		} catch (StopRequestException ex) {
			throw ex;
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new IllegalStateException(ex);
		}
	}

	private void takeScreenshot( String file_name ) throws IOException {
//		File containing_file = new File( this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI() );
		String temp_out_file_location = "/Users/amin/Work/testfile/out_screenshot.png";
		File screenshot_file = new File( temp_out_file_location );
		screenshot( screenshot_file );
	}

}
