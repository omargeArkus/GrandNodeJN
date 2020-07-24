package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VideoGamesCategoriesPage extends PageObject {
	WebDriverWait wait = new WebDriverWait(driver,30);
	
	@FindBy (id = "5eed1c0ee6793b45843441aa-menu") private WebElement SelectVideoGamesCategory;
	@FindBy (xpath = "//a[@class='btn btn-sm btn-light mb-1 mr-1'][2]") private WebElement Filter;
	@FindBy (xpath="//a[contains(text(),'Remove Filter')]") private WebElement RemoveFilter;
	
	
	public VideoGamesCategoriesPage(WebDriver driver) {
		super(driver);		
	}
	

	
	public void AddFilter () {
		Filter.click();
	}
	 public void RemoveFilter () {
		 RemoveFilter.click();
		 
	 }
	 public Boolean VerifyRemoveFilterButton() {
		 return RemoveFilter.isDisplayed();
	 }
	 public Boolean VerifyFilterButton() {
		 return Filter.isDisplayed();
	 }		
		
    
}
