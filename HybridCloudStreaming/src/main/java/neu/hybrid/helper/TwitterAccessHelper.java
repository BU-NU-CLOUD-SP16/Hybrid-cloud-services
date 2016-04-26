package neu.hybrid.helper;

import java.util.HashMap;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * Helper class for streaming twitter feed
 * @author Jaison Babu
 * @maintainer Akash Singh
 *
 */
public class TwitterAccessHelper {

	static {
		Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
		Logger.getLogger("org.apache.spark.storage.BlockManager").setLevel(Level.ERROR);
	}

	public static void configureTwitterCredentials(String apiKey, String apiSecret, String accessToken, String accessTokenSecret) throws Exception {
		HashMap<String, String> configs = new HashMap<String, String>();
		configs.put("apiKey", apiKey);
		configs.put("apiSecret", apiSecret);
		configs.put("accessToken", accessToken);
		configs.put("accessTokenSecret", accessTokenSecret);

		Object[] keys = configs.keySet().toArray();
		for (int k = 0; k < keys.length; k++) 
		{
			String key = keys[k].toString();
			String value = configs.get(key).trim();
			if (value.isEmpty())
			{
				throw new Exception("Error setting authentication - value for " + key + " not set");
			}
			String fullKey = "twitter4j.oauth." + key.replace("api", "consumer");
			System.setProperty(fullKey, value);
			System.out.println("\tProperty " + key + " set as [" + value + "]");
		}
		System.out.println();
	}
}
