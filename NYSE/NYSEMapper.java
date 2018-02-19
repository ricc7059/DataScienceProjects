package stubs;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class NYSEMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {
	
	Text symbol = new Text();
	FloatWritable change = newFloatWritable();

	@Override
	public void map(LongWritable key, Text Value, Context context) throws IOException{

		Float stockPriceHigh;
		Float stockPriceLow;

		String stockRecord = value.toString();

		String[] stockRecordArray = stockRecord.split(",", -1);

		try{
			symbol.set(stockRecordArray[1]);

			stockPriceHigh = Float.parseFloat(stockRecordArray[4]);
			stockPriceLow = Float.parseFloat(stockRecordArray[5]);

			Float percentChange = ((stockPriceHigh-stockPriceLow)*100)/stockPriceLow;

			change.set(percentChange);
			context.write(symbol, change);
		}catch(Exception e)
	}

}