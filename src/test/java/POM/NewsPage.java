package POM;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewsPage extends PageObject{

	@FindBy (xpath = "//h1") private WebElement articleTitle;
	
	
	public NewsPage(WebDriver driver) {
		super(driver);		
	}
	
	//return the title of the Article news
	public String articleTitle() {
		
		return driver.getTitle();	
	}
	


	

}
