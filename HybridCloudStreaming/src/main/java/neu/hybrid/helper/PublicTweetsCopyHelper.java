package neu.hybrid.helper;

import java.io.File;
import java.io.IOException;

import neu.hybrid.SystemProperties;

/**
 * Copy non-sensitive tweets to public cloud securely and auto-scale
 * @author Surekha Jadhwani
 * @maintainer Jaison Babu
 *
 */
public class PublicTweetsCopyHelper {

	public static void copyTweets() throws InterruptedException, IOException
	{
		int count = 0;
		double timeMultiplier = (SystemProperties.getSystemProperty(SystemProperties.AUTOSCALETIME) != null) ? 
				Double.parseDouble(SystemProperties.getSystemProperty(SystemProperties.AUTOSCALETIME)) : 
					Double.MAX_VALUE;

				int autoScaleLimit = (SystemProperties.getSystemProperty(SystemProperties.AUTOSCALE) != null) ? 
						Integer.parseInt(SystemProperties.getSystemProperty(SystemProperties.AUTOSCALE)): 
							Integer.MAX_VALUE;

						while (true)
						{
							// While the program service is running
							Thread.sleep(10000);

							String public_nonsensitive_path = SystemProperties.getSystemProperty(SystemProperties.PUBLIC_CLOUD_LOGIN) + 
									"@" + SystemProperties.getSystemProperty(SystemProperties.PUBLIC_CLOUD_MASTER_IP) + 
									":" + SystemProperties.getSystemProperty(SystemProperties.NON_SENSITIVE_DATA_PATH);
							// execute script to copy files and remove from local
							Process p = Runtime.getRuntime().exec(new String[]{"scp", "-r", 
									SystemProperties.getSystemProperty(SystemProperties.NON_SENSITIVE_DATA_PATH),
									public_nonsensitive_path});
							p.waitFor();
							Process p2 = Runtime.getRuntime().exec(new String[]{"rm", "-r", 
									SystemProperties.getSystemProperty(SystemProperties.NON_SENSITIVE_DATA_PATH)});
							// Wait for termination of script here
							p2.waitFor();

							count++;
							count %= timeMultiplier;

							if (count == 0 && 
									autoScaleLimit != Double.MAX_VALUE && 
									timeMultiplier != Integer.MAX_VALUE &&
									autoScaleLimit < getFolderSize(new File(SystemProperties.getSystemProperty(SystemProperties.SENSITIVE_DATA_PATH))))
							{
								// Limit exceeded
								// Send sensitive data to public cloud

								String public_sensitive_path = SystemProperties.getSystemProperty(SystemProperties.PUBLIC_CLOUD_LOGIN) + 
										"@" + SystemProperties.getSystemProperty(SystemProperties.PUBLIC_CLOUD_MASTER_IP) + 
										":" + SystemProperties.getSystemProperty(SystemProperties.SENSITIVE_DATA_PATH);

								Process sp = Runtime.getRuntime().exec(new String[]{"scp", "-r", 
										SystemProperties.getSystemProperty(SystemProperties.SENSITIVE_DATA_PATH),
										public_sensitive_path});
								sp.waitFor();
								Process sp2 = Runtime.getRuntime().exec(new String[]{"rm", "-r", 
										SystemProperties.getSystemProperty(SystemProperties.SENSITIVE_DATA_PATH)});
								sp2.waitFor();
							}

						}
	}

	public static long getFolderSize(File dir) 
	{
		long size = 0;

		if (dir != null && dir.listFiles() != null)
		{
			for (File file : dir.listFiles())
			{
				size += (file.isFile()) ? file.length() : getFolderSize(file);
			}
		}

		return size;
	}
}
