package com.inetbanking.base;

import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.inetbanking.utilities.ExtentManager;

public class TestListeners extends BaseClass implements ITestListener {

	private static ExtentReports extentReports = ExtentManager.getInstance();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	private static final Logger log = LogManager.getLogger(TestListeners.class.getName());

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		log.info("OnStart -> Test Tag Name: " + context.getName());
		ITestNGMethod methods[] = context.getAllTestMethods();
		log.info("This method will be executed in this <test> tag");
		for (ITestNGMethod method : methods) {
			log.info(method);
		}

	}

	@Override
	public void onFinish(ITestContext context) {
		log.info("OnFinish-> Test Tag Name: " + context.getName());
		extentReports.flush();

	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ExtentTest test = extentReports
				.createTest(result.getInstanceName() + " :: " + result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		log.info("onTestSuccess->Test Method Name: " + result.getName());
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test Method" + methodName + " Successful" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extentTest.get().log(Status.PASS, m);

	}

	@Override
	public void onTestFailure(ITestResult result) {
		log.info("onTestFailure->Test Method Name: " + result.getName());
		String methodName = result.getMethod().getMethodName();
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		extentTest.get()
				.fail("<details>" + "<summary>" + "<b>" + "<font color=red>"
						+ "Exception Occurred: Click to see details: " + "<font>" + "</b>" + "</summary>"
						+ exceptionMessage.replaceAll(",", "<br>") + "</details>" + " \n");
		String browser=WebDriverFactory.getInstance().getBrowser();
		WebDriver driver=WebDriverFactory.getInstance().getDriver(browser);
		CustomDriver cd= new CustomDriver(driver);
		String path=cd.takeScreenshot(result.getName(), browser);
		try 
		{
			extentTest.get().fail("<b>"+"<font color=red>"+"Screenshot of failure"+"</font>"+"</b>", MediaEntityBuilder.createScreenCaptureFromPath(path).build());;
		}
		catch(IOException e) {
			extentTest.get().fail("Test Method Failed, can not attach screenshot");
		}
		
		String logText = "<b>" + "Test Method" + methodName + " Failed" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		extentTest.get().log(Status.FAIL, m);

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		log.info("onTestSkippped->Test Method Name: " + result.getName());
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test Method" + methodName + " Successful" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		extentTest.get().log(Status.SKIP, m);

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

}
