package stubs;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MaxReducer extends Reducer<Text, Text, Text, Text> {

		Text maxWord = new Text();
		String word = new String();
		String longestWord = new String();
	
  @Override
  public void reduce(Text key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {
	  
	  int length = 0;
	  int max = 0;
	  
	  for (Text value : values){
		word = value.toString();
		length = word.length();
		
		if(length > max){
			max = length;
			longestWord = word;
		}  
	  }
	  
	  maxWord.set(longestWord);
	  context.write(key, maxWord);
  }
}
