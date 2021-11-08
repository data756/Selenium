package com.inetbanking.utilities;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;



public class WebEventListeners implements WebDriverEventListener{
	
	private Logger log;
	 
	public WebEventListeners (Logger log) {
		this.log=log;
	}
	
	public void setLoggerForCurrentTestCase(Logger log) {
		this.log=log;
	}
	
	@Override
	public void beforeAlertAccept(WebDriver driver) {
		log.info("Before Alert Accept");
		
	}

	@Override
	public void afterAlertAccept(WebDriver driver) {
		log.info("After Alert Accept");
		
	}

	@Override
	public void afterAlertDismiss(WebDriver driver) {
		log.info("Before Alert Dismissed");
		
	}

	@Override
	public void beforeAlertDismiss(WebDriver driver) {
		log.info("After Alert Dismiss");
		
	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		log.info("Before navigating to: '" + url + "'");
		
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
		log.info("After navigating to: '" + url + "'");
		
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		log.info("Before navigate back");
		
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		log.info("After navigation to back page");
		
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		log.info("Before navigating to Forward page");
		
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		log.info("After navigating to Forward page");
		
	}

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
		log.info("Before page refresh");
		
	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		log.info("After page is refreshed");
		
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		log.info("Trying to find Element By : " + by.toString());
		
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		log.info("Found Element By : " + by.toString());
		
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		log.info("Before Clicking on " + element.toString());
		
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		log.info("After Clicking on " + element.toString());
		
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeGetText(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterGetText(WebElement element, WebDriver driver, String text) {
		// TODO Auto-generated method stub
		
	}

}
