package com.sometotest.test.selenium_test.valuemation;

import com.sometotest.test.selenium_test.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class EditTicketPage extends ValuemationPage {

	private WebElement ok_button;
	private WebElement new_description_button;

	public EditTicketPage( WebDriver driver, Properties pageProperties, String xts ) {
		super( driver, pageProperties, xts );
	}

	public NewTicketDescriptionPage create_description() {
		new_description_button = this.waitForElement( By.xpath( getPropXTS( "edit_ticket_page.new_description_button.xts.xpath" ) ) );
		new_description_button.click();
		return new NewTicketDescriptionPage( getDriver(), pageProperties, xts );
	}

	public TicketCatalogPage save_ticket() throws InterruptedException{
		TimeUnit.MILLISECONDS.sleep( 2000 );
		ok_button = this.waitForElement( By.xpath( getPropXTS( "edit_ticket_page.ok_button.xts.xpath" ) ) );
		ok_button.click();
		return new TicketCatalogPage( getDriver(), pageProperties, xts );
	}
}
