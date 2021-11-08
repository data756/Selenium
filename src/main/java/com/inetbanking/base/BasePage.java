package com.inetbanking.base;
import com.inetbanking.utilities.Util;

import org.openqa.selenium.WebDriver;

public class BasePage extends CustomDriver {
	WebDriver driver;
	
	public BasePage(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	public boolean verifyPageTitle(String expectedTitle) {
		return Util.verifyTextMatch(driver.getTitle(), expectedTitle);
	}
	
	public String getCurrentPageURL() {
		return driver.getCurrentUrl();
	}
}
