package TestCases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import POM.HomePage;
import POM.LoginPage;
import POM.PageObject;
import POM.SearchPage;
import POM.WishlistPage;
import Utilities.ExtentReport;

public class WishlistTestCases {
	
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
		reporter.setProperties("WishlistReport", "Automation Report - Wishlist", "Front-End report for Wishlist feature", properties.getProperty("storeUrl").toString());
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
	
	@Test (description="The user must be able to access to the wishlist from the header of the page clicking on heart icon", 
		   groups   = {"wishlist","regression","happyPath"},
		   priority = 1)
	// GN_FrontEnd_Wishlist_Quick access
	public static void QuickAccess() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Wishlist_Quick access");
		POM.HomePage  homePage  = new HomePage(driver);
		homePage.userIcon();
		homePage.loginButton();
		POM.LoginPage loginPage = new LoginPage(driver);
		loginPage.login("jlara@arkusnexus.com", "wrBKm394uNrhSQ!n");
		homePage.wishlistIcon();
		Assert.assertTrue(driver.getTitle().equals("Your store. Wishlist"));
		homePage.userIcon();
		homePage.logOut();
		homePage.wishlistIcon();
		Assert.assertTrue(driver.getTitle().equals("Your store. Wishlist"));
	}

	@Test (description="The user must be able to access to the Wishlist from the footer of the page clicking on Wishlist link", 
		   groups   = {"wishlist","regression","happyPath"},
		   priority = 1)
	// GN_FrontEnd_Wishlist_Footer link
	public static void FooterLink() {
		reporter.createTest("GN_FrontEnd_Footer link");
		POM.HomePage   homePage   = new HomePage(driver);
		POM.PageObject pageObject = new PageObject(driver);
		pageObject.scrollDownTo(homePage.getAccountLink("Wishlist"));
		homePage.clickAccountLink("Wishlist");
		Assert.assertTrue(driver.getTitle().equals("Your store. Wishlist"));
	}

	@Test (description="The user must be able to add simple products to the Wishlist", 
		   groups= {"wishlist","regression","happyPath"},
		   priority = 2)
	// GN_FrontEnd_Wishlist_Add simple product
	public static void addSimpleProduct() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Wishlist_Add simple product");
		String productName = "Apple iCam";
		WishlistPage wishlistPage = new WishlistPage(driver);
		String msg  = wishlistPage.addSimpleProduct(productName);
		Assert.assertTrue(msg.equals("Product was successfully added to wishlist"));
		POM.SearchPage searchPage = new SearchPage(driver);
		String prod = searchPage.getWishListProduct();
		Assert.assertTrue(prod.equals(productName));
		searchPage.closeButton();
	}
	
	@Test (description="The user must be able to add bundled products to the Wishlist", 
		   groups= {"wishlist","regression","happyPath"},
		   priority = 2)
	// GN_FrontEnd_Wishlist_Add bundle product
	public static void addBundledProduct() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Wishlist_Add bundle product");
		String productName = "ApplePack";
		WishlistPage wishlistPage = new WishlistPage(driver);
		String msg  = wishlistPage.addBundledProduct(productName);
		Assert.assertTrue(msg.equals("Product was successfully added to wishlist"));
		POM.SearchPage searchPage = new SearchPage(driver);
		String prod = searchPage.getWishListProduct();
		Assert.assertTrue(prod.equals(productName));
		searchPage.closeButton();
	}
	
	@Test (description="The user can remain in the results page after adding a product to the Wishlist", 
	       groups= {"wishlist","regression","happyPath"},
	       priority = 3)
	// GN_FrontEnd_Wishlist_Continue adding
	public static void continueAdding() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Wishlist_Continue adding");
		String productName = "The Devotion of Suspect X";
		WishlistPage wishlistPage = new WishlistPage(driver);
		String msg  = wishlistPage.addSimpleProduct(productName);
		Assert.assertTrue(msg.equals("Product was successfully added to wishlist"));
		POM.SearchPage searchPage = new SearchPage(driver);
		searchPage.continueButton();
		Assert.assertTrue(driver.getTitle().equals("Your store. Search"));
	}
	
	@Test (description="The user can go to the Wishlist after adding a product", 
	       groups   = {"wishlist","regression","happyPath"},
	       priority = 3)
	// GN_FrontEnd_Wishlist_Go to my Wishlist
	public static void goToMyWishlist() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Wishlist_Go to my Wishlist");
		String productName = "The Devotion of Suspect X";
		WishlistPage wishlistPage = new WishlistPage(driver);
		String msg  = wishlistPage.addSimpleProduct(productName);
		Assert.assertTrue(msg.equals("Product was successfully added to wishlist"));
		POM.SearchPage searchPage = new SearchPage(driver);
		searchPage.goToMyWishlistButton();
		Assert.assertTrue(driver.getTitle().equals("Your store. Wishlist"));
	}
	
	@Test (description ="The user must be able to remove products from the Wishlist", 
		   groups= {"wishlist","regression","happyPath"},
		   priority = 4)
	// GN_FrontEnd_Wishlist_Remove product
	public static void removeProduct() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Wishlist_Remove product");
		String productName = "The Devotion of Suspect X";
		POM.HomePage  homePage  = new HomePage(driver);
		homePage.wishlistIcon();
		WishlistPage wishlistPage = new WishlistPage(driver);
		boolean actionResult = wishlistPage.removeProduct(productName);
		Assert.assertTrue(actionResult);
	}
}
