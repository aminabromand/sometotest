package com.sometotest.test.selenium_test.gmx;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends GMXSignedInPage{

	//"//use[@xlink:href='#core_mail']"
	private String email_button_path = "(//POS-SVG-ICON[@class='pos-icon-item__icon pos-svg-icon'])[2]";
	private WebElement email_button;

	public HomePage( WebDriver driver ) {
		super( driver );

		email_button = this.waitForElement( By.xpath( email_button_path ) );
	}

	public EmailPage switch_to_mail() {
		email_button.click();
		return new EmailPage( getDriver() );
	}
}
