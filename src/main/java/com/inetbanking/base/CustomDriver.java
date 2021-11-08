package com.inetbanking.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.inetbanking.utilities.Util;

public class CustomDriver {

	public WebDriver driver;
	private JavascriptExecutor js;
	private static final Logger log = LogManager.getLogger(CustomDriver.class.getName());

	public CustomDriver(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
	}

	public void refresh() {
		driver.navigate().refresh();
		log.info("The current browser location was refreshed");
	}

	public String getTitle() {
		String title = driver.getTitle();
		log.info("The title of page is:- " + title);
		return title;
	}

	public String getCurrentUrl() {
		String currentURL;
		currentURL = driver.getCurrentUrl();
		return currentURL;
	}

	public void navigateBrowserBack() {
		driver.navigate().back();
		log.info("Browser has been navigated to previous page");
	}

	public void navigateBrowserForward() {
		driver.navigate().back();
		log.info("Browser has been navigated to next page");
	}

	public List<WebElement> getElementList(String locator, String info) {
		List<WebElement> elementList = new ArrayList<WebElement>();
		By byType = getByType(locator);
		try {
			elementList = driver.findElements(byType);
			log.info("Elemets " + info + " Found With: " + locator);
		} catch (Exception e) {
			log.error("Elemets " + info + "Not Found With: " + locator);
			e.printStackTrace();
		}
		return elementList;

	}

	public By getByType(String locator) {
		By by = null;
		String locatorType = locator.split("=>")[0];
		locator = locator.split("=>")[1];
		try {
			if (locatorType.contains("id")) {
				by = By.id(locator);
			} else if (locatorType.contains("xpath")) {
				by = By.xpath(locator);
			} else if (locatorType.contains("class")) {
				by = By.className(locator);
			} else if (locatorType.contains("name")) {
				by = By.name(locator);
			} else if (locatorType.contains("tagName")) {
				by = By.tagName(locator);
			} else if (locatorType.contains("link")) {
				by = By.linkText(locator);
			} else if (locatorType.contains("partiallink")) {
				by = By.partialLinkText(locator);
			} else {
				log.info("Locator Type is not supported");
			}
		} catch (Exception e) {
			log.error("By type not found with: " + locatorType);
		}
		return by;
	}

	public WebElement getElement(String locator, String Info) {
		WebElement element = null;
		By byType = getByType(locator);
		try {
			element = driver.findElement(byType);
			log.info("Elemet " + Info + " Found With: " + locator);
		} catch (Exception e) {
			log.error("Elemet " + Info + "Not Found With: " + locator);
			e.printStackTrace();
		}
		return element;
	}

	public boolean isElementPresent(String locator, String info) {
		List<WebElement> elementList = getElementList(locator, info);
		int size = elementList.size();
		if (size > 0) {
			return true;
		} else {
			return false;
		}

	}

	public void elementClickWaitTime(WebElement element, String info, long timeToWait) {

		try {
			element.click();
			if (timeToWait == 0) {
				log.info("Click on :: " + info);
			} else {
				Util.sleep(timeToWait, "Click on :: " + info);
			}
		} catch (Exception e) {
			log.error("Can not Click on :: " + info);
			takeScreenshot("Click ERROR", "");
		}
	}

	public void elementClickWaitTime(WebElement element, String info) {
		elementClickWaitTime(element, info, 0);
	}

	public void elementClickWaitTime(String locator, String info, long timeToWait) {
		WebElement element = this.getElement(locator, info);
		elementClickWaitTime(element, info, timeToWait);
	}

	public void elementClickWaitTime(String locator, String info) {
		WebElement element = this.getElement(locator, info);
		elementClickWaitTime(element, info);
	}

	public void javascriptClick(String locator, String info) {
		WebElement element = getElement(locator, info);
		try {
			js.executeScript("arguments[0].click();", element);
			log.info("Clicked on :: " + info);
		} catch (Exception e) {
			log.error("Can not click on :: " + info);
		}
	}

	public void clickWhenReady(By locator, int timeout) {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			WebElement element = null;
			log.info("Waiting for max:: " + timeout + " seconds for element to be clickable");

			WebDriverWait wait = new WebDriverWait(driver, 15);
			element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			element.click();
			log.info("Element clicked on the Web Page");
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("Element not appeared on the Web Page");
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		}
	}

	public void sendData(WebElement element, String data, String info, Boolean clear) {
		try {
			if (clear) {
				element.clear();
			}
			element.sendKeys(data);
			log.info("Sent keys on element:: " + info + " with data ::" + data);
		} catch (Exception e) {
			log.error("Can not send keys on element:: " + info + " with data ::" + data);
		}
	}

	public void sendData(String locator, String data, String info, Boolean clear) {
		WebElement element = getElement(locator, info);
		sendData(element, data, info, clear);
	}

	public void sendData(WebElement element, String data, String info) {
		sendData(element, data, info, true);
	}

	public void sendData(String locator, String data, String info) {
		sendData(locator, data, info, true);
	}

	public String getText(WebElement element, String info) {
		log.info("Getting Text on Element :: " + info);
		String text = null;
		text = element.getText();
		if (text.length() == 0) {
			text = element.getAttribute("innerText");
		}
		if (!text.isEmpty()) {
			log.info("The text is ::" + text);
		} else {
			text = "NOT_FOUND";
		}
		return text.trim();
	}

	public String getText(String locator, String info) {
		WebElement element = getElement(locator, info);
		return getText(element, info);

	}

	public boolean isEnabled(WebElement element, String info) {
		boolean enabled = false;
		if (element != null) {
			enabled = element.isEnabled();
			if (enabled) {
				log.info("Element :: " + info + " is enabled");
			} else {
				log.info("Element :: " + info + " is disabled ");
			}
		}
		return enabled;
	}

	public boolean isEnabled(String locator, String info) {
		WebElement element = getElement(locator, info);
		return this.isEnabled(element, info);
	}

	public boolean isDisplayed(WebElement element, String info) {
		boolean displayed = false;
		if (element != null) {
			displayed = element.isDisplayed();
			if (displayed) {
				log.info("Element :: " + info + " is dispplay");
			} else {
				log.info("Element :: " + info + " is not displayed");
			}
		}
		return displayed;
	}

	public boolean isDisplayed(String locator, String info) {
		WebElement element = getElement(locator, info);
		return this.isDisplayed(element, info);
	}

	public boolean isSelected(WebElement element, String info) {
		boolean selected = false;
		if (element != null) {
			selected = element.isDisplayed();
			if (selected) {
				log.info("Element :: " + info + " is selected");
			} else {
				log.info("Element :: " + info + " is not selected");
			}
		}
		return selected;
	}

	public boolean isSelected(String locator, String info) {
		WebElement element = getElement(locator, info);
		return this.isSelected(element, info);
	}

	/**
	 * Selects a check box irrespective of its state
	 *
	 * @param element
	 * @param info
	 */
	public void Check(WebElement element, String info) {
		if (!isSelected(element, info)) {
			elementClickWaitTime(element, info);
			log.info("Element :: " + info + " is checked");
		} else
			log.info("Element :: " + info + " is already checked");
	}

	/**
	 * Selects a check box irrespective of its state, using locator
	 *
	 * @param locator
	 * @param info
	 * @return
	 */
	public void Check(String locator, String info) {
		WebElement element = getElement(locator, info);
		Check(element, info);
	}

	/**
	 * UnSelects a check box irrespective of its state
	 *
	 * @param element
	 * @param info
	 * @return
	 */
	public void UnCheck(WebElement element, String info) {
		if (isSelected(element, info)) {
			elementClickWaitTime(element, info);
			log.info("Element :: " + info + " is unchecked");
		} else
			log.info("Element :: " + info + " is already unchecked");
	}

	/**
	 * UnSelects a check box irrespective of its state, using locator
	 *
	 * @param locator
	 * @param info
	 * @return
	 */
	public void UnCheck(String locator, String info) {
		WebElement element = getElement(locator, info);
		UnCheck(element, info);
	}

	/**
	 * @param element
	 * @param info
	 * @return
	 */
	public Boolean Submit(WebElement element, String info) {
		if (element != null) {
			element.submit();
			System.out.println("Element :: " + info + " is submitted");
			return true;
		} else
			return false;
	}

	/**
	 * @param locator
	 * @param attribute
	 * @return
	 */
	public String getElementAttributeValue(String locator, String attribute) {
		WebElement element = getElement(locator, "info");
		return element.getAttribute(attribute);
	}

	/**
	 * @param element
	 * @param attribute
	 * @return
	 */
	public String getElementAttributeValue(WebElement element, String attribute) {
		return element.getAttribute(attribute);
	}

	/**
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElement(String locator, int timeout) {
		By byType = getByType(locator);
		WebElement element = null;
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			System.out.println("Waiting for max:: " + timeout + " seconds for element to be available");
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(byType));
			System.out.println("Element appeared on the web page");
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Element not apeared on the web page");
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		}
		return element;
	}

	/***
	 * Wait for element to be clickable
	 * 
	 * @param locator - locator strategy, id=>example, name=>example, css=>#example,
	 *                * tag=>example, xpath=>//example, link=>example
	 * @param timeout - Duration to try before timeout
	 */
	public WebElement waitForElementToBeClickable(String locator, int timeout) {
		By byType = getByType(locator);
		WebElement element = null;
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			System.out.println("Waiting for max:: " + timeout + " seconds for element to be clickable");

			WebDriverWait wait = new WebDriverWait(driver, 15);
			element = wait.until(ExpectedConditions.elementToBeClickable(byType));
			System.out.println("Element is clickable on the web page");
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Element not appeared on the web page");
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		}
		return element;
	}

	/**
	 *
	 */
	public boolean waitForLoading(String locator, long timeout) {
		By byType = getByType(locator);
		boolean elementInvisible = false;
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			System.out.println("Waiting for max:: " + timeout + " seconds for element to be available");
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			elementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(byType));
			System.out.println("Element appeared on the web page");
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Element not appeared on the web page");
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		}
		return elementInvisible;
	}

	/**
	 * Mouse Hovers to an element
	 *
	 * @param locator
	 */
	public void mouseHover(String locator, String info) {
		WebElement element = getElement(locator, info);
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		// Util.sleep(5000);
	}

	/**
	 * @param element
	 * @param optionToSelect
	 */
	public void selectOption(WebElement element, String optionToSelect) {
		Select sel = new Select(element);
		sel.selectByVisibleText(optionToSelect);
		System.out.println("Selected option : " + optionToSelect);
	}

	/**
	 * Selects a given option in list box
	 *
	 * @param locator
	 * @param optionToSelect
	 */
	public void selectOption(String locator, String optionToSelect, String info) {
		WebElement element = getElement(locator, info);
		this.selectOption(element, optionToSelect);
	}

	/**
	 * get Selected drop down value
	 *
	 * @param element
	 * @return
	 */
	public String getSelectDropDownValue(WebElement element) {
		Select sel = new Select(element);
		return sel.getFirstSelectedOption().getText();
	}

	/**
	 * @param element
	 * @param optionToVerify
	 */
	public boolean isOptionExists(WebElement element, String optionToVerify) {
		Select sel = new Select(element);
		boolean exists = false;
		List<WebElement> optList = sel.getOptions();
		for (int i = 0; i < optList.size(); i++) {
			String text = getText(optList.get(i), "Option Text");
			if (text.matches(optionToVerify)) {
				exists = true;
				break;
			}
		}
		if (exists) {
			System.out.println("Selected Option : " + optionToVerify + " exist");
		} else {
			System.out.println("Selected Option : " + optionToVerify + " does not exist");
		}
		return exists;
	}

	/***
	 *
	 * @param methodName
	 * @param browserName
	 * @return
	 */
	public String takeScreenshot(String methodName, String browserName) {
		String fileName = Util.getScreenshotName(methodName, browserName);
		String screenshotDir = System.getProperty("user.dir") + "//" + "test-output/screenshots";
		new File(screenshotDir).mkdirs();
		String path = screenshotDir + "//" + fileName;

		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path));
			System.out.println("Screen Shot Was Stored at: " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public void DoubleClick(WebElement element, String info) {
		Actions action = new Actions(driver);
		action.doubleClick(element);
		System.out.println("Double Clicked on :: " + info);
		action.perform();
	}

	/**
	 * Right Click a WebElement
	 *
	 * @param locator
	 */
	public void rightClick(String locator, String info) {
		WebElement element = getElement(locator, info);
		Actions action = new Actions(driver);
		action.contextClick(element).build().perform();
		System.out.println("Double Clicked on :: " + info);
	}

	/**
	 * Right click a WebElement and select the option
	 *
	 * @param elementLocator
	 * @param itemLocator
	 */
	public void selectItemRightClick(String elementLocator, String itemLocator) {
		WebElement element = getElement(elementLocator, "info");
		Actions action = new Actions(driver);
		action.contextClick(element).build().perform();
		WebElement itemElement = getElement(itemLocator, "info");
		elementClickWaitTime(itemElement, "Selected Item");
	}

	/**
	 * @param key
	 */
	public void keyPress(Keys key, String info) {
		Actions action = new Actions(driver);
		action.keyDown(key).build().perform();
		System.out.println("Key Pressed :: " + info);
	}
}