package stubs;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxLetterMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	IntWritable wordLength = new IntWritable();
	Text text = new Text();
	  

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	  
	  String line = value.toString();
	  for (String word : line.split("\\W+")) {
		  if (word.length() > 0 && word.matches("\\D+")) {
			  text.set(word.substring(0,1).toLowerCase());
			  wordLength.set(word.length());
			  context.write(text, wordLength);
		  }
	  }
   }
}
