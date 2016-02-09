import org.apache.spark.*;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.*;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.twitter.*;

import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.dstream.DStream;

import twitter4j.*;

import java.util.ArrayList;
import java.util.Arrays;

public class TwitterAccess {
  
	public static void streamFromTwitter(TwitterConfiguration twitterConfig) throws Exception
	{
		// Checkpoint directory 
	    String checkpointDir = TwitterAccessHelper.getCheckpointDirectory();
	    
	    String apiKey = twitterConfig.getConsumerKey();
	    String apiSecret = twitterConfig.getConsumerSecret();
	    String accessToken = twitterConfig.getAccessToken();
	    String accessTokenSecret = twitterConfig.getAccessTokenSecret();
	    
	    TwitterAccessHelper.configureTwitterCredentials(apiKey, apiSecret, accessToken, accessTokenSecret);
	    SparkConf conf=new SparkConf().setAppName("Tweets Android").setMaster("local[2]");
	    JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(1000));
	   
	    JavaDStream<Status> tweets = TwitterUtils.createStream(ssc);
	      
	    JavaDStream<String> statuses = tweets.filter(new Function<Status, Boolean>(){

			@Override
			public Boolean call(Status status) throws Exception {
				ArrayList<String> sensitiveTags = SystemProperties.getSensitiveTags();
				  
				  HashtagEntity hashTags[] = status.getHashtagEntities();
				  String s = new String();
				  for (HashtagEntity hashTag : hashTags)
				  {
					  String hashTagValue = hashTag.toString().toUpperCase();
					  if (hashTagValue != null)
					  {
						  s += hashTagValue;
						  
						  for (String sensitiveTag : sensitiveTags)
						  {
							  if (hashTagValue.contains(sensitiveTag.toUpperCase()))
								  return true;
						  }
					  }
				  }
				  return false;
			} 	
	    }).map(new Function<Status, String>() 
	    {

			public String call(Status status) throws Exception {
				return status.getText();
			}
	    });
	    
	    int numTweetsCollected = 0;
	    int numTweetsToCollect = 0;
	    
	    statuses.print();
	    
	    ssc.checkpoint(checkpointDir);
	
	    ssc.start();
	}
}