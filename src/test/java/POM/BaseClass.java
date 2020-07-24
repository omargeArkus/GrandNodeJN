package POM;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;


import Utilities.BrowserConfiguration;

public class BaseClass {
	
	public static WebDriver driver;	
	
	@BeforeClass
	public static void SetUp() {
		driver=BrowserConfiguration.startApplicationDriver(driver, "Chrome", "http://187.191.25.39:8891/");	   
	}
	
	@AfterClass
	public static void CloseDriver () {
		driver.close();
	}	
	
	
	
}
