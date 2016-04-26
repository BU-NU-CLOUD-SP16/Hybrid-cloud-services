package neu.hybrid;
import java.io.File;

import neu.hybrid.helper.PublicTweetsCopyHelper;
import neu.hybrid.stream.TwitterAccess;
import neu.hybrid.stream.TwitterConfiguration;

/**
 * This is the starter program for streaming job 
 * @author Surekha Jadhwani, Akash Singh
 * @maintainer Jaison Babu, Vignesh Shanmuganathan
 *
 */
public class HybridCloud {

	static TwitterConfiguration twitterConfig = new TwitterConfiguration();

	public static void main(String[] args) throws Exception {

		// Processing the system properties
		for (String argument : args)
		{
			File f = new File(argument);

			SystemProperties.updateSystemProperties(f);
		}
		// Update Twitter credentials  
		twitterConfig.setConsumerKey(SystemProperties.getSystemProperty(TwitterConfiguration.Property.CONSUMER_KEY));
		twitterConfig.setConsumerSecret(SystemProperties.getSystemProperty(TwitterConfiguration.Property.CONSUMER_SECRET));
		twitterConfig.setAccessToken(SystemProperties.getSystemProperty(TwitterConfiguration.Property.ACCESS_TOKEN));
		twitterConfig.setAccessTokenSecret(SystemProperties.getSystemProperty(TwitterConfiguration.Property.ACCESS_TOKEN_SECRET));

		if(SystemProperties.getSystemProperty(SystemProperties.NON_SENSITIVE_DATA_PATH).startsWith("hdfs://"))
		{
			// Start Twitter feed stream
			TwitterAccess.streamFromTwitter(twitterConfig);	
		}
		else
		{
			// Create two threads: 1 for Twitter feed streaming, 2 for copying to public cloud
			Thread[] threads = new Thread[2];
			for(int i=0; i<threads.length; i++)
			{
				threads[i] = new Thread(new R(i));
			}

			for(Thread t: threads)
			{
				try{
					t.start();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			for(Thread t: threads)
			{
				try {
					t.join(); // wait for previous thread to finish
				} catch (InterruptedException e) {
					new Error(e);
				}
			}
		}
	}

	/**
	 * 
	 * @author Akash Singh
	 * @maintainer Surekha Jadhwani
	 */
	static class R implements Runnable {

		int c;

		public R(int i) {
			c=i;
		}
		public void run()
		{  
			if(c == 0)
			{
				try{
					TwitterAccess.streamFromTwitter(twitterConfig);	
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			else
			{
				try{
					PublicTweetsCopyHelper.copyTweets();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

}