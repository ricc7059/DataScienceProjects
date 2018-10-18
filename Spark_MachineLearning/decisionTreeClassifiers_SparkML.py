# Using Decision Tree Classifiers with Spark ML in Python

# import needed modules
from pyspark.mllib.linalg import Vectors
from pyspark.ml import Pipeline
from pyspark.sql import Row
from pyspark.ml.feature import StringIndexer, VectorAssembler
from pyspark.ml.classification import DecisionTreeClassifier

# load data
rawdata = sc.textFile('hdfs:///user/training/mldata/cars_train.csv')

# map rawdata to new RDD, and split values on delimiter
lrdd = rawdata.map(lambda x: x.split(','))

# create an RDD representing each row of data - create an RDD of Rows by 
# supplying the name of each column and the corresponding element 
# from the lists in the lrdd.
rowrdd = lrdd.map(lambda x: Row(buying=x[0],
								maint=x[1],
								doors=x[2],
								persons=x[3],
								lugboots=x[4],
								safety=x[5],
								label=x[6]
								))

# convert rowrdd to a Spark dataframe
train_df = rowrdd.toDF()

# instantiate a StringIndexer transformer for each of the columns in the dataframe
si1 = StringIndexer(inputCol = 'buying', outputCol = 'buying_ix' )
si2 = StringIndexer(inputCol = 'maint', outputCol = 'maint_ix')
si3 = StringIndexer(inputCol = 'doors', outputCol = 'doors_ix')
si4 = StringIndexer(inputCol = 'persons', outputCol = 'persons_ix')
si5 = StringIndexer(inputCol = 'lugboots', outputCol = 'lugboots_ix')
si6 = StringIndexer(inputCol = 'safety', outputCol = 'safety_ix')
si7 = StringIndexer(inputCol = 'label', outputCol = 'label_ix')

# create a list containing all of the output column names except 'label_ix'
indexedcols = ["buying_ix", "maint_ix", "doors_ix", "persons_ix", "lugboots_ix", "safety_ix"]

# instantiate a VectorAssembler transformer and convert the six columns in indexedcols to 
# a single column called features
va = VectorAssembler(inputCols = indexedcols, outputCol = 'features')

# create a DecisionTreeClassifier
# set label column to 'label_ix' and feature column to the newly created 'features' column
dtc = DecisionTreeClassifier(labelCol = 'label_ix', featuresCol = 'features')

# create list containing all of the created StringIndexers, the VectorAssembler, and the 
# DecisionTreeClassifier
steps = [si1,si2,si3,si4,si5,si6,si7,va,dtc]

# instantiate a Spark ML Pipeline object, and set the stages parameter with the list 'steps'
pl = Pipeline(stages = steps)

# create a PipelineModel by calling the fit method of the Pipeline object pl
plmodel = pl.fit(train_df)

# create new dataframe called test_df - follow subsequent steps
#create rdd
raw_test = sc.textFile('hdfs:///user/training/mldata/cars_test.csv')

#perform map function to split data on separator
testrdd = raw_test.map(lambda x: x.split(','))

#perform map function to map values to rows
rowtest = testrdd.map(lambda x: Row(buying=x[0],
									maint=x[1],
									doors=x[2],
									persons=x[3],
									lugboots=x[4],
									safety=x[5],
									label=x[6]
									))

test_df = rowtest.toDF()

# create new dataframe with predictions by running the transform method of the 
# PipelineModel on test_df
predictions = plmodel.transform(test_df)

# view the first 15 predictions compared to the known label
predictions.select("label_ix", "prediction").show(15)