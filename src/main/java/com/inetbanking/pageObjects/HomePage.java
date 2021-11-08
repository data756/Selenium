package com.inetbanking.pageObjects;

import org.openqa.selenium.WebDriver;
import com.inetbanking.base.BasePage;
public class HomePage extends BasePage {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	private String HOMEPAGE_TITLE="xpath=>//title";
	private String LOGOUT_BUTTON="xpath=>//a[@href='Logout.php']";
	private String MANAGER_ID="xpath=>//td[contains(text(),'Manger Id')]";


	public boolean verifyManagerId(String userid) {
		boolean result=false;
		String managerId="";
		managerId=getText(MANAGER_ID, "Manager ID").split(":")[1].trim();
		if(managerId.equalsIgnoreCase(userid)) {
			result=true;
		}
		return result;
	}
	
	public void clickLogOutBtn() {
		elementClickWaitTime(LOGOUT_BUTTON, "Logout Button");
	}

	public boolean loginSuccessful() {
		boolean result = false;
		if (super.verifyPageTitle("Guru99 Bank Manager HomePage")) {
			result = true;
		}
		return result;
	}

}
