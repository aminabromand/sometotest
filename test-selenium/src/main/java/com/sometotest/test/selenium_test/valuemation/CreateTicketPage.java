package com.sometotest.test.selenium_test.valuemation;

import com.sometotest.test.selenium_test.selenium.PageObject;
import org.openqa.selenium.*;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CreateTicketPage extends ValuemationPage{
	private WebElement ok_button;
	private WebElement cancel_button;
	private WebElement short_text;
	private WebElement description;
	private WebElement ticket_type;
	private WebElement impact;
	private WebElement assign_system_button;
	private WebElement attach_file_button;
	private WebElement cancel_attach_file_button;
	private WebElement file_input;

	private WebElement ticket_type_selection;
	private WebElement impact_selection;

	private WebElement modal_loading_screen;

	private WebElement frame1;

	public CreateTicketPage( WebDriver driver, Properties pageProperties, String xts ) {
		super( driver, pageProperties, xts );
		impact = this.waitForElement( By.id( getProperty( "create_ticket_page.impact.id" ) ) );
		// assign_system_button = this.waitForElement( By.xpath( getProperty( "create_ticket_page.assign_system_button" ) ) );
		// attach_file_button = this.waitForElement( By.xpath( getProperty( "create_ticket_page.attach_file_button" ) ) );
	}

	public void fill_ticket(String short_text, String description) {

		this.frame1 = this.waitForElement( By.xpath( getProperty( "create_ticket_page.frame1" ) ) );
		this.getDriver().switchTo().frame( frame1 );
		this.description = this.waitForElement( By.xpath( getProperty( "create_ticket_page.frame1.description.xpath" ) ) );
		this.description.sendKeys( description );
		this.getDriver().switchTo().defaultContent();

		this.short_text = this.waitForElement( By.name( getProperty( "create_ticket_page.short_text.name" ) ) );
		this.short_text.sendKeys( short_text );
		ticket_type = this.waitForElement( By.xpath( getProperty( "create_ticket_page.ticket_type.xpath" ) ) );
		ticket_type.click();
		ticket_type_selection = this.waitForElement( By.xpath( getProperty( "create_ticket_page.ticket_type.disturbance.xpath" ) ) );
		ticket_type_selection.click();


	}

	public EndUserFolder save_ticket()  throws InterruptedException {
		ok_button = this.waitForElement( By.xpath( getPropXTS( "create_ticket_page.ok_button.xts.xpath" ) ) );
		ok_button.click();
		return new EndUserFolder( getDriver(), pageProperties, xts );
	}

	public EndUserFolder cancel_ticket() {
		cancel_button = this.waitForElement( By.xpath( getProperty( "create_ticket_page.cancel_button.xpath" ) ) );
		System.out.println( "cancel ticket" );
		cancel_button.click();
		return new EndUserFolder( getDriver(), pageProperties, xts );
	}

	public void test_upload() throws InterruptedException{
		attach_file_button = this.waitForElement( By.xpath( getProperty( "create_ticket_page.add_file_button.xpath" ) ) );
		attach_file_button.click();

//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException ex) {
//			System.out.println("OMG");
//		}
//		Set<Cookie> cookies = getDriver().manage().getCookies();
//		System.out.println("Size: " + cookies.size());
//
//		Iterator<Cookie> itr = cookies.iterator();
//		while (itr.hasNext()) {
//			Cookie cookie = itr.next();
//			System.out.println(cookie.getName() + "\n" + cookie.getPath()
//							+ "\n" + cookie.getDomain() + "\n" + cookie.getValue()
//							+ "\n" + cookie.getExpiry());
//		}
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException ex) {
//			System.out.println("OMG");
//		}

		file_input = this.waitForElementToBePresent( By.xpath( getProperty( "attach_file_page.file_input.xpath" ) ) );
//		if (getDriver() instanceof JavascriptExecutor) {
//			((JavascriptExecutor)getDriver()).executeScript( "document.getElementsByClassName('gwt-FileUpload')[0].style.display='inline';" );
//		} else {
//			throw new IllegalStateException("This driver does not support JavaScript!");
//		}
//		file_input = this.waitForElement( By.xpath( getProperty( "attach_file_page.file_input.xpath" ) ) );
		file_input.sendKeys( "/Users/amin/Desktop/test.png" );

		TimeUnit.MILLISECONDS.sleep( 200 );
		this.waitForElementToDisapear( By.xpath( getProperty( "attach_file_page.upload_file_dialog.xpath" ) ) );

		TimeUnit.MILLISECONDS.sleep( 200 );
		this.waitForElementToDisapear( By.xpath( getProperty( "create_ticket_page.modal_loading_screen.xpath" ) ) );
	}
}
