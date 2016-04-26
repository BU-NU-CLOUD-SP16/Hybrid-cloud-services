package neu.hybrid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * This class is used for setting user defined configuration
 * @author Akash Singh
 * @maintainer Vignesh Shanmuganathan
 *
 */
public class SystemProperties {

	private static Hashtable<String, String> systemProperties = new Hashtable<String, String>();
	private static ArrayList<String> sensitiveTags = new ArrayList<String>();

	public static void updateSystemProperties(File f) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(f));

		String line;
		while ((line = br.readLine()) != null)
		{
			int separatorIndex = line.indexOf(SYSTEM_PROPERTY_SEPARATOR);
			if (SENSITIVE_TAGS_ID.equals(line.substring(0, separatorIndex)))
			{
				String tags[] = line.substring(separatorIndex + 1).split(","); 
				for (String tag: tags)
				{
					sensitiveTags.add(tag);
				}
			}
			else
			{
				systemProperties.put(line.substring(0, separatorIndex), line.substring(separatorIndex + 1));
			}
		}

		br.close();
	}

	public static String getSystemProperty(String property)
	{
		return systemProperties.get(property);
	}

	public static String getSystemProperty(String property, String defaultValue)
	{
		String propertyValue = systemProperties.get(property);
		return (propertyValue == null) ? defaultValue : propertyValue;
	}

	public static ArrayList<String> getSensitiveTags()
	{
		return sensitiveTags;
	}

	public class PropertyFile
	{
		public static final String TWITTER_CONFIG_FILE = "twitter4jproperties.txt";
	}

	public static final char SYSTEM_PROPERTY_SEPARATOR 				= '=';
	public static final char SENSITIVE_TAG_SEPARATOR 				= ',';
	public static final String SENSITIVE_TAGS_ID					= "cloud.sensitivetags";
	public static final String AUTOSCALE							= "cloud.autoscale";
	public static final String AUTOSCALETIME						= "cloud.autoscaletime";
	public static final String SENSITIVE_DATA_PATH                  = "data.sensitive.path";
	public static final String NON_SENSITIVE_DATA_PATH              = "data.non-sensitive.path";
	public static final String PUBLIC_CLOUD_LOGIN                   = "public.cloud.login.user";
	public static final String PUBLIC_CLOUD_MASTER_IP               = "public.cloud.master.ip";

}