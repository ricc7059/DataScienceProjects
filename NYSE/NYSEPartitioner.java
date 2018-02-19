package stubs;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class NYSEPartitioner extends Partitioner<Text, FloatWritable> {


  @Override
  public int getPartition(Text key, FloatWritable value, int numReduceTasks) {
    
	 String symbol = key.toString();
	 char c = (char) symbol.charAt(0);
	 int n = 0;
	 
	 
	 if(c == 'A'){
		 n = 0;
	 }
	 else{
		 if(c == 'B'){
			 n = 1;
		 }
	 else{
		 if(c == 'C'){
			 n = 2;
		 }
	 else{
		 if(c == 'D'){
			 n = 3;
		 }
     else{
    	 if(c == 'E'){
    		 n = 4;
    	 }
     else{
    	 if(c == 'F'){
    		 n = 5;
    	 }
     else{
    	 if(c == 'G'){
    		 n = 6;
    	 }
     else{
    	 if(c == 'H'){
    		 n = 7;
    	 }
     else{
         if(c == 'I'){
        	 n = 8;
         }
     else{
    	 if(c == 'J'){
    		 n = 9;
    	 }
     else{
    	 if(c == 'K'){
    		 n = 10;
    	 }
     else{
    	 if(c == 'L'){
    		 n = 11;
    	 }
     else{
    	 if(c == 'M'){
    		 n = 12;
    	 }
     else{
    	 if(c == 'N'){
    		 n = 13;
    	 }
     else{
    	 if(c == 'O'){
    		 n = 14;
    	 }
     else{
    	 if(c == 'P'){
    		 n = 15;
    	 }
     else{
    	 if(c == 'Q'){
    		 n = 16;
    	 }
     else{
    	 if(c == 'R'){
    		 n = 17;
    	 }
     else{
    	 if(c == 'S'){
    		 n = 18;
    	 }
     else{
    	 if(c == 'T'){
    		 n = 19;
    	 }
     else{
    	 if(c == 'U'){
    		 n = 20;
    	 }
     else{
    	 if(c == 'V'){
    		 n = 21;
    	 }
     else{
    	 if(c == 'W'){
    		 n = 22;
    	 }
     else{
    	 if(c == 'X'){
    		 n = 23;
    	 }
     else{
    	 if(c == 'Y'){
    		 n = 24;
    	 }
     else{
    	 if(c == 'Z'){
    		 n = 25;
    	 }
     }}}}}}}}}}}}}}}}}}}}}}}}}
	return n;
 }
}