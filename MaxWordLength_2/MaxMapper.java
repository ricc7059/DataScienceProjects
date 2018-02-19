package stubs;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxMapper extends Mapper<LongWritable, Text, Text, Text> {
	
    Text letter = new Text();
	Text text = new Text();
	  

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	  
	  String line = value.toString();
	  for (String word : line.split("\\W+")) {
		  if (word.length() > 0 && word.matches("\\D+")) {
			  letter.set(word.substring(0,1).toLowerCase());
			  text.set(word.toLowerCase());
			  context.write(letter, text);
		  }
	  }
   }
}
