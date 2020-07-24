package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends PageObject{
	WebDriverWait wait = new WebDriverWait(driver,30);
	@FindBy (id = "Email")		private WebElement email;
	@FindBy (id = "Password")	private WebElement password;
	//@FindBy (linkText = "Forgot password?")	private WebElement forgotPassword;
	@FindBy (xpath = "//form/div[2]/div/input")	private WebElement loginButton;
	//@FindBy (linkText = "Register")	private WebElement register;
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	// Login into application
	public void login(String email, String pass) {
		this.email.sendKeys(email);
		this.password.sendKeys(pass);
		this.loginButton.click();
	}
}
