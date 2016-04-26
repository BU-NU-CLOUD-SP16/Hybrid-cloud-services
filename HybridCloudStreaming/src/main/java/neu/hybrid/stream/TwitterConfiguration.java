package neu.hybrid.stream;

/**
 * Twitter developer API credentials properties
 * @author Akash Singh
 *
 */
public class TwitterConfiguration {
	
	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;
	
	public String getConsumerKey() {
		return consumerKey;
	}
	
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	
	public String getConsumerSecret() {
		return consumerSecret;
	}
	
	public void setConsumerSecret(String secretKey) {
		this.consumerSecret = secretKey;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}
	
	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}
	
	public class Property
	{
		public static final String CONSUMER_KEY = "oauth.consumerKey";
		public static final String CONSUMER_SECRET = "oauth.consumerSecret";
		public static final String ACCESS_TOKEN = "oauth.accessToken";
		public static final String ACCESS_TOKEN_SECRET = "oauth.accessTokenSecret";
	}
}