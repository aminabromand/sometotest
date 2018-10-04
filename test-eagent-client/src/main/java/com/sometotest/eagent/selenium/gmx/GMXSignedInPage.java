package com.sometotest.eagent.selenium.gmx;

import com.sometotest.eagent.selenium.base.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GMXSignedInPage extends PageObject{

	private String logout_button_path = "(//POS-SVG-ICON[@class='pos-icon-item__icon pos-svg-icon'])[12]";
	private WebElement logout_button;

	public GMXSignedInPage( WebDriver driver ) {
		super( driver );

		logout_button = this.waitForElement(By.xpath( logout_button_path ));
	}

	public void logout() {
		logout_button.click();
	}
}
