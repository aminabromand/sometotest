package com.sometotest.eagent.selenium.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PageObject{

	protected Properties pageProperties;
	private WebDriver driver;

	public PageObject(WebDriver driver) {
		this.driver = driver;
		this.getDriver().switchTo().defaultContent();
	}

	public PageObject( WebDriver driver, Properties properties ) {
		this( driver );
		pageProperties = properties;
	}

	public WebElement waitForElement( By selector ) {
		WebDriverWait wait = new WebDriverWait( getDriver(), 20 );
		return wait.until( ExpectedConditions.elementToBeClickable( selector ) );
	}

	public WebElement waitForElementVisible( By selector ) {
		WebDriverWait wait = new WebDriverWait( getDriver(), 20 );
		return wait.until( ExpectedConditions.visibilityOfElementLocated( selector ) );
	}

	public Boolean waitForElementToDisapear( By selector ) {
		WebDriverWait wait = new WebDriverWait( getDriver(), 20 );
		return wait.until( ExpectedConditions.invisibilityOfElementLocated( selector ) );
	}

	public WebElement waitForElementToBePresent( By selector ) {
		WebDriverWait wait = new WebDriverWait( getDriver(), 20 );
		return wait.until( ExpectedConditions.presenceOfElementLocated( selector ) );
	}

	public WebDriver getDriver() {
		return driver;
	}

	public Properties loadProperties( String propertiesLocation ){
		pageProperties = new Properties();
		InputStream properties_input_stream = this.getClass().getResourceAsStream( propertiesLocation );
		try{
			pageProperties.load( properties_input_stream );
			return pageProperties;
		} catch( IOException ioex ) {
			return null;
		}
	}

	protected String getProperty( String key ) {
		return pageProperties.getProperty( key );
	}

}
