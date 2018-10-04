package com.sometotest.eagent.selenium.gmx;

import com.sometotest.eagent.selenium.base.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignInPage extends PageObject{

	private String target_url = "https://www.gmx.net";
	private WebElement username_input_field;
	private WebElement password_input_field;
	private WebElement login_submit;

	public SignInPage( WebDriver driver ) {
		super( driver );
		getDriver().get( target_url );

		username_input_field = this.waitForElement( By.id("freemailLoginUsername") );
		password_input_field = this.waitForElement( By.id("freemailLoginPassword") );
		login_submit = this.getDriver().findElement( By.xpath("//input[@value='Login']") );
	}

	public HomePage sign_in(String username, String password) {
		username_input_field.sendKeys( username );
		password_input_field.sendKeys( password );
		login_submit.click();
		return new HomePage( getDriver() );
	}
}
