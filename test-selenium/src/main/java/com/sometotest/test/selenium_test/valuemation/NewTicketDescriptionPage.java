package com.sometotest.test.selenium_test.valuemation;

import com.sometotest.test.selenium_test.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class NewTicketDescriptionPage extends ValuemationPage{

	private WebElement shorttext_input;
	private WebElement description;
	private WebElement ok_button;

	private WebElement frame1;

	public NewTicketDescriptionPage( WebDriver driver, Properties pageProperties, String xts ) {
		super( driver, pageProperties, xts );
	}

	public EditTicketPage fill_and_save( String shorttext, String description ) {

		this.frame1 = this.waitForElement( By.xpath( getProperty( "new_ticket_description_page.frame1" ) ) );
		this.getDriver().switchTo().frame( frame1 );
		this.description = this.waitForElement( By.xpath( getProperty( "new_ticket_description_page.frame1.description.xpath" ) ) );
		this.description.sendKeys( description );
		this.getDriver().switchTo().defaultContent();

		shorttext_input = this.waitForElement( By.name( getProperty( "new_ticket_description_page.shorttext_input.name" ) ) );
		shorttext_input.sendKeys( shorttext );

		ok_button = this.waitForElement( By.xpath( getPropXTS( "new_ticket_description_page.ok_button.xts.xpath" ) ) );
		ok_button.click();

		this.waitForElementToDisapear( By.xpath( getPropXTS( "new_ticket_description_page.ok_button.xts.xpath" ) ) );
		return new EditTicketPage( getDriver(), pageProperties, xts );
	}

}
