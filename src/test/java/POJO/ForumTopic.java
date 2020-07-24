package POJO;

public class ForumTopic {
	
	/*private enum Priority{
		Normal,
		Sticky,
		Announcement
	}*/
	private String title;
	private String body;
	private String priority;
	private Boolean  notifications;
	
	public ForumTopic() {
		// TODO Auto-generated constructor stub
	}

	public ForumTopic(String title, String body, String priority, Boolean notifications) {
		super();
		this.title = title;
		this.body = body;
		this.priority = priority;
		this.notifications = notifications;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Boolean getNotifications() {
		return notifications;
	}

	public void setNotifications(Boolean notifications) {
		this.notifications = notifications;
	}

}
