package com.sometotest.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumTest{

	public void writeSeleniumInfos() {

		System.setProperty("webdriver.gecko.driver","/Users/amin/Documents/geckodriver/geckodriver");

		FirefoxOptions opts = new FirefoxOptions();
		opts.setCapability( "moz:webdriverClick", false );

		WebDriver driver = new FirefoxDriver( opts );

		for ( String key : ((FirefoxDriver)driver).getCapabilities().asMap().keySet() ) {
			System.out.println("key: " + key + ", value: " + ((FirefoxDriver)driver).getCapabilities().getCapability( key ));
		}
	}

}
