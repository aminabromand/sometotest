package com.sometotest.test.selenium_test.desktop;

import com.sometotest.test.selenium_test.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.Properties;

public class TestPage extends PageObject {
	private WebElement test_button_1;
	private WebElement test_button_2;
	private WebElement stop_button;

	public TestPage( WebDriver driver, Properties pageProperties ) {
		super( driver );

		if ( pageProperties == null ) {
			loadProperties( File.separator + "properties" + File.separator + "desktop.selenium.properties" );
		}

		getDriver().get( getProperty( "test_page.target_url" ) + getProperty( "test_page.url" ) );
	}

	public TestPage( WebDriver driver ) {
		this( driver, null );
	}

	public void test() {
		test_button_1 = this.waitForElement( By.xpath( getProperty( "test_page.test_button.xpath1" )) );
		test_button_1.click();
		test_button_2 = this.waitForElement( By.xpath( getProperty( "test_page.test_button.xpath2" )) );
		test_button_2.click();
		stop_button = this.waitForElement( By.id( getProperty( "test_page.stop_button.id" )) );
		stop_button.click();
	}
}
