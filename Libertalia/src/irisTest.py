from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
from tensorflow.contrib import layers

import os
import urllib

import numpy as np
import tensorflow as tf

tf.logging.set_verbosity(tf.logging.INFO)

# Data sets
#IRIS_TRAINING = "iris_training.csv"
IRIS_TRAINING = "LibertaliaData/bigTrainFile1"
IRIS_TRAINING_URL = "http://download.tensorflow.org/data/iris_training.csv"

#IRIS_TEST = "iris_test.csv"
IRIS_TEST = "LibertaliaData/bigTestFile1"
IRIS_TEST_URL = "http://download.tensorflow.org/data/iris_test.csv"

def main():
  # If the training and test sets aren't stored locally, download them.
  if not os.path.exists(IRIS_TRAINING):
    raw = urllib.urlopen(IRIS_TRAINING_URL).read()
    with open(IRIS_TRAINING, "w") as f:
      f.write(raw)

  if not os.path.exists(IRIS_TEST):
    raw = urllib.urlopen(IRIS_TEST_URL).read()
    with open(IRIS_TEST, "w") as f:
      f.write(raw)

  # Load datasets.
  training_set = tf.contrib.learn.datasets.base.load_csv_with_header(
      filename=IRIS_TRAINING,
      target_dtype=np.int,
      features_dtype=np.int8)
  test_set = tf.contrib.learn.datasets.base.load_csv_with_header(
      filename=IRIS_TEST,
      target_dtype=np.int,
      features_dtype=np.int8)
  
  sess = tf.Session()
  # Specify that all features have real-value data
  feature_columns = [tf.contrib.layers.real_valued_column("", dimension=293)]

  # Build 3 layer DNN with 10, 20, 10 units respectively.
  classifier = tf.contrib.learn.DNNClassifier(feature_columns=feature_columns,
                                              hidden_units=[20, 40, 20],
                                              n_classes=2,
                                              model_dir="/tmp/newNetwork")
  # Define the training inputs
  def get_train_inputs():
    x = tf.constant(training_set.data)
    y = tf.constant(training_set.target)

    return x, y

  print(training_set.target)
  # Fit model.
  classifier.fit(input_fn=get_train_inputs, steps=7000)
  print(classifier.get_variable_names)
  tfrecord_serving_input_fn = tf.contrib.learn.build_parsing_serving_input_fn(layers.create_feature_spec_for_parsing(feature_columns))  
  #classifier.export_savedmodel(export_dir_base="/tmp/test", serving_input_fn = tfrecord_serving_input_fn,as_text=True)
  
  # Define the test inputs
  def get_test_inputs():
    x = tf.constant(test_set.data)
    y = tf.constant(test_set.target)

    return x, y

  # Evaluate accuracy.
  accuracy_score = classifier.evaluate(input_fn=get_test_inputs,
                                       steps=1)["accuracy"]

  print("\nTest Accuracy: {0:f}\n".format(accuracy_score))

  # Classify two new flower samples.
  def new_samples():
    return np.array(
      [[2,1,5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,4,3,4,5,1,2,17,15,0,5,0,5,0,0,4,0,0,5,4,0,5,0,5,1,1,0,0,4,0,0,0,0,5,3,1,0,2,3,4,4,0,2,6,0,0,0,0,0,0,4,13,0,5,0,5,0,0,4,0,0,5,1,0,1,0,3,5,4,0,0,1,0,0,0,0,5,4,1,0,2,4,6,6,0,5,5,0,0,0,0,0,0,19,13,0,4,0,5,0,0,1,0,0,5,5,0,4,0,4,5,1,0,0,1,0,0,0,0,5,4,1,0,1,3,3,4,0,0,0,0,0,0,0,0,0,26,15,0,3,0,5,0,0,3,0,0,3,5,0,5,0,1,5,1,0,0,3,0,0,0,0,3,1,1,0,5,1,6,3,1,0,3,0,0,0,0,0,0,13,12,0,5,0,4,0,0,1,0,0,4,5,0,5,0,1,5,1,0,0,1,0,0,0,0,5,4,1,0,5,3,6,6,4,5,3,0,0,0,0,0,0,31,14,0,4,0,5,0,0,1,0,0,5,5,0,4,0,3,5,3,0,0,1,0,0,0,0,5,3,1,0,5,1,0,0,3,3,1,0,0,0,0,0,0,0,29],
       [6,1,5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,4,3,4,5,1,2,17,15,0,5,0,5,0,0,4,0,0,5,4,0,5,0,5,1,1,0,0,4,0,0,0,0,5,3,1,0,2,3,4,4,0,2,6,0,0,0,0,0,0,4,13,0,5,0,5,0,0,4,0,0,5,1,0,1,0,3,5,4,0,0,1,0,0,0,0,5,4,1,0,2,4,6,6,0,5,5,0,0,0,0,0,0,19,13,0,4,0,5,0,0,1,0,0,5,5,0,4,0,4,5,1,0,0,1,0,0,0,0,5,4,2,0,1,3,3,4,0,0,0,0,0,0,0,0,0,26,15,0,3,0,5,0,0,3,0,0,3,5,0,5,0,2,5,1,0,0,3,0,0,0,0,3,1,1,0,5,1,6,3,1,0,3,0,0,0,0,0,0,13,12,0,5,0,4,0,0,2,0,0,4,5,0,5,0,1,5,1,0,0,1,0,0,0,0,5,4,1,0,5,3,6,6,4,5,3,0,0,0,0,0,0,31,14,0,4,0,5,0,0,1,0,0,5,5,0,4,0,3,5,3,0,0,1,0,0,0,0,5,3,2,0,5,1,0,0,3,3,1,0,0,0,0,0,0,0,27]], dtype=np.int8)

  predictions = list(classifier.predict_proba(input_fn=new_samples))
  #print (classifier.predict_scores(new_samples))
  print(
      "New Samples, Class Predictions:    {}\n"
      .format(predictions))
  #saver = tf.train.Saver()
  #saver.save(sess, "/tmp/sessionSave")
  #classifier.export_savedmodel("/tmp/savedModelThing")  
  classifier.export_savedmodel(export_dir_base="/tmp/test", serving_input_fn = tfrecord_serving_input_fn,as_text=True)

if __name__ == "__main__":
    main()
