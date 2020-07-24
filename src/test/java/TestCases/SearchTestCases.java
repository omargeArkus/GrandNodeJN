package TestCases;
import org.testng.ITestResult;
import org.testng.TestNGException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import POM.BaseClass;
import POM.HomePage;
import POM.SearchPage;
import Utilities.BrowserConfiguration;


public class SearchTestCases {
	
	protected static WebDriver driver;	
	protected static ExtentSparkReporter htmlReporter;
	protected static ExtentReports extent;
	protected static ExtentTest test;	
	
	@Parameters ("browser")
	@BeforeClass
	public static void SetUp(String browser) {
	//public static void SetUp() {
		driver=BrowserConfiguration.startApplicationDriver(driver, "Chrome", "http://187.191.25.39:8891/");	   
	}
	
	@AfterClass
	public static void CloseDriver () {
		driver.close();
	}	
	
	
	@BeforeTest
	public void SetExtent() {
		htmlReporter=new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/SearchReport.html");	
		
		htmlReporter.config().setDocumentTitle("Automation Report =Search="); //Title of the Report
		htmlReporter.config().setReportName("Front End Report for Search Feature");
		htmlReporter.config().setTheme(Theme.STANDARD);
		
		extent= new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		extent.setSystemInfo("Hostname", "http://187.191.25.39:8891/");
		extent.setSystemInfo("OS", "Windows 10 Pro");
		extent.setSystemInfo("Browser", "Chrome");
	}
	
	@AfterTest
	public void EndReport() {
		extent.flush();
	}
	
	@AfterMethod
	public void TearDown (ITestResult result) throws IOException {
		if (result.getStatus()== ITestResult.FAILURE) {
			test.log(Status.FAIL,"TEST CASE FAILED IS "+ result.getName()); //to add name in extent report
			test.log(Status.FAIL,"TEST CASE FAILED IS "+ result.getThrowable()); //to add/error exception in extent report
			
			String screnshotpathString = SearchTestCases.getScreenshot(driver, result.getName());
			
			test.addScreenCaptureFromPath(screnshotpathString);
		} else if (result.getStatus()==ITestResult.SKIP){
			test.log(Status.SKIP,"Test Case SKKIPED is "+ result.getName()); //to add name in extent report
		}
		else if (result.getStatus()==ITestResult.SUCCESS){
			test.log(Status.PASS,"Test Case PASSED is "+ result.getName()); //to add name in extent report
		}		
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
	
	@Test (priority=1, description="Basic Search from the Home Page e-commerce with an article included in the store", groups = { "regression", "search" })
	//GN-FrontEnd-Searching-Basic Search from Home Page
	public static void BasicSearch() {
		POM.HomePage home = new HomePage(driver);
		POM.SearchPage Search = new SearchPage(driver);		
		test=extent.createTest("GN-FrontEnd-Searching-Basic Search from Home Page");	
		
		String searchTxt = "Xbox";		
		home.SearchBar (searchTxt);
		assertTrue(Search.TitlePageDisplay(), "Basic Search Failed! ");		
	} 
	
	@Test (priority=2, description="Advanced Search from the Home Page e-commerce with an article included in the store", groups = { "regression", "search" })
	//GN-FrontEnd-Searching-Advanced Search
	public void AdvancedSearch() {
		POM.HomePage home = new HomePage(driver);
		POM.SearchPage search = new SearchPage(driver);
		test=extent.createTest("GN-FrontEnd-Searching-Advanced Search");
		
		home.GoHome();
		home.FooterSearch(); 
		search.AdvancedSearch("call", "Videogames", "All", "0", "200", true, false);
		assertTrue(search.itemsLabelDisplayed(), "Advanced Search Failed!" );		
	} 

}
