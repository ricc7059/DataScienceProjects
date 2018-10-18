# Clusting with k-Means in Spark ML in Python

# import needed modules
from pyspark.mllib.linalg import Vectors
from pyspark.ml.feature import StandardScaler
from pyspark.ml import Pipeline
from pyspark.sql import Row
from pyspark.ml.clustering import KMeans

# load data
rawdata = sc.textFile('hdfs:///user/training/mldata/wine.csv')

# map rawdata to a new RDD of dense vectors, split values on delimiter
# convert values to float prior to casting to vector
vecrdd = rawdata.map(lambda x: Vectors.dense([float(i) for i in x.split(',')]))

# convert vecrdd to dataframe with single column called 'features'
rowrdd = vecrdd.map(lambda x: Row(features = x))
df = rowrdd.toDF()

# create a StandardScaler transformer to scale and center the data
# set the following parameters: withMean and withStd to 'True'
# inputCol to 'features' and outputCol to 'scaled'
ss = StandardScaler(withMean = True, withStd = True, inputCol = 'features', outputCol = 'scaled')

# create a KMeans estimator with the following parameters:
# k to 3 and featuresCol to 'scaled'
km = KMeans(k = 3, featuresCol = 'scaled')

# instantiate a Pipeline object
# set stages to ss and km
pl = Pipeline(stages = [ss, km])

# call the fit and transform methods of the Pipeline object to create
# a new dataframe that contains the prediction for each data point
clusters = pl.fit(df).transform(df)

# collect all of the predictions to the driver, and print them to screen
for line in clusters.select("prediction").collect():
		print line