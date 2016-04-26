package neu.hybrid.helper;
import java.util.ArrayList;

import neu.hybrid.SystemProperties;
import twitter4j.HashtagEntity;
import twitter4j.Status;

/**
 * Parse data to segregate sensitive and non-sensitive data
 * @author Vignesh Shanmuganathan
 * @maintainer Surekha Jadhwani
 *
 */
public class TwitterParseHelper {

	public static boolean containsHashtag(Status status)
	{
		ArrayList<String> sensitiveTags = SystemProperties.getSensitiveTags();

		HashtagEntity hashTags[] = status.getHashtagEntities();
		for (HashtagEntity hashTag : hashTags)
		{
			String hashTagValue = hashTag.toString().toUpperCase();
			if (hashTagValue != null)
			{	  
				for (String sensitiveTag : sensitiveTags)
				{
					if (hashTagValue.contains(sensitiveTag.toUpperCase()))
						return true;
				}
			}
		}
		return false;
	}
}
