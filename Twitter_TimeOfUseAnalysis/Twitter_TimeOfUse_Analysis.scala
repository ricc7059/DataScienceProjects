spark-shell --packages com.databricks:spark-csv_2.10:1.5.0

//read in dataset
scala> val tweets = sc.textFile("/SEIS736/tweets2/allTweets")

//map tweets - split by tab
scala> val splitTweet = tweets.map(x => x.split("\\t"))

//filter tweets to ensure there are 5 elements per tweet array
scala> val filterTweet = splitTweet.filter(x => x.size == 5)

//isolate date element from tweet
scala> val dates = filterTweet.map(x => x(3))

//map date elements - split by " "
scala> val dateMap = dates.map(x => x.split(" "))

//filter date array elements to ensure there are no empty/missing elements
scala> val filterDateMap = dateMap.filter(x => x.nonEmpty)

//filter time element of the date arrary to ensure all are 8 characters in length
scala> val dblFilter = filterDateMap.filter(x => (x(3).length == 8))

//isolate the dayOfTheWeek and hourOfDay - convert the hourOfDay to Int
scala> val dayTime = dblFilter.map(x => (x(0),x(3).substring(0,2).toInt))

//print three lines to verify what fields have been isolated
scala> dayTime.take(3).foreach(println)

//create tweet dataframe
scala> val tweetDF = dayTime.toDF("Day", "HourOfDay")

//verify dataframe contents (top 20 rows)
scala> tweetDF.show

//perform groupBy and count operations on dataframe and write results to csv file
 scala> tweetDF.groupBy("Day","HourOfDay").count.write.format("com.databricks.spark.csv").option("header","true").save("/user/ricci/dayHourTweetCount")

//write dataframe to parquet file
scala> val o = Map("path" -> "/user/ricci/finalProject")
scala> tweetDF.write.format("parquet").options(o).saveAsTable("fp")

--ALSO TRIED--

scala> tweetDF.write.parquet("/user/ricci/riccidf")





//replace NULL values with 000 in HourOfDay column - Day column is NULLABLE
scala> tweetDF.withColumn("HourOfDay", when($"HourOfDay".isNull,000))

//replace empty values
scala> tweetDF.withColumn("Day", when($"Day".contains(""),"empty"))

scala> tweetDF.withColumn("HourOfDay", when($"HourOfDay".contains(""),0000))