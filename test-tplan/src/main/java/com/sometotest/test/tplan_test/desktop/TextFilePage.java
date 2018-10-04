package com.sometotest.test.tplan_test.desktop;

import com.sometotest.test.tplan_test.tplan.PageObject;
import com.sometotest.test.tplan_test.tplan.Utils;
import com.tplan.robot.scripting.ScriptingContext;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TextFilePage extends PageObject{
	private PageObject parentPage;
	private String closeButtonImageLocation = File.separator + "text_file_page" + File.separator + "close_button";
	private String closeButtonImageName = "text_file_page.close_button.png";
	private Point closeButton;
	private String text;

	public TextFilePage ( PageObject parentPage, ScriptingContext context ) {
		super( context );
		this.parentPage = parentPage;
		prepareMe();
	}

	public Boolean prepareMe () {
		try {
			wait( "1s" );
			File imageFile = Utils.getFile( this, closeButtonImageLocation, closeButtonImageName );
			File[] imageFileList = new File[] { imageFile };
			compareTo( imageFileList, "search2");
			closeButton = getContext().getSearchHitClickPoint();
			compareTo( new Rectangle( 178, 154, 541, 385 ), "deu" );
			text = getVariableAsString("_TOCR_TEXT");
			prepared = true;
		} catch ( IOException ex ) {
			prepared = false;
		}
		return prepared;
	}

	public String getText () {
		return text;
	}

	public PageObject closeTextFile () {
		try {
			mouseDoubleClick( closeButton );
			return parentPage;
		} catch ( IOException ex ) {
			return null;
		}
	}
}
