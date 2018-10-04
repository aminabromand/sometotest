package com.sometotest.test.selenium_test.valuemation;

import com.sometotest.test.selenium_test.selenium.PageObject;
import org.openqa.selenium.WebDriver;

import java.text.MessageFormat;
import java.util.Properties;

public class ValuemationPage extends PageObject{

	protected String xts;

	public ValuemationPage(WebDriver driver) {
		super( driver );
	}

	public ValuemationPage(WebDriver driver, String xts) {
		super( driver );
		this.xts = xts;
	}

	public ValuemationPage( WebDriver driver, Properties pageProperties, String xts ) {
		super( driver, pageProperties );
		this.xts = xts;
	}

	protected String getPropXTS( String key ) {
		return MessageFormat.format((String) getProperty( key ), xts);
	}

}
