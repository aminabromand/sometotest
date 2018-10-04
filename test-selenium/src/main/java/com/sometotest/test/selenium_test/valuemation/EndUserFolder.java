package com.sometotest.test.selenium_test.valuemation;

import com.sometotest.test.selenium_test.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Properties;

public class EndUserFolder extends ValuemationPage{

	private WebElement create_ticket_button;
	private WebElement close_folder_button;
	private WebElement list_tickets_button;

	private WebElement frame1;

	public EndUserFolder( WebDriver driver, Properties pageProperties, String xts ) {
		super( driver, pageProperties, xts );
		frame1 = this.waitForElement( By.xpath( getPropXTS( "homepage.frame1.xts.xpath" ) ) );
		this.getDriver().switchTo().frame( frame1 );
		close_folder_button = this.waitForElement( By.xpath( getProperty( "end_user_folder.close_folder_button.xpath" ) ) );
		create_ticket_button = this.waitForElement( By.xpath( getProperty( "end_user_folder.create_ticket_button.xpath" ) ) );
		this.getDriver().switchTo().defaultContent();
	}

	public CreateTicketPage create_ticket() {
		this.getDriver().switchTo().frame( frame1 );
		create_ticket_button.click();
		return new CreateTicketPage( getDriver(), pageProperties, xts );
	}

	public TicketCatalogPage list_tickets() {
		this.getDriver().switchTo().frame( frame1 );
		list_tickets_button = this.waitForElement( By.xpath( getProperty( "end_user_folder.list_tickets_button.xpath" ) ) );
		list_tickets_button.click();
		return new TicketCatalogPage( getDriver(), pageProperties, xts );
	}

	public HomePage close_folder(){
		this.getDriver().switchTo().frame( frame1 );
		close_folder_button.click();
		return new HomePage( getDriver(), pageProperties, xts );
	}
}