package stubs;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/* 
 * To define a map function for your MapReduce job, subclass 
 * the Mapper class and override the map method.
 * The class definition requires four parameters: 
 *   The data type of the input key
 *   The data type of the input value
 *   The data type of the output key (which is the input key type 
 *   for the reducer)
 *   The data type of the output value (which is the input value 
 *   type for the reducer)
 */

public class TweetMapper2 extends Mapper<LongWritable, Text, IntWritable, Text> {

  /*
   * The map method runs once for each line of text in the input file.
   * The method receives a key of type LongWritable, a value of type
   * Text, and a Context object.
   */
	
	
	IntWritable intwritable = new IntWritable();
	Text text = new Text();
	
  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

    /*
     * Convert the line, which is received as a Text object,
     * to a String object, separate each part of the line, and store it
     * in an array - setting the split limit to -1 allows the regex pattern to
     * be applied as many times as possible, and the array can have any length.
     */
    String[] line = value.toString().split("\\t", -1);

    int wordCount = Integer.parseInt(line[1]);
    
    intwritable.set(wordCount);
    text.set(line[0]);
    
    context.write(intwritable, text);
  }
}