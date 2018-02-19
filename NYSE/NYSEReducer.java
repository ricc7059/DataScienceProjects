package stubs;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class NYSEReducer extends Reducer<Text, FloatWritable, Text, FloatWritable>{
	FloatWritable floatwritable = new FloatWritable();

	@Override
	public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException{
		Float maxChange = Float.MIN_VALUE;

		for(FloatWritable value : values){
			if(value.get() > maxChange){
				maxChange = value.get();
			}
		}

		floatwriable.set(maxChange);

		context.write(key, floatwritable);
	}
}