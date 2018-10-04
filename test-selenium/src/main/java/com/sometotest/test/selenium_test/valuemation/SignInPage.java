package com.sometotest.test.selenium_test.valuemation;

import com.sometotest.test.selenium_test.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class SignInPage extends ValuemationPage{

	// private String target_url = "https://www.gmx.net";
	private WebElement username_input_field;
	private WebElement password_input_field;
	private WebElement login_submit;

	public SignInPage( WebDriver driver, Properties pageProperties ) throws InterruptedException{
		super( driver );

		if ( pageProperties == null ) {
			loadProperties( File.separator + "properties" + File.separator + "valuemation.selenium.properties" );
		}

		getDriver().get( getProperty( "valuemation.target_url" ) + getProperty( "sign_in_page.url" ) );

		TimeUnit.MILLISECONDS.sleep( 2000 );
		String cacheId = (String)((JavascriptExecutor)getDriver()).executeScript( "return cacheId;" );
		String[] xts = cacheId.split( "=" );
		this.xts = xts[1];


		username_input_field = this.waitForElement( By.name( getProperty( "sign_in_page.username_field.name" )) );
		password_input_field = this.waitForElement( By.name( getProperty( "sign_in_page.password_field.name" )) );
		login_submit = this.getDriver().findElement( By.xpath( getProperty( "sign_in_page.login_button.xpath" ) ) );
	}

	public SignInPage( WebDriver driver ) throws InterruptedException{
		this( driver, null );
	}

	public HomePage sign_in(String username, String password) {
		username_input_field.sendKeys( username );
		password_input_field.sendKeys( password );
		login_submit.click();
		return new HomePage( getDriver(), pageProperties, xts );
	}
}