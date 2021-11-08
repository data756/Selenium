package com.inetbanking.base;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.inetbanking.utilities.Constants;
import com.inetbanking.utilities.ExcelReadExample;

public class WebDriverFactory {
	ExcelReadExample config = new ExcelReadExample();
	// Singelton class
	// only one instance of the class can exist at one time.
	private static final WebDriverFactory instance = new WebDriverFactory();
	private static final Logger log=LogManager.getLogger(WebDriverFactory.class.getName());
	private WebDriverFactory() {

	}

	public static WebDriverFactory getInstance() {
		return instance;
	}

	// ThreadLocal in Java is another way to achieve thread-safety apart from
	// writing immutable classes. Thread local can be considered as a scope of
	// access like session scope or request scope. In thread local, you can set any
	// object and this object will be local and global to the specific thread which
	// is accessing this object.

	// Java ThreadLocal class provides thread-local variables. It enables you to
	// create variables that can only be read and write by the same thread. If two
	// threads are executing the same code and that code has a reference to a
	// ThreadLocal variable then the two threads can't see the local variable of
	// each other.
	// Threadedlocal gives each thread a seperate instance of the given object.If we
	// are running parallel test then each thread has the separate copy by
	// default.Here in our case , each one has the seperate copy of threaded driver.
	// synchronisation issue will be handled by the ThreadLoal automatically.
	private static ThreadLocal<WebDriver> threadedDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<String> threadedBrowser= new ThreadLocal<String>();
	public WebDriver getDriver(String browser) {
		WebDriver driver;
		setDriver(browser);
		threadedBrowser.set(browser);
		if (threadedDriver.get() == null) {
			try {
				if (browser.equalsIgnoreCase(Constants.FIREFOX)) {
					driver = new FirefoxDriver();
					threadedDriver.set(driver);
				} else if (browser.equalsIgnoreCase(Constants.CHROME)) {
					ChromeOptions options = setChromeOptions();
					driver = new ChromeDriver(options);
					threadedDriver.set(driver);
				} else if (browser.equalsIgnoreCase(Constants.INTERNET_EXPLORER)) {
					driver = new InternetExplorerDriver();
					threadedDriver.set(driver);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			threadedDriver.get().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			threadedDriver.get().manage().window().maximize();
		}
		return threadedDriver.get();
	}
	
	public String getBrowser() {
		return threadedBrowser.get();
	}

	public void quitDriver() {
		threadedDriver.get().quit();
		threadedDriver.set(null);
	}

	private void setDriver(String browser) {
		String driverPath = "";
		String os = Constants.OS_NAME.toLowerCase().substring(0, 3);
		log.info("OS name from system property :: " + os);
		String directory = Constants.USER_DIRECTORY + Constants.DRIVERS_DIRECTORY;
		String driverKey = "";
		String driverValue = "";
		if (browser.equalsIgnoreCase(Constants.CHROME)) {
			driverKey = Constants.CHROME_DRIVER_KEY;
			driverValue = Constants.CHROME_DRIVER_VALUE;
		} else if (browser.equalsIgnoreCase(Constants.INTERNET_EXPLORER)) {
			driverKey = Constants.INTERNET_EXPLORER_KEY;
			driverValue = Constants.INTERNET_EXPLORER_VALUE;
		} else {
			log.info("Browser Type Not Supported");
		}
		driverPath = directory + driverValue + (os.equals("win") ? ".exe" : "");
		log.info("Driver Binary :: " + driverPath);
		System.setProperty(driverKey, driverPath);
	}

	private ChromeOptions setChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.addArguments("disable-infobars");
		return options;
	}
}
