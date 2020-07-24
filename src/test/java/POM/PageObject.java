package POM;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class PageObject {
	WebDriver driver;

	public PageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// Scroll down to element
	public void scrollDownTo(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",element);
	}
	
	// Move mouse over element
	public void mouseOverElement(WebElement element) {
		Actions a = new Actions(driver);
		a.moveToElement(element).build().perform();
	}

	// Click on alert confirmation button
	public void confirm() throws InterruptedException {
		driver.switchTo().alert().accept();
		Thread.sleep(4000);
	}
	
	// Click on alert cancel button
	public void dismiss() throws InterruptedException {
		driver.switchTo().alert().dismiss();
		Thread.sleep(4000);
	}
}
