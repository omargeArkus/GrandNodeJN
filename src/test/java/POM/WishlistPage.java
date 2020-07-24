package POM;



import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WishlistPage extends PageObject{
	
	WebDriverWait wait = new WebDriverWait(driver,30);
	@FindBy (name  = "updatecart")		private WebElement updateWishlistButton;
	@FindBy (name  = "addtocartbutton")	private WebElement AddToCartButton;
	@FindBy (xpath = "//table[@class = 'shopping-cart']") public WebElement wishListTable;
	
	public WishlistPage(WebDriver driver) {
		super(driver);
	}
	
	// Return the products name in the wishlist
	public List<WebElement> getProducts() {
		List<WebElement> products = wishListTable.findElements(By.xpath("//tbody/tr/td[@class='product']"));
		return products;
	}
	
	// Add one simple product to the wishlist, return success message
	public String addSimpleProduct(String productName) throws InterruptedException {
		POM.HomePage homePage = new HomePage(driver);
		homePage.SearchBar(productName);
		POM.SearchPage searchPage = new SearchPage(driver);
		POM.PageObject pageObject = new PageObject(driver);
		pageObject.scrollDownTo(searchPage.getProductElement(productName));
		int position = searchPage.MouseOverProduct(productName);
		if(position==-1) {
			return "Product not found";
		}
		searchPage.addToWishlist(position);
		String msg = searchPage.getWishListMessage();
		return msg;
	}
	
	// Add one bundled product to the wishlist, return success message
	public String addBundledProduct(String productName) throws InterruptedException {
		POM.HomePage homePage = new HomePage(driver);
		homePage.SearchBar(productName);
		POM.PageObject pageObject = new PageObject(driver);
		POM.SearchPage searchPage = new SearchPage(driver);
		pageObject.scrollDownTo(searchPage.getProductElement(productName));
		int position = searchPage.MouseOverProduct(productName);
		if(position==-1) {
			return "Product not found";
		}
		searchPage.OpenProduct(position);
		searchPage.addToWishlist();
		Thread.sleep(2000);
		String msg = searchPage.getWishListMessage();
		return msg;
	}
	
	// Remove one product from the wishlist, return false if was not removed
	public boolean removeProduct(String productName) throws InterruptedException {
		List<WebElement> productsList = wishListTable.findElements(By.xpath("//tbody/tr/td[@class='product']"));
		List<WebElement> removeList   = wishListTable.findElements(By.xpath("//td[@class='remove-from-cart']/label"));
		Boolean result = false;
		for(int i=0;i<productsList.size();i++) {
			if(productsList.get(i).getText().equals(productName)) {
				removeList.get(i).click();
				result = true;
				break;
			}
		}
		updateWishlist();
		return result;
	}
	
	// Update the wishlist
	public void updateWishlist() throws InterruptedException {
		updateWishlistButton.click();
		Thread.sleep(1000);
	}
	
}
