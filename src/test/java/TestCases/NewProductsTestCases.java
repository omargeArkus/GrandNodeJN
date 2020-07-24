package TestCases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import POM.HomePage;
import POM.PageObject;
import POM.SearchPage;
import Utilities.ExtentReport;

public class NewProductsTestCases {
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
		reporter.setProperties("NewProductsReport", "Automation Report - New products", "Front-End report for New products feature", properties.getProperty("storeUrl").toString());
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
	
	@Test (description="Products marked as new must be displayed in the New products page", 
		   groups= {"newProducts","regression","happyPath"})
	// GN_FrontEnd_New Products_Simple_Mark as new product
	// GN_FrontEnd_New Products_Bundled_Mark as new product
	public void MarkAsNewProduct() {
		reporter.createTest("GN_FrontEnd_New Products_Mark as new product");
		String newProductTitle = "Laptop DELL Latitude E5570";
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage homePage = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("New products"));
		homePage.clickInformationLink("New products");
		POM.SearchPage searchPage = new SearchPage(driver);
		WebElement newProductElement = searchPage.getProductElement(newProductTitle);
		if(newProductElement == null){
			Assert.assertTrue(false);
		} else {
			Assert.assertTrue(true);
		}
	}
	
	@Test (description="Products unmarked as new must not be displayed in the New products page", 
		   groups= {"newProducts","regression","happyPath"})
	// GN_FrontEnd_New Products_Simple_Unmark as new product
	// GN_FrontEnd_New Products_Bundled_Unmark as new product
	public void UnMarkAsNewProduct() {
		reporter.createTest("GN_FrontEnd_New Products_Unmark as new product");
		String productTitle = "Trylle Trilogy Boxed Set";
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage homePage = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("New products"));
		homePage.clickInformationLink("New products");
		POM.SearchPage searchPage = new SearchPage(driver);
		WebElement newProductElement = searchPage.getProductElement(productTitle);
		if(newProductElement == null){
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}
	
}
