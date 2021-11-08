package com.inetbanking.base;

//import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

//import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.inetbanking.utilities.Constants;
//import java.util.logging.Logger;
//import com.inetbanking.utilities.WebEventListeners;

public class BaseClass {
	String baseURL = Constants.BASE_URL;
	public String username = Constants.USERNAME;
	public String password = Constants.PASSWORD;
	public static WebDriver driver;

	@Parameters("browser")
	@BeforeClass
	public void commonSetUp(String br) {
		driver = WebDriverFactory.getInstance().getDriver(br);
		driver.get(baseURL);
	}

	@AfterClass
	public void commonTearDown() throws Exception {
		WebDriverFactory.getInstance().quitDriver();
	}
}
