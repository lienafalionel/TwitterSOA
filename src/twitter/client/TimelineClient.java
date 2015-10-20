package twitter.client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class TimelineClient {

	private static final String CONSUMER_KEY = "s6mZUMKqFIzunUwDEb3uVlOFT";
	private static final String CONSUMER_SECRET = "GVEBn9MwjfehBbORQUeUcEZnBJfDH8Ct9EMhMhoYNsShtPDYJU";

	private OAuthService service;
	private Token requestToken;
	private Token accessToken;
	private String verifier;

	public void login() {
		service = new ServiceBuilder().provider(TwitterApi.class)
				.apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
		requestToken = service.getRequestToken();
		String authUrl = service.getAuthorizationUrl(requestToken);
		try {
			Desktop.getDesktop().browse(new URI(authUrl));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		PINJFrame frame = new PINJFrame();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void initOAuth() {
		Verifier v = new Verifier(verifier);
		accessToken = service.getAccessToken(requestToken, v);
		getUserTimeline();
		
		Timer t = new Timer("Twitter Updater`", false);
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Refresh Friends Timeline");
				getFriendsTimeline();
			}
		}, 1500, 75000);
	}

	public void updateStatus(String status) {
		OAuthRequest request = new OAuthRequest(Verb.POST,
				"https://api.twitter.com/1.1/statuses/update.json");
		request.addBodyParameter("status", status);
		service.signRequest(accessToken, request); 
													
		Response response = request.send();
		if(response.getCode() == 200) {
			getUserTimeline();
			getFriendsTimeline();
		}
	}
	
	public void getFriendsTimeline() {
		OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/statuses/home_timeline.json");
		service.signRequest(accessToken, request);
		
		Response response = request.send();
		if(response.getCode() == 200) {
			JSONArray array = new JSONArray(response.getBody());
			TwitterW.tweetsFriends.clear();
			for(int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				TwitterW.tweetsFriends.add(new Tweet(o));
			}
			TwitterW.getFriendsTimeline();
		}
	}
	
	public void getUserTimeline() {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				"https://api.twitter.com/1.1/statuses/user_timeline.json");
		service.signRequest(accessToken, request); // the access token from step
													// 4
		Response response = request.send();
		if (response.getCode() == 200) {
			JSONArray obj = new JSONArray(response.getBody());
			TwitterW.tweets.clear();
			for (int i = 0; i < obj.length(); i++) {
				JSONObject o = obj.getJSONObject(i);
				TwitterW.tweets.add(new Tweet(o));
			}
			TwitterW.getUserTimeline();
		}
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public OAuthService getService() {
		return service;
	}

	public void setService(OAuthService service) {
		this.service = service;
	}

	public Token getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(Token requestToken) {
		this.requestToken = requestToken;
	}
}
