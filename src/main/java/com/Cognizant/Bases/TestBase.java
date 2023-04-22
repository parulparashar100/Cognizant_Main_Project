package com.Cognizant.Bases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

import com.Cognizant.utilities.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public static ExtentReports report = ExtentReportManager.getReportInstance();
	public static ExtentTest logger;
	File file;
	FileInputStream fis;

	// ************************ To invoke the browser ***********************//
	public void setupDriver() {
		prop = new Properties();
		
		try {
			fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/com/Cognizant/config/configure.properties");
			prop.load(fis);
		} catch (FileNotFoundException fnf) {
			System.out.println("File name or path name is not correct");
		} catch (IOException ioe) {
			System.out.println("Input output error");
		}

		 /* To launch chrome browser */
		if (prop.getProperty("Browser").equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);
		} 
		 /* To launch firefox browser */
		else if (prop.getProperty("Browser").equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
		 /* To launch edge browser */
		else if (prop.getProperty("Browser").equalsIgnoreCase("edge")) {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new EdgeDriver(options);
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

	}
	
	@AfterMethod
	public void flushReport() {
		report.flush();
	}

	//****************************** To navigate to the URL ***********************************//
	public void navigateToURL() {
		driver.get(prop.getProperty("URL"));
	}
	
	//*********************************** To to close the browser *******************************//
	public void closeDriver() {
		driver.quit();
	}
	
	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		screenShot();
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) {
		screenShot();
		logger.log(Status.PASS, reportString);
	}

	//****************************** To capture Screenshot***********************************//
		public void screenShot() {
			
			/* Thread sleep Wait */
			try {
				Thread.sleep(2000);

				/* To capture the displayed screen */
				TakesScreenshot scrShot = (TakesScreenshot) driver;
				File srcFile = scrShot.getScreenshotAs(OutputType.FILE);

				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss"); // yyyy-MM-dd
				String dateTime = sdf.format(date);
				String fileName = "screenshot_" + dateTime;

				File destFile = new File(".\\screenshot\\" + fileName + ".png");
				FileUtils.copyFile(srcFile, destFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
}
