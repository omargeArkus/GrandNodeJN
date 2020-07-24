package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends PageObject {
	WebDriverWait wait = new WebDriverWait(driver,30);
	
	@FindBy (xpath = "//input[@id='FirstName']") private WebElement FirstName;
	@FindBy (xpath = "//input[@id='LastName']") private WebElement LastName;
	@FindBy (xpath = "//input[@id='Email']") private WebElement Email;
	
	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
	
	public void YourPersonalDetails(String strFirstName, String strLastName, String strEmail) {
		FirstName.sendKeys(strFirstName);
		LastName.sendKeys(strLastName);
		Email.sendKeys(strEmail);
	
	
}
}
