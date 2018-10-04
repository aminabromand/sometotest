package com.sometotest.test.selenium_test.valuemation;

import com.sometotest.test.selenium_test.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Properties;

public class HomePage extends ValuemationPage{

	private WebElement end_user_button;
	private WebElement logout_button;

	private WebElement frame1;

	public HomePage( WebDriver driver, Properties pageProperties, String xts ) {
		super( driver, pageProperties, xts );
		logout_button = this.waitForElement( By.xpath( getProperty( "homepage.logout_button.xpath" ) ) );
	}

	public EndUserFolder switch_to_end_user() {
		frame1 = this.waitForElement( By.xpath( getPropXTS( "homepage.frame1.xts.xpath" ) ) );
		this.getDriver().switchTo().frame( frame1 );
		end_user_button = this.waitForElement( By.xpath( getProperty( "homepage.end_user_button.xpath" ) ) );
		end_user_button = this.waitForElementVisible( By.xpath( getProperty( "homepage.end_user_button.xpath" ) ) );
		end_user_button.click();
		return new EndUserFolder( getDriver(), pageProperties, xts );
	}

	public void logout(){
		logout_button.click();
	}
}
