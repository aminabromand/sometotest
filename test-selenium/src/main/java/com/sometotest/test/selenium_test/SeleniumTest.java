package com.sometotest.test.selenium_test;

import com.sometotest.test.selenium_test.desktop.TestPage;
import com.sometotest.test.selenium_test.gmx.EmailPage;
import com.sometotest.test.selenium_test.gmx.WriteEmailPage;
import com.sometotest.test.selenium_test.valuemation.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SeleniumTest{

	private String target_url;

	public SeleniumTest(String target_url) {
		this.target_url = target_url;
	}

	public void writeSeleniumInfos() {

		System.setProperty("webdriver.gecko.driver","/Users/amin/Documents/geckodriver/geckodriver");

		FirefoxOptions opts = new FirefoxOptions();
		opts.setCapability( "moz:webdriverClick", false );

		WebDriver driver = new FirefoxDriver( opts );

		for ( String key : ((FirefoxDriver)driver).getCapabilities().asMap().keySet() ) {
			System.out.println("key: " + key + ", value: " + ((FirefoxDriver)driver).getCapabilities().getCapability( key ));
		}
	}

	public void doSelenium() {

		System.setProperty("webdriver.gecko.driver","/Users/amin/Documents/geckodriver/geckodriver");

		FirefoxOptions opts = new FirefoxOptions();
		opts.setCapability( "moz:webdriverClick", false );

		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new FirefoxDriver( opts );

		// And now use this to visit Google
		driver.get(target_url);
		// Alternatively the same thing can be done like this
		// driver.navigate().to("http://www.google.com");

		// Find the text input element by its name
		WebElement element = driver.findElement(By.name("q"));

		// Enter something to search for
		element.sendKeys("Cheese!");

		// Now submit the form. WebDriver will find the form for us from the element
		element.submit();

		// Check the title of the page
		System.out.println("Page title is: " + driver.getTitle());

		// Google's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("cheese!");
			}
		});

		// Should see: "cheese! - Google Search"
		System.out.println("Page title is: " + driver.getTitle());

		//Close the browser
		driver.quit();
	}

	public void doGMX() {
		System.setProperty("webdriver.gecko.driver","/Users/amin/Documents/geckodriver/geckodriver");

		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new FirefoxDriver();

		com.sometotest.test.selenium_test.gmx.SignInPage gmx_sign_in_page = new com.sometotest.test.selenium_test.gmx.SignInPage( driver );
		com.sometotest.test.selenium_test.gmx.HomePage gmx_home_page = gmx_sign_in_page.sign_in( "abromand@gmx.de", "pOs110end!" );
		EmailPage gmx_email_page = gmx_home_page.switch_to_mail();
		WriteEmailPage gmx_write_email_page = gmx_email_page.write_mail();
		gmx_write_email_page.send_email( "amin.abromand@metaproc.com" );

		//Close the browser
		driver.quit();

	}

	public void doValuemation() {
		System.setProperty("webdriver.gecko.driver","/Users/amin/Documents/geckodriver/geckodriver");

		FirefoxOptions opts = new FirefoxOptions();
		opts.setCapability( "moz:webdriverClick", false );

		Random rand = new Random();
		int  n = rand.nextInt(500000000 ) + 1;

		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new FirefoxDriver( opts );

		try{
			SignInPage valuemationSignInPage = new SignInPage( driver );
			HomePage valuemationHomePage = valuemationSignInPage.sign_in( "vm", "mv" );


			EndUserFolder valuemationEndUserFolder = valuemationHomePage.switch_to_end_user();
			CreateTicketPage valuemationCreateTicketPage = valuemationEndUserFolder.create_ticket();

	//		valuemation.getHomePage().switch_to_end_user();
	//		valuemation.getEndUserFolder().create_ticket();


			valuemationCreateTicketPage.fill_ticket( "test ticket #" + n, "this is a test ticket!" );
			valuemationCreateTicketPage.test_upload();

			valuemationEndUserFolder = valuemationCreateTicketPage.save_ticket();
			valuemationHomePage = valuemationEndUserFolder.close_folder();
			valuemationHomePage.logout();

		} catch(InterruptedException e){
			e.printStackTrace();
		} finally{
			//Close the browser
			driver.quit();
		}
	}

	public void doValuemationChange( String shorttext, String description) {
		System.setProperty("webdriver.gecko.driver","/Users/amin/Documents/geckodriver/geckodriver");

		FirefoxOptions opts = new FirefoxOptions();
		opts.setCapability( "moz:webdriverClick", false );

		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new FirefoxDriver( opts );

		try{
			SignInPage valuemationSignInPage = new SignInPage( driver );
			HomePage valuemationHomePage = valuemationSignInPage.sign_in( "vm", "mv" );


			EndUserFolder valuemationEndUserFolder = valuemationHomePage.switch_to_end_user();
			TicketCatalogPage valuemationTicketCatalogPage = valuemationEndUserFolder.list_tickets();

			String ticket_number = "IN-0000001";
			valuemationHomePage = valuemationTicketCatalogPage
							.search_ticket( ticket_number )
							.open_ticket( ticket_number )
							.create_description()
							.fill_and_save( shorttext, description )
							.save_ticket()
							.close_catalog()
							.close_folder();

			valuemationHomePage.logout();

		} catch(InterruptedException e){
			e.printStackTrace();
		} finally{
			//Close the browser
			//driver.quit();
		}
	}

	public void doValuemationProcessList() {
		System.setProperty("webdriver.gecko.driver","/Users/amin/Documents/geckodriver/geckodriver");

		FirefoxOptions opts = new FirefoxOptions();
		opts.setCapability( "moz:webdriverClick", false );

		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new FirefoxDriver( opts );

		try{
			SignInPage valuemationSignInPage = new SignInPage( driver );
			HomePage valuemationHomePage = valuemationSignInPage.sign_in( "vm", "mv" );


			EndUserFolder valuemationEndUserFolder = valuemationHomePage.switch_to_end_user();
			TicketCatalogPage valuemationTicketCatalogPage = valuemationEndUserFolder.list_tickets();

			String last_changed_date = "07/26/2018 17:08:00";
			valuemationHomePage = valuemationTicketCatalogPage
							.list_tickets_by_date( last_changed_date )
							.process_ticket_list()
							.close_catalog()
							.close_folder();

			valuemationHomePage.logout();

		} catch(InterruptedException e){
			e.printStackTrace();
		} finally{
			//Close the browser
			//driver.quit();
		}
	}

	public void doDesktopTest() {
		System.setProperty( "webdriver.gecko.driver", "/Users/amin/Documents/geckodriver/geckodriver" );

		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new FirefoxDriver();

		TestPage testPage = new TestPage( driver );
		testPage.test();

		//Close the browser
		driver.quit();

	}

}
