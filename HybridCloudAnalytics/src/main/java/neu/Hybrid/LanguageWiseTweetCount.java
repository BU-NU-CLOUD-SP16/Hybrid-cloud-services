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
 * Find distribution of tweets based on language
 * @author Akash Singh, Jaison Babu, Surekha Jadhwani, Vignesh Shanmuganathan
 *
 */
public class LanguageWiseTweetCount {
	public static class LanguageMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String[] tweetCol = value.toString().split(",");
			for (String s : tweetCol)
			{
				if (s != null && s.startsWith(" lang="))
				{
					String loc = s.substring("lang=".length() + 2, s.length() - 1);
					if ("null".equals(loc))
					{
						loc = "Unknown";
					}
					context.write(new Text(loc), one);
				} 
			}
		}
	}

	public static class LanguageReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

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
		conf.set("mapreduce.input.fileinputformat.input.dir.recursive","true");

		Job job = new Job(conf, "wordcount");
		job.setJarByClass(LanguageWiseTweetCount.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(LanguageMapper.class);
		job.setReducerClass(LanguageReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
		Long end = System.currentTimeMillis();
		System.out.println("Total execution time: " + (end - start) + " milliseconds.");
	}
}
