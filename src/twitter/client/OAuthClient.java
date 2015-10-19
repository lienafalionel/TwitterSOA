package twitter.client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
	private String verifier;
	
	public void login() {
		service = new ServiceBuilder().provider(TwitterApi.class)
				.apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
		requestToken = service.getRequestToken();
		String authUrl = service.getAuthorizationUrl(requestToken);
		System.out.println(authUrl);
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
		Token accessToken = service.getAccessToken(requestToken, v);
		
		OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/statuses/mentions_timeline.json");
		service.signRequest(accessToken, request); // the access token from step 4
		Response response = request.send();
		System.out.println(response.getBody());
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
}
