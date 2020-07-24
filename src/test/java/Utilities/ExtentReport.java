package Utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestCases.SearchTestCases;

public class ExtentReport {
	
	protected static WebDriver driver;
	protected static ExtentSparkReporter htmlReporter;
	protected static ExtentReports extent;
	protected static ExtentTest test;
	
	public ExtentReport(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setProperties(String htmlName, String documentTitle, String reportName, String hostName) throws IOException {
		// Store directory
		htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/"+htmlName+".html");
		// Title of the Report
		htmlReporter.config().setDocumentTitle(documentTitle);
		htmlReporter.config().setReportName(reportName);
		htmlReporter.config().setTheme(Theme.STANDARD);	
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);	
		// Environment properties
		extent.setSystemInfo("Hostname", hostName);
		extent.setSystemInfo("OS", "Windows 10 Pro");
		extent.setSystemInfo("Browser", "Chrome");
	}
	
	public void endReport() {
		extent.flush();
	}
	
	public void TearDown (ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			// Get test case failed name and error exception
			test.log(Status.FAIL,"TEST CASE FAILED IS "+ result.getName());
			test.log(Status.FAIL,"TEST CASE FAILED IS "+ result.getThrowable());
			// Take a screenshot of the failed step
			String screnshotpathString = SearchTestCases.getScreenshot(driver, result.getName());
			test.addScreenCaptureFromPath(screnshotpathString);
		} else if (result.getStatus() == ITestResult.SKIP){
			// Get test case skiped name
			test.log(Status.SKIP,"Test Case SKKIPED is "+ result.getName());
		}
		else if (result.getStatus()==ITestResult.SUCCESS){
			// Get test cases passed name
			test.log(Status.PASS,"Test Case PASSED is "+ result.getName());
		}		
	}
	
	public void createTest(String testName) {
		test = extent.createTest(testName);
	}
	
	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		//After execution, you could see a folder "FailedTestsScreenshots" under src folder
		String destination = System.getProperty("user.dir")+"/Screenshots/"+screenshotName +dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

}
