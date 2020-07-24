package POM;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends PageObject {
	WebDriverWait wait = new WebDriverWait(driver,30);
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	//--* Header elements
	@FindBy (xpath = "//header/div/div/div[1]/div/a") private WebElement home;
	@FindBy (xpath = "//a[@href='/']") private WebElement HomeLink;
	@FindBy (xpath = "//ul[@class='navbar-nav']/li/a")private List<WebElement> categories;
	@FindBy (id = "small-searchterms") 		  private WebElement SearchBar;
	@FindBy (xpath = "//a[text()='Search']")  private WebElement SearchLink;
	@FindBy (xpath = "//header/div/div/a[2]") private WebElement userIcon;
	@FindBy (xpath = "//ul[@class='shopping-links']/li[1]/a") 	  private WebElement wishlistIcon;
	@FindBy (xpath = "//ul[@class='shopping-links']/li[2]/div/a") private WebElement cartIcon;
	
	//News Section	
	@FindBy (xpath = "//h2[contains(text(),'News')]") private WebElement Newstitle;
	@FindBy (xpath = "//h3[@class='h5']") private List<WebElement> NewsArticles;
	@FindBy (xpath = "//div[@class='card text-lg-left text-center']//a[text()='details']") private List<WebElement> detailsButton;
	@FindBy (xpath = "//h2[contains(text(),'Community poll')]") private WebElement pollTitle;
	@FindBy (className = "h5 poll-display-text") private List<WebElement> pollNames;
		
	
	// THIS COULD BE REPLACED BY homePage.navigateTo(Videogames,null,null)
	@FindBy (id = "5eed1c0ee6793b45843441aa-menu") private WebElement SelectVideoGamesCategory;
	
	//--* My account menu elements - Unregistered user
	@FindBy (xpath = "//*[@id='user_panel']/ul/li[2]/a[1]") private WebElement loginButton;
	//@FindBy () private WebElement registerButton;
		
	//--* My account menu elements - Registered user
	@FindBy (xpath = "//*[@id='user_panel']/ul/li") private List<WebElement> myAccountList;
		
	//--* Footer elements
	@FindBy (xpath = "//div[@id='footer-block-my-account']/div[@class='viewBox'][1]/ul/li") private List<WebElement> myAccountFooterList;
	@FindBy (xpath = "//*[@id='footer-block-information']/div[@class='viewBox']/ul/li/a" ) private List<WebElement> informationFooterList;


	public HomePage(WebDriver driver) {
		super(driver);		
	}	
	
	//Send you Home page from any page
	public void GoHome() {
		HomeLink.click();
	}
	
	
	public void SelectMyAccount() {
		
	}
	
	
	
	// Send you from Home Page to Search page	
	public void FooterSearch () {
		SearchLink.click();
	}
	
	public void SelectVideoGamesCategory() {
		SelectVideoGamesCategory.click();		
	}
	
	//--* HEADER METHODS
	
	// Go home page
	public void home() {
		home.click();
	}
		
	// Go to Category/SubCategory
	public void navigateTo(String category, String subCategory1, String subCategory2) throws InterruptedException {
		if(!category.equals(null)) {
			// Objects declaration
			int auxIndex = -1;
			List<WebElement> subCategories1;
			List<WebElement> subCategories2;
			POM.PageObject pageObject = new PageObject(driver);
			// Searching for category
			for(int i=0;i<categories.size();i++) {
				if(categories.get(i).getText().split("\n")[0].equals(category)) {
					auxIndex = i;
					break;
				}
			}
			// Continue only if category exists
			if(subCategory1.isEmpty() && auxIndex>=0) {
				categories.get(auxIndex).click();
			} else if (auxIndex>=0) {
				pageObject.mouseOverElement(categories.get(auxIndex));
				subCategories1 = categories.get(auxIndex).findElements(By.xpath("//parent::li/ul/li/ul/li/a"));
				auxIndex=-1;
				// Searching for subCategory1
				for(int j=0;j<subCategories1.size();j++) {
					if(subCategories1.get(j).getText().split("\n")[0].equals(subCategory1)) {
						auxIndex=j;
						break;
					}
				}
				//System.out.println("SubCategory1 " + subCategories1.get(auxIndex).getText());
				// Continue only if subCategory1 exists
				if(subCategory2.isEmpty() && auxIndex>=0) {
					subCategories1.get(auxIndex).click();
				} else if (auxIndex>=0){
					pageObject.mouseOverElement(subCategories1.get(auxIndex));Thread.sleep(2000);
					subCategories2 = subCategories1.get(auxIndex).findElements(By.xpath("parent::li/ul/li/a"));
					auxIndex=-1;
					//Searching for subCategory2
					for(int k=0;k<subCategories2.size();k++) {
						 if(subCategories2.get(k).getText().split("\n")[0].equals(subCategory2)){
						 	subCategories2.get(k).click();
						 	break;
						 }					 
					}
				}
			}
		}
	}
		
	// Basic search from Home page using Search Bar
	public void SearchBar (String strSearch) {
		SearchBar.click();
		SearchBar.sendKeys(strSearch);
		SearchBar.sendKeys(Keys.ENTER);
		
	}
	
	// Click on search button
	public void SearchButton () {
		SearchLink.click();
	}
	
	//Scroll to section

	
	
	//News section	
	public Boolean NewsSectionIsDisplayed () {
		js.executeScript("arguments[0].scrollIntoView();", Newstitle);
		return Newstitle.isDisplayed();
	}
	
	public String SelectArticleFromNews(int ArticleNew) {
		js.executeScript("arguments[0].scrollIntoView();", Newstitle);
		String textTitle = NewsArticles.get(ArticleNew).getText();
		NewsArticles.get(ArticleNew).click();		
		return textTitle;
	}
	
	public String ClickDetailsButtonFromNews(int ArticleNew) {
		js.executeScript("arguments[0].scrollIntoView();", Newstitle);
		String textTitle = NewsArticles.get(ArticleNew).getText();
		detailsButton.get(ArticleNew).click();	
		return textTitle;
	}
	
	public void AnswerPoll(String pollName, String pollOption) {
		js.executeScript("arguments[0].scrollIntoView();", pollTitle);
		
		
	}
	
	// Go to wishlist page
	public void wishlistIcon() throws InterruptedException {
		wishlistIcon.click();
		Thread.sleep(1000);
	}
	
	// Go to cart basket
	public void cartIcon() throws InterruptedException {
		cartIcon.click();
		Thread.sleep(1000);
	}
	
	//--* MY ACCOUNT MENU
	
	// Open the user menu
	public void userIcon() throws InterruptedException {
		userIcon.click();
		Thread.sleep(1000);
	}
	
	// Go to login page
	public void loginButton() throws InterruptedException {
		loginButton.click();
		Thread.sleep(1000);
	}
	
	// Logout
	public void logOut() throws InterruptedException {
		myAccountList.get(myAccountList.size()-1).click();
		Thread.sleep(1000);
	}
		
	//--* FOOTER METHODS
	
	// Get WebElement of 'linkText' in the information footer list
	public WebElement getInformationLink(String linkText) {
		for(int i=0;i<informationFooterList.size();i++) {
			if(informationFooterList.get(i).getText().equals(linkText)) {
				return informationFooterList.get(i);
			}
		}
		return null;
	}
	
	// Click on 'linkText' in the information footer list
	public void clickInformationLink(String linkText) {
		for(int i=0;i<informationFooterList.size();i++) {
			if(informationFooterList.get(i).getText().equals(linkText)) {
				informationFooterList.get(i).click();
			}
		}
	}
		
	// Get WebElement of 'linkText' in the account footer list
	public WebElement getAccountLink(String linkText) {
		for(int i=0;i<myAccountFooterList.size();i++) {
			if(myAccountFooterList.get(i).getText().equals(linkText)) {
				return myAccountFooterList.get(i);
			}
		}
		return null;
	}
	
	// Click on 'linkText' in the account footer list
	public void clickAccountLink(String linkText) {
		for(int i=0;i<myAccountFooterList.size();i++) {
			if(myAccountFooterList.get(i).getText().equals(linkText)) {
				myAccountFooterList.get(i).click();
			}
		}
	}
    
}
