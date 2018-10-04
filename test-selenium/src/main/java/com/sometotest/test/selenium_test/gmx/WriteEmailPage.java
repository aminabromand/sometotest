package com.sometotest.test.selenium_test.gmx;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WriteEmailPage extends GMXSignedInPage {

	private WebElement email_to;
	private WebElement email_subject;
	private WebElement email_body;
	private WebElement email_send;

	private WebElement body_frame;

	public WriteEmailPage( WebDriver driver ) {
		super( driver );

		this.getDriver().switchTo().frame( "mail" );
		email_to = this.waitForElement(By.xpath("//div/ul/li/input[@class='select2-input']"));
		email_subject = this.waitForElement(By.xpath("//input[@name='composeHeader:subject:mailObjectContent:mailObjectInput']"));
		email_send = this.waitForElement(By.xpath("//button[@name='composeHeader:buttonSend']"));

		body_frame = this.waitForElement(By.xpath("//iframe[@title='WYSIWYG-Editor, editor']"));
		this.getDriver().switchTo().frame( body_frame );
		email_body = this.waitForElement(By.id("body"));

		this.getDriver().switchTo().defaultContent();
		this.getDriver().switchTo().frame( "mail" );
	}

	public EmailPage send_email(String address) {
		email_to.sendKeys( address );
		email_subject.sendKeys("test subject");

		this.getDriver().switchTo().frame( body_frame );
		email_body.sendKeys("test body");

		this.getDriver().switchTo().defaultContent();
		this.getDriver().switchTo().frame( "mail" );
		email_send.click();

		return new EmailPage( getDriver() );
	}
}
