package com.sometotest.eagent.tplan.desktop;

import com.sometotest.eagent.tplan.base.PageObject;
import com.sometotest.eagent.tplan.base.Utils;
import com.tplan.robot.scripting.ScriptingContext;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TextFilePage extends PageObject{
	private PageObject parentPage;
	private String closeButtonImageLocation = File.separator + "tplan" + File.separator + "text_file_page" + File.separator + "close_button";
	private String closeButtonImageName = "close_button.png";
	private Point closeButton;
	private String text;

	TextFilePage ( PageObject parentPage, ScriptingContext context ) {
		super( context );
		this.parentPage = parentPage;
		prepareMe();
	}

	private Boolean prepareMe () {
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
