package Utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class BrowserConfiguration {
	
	public static WebDriver startApplicationDriver (WebDriver driver, String browserName, String appURL) {
		if (browserName.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/Drivers/chromedriver.exe"); 
			driver= new ChromeDriver();
						
		} else if (browserName.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "src/test/resources/Drivers/geckodriver.exe"); 
			driver= new FirefoxDriver();
			
		} else if (browserName.equals("IE")) {
			InternetExplorerOptions options = new InternetExplorerOptions();
			options.setCapability("ignoreZoomSetting", true);
			
			System.setProperty("webdriver.ie.driver", "src/test/resources/Drivers/IEdriverServer.exe"); 
			driver= new InternetExplorerDriver(options);
			
		}
		//driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();	
	    driver.get(appURL);
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    return driver;
	}
	
	public static void quitBrowser(WebDriver driver) {		
		driver.quit();
	}
	

}
