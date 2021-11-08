package com.inetbanking.testCases;


import org.testng.annotations.BeforeClass;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inetbanking.base.BaseClass;
import com.inetbanking.base.CheckPoint;
import com.inetbanking.pageObjects.HomePage;
import com.inetbanking.pageObjects.LoginPage;
import com.inetbanking.utilities.Constants;
import com.inetbanking.utilities.ExcelUtility;

public class TC_LoginTest_001 extends BaseClass {

	LoginPage loginPage;
	HomePage homePage;
	
	@BeforeClass
	public void setUp() {
		ExcelUtility.setExcelFile(Constants.EXCEL_DIRECTORY, "AllCoursesTests");
		loginPage = new LoginPage(driver);
		CheckPoint.clearHashMap();
	}
	
	@DataProvider(name="getUsernameDataProvider")
	public Object[][] getUsernameData(){
		Object[][] testData=ExcelUtility.getTestData("loginTest");
		return testData;
	}

	@Test(dataProvider="getUsernameDataProvider")
	public void loginTest(String Username) {
		HomePage homePage = loginPage.signInWith(Username, password);
		boolean flagUserSignedIn = homePage.loginSuccessful();
		CheckPoint.mark("TC-01", flagUserSignedIn, "Login Successful Verification");
		boolean managerIDVerification =homePage.verifyManagerId(username);
		CheckPoint.markFinal("TC-01", managerIDVerification, "ManagerID Display HomePage");
	}
}
