package stubs;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MaxReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

  IntWritable maxLength = new IntWritable();    
	
  @Override
  public void reduce(Text key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {
	  
	  int length = 0;
	  int max = 0;
	  
	  for (IntWritable value : values){
		length = value.get();
		
		if(length > max){
			max = length;			
		}  
	  }
	  
	  maxLength.set(max);
	  context.write(key, maxLength);
  }
}
