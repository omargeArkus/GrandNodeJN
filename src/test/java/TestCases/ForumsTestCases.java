package TestCases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import POJO.ForumTopic;
import POM.ForumsPage;
import POM.HomePage;
import POM.LoginPage;
import POM.PageObject;
import Utilities.ExtentReport;

public class ForumsTestCases {
	
	protected static WebDriver driver;
	protected static Properties properties;
	protected static ExtentReport reporter;
	
	@BeforeClass
	public static void SetUp() throws IOException {		
		// Set up chrome driver
		System.setProperty("webdriver.chrome.driver", ".\\src\\test\\resources\\Drivers\\chromedriver.exe"); 
		driver = new ChromeDriver ();		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);		
		driver.manage().window().maximize();	    
		properties = new Properties();
		FileInputStream fis = new FileInputStream(".\\src\\test\\java\\Utilities\\data.properties");
		properties.load(fis);
	    driver.get(properties.getProperty("storeUrl").toString());
	    // Set up extent report
		reporter = new ExtentReport(driver);
		reporter.setProperties("ForumsReport", "Automation Report - Forums", "Front-End report for Forums feature", properties.getProperty("storeUrl").toString());
	}
	
	@AfterClass
	public static void terminateInstance() throws InterruptedException {
		// Kill driver process
		driver.close();
		driver.quit();
		// finish report
		reporter.endReport();
	}
	
	@AfterMethod
	public void TearDown (ITestResult result) throws IOException {
		// Add test result to the extent report
		reporter.TearDown(result);
	}
	
	@Test (description = "All forums group created must be displayed in the Forums page", 
			   groups  = {"forums","regression","happyPath"},
			   priority = 0)
	// GN_FrontEnd_Forums_Groups
	public void forumGroups() {
		reporter.createTest("GN_FrontEnd_Forums_Groups");
		List<String> forumGroupsName = new ArrayList<String>();
		forumGroupsName.add("Help & Support");
		forumGroupsName.add("General");
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage homePage = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Validate each Forum Group is displayed
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		for(int i=0; i<forumGroupsName.size(); i++) {
			Assert.assertTrue(forumsPage.forumGroupIsShown(forumGroupsName.get(i)));
		}
	}
	
	@Test (description="Customer must be able to post a topic into the Forum topics", 
		   groups   = {"forums","regression","happyPath"},
		   priority = 1)
	// GN_FrontEnd_Forums_Offline session_Post a topic
	public void postTopicWithOfflineSession() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Forums_Offline session_Post a topic");
		String forumGroup = "Help & Support";
		String forum = "Shipping Info";
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage homePage = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Open one forum
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		forumsPage.navigateTo(forumGroup, forum);
		// Post a topic
		String title = "Automated topic"; 
		String body  = "Automated topic body";
		String priority = "Normal";
		Boolean notifications=true;
		POJO.ForumTopic topic = new ForumTopic(title, body, priority, notifications);
		forumsPage.newTopic();
		// Required login
		POM.LoginPage loginPage = new LoginPage(driver);
		String email = "mgmartinez@arkusnexus.com";
		String pass  = "alltesti";
		loginPage.login(email, pass);
		Thread.sleep(1000);
		forumsPage.topicForm("customer",topic);
		String postedForumTitle = forumsPage.SubmitTopic();
		// Validate that the topic was sent
		Assert.assertTrue(postedForumTitle.equals(topic.getTitle()));
		Thread.sleep(1000);
		homePage.userIcon();
		homePage.logOut();
	}
	
	@Test (description="Customer must be able to post a topic into the Forum topics", 
		   groups   = {"forums","regression","happyPath"},
		   priority = 2)
	// GN_FrontEnd_Forums_Online session_Post a topic
	public void postTopicWithOnlineSession() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Forums_Online session_Post a topic");
		String forumGroup = "Help & Support";
		String forum = "Shipping Info";
		// Login with valid credentials
		POM.HomePage homePage = new HomePage(driver);
		homePage.userIcon();
		homePage.loginButton();
		POM.LoginPage loginPage = new LoginPage(driver);
		String email = "jlara@arkusnexus.com";
		String pass  = "wrBKm394uNrhSQ!n";
		loginPage.login(email, pass);
		Thread.sleep(1000);		
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Open one forum
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		forumsPage.navigateTo(forumGroup, forum);
		// Post a topic
		String title = "Automated topic 2"; 
		String body  = "Automated topic body 2";
		String priority = "Announcement";
		Boolean notifications=true;
		POJO.ForumTopic topic = new ForumTopic(title, body, priority, notifications);
		forumsPage.newTopic();
		forumsPage.topicForm("admin",topic);
		String postedForumTitle = forumsPage.SubmitTopic();
		// Validate that the topic was sent
		Assert.assertTrue(postedForumTitle.equals(topic.getTitle()));
	}
	
	@Test (description="The user must be able to reply topic posts", 
			   groups   = {"forums","regression","happyPath"},
			   priority = 3)
	// GN_FrontEnd_Forums_Reply topic
	public void replyTopic() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Forums_Reply topic");
		String forumGroup = "Help & Support";
		String forum = "Shipping Info";
		String topicTitle = "Automated topic";
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage homePage = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Open one forum
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		forumsPage.navigateTo(forumGroup, forum);
		// Open one topic
		forumsPage.openTopic(topicTitle);
		// Reply the topic
		forumsPage.replyButton(forumsPage.getTotalPosts()-1);
		String postContent = "Reply content body";
		forumsPage.fillPostForm(postContent, true, false);
		String postedBody = forumsPage.submitPost();
		// Validate that last posted body equals to the posted content
		Assert.assertTrue(postedBody.equals(postContent));
	}
	
	@Test (description="The user must be able to reply topic posts quoting another reply", 
			   groups   = {"forums","regression","happyPath"},
			   priority = 4)
	// GN_FrontEnd_Forums_Quote topic
	public void quotePost() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Forums_Quote topic");
		String forumGroup = "Help & Support";
		String forum = "Shipping Info";
		String topicTitle = "Automated topic";
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage homePage = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Open one forum
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		forumsPage.navigateTo(forumGroup, forum);
		// Open one topic
		forumsPage.openTopic(topicTitle);
		// Quote last post
		forumsPage.quotePostButton(forumsPage.getTotalPosts()-1);
		String postContent = "Content of post with quotation";
		forumsPage.fillPostForm(postContent, false, false);
		String postedBodyWithQuotation = forumsPage.submitPost();
		Assert.assertTrue(postedBodyWithQuotation.contains(postContent));
	}
	
	@Test (description="Users must be able to add votes to the useful posts of other users.", 
			   groups   = {"forums","regression","happyPath"},
			   priority = 5)
	// GN_FrontEnd_Forums_Vote for one post
	public void increasePostVotes() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Forums_Vote for one post");
		String forumGroup = "Help & Support";
		String forum = "Shipping Info";
		String topicTitle = "Automated topic";
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage homePage = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Open one forum
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		forumsPage.navigateTo(forumGroup, forum);
		// Open one topic
		forumsPage.openTopic(topicTitle);
		// Increase votes of the post
		int additionalVotes = 1;
		int startVotes = forumsPage.getVotesCount(0);
		int finalVotes = forumsPage.voteUp(additionalVotes,0);
		if(startVotes == (finalVotes-additionalVotes)) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}
	
	@Test (description="Users must be able to remove votes to the posts of other users.", 
			   groups   = {"forums","regression","happyPath"},
			   priority = 6)
	// GN_FrontEnd_Forums_Decrease votes to one post
	public void decreasePostVotes() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Forums_Decrease votes to one post");
		String forumGroup = "Help & Support";
		String forum = "Shipping Info";
		String topicTitle = "Automated topic";
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage homePage = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Open one forum
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		forumsPage.navigateTo(forumGroup, forum);
		// Open one topic
		forumsPage.openTopic(topicTitle);
		// Decrease votes of the post
		int negativeVotes = 1;
		int startVotes = forumsPage.getVotesCount(0);
		int finalVotes = forumsPage.voteDown(negativeVotes,0);
		if(finalVotes == (startVotes-negativeVotes)) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}
	
	@Test (description="Admin user must be able to edit posts", 
			   groups   = {"forums","regression","happyPath"},
			   priority = 7)
	// GN_FrontEnd_Forums_Edit own post
	// GN_FrontEnd_Forums_Edit customer post
	public void editPost() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Forums_Edit post");
		String forumGroup = "Help & Support";
		String forum = "Shipping Info";
		String topicTitle = "Automated topic";
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage homePage = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Open one forum
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		forumsPage.navigateTo(forumGroup, forum);
		// Open one topic
		forumsPage.openTopic(topicTitle);
		// Edit last published post
		forumsPage.editPostButton(forumsPage.getTotalPosts()-1);
		String newPostContent = "Editted content body";
		forumsPage.fillPostForm(newPostContent, true,true);
		String postedBody = forumsPage.submitPost();
		// Validate that last posted body equals to the editted post content
		Assert.assertTrue(postedBody.equals(newPostContent));
	}
	
	@Test (description="Admin user must be able to delete posts", 
			   groups   = {"forums","regression","happyPath"},
			   priority = 8)
	// GN_FrontEnd_Forums_Delete own post
	// GN_FrontEnd_Forums_Delete customer post
	public void deletePost() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Forums_Delete post");
		String forumGroup = "Help & Support";
		String forum = "Shipping Info";
		String topicTitle = "Automated topic";
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage   homePage   = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Open one forum
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		forumsPage.navigateTo(forumGroup, forum);
		// Open one topic
		forumsPage.openTopic(topicTitle);
		// Delete last post
		int startPosts = forumsPage.getTotalPosts();
		forumsPage.deletePostButton(forumsPage.getTotalPosts()-1);
		pageObject.confirm();
		int finalPosts = forumsPage.getTotalPosts();
		if(finalPosts == (startPosts-1)) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}		
	}
	
	@Test (description="", 
			   groups   = {"forums","regression","happyPath"},
			   priority = 9)
	// GN_FrontEnd_Forums_Edit topic
	public void editTopic() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Forums_Edit topic");
		String forumGroup = "Help & Support";
		String forum = "Shipping Info";
		String topicTitle = "Automated topic";
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage   homePage   = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Open one forum
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		forumsPage.navigateTo(forumGroup, forum);
		// Open one topic
		forumsPage.openTopic(topicTitle);
		// Edit main post (topic)
		forumsPage.editTopicButton();
		// Fill new topic information
		String title = "Automated topic (edited)"; 
		String body  = "Automated topic body (edited)";
		String priority = "Normal";
		Boolean notifications=false;
		POJO.ForumTopic topic = new ForumTopic(title, body, priority, notifications);
		forumsPage.topicForm("customer",topic);
		String postedForumTitle = forumsPage.SubmitTopic();
		// Validate that the topic was sent
		Assert.assertTrue(postedForumTitle.equals(topic.getTitle()));		
	}
	
	@Test (description="Admin user must be able to move forum topic posts", 
			   groups   = {"forums","regression","happyPath"},
			   priority = 10)
	// GN_FrontEnd_Forums_Move topic
	public void moveTopic() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Forums_Move topic");
		String originalForumGroup = "Help & Support";
		String originalForum = "Shipping Info";
		String topicTitle = "Automated topic 2";
		String moveToForumGroup = "General";
		String moveToForum = "Packaging & Shipping";
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage   homePage   = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Open one forum
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		forumsPage.navigateTo(originalForumGroup, originalForum);
		// Open one topic
		forumsPage.openTopic(topicTitle);
		// Move topic
		forumsPage.moveTopicButton();
		forumsPage.moveTopicTo(moveToForum);
		forumsPage.submitButton();
		// Back to Forums main page
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Validate topic is displayed in the new Forum
		forumsPage.navigateTo(moveToForumGroup, moveToForum);
		Assert.assertTrue(forumsPage.topicIsShown(topicTitle));
	}
	
	@Test (description="Admin user must be able to delete forum topic posts", 
			   groups   = {"forums","regression","happyPath"},
			   priority = 11)
	// GN_FrontEnd_Forums_Delete topic
	public void deleteTopic() throws InterruptedException {
		reporter.createTest("GN_FrontEnd_Forums_Delete topic");
		String forumGroup = "Help & Support";
		String forum = "Shipping Info";
		String topicTitle = "Automated topic (edited)";
		// Go to Forums page
		POM.PageObject pageObject = new PageObject(driver);
		POM.HomePage   homePage   = new HomePage(driver);
		pageObject.scrollDownTo(homePage.getInformationLink("Forums"));
		homePage.clickInformationLink("Forums");
		// Open one forum
		POM.ForumsPage forumsPage = new ForumsPage(driver);
		forumsPage.navigateTo(forumGroup, forum);
		// Open one topic
		forumsPage.openTopic(topicTitle);
		// Delete topic
		Thread.sleep(1000);
		int startForumTopics = forumsPage.getTotalTopics();
		forumsPage.deleteTopicButton();
		pageObject.confirm();
		Thread.sleep(1500);
		if(!forumsPage.topicIsShown(topicTitle)) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}
}
