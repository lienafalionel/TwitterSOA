package twitter.client;

import org.json.JSONObject;

public class User {

	private String name;
	private String screenName;
	private String description;
	private String profileImageUrl;
	
	public User(JSONObject JSON) {
		name = JSON.getString("name");
		screenName = JSON.getString("screen_name");
		description = JSON.getString("description");
		profileImageUrl = JSON.getString("profile_image_url");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
}
