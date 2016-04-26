package neu.Hybrid;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Count number of tweets based on location using TimeZone tag.
 * @author Akash Singh, Jaison Babu, Surekha Jadhwani, Vignesh Shanmuganathan
 *
 */
public class LocationWiseTweetCount {
	public static class LocationMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] tweetCol = value.toString().split(",");
			for (String s : tweetCol)
			{
				if (s != null && s.contains("timeZone="))
				{
					String loc = s.substring("timeZone=".length() + 2, s.length() - 1);
					if ("null".equals(loc))
					{
						loc = "Unknown";
					}
					context.write(new Text(loc), one);
				} 
			}
		}
	}

	public static class LocationReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}

	public static void main(String[] args) throws Exception {

		Long start = System.currentTimeMillis();
		Configuration conf = new Configuration();
		conf.set("mapred.textoutputformat.separator", ":");

		Job job = new Job(conf, "locationcount");
		job.setJarByClass(LocationWiseTweetCount.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(LocationMapper.class);
		job.setReducerClass(LocationReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
		Long end = System.currentTimeMillis();
		System.out.println("Total execution time: " + (end - start) + " milliseconds.");
	}
}
