# Performing Linear Regression with Spark MLlib using Python

# import needed modules
from pyspark.mllib.linalg import Vectors
from pyspark.mllib.feature import StandardScaler
from pyspark.mllib.regression import LinearRegressionWithSGD, LabeledPoint

# load data
rawdata = sc.textFile("hdfs:///user/training/mldata/concrete_train.csv")

# map rawdata RDD to an RDD of dense vectors, split values on ',', and 
# convert values to float prior to casting the resulting Python list
# as a dense vector

vecrdd = rawdata.map(lambda x: Vectors.dense([float(i) for i in x.split(',')]))

# instantiate a StandardScaler object, set withMean and withStd to 'True',
# and call the fit method on RDD vecrdd to create a StandardScalerModel
ss = StandardScaler(withMean=True, withStd=True)
model = ss.fit(vecrdd)

# call the transform method of the StandardScalerModel to scale and center vecrdd RDD
scaled = model.transform(vecrdd)

# map the scaled RDD to a new RDD of LabeledPoint - label should be the last element of the 
# Vectors in vecrdd, and the features should be all other elements
# be sure to explicitly cast the features as a dense vector before using in the 
# labeledPoint initialization
lprdd = scaled.map(lambda x: LabeledPoint(label = x[-1], features=Vectors.dense(x[:-1])))

# create three linear regression model - one with no regularization, one with L1 regularization,
# and one with L2 regularization
# set regParam to 0.2
lr_model = LinearRegressionWithSGD.train(lprdd)
lr_l1_model = LinearRegressionWithSGD.train(lprdd, regType='l1', regParam=0.2)
lr_l2_model = LinearRegressionWithSGD.train(lprdd, regType='l2', regParam=0.2)

# create an RDD of LabeledPoint, follow the procedure above, but do not create a new 
# StandardScalerModel - use the previously created StandardScalerModel to transform the new
# RDD
test_rawdata = sc.textFile('hdfs:///user/training/mldata/concrete_test.csv')
test_vecrdd = test_rawdata.map(lambda x: Vectors.dense([float(i) for i in x.split(',')]))
test_scaled = model.transform(test_vecrdd)
test_lprdd = test_scaled.map(lambda x: LabeledPoint(label = x[-1], features=Vectors.dense(x[:-1])))

# use the predict method of the previously created linear regression models to create predictions
# on the features of the test_lprdd data
# prior to calling the predict method, map the test_lprdd RDD to only features to be used as the input
# for the predict method
# view results
pred_lr = test_lprdd.map(lambda x: (x.label, lr_model.predict(x.features)))
pred_lr.collect()

pred_lr_l1 = test_lprdd.map(lambda x: (x.label, lr_l1_model.predict(x.features)))
pred_lr_l1.collect()

pred_lr_l2 = test_lprdd.map(lambda x: (x.label, lr_l2_model.predict(x.features)))
pred_lr_l2.collect()