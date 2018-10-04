package com.sometotest.test.selenium_test.gmx;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EmailPage extends GMXSignedInPage {

	private WebElement write_email_button;

	public EmailPage( WebDriver driver ) {
		super( driver );

		//WebElement myframe = this.waitForElement(By.id("thirdPartyFrame_mail"));
		//this.getDriver().switchTo().frame(myframe.getAttribute( "name" ));
		this.getDriver().switchTo().frame( "mail" );
		//write_email_button = this.waitForElement( By.id( "id6" ) );
		write_email_button = this.waitForElement(By.xpath("//a[@title='E-Mail schreiben']"));
	}

	public WriteEmailPage write_mail() {
		write_email_button.click();
		return new WriteEmailPage( getDriver() );
	}

//thirdPartyFrame_mail
}