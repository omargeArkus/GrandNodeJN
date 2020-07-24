package TestCases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import POM.HomePage;
import POM.PageObject;
import POM.SearchPage;
import Utilities.ExtentReport;

public class RecentlyViewedProductsTestCases {
	protected static WebDriver driver;
	protected static Properties properties;
	protected static ExtentReport reporter;
	
	@BeforeClass
	public static void SetUp() throws IOException {		
		// Set up chrome driver
		System.setProperty("webdriver.chrome.driver", ".\\src\\test\\resources\\Drivers\\chromedriver.exe"); 
		driver = new ChromeDriver ();		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);		
		driver.manage().window().maximize();	    
		properties = new Properties();
		FileInputStream fis = new FileInputStream(".\\src\\test\\java\\Utilities\\data.properties");
		properties.load(fis);
	    driver.get(properties.getProperty("storeUrl").toString());
	    // Set up extent report
		reporter = new ExtentReport(driver);
		reporter.setProperties("RecentlyViewedProductsReport", "Automation Report - Recently viewed products", "Front-End report for Recently viewed products feature", properties.getProperty("storeUrl").toString());
	}
	
	@AfterClass
	public static void terminateInstance() throws InterruptedException {
		// Kill driver process
		driver.close();
		driver.quit();
		// finish report
		reporter.endReport();
	}
	
	@AfterMethod
	public void TearDown (ITestResult result) throws IOException {
		// Add test result to the extent report
		reporter.TearDown(result);
	}
	
	@Test (description="The user must be able to add products after perform a quick search", 
		   groups= {"recentlyViewedProducts","regression","happyPath"},
		   priority = 1)
	// GN_FrontEnd_Recently viewed products_Quick search
	public static void quickSearch() throws InterruptedException{
		reporter.createTest("GN_FrontEnd_Recently viewed products_Quick search");
		// Quick search using search bar
		String strSearch = "Apple iCam";
		POM.HomePage  homePage  = new HomePage(driver);
		homePage.SearchBar(strSearch);
		// Open first product in the results
		POM.SearchPage searchPage = new SearchPage(driver);
		String firstProduct = searchPage.getProductName(0);
		searchPage.OpenProduct(0);
		// Go to Recently viewed products mini-section
		searchPage.goBack();
		POM.PageObject general = new PageObject(driver);
		general.scrollDownTo(searchPage.getRecentlyViewedProductsBlock());
		// Find the product previously opened
		int findProduct = searchPage.findRecentlyViewedProduct(firstProduct);
		if(findProduct>-1) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}
	
	@Test (description="The user must be able to add products after perform a manual search", 
		   groups= {"recentlyViewedProducts","regression","happyPath"},
		   priority = 2)
	// GN_FrontEnd_Recently viewed products_manual search
	public static void manualSearch() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Recently viewed products_manual search");
		// Search a product manually
		String category = "Electronics";
		String subCategory1 = "Computers";
		String subCategory2 = "Desktops";
		POM.HomePage homePage  = new HomePage(driver);
		homePage.navigateTo(category, subCategory1, subCategory2);
		// Open first product in the results
		POM.SearchPage searchPage = new SearchPage(driver);
		String firstProduct = searchPage.getProductName(0);
		searchPage.OpenProduct(0);
		// Go to Recently viewed products mini-section
		searchPage.goBack();
		POM.PageObject general = new PageObject(driver);
		general.scrollDownTo(searchPage.getRecentlyViewedProductsBlock());
		// Find the product previously opened
		int findProduct = searchPage.findRecentlyViewedProduct(firstProduct);
		if(findProduct>-1) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}
	
	@Test (description="The user must be able to access to the Recently viewed products list from the footer of the page clicking on Recently viewed products link", 
			   groups= {"recentlyViewedProducts","regression","happyPath"},
			   priority = 3)
	// GN_FrontEnd_Recently viewed products_footer access
	public static void footerAccess() {
		reporter.createTest("GN_FrontEnd_Recently viewed products_footer access");
		// Go to Recently viewed products page
		POM.HomePage   homePage  = new HomePage(driver);
		POM.PageObject pageObject = new PageObject(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Recently viewed products"));
		homePage.clickInformationLink("Recently viewed products");
		// Validate the length of products list
		POM.SearchPage searchPage = new SearchPage(driver);
		if(searchPage.getResultsSize()>0) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}
	
}
