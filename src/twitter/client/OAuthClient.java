package twitter.client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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

public class OAuthClient {

	private static final String CONSUMER_KEY = "e0aHjc3MmgpV5Q3c0YjipwMd3";
	private static final String CONSUMER_SECRET = "xXNk9yIRR76FQ9rHHejIoEvqlmPzIex9V4PuVLKUWSO31nTN6V";

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new PINJFrame().setVisible(true);
	}

	public void initOAuth() {
		Verifier v = new Verifier(verifier);
		accessToken = service.getAccessToken(requestToken, v);
		refresh();
	}

	public void updateStatus(String status) {
		OAuthRequest request = new OAuthRequest(Verb.POST,
				"https://api.twitter.com/1.1/statuses/update.json");
		request.addBodyParameter("status", status);
		service.signRequest(accessToken, request); // the access token from step
													// 4
		Response response = request.send();
		if(response.getCode() == 200) {
			refresh();
		}
	}
	
	public void refresh() {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				"https://api.twitter.com/1.1/statuses/user_timeline.json");
		service.signRequest(accessToken, request); // the access token from step
													// 4
		Response response = request.send();
		if (response.getCode() == 200) {
			JSONArray obj = new JSONArray(response.getBody());
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
