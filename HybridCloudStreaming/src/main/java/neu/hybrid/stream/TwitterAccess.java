package neu.hybrid.stream;
import org.apache.spark.*;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.twitter.*;

import neu.hybrid.helper.TwitterAccessHelper;

import org.apache.spark.streaming.api.java.*;

import twitter4j.*;
import neu.hybrid.SystemProperties;
import neu.hybrid.helper.*;

/**
 * Stream live twitter feed and save to files
 * @author Surekha Jadhwani
 * @maintainer Akash Singh
 *
 */
public class TwitterAccess {

	public static void streamFromTwitter(TwitterConfiguration twitterConfig) throws Exception
	{	    
		String apiKey = twitterConfig.getConsumerKey();
		String apiSecret = twitterConfig.getConsumerSecret();
		String accessToken = twitterConfig.getAccessToken();
		String accessTokenSecret = twitterConfig.getAccessTokenSecret();

		TwitterAccessHelper.configureTwitterCredentials(apiKey, apiSecret, accessToken, accessTokenSecret);
		SparkConf conf=new SparkConf().setAppName("Stream Tweets").setMaster("local[2]");
		JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(1000));

		JavaDStream<Status> tweets = TwitterUtils.createStream(ssc);

		@SuppressWarnings("serial")
		JavaDStream<Status> statuses = tweets.filter(new Function<Status, Boolean>(){

			public Boolean call(Status status) throws Exception {
				return TwitterParseHelper.containsHashtag(status);
			}

		}); 

		@SuppressWarnings("serial")
		JavaDStream<Status> non_sensitive_statuses = tweets.filter(new Function<Status, Boolean>(){
			public Boolean call(Status status) throws Exception {
				return !TwitterParseHelper.containsHashtag(status);
			} 	
		});

		statuses.dstream().saveAsTextFiles(SystemProperties.getSystemProperty(SystemProperties.SENSITIVE_DATA_PATH)+"/Tweets",
				String.valueOf(System.currentTimeMillis()));
		non_sensitive_statuses.dstream().saveAsTextFiles(SystemProperties.getSystemProperty(SystemProperties.NON_SENSITIVE_DATA_PATH)+"/Tweets",
				String.valueOf(System.currentTimeMillis()));

		ssc.start();
		ssc.awaitTermination();
	}
}