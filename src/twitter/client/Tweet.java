package twitter.client;

import java.util.Date;

import org.json.JSONObject;

public class Tweet {

	private String id;
	private String text;
	private User user;
	private Date createdAt;
	
	@SuppressWarnings("deprecation")
	public Tweet(JSONObject JSON) {
		id = JSON.getString("id_str");
		text = JSON.getString("text");
		user = new User((JSONObject) JSON.get("user"));
		String date = JSON.getString("created_at");
		createdAt = new Date(date);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return text + "             " + createdAt;
	}
}
