package com.sometotest.test.tplan_test.desktop;

import com.sometotest.test.tplan_test.tplan.PageObject;
import com.sometotest.test.tplan_test.tplan.Utils;
import com.tplan.robot.scripting.ScriptingContext;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DesktopPage extends PageObject{
	private String textFileImageLocation = File.separator + "desktop_page" + File.separator + "text_file";
	private String textFileImageName = "text_file.png";
	private Point textFile;

	public DesktopPage ( ScriptingContext context ) {
		super( context );
		prepareMe();
	}

	public Boolean prepareMe () {
		try {
			File imageFile = Utils.getFile( this, textFileImageLocation, textFileImageName );
			System.out.println( "image file exists: " + imageFile.exists());
			File[] imageFileList = new File[] { imageFile };
			// compareTo(new File[] { getFile( textFileImageLocation, textFileImageName ) }, "search2", 70.0f);
			compareTo(imageFileList, "search2", 70.0f);

			// File[] testme = new File[] { new File("/Users/amin/Work/testfile") };
//			File[] testme = new File[2];
//			testme[0] = new File("/Users/amin/Work/testfile/text_file.png");
//			testme[1] = new File("/Users/amin/Work/testfile/out_text_file_2.png");

//			for(int i = 0; i < testme.length; i++) {
//				System.out.println("file exists " + testme[i].exists());
//			}

//			compareTo(testme, "search2", 70.0f);

			textFile = getContext().getSearchHitClickPoint();
			prepared = true;
		} catch ( IOException ex ) {
			prepared = false;
		}
		return prepared;
	}

	public TextFilePage openTextFile () {
		try {
			mouseDoubleClick( textFile );
			return new TextFilePage( this, getContext() );
		} catch ( IOException ex ) {
			return null;
		}
	}

}
