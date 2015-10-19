package twitter.client;

import org.json.JSONObject;

public class Tweet {

	private String text;
	private User user;
	
	public Tweet(JSONObject JSON) {
		text = JSON.getString("text");
		user = new User((JSONObject) JSON.get("user"));
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
	
	@Override
	public String toString() {
		return text;
	}
}
