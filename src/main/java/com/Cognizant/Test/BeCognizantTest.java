package com.Cognizant.Test;

import java.io.IOException;

//******************* To import all the required files *******************************//
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Cognizant.Bases.TestBase;
import com.Cognizant.Pages.CognizantHomePage;
import com.Cognizant.Pages.CognizantLoginPage;
import com.Cognizant.Pages.GoogleStockPage;
import com.Cognizant.utilities.ExcelUtils;

public class BeCognizantTest extends TestBase {

	@BeforeClass(groups = { "BaseMethods" })
	public void beforeClass() throws InterruptedException {
		logger = report.createTest("Before Class Report");
		setupDriver();
		navigateToURL();
		String actTitle = driver.getTitle();
		String expTitle = "Sign in to your account";

		Assert.assertEquals(actTitle, expTitle);
		reportPass("Successfully Opened on Login Page");
	}

	
	@Test(priority = 1,groups = { "BaseMethods" })
	public void getLoginAccount() throws InterruptedException {
		logger = report.createTest("Login report");
	
		CognizantLoginPage login = new CognizantLoginPage();
		
		login.enterUserId();
		login.enterPassword();	
		
		/* Invoking selectApproval and verifyId methods in CognizantLoginPage class */
		login.selectApproval();
		
		login.verifyId();
		
		reportPass("Logged in Successfully");
	} 


	@Test(priority = 2,groups = { "Smoke" })
	public void getUserInfoTest() {
		logger = report.createTest("User Detail Report");

		CognizantHomePage home = new CognizantHomePage();
		
		home.userInfo();

		String actTitle = driver.getTitle();
		String expTitle = "Be.Cognizant";

		Assert.assertEquals(actTitle, expTitle);
		
		reportPass("Successfully verified page title and fetched user details");

	} 

	
	
	@Test(priority = 3,groups = { "Smoke" })
	public void getCognizantStockPriceTest() {
		logger = report.createTest("Stock Price Report");

		CognizantHomePage home = new CognizantHomePage();
		
		home.stockPriceCTSH(); 
		
		GoogleStockPage price = new GoogleStockPage();
		
		price.google();

		String actTitle = driver.getTitle();
		String expTitle = "CTS Stock Price - Google Search";

		Assert.assertEquals(actTitle, expTitle);
		System.out.println(
				"**********************************************************************************************************************");
		System.out.println("\nThe Title of the Google page is: " + actTitle + "\n");
		System.out.println(
				"**********************************************************************************************************************");
		
		price.check();
		
		reportPass("Successfully Verified CTSH Stock Price");
	}

	
	@Test(priority = 4,groups = { "Regression" })
	public void getAppAndTools() {
		logger = report.createTest("Apps and Tools Report");
		
		CognizantHomePage home = new CognizantHomePage();
		
		home.selectWindowIcon();

		String actTitle = driver.getTitle();
		String expTitle = "Be.Cognizant";


		Assert.assertEquals(actTitle, expTitle);
		
		reportPass("Successfully opened Apps and Tools");
	}

	@DataProvider
	public Object[][] dp() throws IOException {
		Object[][] obj = new Object[1][1];
		obj = ExcelUtils.readExcelData();
		return obj;
	}

	@Test(priority = 5, dataProvider = "dp",groups = { "Regression" })
	public void getAppsAndToolsTest(String getAppName) throws InterruptedException {
		logger = report.createTest(getAppName + " Report");

		CognizantHomePage home = new CognizantHomePage();
		
		home.displayAppsName(getAppName);

		reportPass("Successfully Fetched all Apps and Tools");
	}

	@AfterClass(groups = { "Regression" })
	public void afterClass() {
		closeDriver();
	} 
	
}