import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import twitter4j.TwitterException;

public class HybridCloud {

	public static void main(String[] args) throws Exception {
	
		// Processing the system properties
		for (String argument : args)
		{
			File f = new File(argument);
			
			//if (SystemProperties.PropertyFile.TWITTER_CONFIG_FILE.equals(f.getName()))
			//{
				SystemProperties.updateSystemProperties(f);
			//}
		}
		
		// Twitter part
		TwitterConfiguration twitterConfig = new TwitterConfiguration();
		twitterConfig.setConsumerKey(SystemProperties.getSystemProperty(TwitterConfiguration.Property.CONSUMER_KEY));
		twitterConfig.setConsumerSecret(SystemProperties.getSystemProperty(TwitterConfiguration.Property.CONSUMER_SECRET));
		twitterConfig.setAccessToken(SystemProperties.getSystemProperty(TwitterConfiguration.Property.ACCESS_TOKEN));
		twitterConfig.setAccessTokenSecret(SystemProperties.getSystemProperty(TwitterConfiguration.Property.ACCESS_TOKEN_SECRET));
		
		TwitterAccess.streamFromTwitter(twitterConfig);				
	}
}