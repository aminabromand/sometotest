package com.sometotest.test.selenium_test.valuemation;

import com.sometotest.test.selenium_test.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TicketCatalogPage extends ValuemationPage{

	private WebElement ticket_no_input;
	private WebElement search_button;
	private WebElement ticket_button;
	private WebElement close_catalog_button;

	private WebElement ticket_list_table;
	private List<WebElement> ticket_list;
	private WebElement ticket;
	private WebElement ticket_name_element;

	public TicketCatalogPage( WebDriver driver, Properties pageProperties, String xts ) {
		super( driver, pageProperties, xts );
	}

	public TicketCatalogPage search_ticket( String ticket_number ) throws InterruptedException{
		TimeUnit.MILLISECONDS.sleep( 2000 );
		ticket_no_input = this.waitForElement( By.name( getProperty( "ticket_catalog_page.ticket_no_input.name" ) ) );
		ticket_no_input.clear();
		ticket_no_input.sendKeys( ticket_number );
		search_button = this.waitForElement( By.xpath( getProperty( "ticket_catalog_page.search_button.xpath" ) ) );
		search_button.click();
		return this;
	}

	public EditTicketPage open_ticket( String ticket_number ) {
		ticket_button = this.waitForElement( By.xpath(
						getProperty( "ticket_catalog_page.ticket_button.part1.xpath" )
						+ ticket_number
						+ getProperty( "ticket_catalog_page.ticket_button.part2.xpath") ) );

		Actions action = new Actions( getDriver() );
		action.moveToElement( ticket_button ).doubleClick().perform();
		return new EditTicketPage( getDriver(), pageProperties, xts );
	}

	public TicketCatalogPage list_tickets_by_date( String ticket_change_date ) throws InterruptedException{
		TimeUnit.MILLISECONDS.sleep( 2000 );
		ticket_no_input = this.waitForElement( By.name( getProperty( "ticket_catalog_page.ticket_change_date_input.name" ) ) );
		ticket_no_input.clear();
		ticket_no_input.sendKeys( ticket_change_date );
		search_button = this.waitForElement( By.xpath( getProperty( "ticket_catalog_page.search_button.xpath" ) ) );
		search_button.click();
		TimeUnit.MILLISECONDS.sleep( 2000 );

		ticket_list_table = this.waitForElement( By.xpath( getProperty( "ticket_catalog_page.ticket_list_table.xpath" ) ) );

		ticket_list = ticket_list_table.findElements( By.xpath( getProperty( "ticket_catalog_page.ticket_list.xpath" ) ) );
		return this;
	}

	public TicketCatalogPage process_ticket_list() throws InterruptedException{
		for ( WebElement ticket : ticket_list ){
			TimeUnit.MILLISECONDS.sleep( 2000 );
			ticket_name_element = ticket.findElement( By.xpath( getProperty( "ticket_catalog_page.ticket.xpath" ) ) );
			String ticket_name = ticket_name_element.getText();
			open_ticket( ticket_name ).save_ticket();
		}
		return this;
	}

	public EndUserFolder close_catalog() throws InterruptedException{
		TimeUnit.MILLISECONDS.sleep( 2000 );
		close_catalog_button = this.waitForElement( By.xpath( getPropXTS( "ticket_catalog_page.close_window_button.xts.xpath" ) ) );
		close_catalog_button.click();
		return new EndUserFolder( getDriver(), pageProperties, xts );
	}

}
