package TestCases;

import static org.testng.Assert.assertTrue;

//import static org.testng.Assert.assertTrue;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import POM.HomePage;
import POM.VideoGamesCategoriesPage;


public class DisplayAndFiltering {
	
	protected static WebDriver driver;
	
	@BeforeSuite
	public static void SetUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/Drivers/chromedriver.exe"); 
		driver = new ChromeDriver ();		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);		
		driver.manage().window().maximize();	
	    driver.get("http://187.191.25.39:8891/");		
	}
	
	@AfterSuite
	public static void TearDown () {
		driver.close();
	}
	
	@Test
	//GN_FrontEnd_Display & Filtering_Filter by Price
	public static void FilterByPrice () {

		POM.HomePage home = new HomePage(driver);
		POM.VideoGamesCategoriesPage category = new VideoGamesCategoriesPage(driver);
		home.SelectVideoGamesCategory();
		category.AddFilter();
		
		assertTrue(category.VerifyRemoveFilterButton());		
	}
	
	@Test
	//GN_FrontEnd_Display & Filtering_Remove Filter
	public static void RemoveFilter () {
		POM.HomePage home = new HomePage(driver);
		POM.VideoGamesCategoriesPage category = new VideoGamesCategoriesPage(driver);
		home.SelectVideoGamesCategory();
		category.AddFilter();
		category.RemoveFilter();
		assertTrue(category.VerifyFilterButton());		
		
		}
	
	
	}