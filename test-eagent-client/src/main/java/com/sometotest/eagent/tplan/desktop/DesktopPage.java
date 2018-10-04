package com.sometotest.eagent.tplan.desktop;

import com.sometotest.eagent.tplan.base.PageObject;
import com.sometotest.eagent.tplan.base.Utils;
import com.tplan.robot.scripting.ScriptingContext;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DesktopPage extends PageObject{
	private String textFileImageLocation = File.separator + "tplan" + File.separator + "desktop_page" + File.separator + "text_file";
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
			compareTo(imageFileList, "search2", 70.0f);


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
