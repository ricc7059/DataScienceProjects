# Applying Transformations with Spark ML in Python

# import needed modules
from pyspark.mllib.linalg import Vectors
from pyspark.ml.feature import StandardScaler, PolynomialExpansion
from pyspark.ml import Pipeline
from pyspark.sql import Row

# load data
rawdata = sc.textFile("hdfs:///user/training/mldata/concrete.csv")

# split the data, and create an RDD
# convert values to float
splits = rawdata.map(lambda x: [float(i) for i in x.split(',')])

# create a Spark dataframe with two columns: labels and features
# the labels column contains the last element of the list, the features column
# contains dense vectors of all elements except the last
df = splits.map(lambda x: Row(labels=float(x[-1]), features=Vectors.dense(x[:-1]))).toDF()

# instantiate a StandardScaler object and set the following parameters:
# withMean and withStd to 'True'
# inputCol to 'features' and outputCol to 'scaledfeatures'
ss = StandardScaler(withMean=True, withStd=True, inputCol='features', outputCol='scaledfeatures')

# instantiate a PolynomialExpansion object and set the following parameters:
# degree = 2
# inputCol to 'scaledfeatures' and outputCol to 'epandedfeatures'
pe = PolynomialExpansion(degree = 2, inputCol = 'scaledfeatures', outputCol = 'expandedfeatures')

# instantiate a Pipeline object and set stages parameter to a list containing ss and pe
pl = Pipeline(stages = [ss, pe])

# call the fit method of the Pipeline transformer and create a PipelineModel
model = pl.fit(df)

# call the transform method of the PipelineModel, input the Spark dataframe df
# print column attributes to confirm the expected dataframe transformation
transformed = model.transform(df)
print transformed.columns