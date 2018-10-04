package com.sometotest.eagent.selenium;

import com.sometotest.eagent.selenium.gmx.EmailPage;
import com.sometotest.eagent.selenium.gmx.HomePage;
import com.sometotest.eagent.selenium.gmx.SignInPage;
import com.sometotest.eagent.selenium.gmx.WriteEmailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SeleniumTest{
	private final static Logger logger = LoggerFactory.getLogger( SeleniumTest.class );

	private String geckodriver_path;

	public SeleniumTest(String geckodriver_path) {
		this.geckodriver_path = geckodriver_path;
	}

	public void writeSeleniumInfos() {

		System.setProperty( "webdriver.gecko.driver", geckodriver_path );

		FirefoxOptions opts = new FirefoxOptions();
		opts.setCapability( "moz:webdriverClick", false );

		WebDriver driver = new FirefoxDriver( opts );

		for ( String key : ((FirefoxDriver)driver).getCapabilities().asMap().keySet() ) {
			logger.info("key: " + key + ", value: " + ((FirefoxDriver)driver).getCapabilities().getCapability( key ));
		}



	}

	public void doSelenium() {

		System.setProperty( "webdriver.gecko.driver", geckodriver_path );

		FirefoxOptions opts = new FirefoxOptions();
		opts.setCapability( "moz:webdriverClick", false );

		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new FirefoxDriver( opts );

		// And now use this to visit Google
		driver.get( "https://www.google.de" );
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

	public void doGMX( String email ) {
		System.setProperty( "webdriver.gecko.driver", geckodriver_path );

		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new FirefoxDriver();

		SignInPage gmx_sign_in_page = new SignInPage( driver );
		HomePage gmx_home_page = gmx_sign_in_page.sign_in( "eAgent@gmx.de", "CAg9mV3JGblq2QCFwInB" );
		EmailPage gmx_email_page = gmx_home_page.switch_to_mail();
		WriteEmailPage gmx_write_email_page = gmx_email_page.write_mail();
		gmx_write_email_page.send_email( email );

		//Close the browser
		driver.quit();

	}


}
