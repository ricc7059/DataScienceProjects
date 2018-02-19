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

public class TweetMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

  /*
   * The map method runs once for each line of text in the input file.
   * The method receives a key of type LongWritable, a value of type
   * Text, and a Context object.
   */
	
  //Adding user-defined counter
	static enum Tweet {
		NUM_TWEETS, BAD_RECORD;
	};
	
	IntWritable intwritable = new IntWritable(1);
	Text text = new Text();
	
  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

    /*
     * Convert the line, which is received as a Text object,
     * to a String object, separate each part of the tweet, and store it
     * in an array.
     */
    String[] line = value.toString().split("\\t");

    /*
     * The line.split("\\W+") call uses regular expressions to split the
     * line up by non-word characters.
     */
    if(line.length == 5){
    	try{
    		
    		for (String word : line[1].split("\\W+")) {
    			if (word.length() > 0) {
        
    				text.set(word);
    				context.write(text, intwritable);
    			}
    		}
    		context.getCounter(Tweet.NUM_TWEETS).increment(1);
    	
    	}catch(Exception e){
    		context.getCounter(Tweet.BAD_RECORD).increment(1);
    	}
    }
    else{
    	context.getCounter(Tweet.BAD_RECORD).increment(1);
    }
  }
}