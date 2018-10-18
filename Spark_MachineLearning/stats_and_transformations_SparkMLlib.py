# Calculating statistics and performing transformations in Spark MLLib using Python

# import needed modules
from pyspark.mllib.linalg import Vectors
from pyspark.mllib.feature import ElementwiseProduct
from pyspark.mllib.feature import StandardScaler
from pyspark.mllib.stat import Statistics

# load data
rawdata = sc.textFile("hdfs:///user/training/mldata/concrete.csv")

# convert raw data to RDD of dense vectors
# need to convert the split values to float before casting to a dense vector
vecrdd = rawdata.map(lambda x: Vectors.dense([float(i) for i in x.split(',')]))

# creating dense vector of weights to be used with an ElementWiseProduct transformer
# to be used to weight the values of vecrdd
weights = Vectors.dense([0.2,0.1,0.1,0.1,0.5,0.5,0.7,0.9,1.0])

# instantiate an ElementWiseProduct object and initialize with the weights vector
ep = ElementwiseProduct(weights)

# transform vecrdd using the transform method of the ElementWiseProduct object 
# to create an RDD of weighted values
# print the top line of each RDD to confirm that the transformation was successful
weighted = ep.transform(vecrdd)

print weighted.take(1)
print vecrdd.take(1)

# call the colStats method of the Statistics object on vecrdd and print the 
# mean, variance, and number of non-zero values
stats = Statistics.colStats(vecrdd)

print stats.mean()
print stats.variance()
print stats.numNonzeros()

# instantiate a StandardScaler object and set withMean and withStd to 'True'
ss = StandardScaler(withMean=True, withStd=True)

# call the fit method of the StandardScaler object to create a StandardScalerModel
model = ss.fit(vecrdd)

# call the transform method of the StandardScalerModel to center and scale the data
# in vecrdd RDD
scaled = model.transform(vecrdd)

# call colStats method of the Statistics object and print the mean, variance, 
# and number of non-zero values to confirm that vecrdd was scaled and centered
scaledStats = Statistics.colStats(scaled)

print scaledStats.mean()
print scaledStats.variance()
print scaledStats.numNonzeros()


