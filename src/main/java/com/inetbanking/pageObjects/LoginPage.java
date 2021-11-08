package com.inetbanking.pageObjects;

import org.openqa.selenium.WebDriver;
import com.inetbanking.base.BasePage;

public class LoginPage extends BasePage{
	
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	WebDriver driver;
	private String USERNAME_FIELD="name=>uid";
	private String PASSWORD_FIELD="name=>password";
	private String LOGIN_BUTTON="name=>btnLogin";


	public void sendUsername(String uname) {
		sendData(USERNAME_FIELD, uname, " Username Field  ");
	}

	public void sendPassword(String password) {
		sendData(PASSWORD_FIELD, password, " Password Field ");
	}

	public void clickOnLoginBtn() {
		elementClickWaitTime(LOGIN_BUTTON, " Login Button ");
	}

	public HomePage signInWith(String username, String password) {
		sendUsername(username);
		sendPassword(password);
		clickOnLoginBtn();
		return new HomePage(driver); // return the object of next page
	}
}
