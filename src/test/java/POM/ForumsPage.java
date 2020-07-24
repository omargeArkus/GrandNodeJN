package POM;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import POJO.ForumTopic;

public class ForumsPage  extends PageObject{
	
	// Forums page
	@FindBy (xpath = "//div[@class='forums-table-section forum-group']/div")  private List<WebElement> forumGroups;
	@FindBy (xpath = "//div[@class='forums-table-section forum-group']/h5/a") private List<WebElement> forumGroupsTitle;
	
	// Forum page
	@FindBy (xpath = "//div[@class='topic-title']/a") private List<WebElement> topicLinks;
	
	// Topic page
	@FindBy (xpath = "//div[@class='forum-actions']/div/a") private WebElement newTopicButton;
	@FindBy (id = "watch-forum") private WebElement watchForumButton;
	
	// New topic form
	@FindBy (id = "Subject") private WebElement topicTitle;
	@FindBy (id = "Text")    private WebElement topicBody;
	@FindBy (id = "TopicTypeId") private WebElement priorityDropDown;
	@FindBy (xpath = "//input[@id='Subscribed']/parent::label")  private WebElement notifyCheckbox;
	@FindBy (xpath = "//input[@value='Submit']")  private WebElement submitButton;
	
	// Posts page
	@FindBy (xpath = "//div[@class='page forum-topic-page']/h2") private WebElement generalTopicTitle;
	@FindBy (linkText = "Edit Topic")   private List<WebElement> editTopicButton;
	@FindBy (xpath = "//a[contains(@class,'delete-topic-button')]") private List<WebElement> deleteTopicButton;
	@FindBy (linkText = "Move Topic")   private List<WebElement> moveTopicButton;
	@FindBy (linkText = "Reply")		private List<WebElement> replyButton;
	@FindBy (linkText = "Edit Post")	private List<WebElement> editPostButton;
	@FindBy (id = "ForumSelected") 		private WebElement ForumsDropDown;
	@FindBy (xpath = "//a[@class='btn btn-secondary delete-post-button']") private List<WebElement> deletePostButton;
	@FindBy (linkText = "Quote") private List<WebElement> quotePostButton;
	@FindBy (xpath = "//div[@class='topic-post']") private List<WebElement> postsList;
	@FindBy (xpath = "//span[@class='col vote up ']/i") private List<WebElement> voteUpList;
	@FindBy (xpath = "//span[contains(@class,'col vote up')]/following-sibling::div") private List<WebElement> votesCountList;
	@FindBy (xpath = "//span[@class='col vote down ']/i") private List<WebElement> voteDownList;

	// New post form
	@FindBy (xpath = "//textarea[@id='Text']") private WebElement postBody;
	@FindBy (xpath = "//input[@id='Subscribed']/parent::label") private WebElement notifyPostsCheckbox;
	@FindBy (xpath = "//input[@value='Submit']")  private WebElement submitPostButton;
	
	public ForumsPage(WebDriver driver) {
		super(driver);
	}
	
	//--* Forums Page
	
	public boolean forumGroupIsShown(String forumGroupName) {
		for(int i=0;i<forumGroupsTitle.size();i++) {
			if(forumGroupsTitle.get(i).getText().equals(forumGroupName)) {
				return true;
			}
		}
		return false;
	}
	
	// This method clicks on forum name of specific forum group **Need pre-review
	public String navigateTo(String forumGroup, String forum) throws InterruptedException {
		for(int i=0;i<forumGroupsTitle.size();i++) {
			if(forumGroupsTitle.get(i).getText().equals(forumGroup)) {
				List<WebElement> forums = forumGroups.get(i).findElements(By.xpath("//div[@class='forum-title']/a"));
				for(int j=0;j<forums.size();j++) {
					if(forums.get(j).getText().equals(forum)) {
						forums.get(j).click();
						Thread.sleep(1500);
						return "Successful redirect";
					}
					
				}
			}
		}
		Thread.sleep(1000);
		return "Forum was not found";
	}
	
	//--* Forum page
	
	// Open topic with title 'topicTitle'
	public void openTopic(String topicTitle) throws InterruptedException {
		for(int i=0;i<topicLinks.size();i++) {
			if(topicLinks.get(i).getText().equals(topicTitle)) {
				topicLinks.get(i).click();
				Thread.sleep(2000);
				break;
			}
		}
	}
	
	// Validate if specific forum name is displayed
	public boolean topicIsShown(String forumName) {
		for(int i=0;i<topicLinks.size();i++) {
			if(topicLinks.get(i).getText().equals(forumName)) {
				return true;
			}
		}
		return false;
	}
	
	//--* Topic Page
	
	// Return total number of topics in the current page
	public int getTotalTopics() {
		return topicLinks.size();
	}
	
	// Click on new topic
	public void newTopic() throws InterruptedException {
		newTopicButton.click();
		Thread.sleep(1000);
	}
	
	//--* New topic form
	
	// Fill topic form as admin or customer
	public void topicForm(String userType, ForumTopic topic) {
		topicTitle.clear();
		topicTitle.sendKeys(topic.getTitle());
		topicBody.clear();
		topicBody.sendKeys(topic.getBody());
		if(userType.equals("admin")) {
			priorityDropDown.click();
			Select s = new Select(priorityDropDown);
		   	s.selectByVisibleText(topic.getPriority());
		}
	   	if( (topic.getNotifications() && !notifyCheckbox.isSelected()) || (notifyCheckbox.isSelected()) && !topic.getNotifications()) {
	  		notifyCheckbox.click();
	   	}
	}
	
	// Send topic form, return topic title if was posted correctly
	public String SubmitTopic() throws InterruptedException {
		submitButton.click();
		Thread.sleep(1000);
		return generalTopicTitle.getText();
	}
	
	//--* Posts page
	
	// Get total number fo posts (including main post topic)
	public int getTotalPosts() {
		return postsList.size();
	}
	
	// Click on Reply button
	public void replyButton(int index) throws InterruptedException {
		replyButton.get(index).click();
		Thread.sleep(1000);
	}
	
	// Click on Edit topic button
	public void editTopicButton() throws InterruptedException {
		editTopicButton.get(0).click();
		Thread.sleep(1000);
	}
	
	// Click on Move topic button
	public void moveTopicButton() throws InterruptedException {
		moveTopicButton.get(0).click();
		Thread.sleep(1000);
	}
	
	// Move topic to new Forum Group and Forum
	public void moveTopicTo(String forumTitle) {
		ForumsDropDown.click();
		String option = "--";
		Select s = new Select(ForumsDropDown);
	   	s.selectByVisibleText(option.concat(forumTitle));	
	}
	
	// Click on Delete topic button
	public void deleteTopicButton() throws InterruptedException {
		deleteTopicButton.get(0).click();
		Thread.sleep(1000);
	}
	
	// Click on Quote button
	public void quotePostButton(int index) throws InterruptedException {
		quotePostButton.get(index).click();
		Thread.sleep(1000);
	}
	
	// Click on Edit Post button of post in 'index' position
	public void editPostButton(int index) throws InterruptedException {
		editPostButton.get(index).click();
		Thread.sleep(1000);
	}
	
	// Click on delete post button
	public void deletePostButton(int index) throws InterruptedException {
		deletePostButton.get(index).click();
		Thread.sleep(1000);
	}
	
	// Increase votes of the post in 'postPosition' position, return final votes
	public int voteUp(int additionalVotes, int postPosition) throws InterruptedException {
		for(int i=0;i<additionalVotes;i++) {
			voteUpList.get(postPosition).click();
		}
		Thread.sleep(2000);
		return Integer.valueOf(votesCountList.get(postPosition).getText());
	}
	
	// Decrease votes of the post in 'postPosition' position, return final votes
	public int voteDown(int negativeVotes, int postPosition) throws InterruptedException {
		for(int i=0;i<negativeVotes;i++) {
			voteDownList.get(postPosition).click();
		}
		Thread.sleep(2000);
		return Integer.valueOf(votesCountList.get(postPosition).getText());
	}
	
	// Return number of votes of the post in 'postPosition' position
	public int getVotesCount(int postPosition) {
		return Integer.valueOf(votesCountList.get(postPosition).getText());
	}
	
	//--* New post form
	
	// Fill post form
	public void fillPostForm(String body, Boolean notifications, Boolean replaceContent) {
		if(replaceContent) {
			postBody.clear();
		}
		postBody.sendKeys(body);
		if( (!notifyPostsCheckbox.findElement(By.xpath("//input[@id='Subscribed']")).isSelected() && notifications) || (!notifications && notifyPostsCheckbox.findElement(By.xpath("//input[@id='Subscribed']")).isSelected()) ) {
			notifyPostsCheckbox.click();
		}
	}
	
	// Send post form, return body of last published post body
	public String submitPost() throws InterruptedException {
		submitPostButton.click();
		Thread.sleep(1000);
		List<WebElement> body = postsList.get(postsList.size()-1).findElements(By.xpath("//div[@class='post-text pt-2']"));
		return body.get(body.size()-1).getText();
	}
	
	public void submitButton() throws InterruptedException {
		submitPostButton.click();
		Thread.sleep(1000);
	}
	
}
