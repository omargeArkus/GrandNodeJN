package POM;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.html5.AddApplicationCache;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.IFactoryAnnotation;

public class SearchPage extends PageObject {
	WebDriverWait wait = new WebDriverWait(driver,30);	
	Actions action = new Actions(driver);		

	@FindBy (className = "generalTitle") private WebElement searchTitle;
	
	//Search page	
	@FindBy (className = "custom-control-description") private WebElement advancedCheckbox;
	@FindBy (id = "q") private WebElement searchInput;
	@FindBy (xpath = "//input[@value='Search']") private WebElement searchButton;
	@FindBy (id ="cid") private WebElement categoryDropdown;
	@FindBy (id ="mid") private WebElement ManufacturerDropdown;
	@FindBy (xpath = "//span[text()='Check to search in sub categories.']") private WebElement subCategoryCheckbox;
	@FindBy (xpath = "//span[text()='Search In product descriptions']") private WebElement pdctdescCheckbox;
	@FindBy (id ="pf") private WebElement fromInput;
	@FindBy (id ="pt") private WebElement ToInput;	
	@FindBy (xpath="//span[text()='Items']") private WebElement itemsLabel;
	
	//Results page
	@FindBy (xpath = "//div[@class='product-info']/parent::div") 	private List<WebElement> productResults;
	@FindBy (xpath = "//div[@id='block-recently-viewed-products']") private WebElement recentlyViewedProductsBlock;
	@FindBy (xpath = "//*[@id='block-recently-viewed-products']/ul/li/div/a") private List<WebElement> recentlyViewedProductsName;
	
	//Product details page
	@FindBy (xpath = "//*[@id='add-to-wishlist-button-5ef38b359d6afb5b1c1fa0c9']") private WebElement wishlistButton;
	@FindBy (xpath = "//li[@itemprop ='itemListElement']") private List<WebElement> navigationPath;
	
	//Wishlist modal
	@FindBy (xpath = "//*[@id='ModalAddToCart']") private WebElement wishlistModal;
	@FindBy (xpath = "//button[@class='close text-white']/parent::div/h5") private WebElement successMessage;
	@FindBy (xpath = "//button[@class='close text-white']") private WebElement closeButton;
	
	//Methods
	public SearchPage(WebDriver driver) {
		super(driver);		
	}
	
	public boolean TitlePageDisplay () {
		return searchTitle.isDisplayed();
	}
	
	//--** SEARCH PAGE
	
	//Method to interact with products and overmouse actions
	/*public void AddCompareFromProduct() {
		action.moveToElement(target)
	}*/
	
	//Method to validate if the label Items is displayed
	public Boolean itemsLabelDisplayed() {
		return itemsLabel.isDisplayed();
	}
	
	//Method to perform an advanced search
	public void AdvancedSearch(String strSearch, String strCategory, String strManufacturer, String strFrom, String strTo, Boolean bolChecksubCategories, Boolean bolSearchPdctDesc ) {
		//Select advanced search checkbox
		advancedCheckbox.click();
		
		//Select, clean and send string for search
		searchInput.click();
		searchInput.clear();
		searchInput.sendKeys(strSearch);
		
		//Select Category
		Select category = new Select(categoryDropdown);
		category.selectByVisibleText(strCategory);
		
		//Select Manufacturer
		Select manufacturer = new Select (ManufacturerDropdown);
		manufacturer.selectByVisibleText(strManufacturer);
		
		//Check "Check to search in sub categories." checkbox		
		if (bolChecksubCategories==true) {
			subCategoryCheckbox.click();
		} 
		
		//Check "Search In product descriptions" checkbox
		if (bolSearchPdctDesc==true) {
			pdctdescCheckbox.click();			
		} 
		
		//Select, clean and send string for From
		fromInput.click();
		fromInput.clear();
		fromInput.sendKeys(strFrom);
		
		//Select, clean and send string for To
		ToInput.click();
		ToInput.clear();
		ToInput.sendKeys(strTo);
		ToInput.submit(); 		
	}
	
	//--* RESULTS PAGE
	
		// Put mouse pointer over 'productName' product, return position in the list
		public int MouseOverProduct(String productName) {
			POM.PageObject pageObject = new PageObject(driver);
			for(int i=0; i<productResults.size();i++) {
				if( productResults.get(i).findElement(By.xpath("//div[@class='title']/h5/a")).getText().equals(productName) ) {
					pageObject.mouseOverElement(productResults.get(i));
					return i;
				}
			}
			return -1;
		}
		
		//Get number of products in the current page
		public int getResultsSize() {
			return productResults.size();
		}
		
		// Add product in 'position' position to the wishlist
		public void addToWishlist(int position) throws InterruptedException {
			productResults.get(position).findElement(By.xpath("//button[@title='Add to wishlist']")).click();
			Thread.sleep(2500);
		}
		
		// Open product in 'position' position
		public void OpenProduct(int position) throws InterruptedException {
			productResults.get(position).click();
			Thread.sleep(2000);
		}
		
		// Get product name in 'position' position
		public String getProductName(int position) {
			return productResults.get(position).findElement(By.xpath("//div[@class='title']/h5/a")).getText();
		}
		
		//Get product element
		public WebElement getProductElement(String productName) {
			for(int i=0;i<productResults.size();i++) {
				if(productResults.get(i).findElements(By.xpath("//div[@class='title']/h5/a")).get(i).getText().equals(productName)) {
					return productResults.get(i);
				}
			}
			return null;
		}
		
		// Get Block with recently viewed products
		public WebElement getRecentlyViewedProductsBlock(){
			return recentlyViewedProductsBlock;
		}
		
		// Find position for one recently viewed product, return -1 if it's not found
		public int findRecentlyViewedProduct(String productName) {
			for(int i=0;i<recentlyViewedProductsName.size();i++) {
				if(recentlyViewedProductsName.get(i).getText().equals(productName)) {
					return i;
				}
			}
			return -1;
		}
		
		//--* PRODUCT DETAILS PAGE
		
		// Add product to the wishlist
		public void addToWishlist() throws InterruptedException {
			wishlistButton.click();
			Thread.sleep(2000);
		}
		
		public void goBack() {
			navigationPath.get(navigationPath.size()-2).click();
		}
		
		//--* WISHLIST MODAL METHODS
		
		// Get success message
		public String getWishListMessage(){
			return successMessage.getText();
		}
		
		// Get product name
		public String getWishListProduct(){
			return wishlistModal.findElement(By.xpath("//h4/a")).getText();
		}
		//Click on close button
		public void closeButton() throws InterruptedException {
			closeButton.click();
			Thread.sleep(1000);
		}
		
		// Click on continue button
		public void continueButton() throws InterruptedException {
			wishlistModal.findElement(By.xpath("//button[1]")).click();
			Thread.sleep(1500);
		}
		
		// Click on go to my wishlist button
		public void goToMyWishlistButton() throws InterruptedException {
			wishlistModal.findElement(By.xpath("//button[2]")).click();
			Thread.sleep(1500);
		}
		
}
