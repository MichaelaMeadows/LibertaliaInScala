package Driver;

import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.TensorFlow;
import java.io._;

class TFAdapter {
  val trainedModel:SavedModelBundle = SavedModelBundle.load("pirateModel/1494570236", "serve");
  val session:Session = trainedModel.session();
  val g:Graph = trainedModel.graph();
  
  /*
   * Input: An nXStateLength matrix
   * Output: N X possible outputs matrix (currently 2)
   */
  def getExpectedMoveValues(stateInputs:Array[Array[Float]]):Array[Array[Float]] = {
    //var inputMatrix:Array[Array[Float]] = Array.ofDim[Float](1, 299);
    var input:Tensor = Tensor.create(stateInputs);
    var output:Array[Array[Float]] = Array.ofDim[Float](stateInputs.length, 2);
    session.runner().feed("dnn/input_from_feature_columns/input_from_feature_columns/concat", input)
      .fetch("dnn/binary_logistic_head/predictions/probabilities").run()
      .get(0).copyTo(output);
    return output;
  }

}

/*object HelloTF {
  def main(args: Array[String]): Unit = {
      var trainedModel:SavedModelBundle = SavedModelBundle.load("C:/model/test/pirate_1", "serve");
      var session:Session = trainedModel.session();
      var matrix:Array[Array[Float]] = Array.ofDim[Float](2, 107);
      matrix(0) = test2Value;
      matrix(1) = testValue;
      var input:Tensor = Tensor.create(matrix);
      //var g:Graph = trainedModel.graph();
      
      var output:Array[Array[Float]] = Array.ofDim[Float](2, 2);
      
      session.runner().feed("dnn/input_from_feature_columns/input_from_feature_columns/concat", input)
      .fetch("dnn/binary_logistic_head/predictions/probabilities").run()
      .get(0).copyTo(output);
      
      println(output(0).foreach(f => println(f)));
      println(output(1).foreach(f => println(f)));
  }

  private val testValue:Array[Float] = Array(2,1,5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,4,3,4,5,1,2,17,15,0,5,0,5,0,0,4,0,0,5,4,0,5,0,5,1,1,0,0,4,0,0,0,0,5,3,1,0,2,3,4,4,0,2,6,0,0,0,0,0,0,4,13,0,5,0,5,0,0,4,0,0,5,1,
0,1,0,3,5,4,0,0,1,0,0,0,0,5,4,1,0,2,4,6,6,0,5,5,0,0,0,0,0,0,19,13,0,4,0,5,0,0,1,0,0,5,5,0,4,0,4,5,1,0,0,1,0,0,0,0,5,4,1,0,1,3,3,4,0,0,0,0,0,0,0,0,0,26,15,0,3,0,5,0,0,3,0,0,3,5,0,5,0,1,5,1,0,0,3,0,0,0,0,3,1,1,0,5,1,6,3,1,0,3,0,0,0,0
,0,0,13,12,0,5,0,4,0,0,1,0,0,4,5,0,5,0,1,5,1,0,0,1,0,0,0,0,5,4,1,0,5,3,6,6,4,5,3,0,0,0,0,0,0,31,14,0,4,0,5,0,0,1,0,0,5,5,0,4,0,3,5,3,0,0,1,0,0,0,0,5,3,1,0,5,1,0,0,3,3,1,0,0,0,0,0,0,0,29);
  
  private val test2Value:Array[Float] = Array(6,1,5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,4,3,4,5,1,2,17,15,0,5,0,5,0,0,4,0,0,5,4,0,5,0,5,1,1,0,0,4,0,0,0,0,5,3,1,0,2,3,4,4,0,2,6,0,0,0,0,0,0,4,13,0,5,0,5,0,0,4,0,0,5,1,0,1,0,3,5,4,0,0,1,0,0,0,0,5,4,1,0,2,4,6,6,0,5,5,0,0,0,0,0,0,19,13,0,4,0,5,0,0,1,0,0,5,5,0,4,0,4,5,1,0,0,1,0,0,0,0,5,4,2,0,1,3,3,4,0,0,0,0,0,0,0,0,0,26,15,0,3,0,5,0,0,3,0,0,3,5,0,5,0,2,5,1,0,0,3,0,0,0,0,3,1,1,0,5,1,6,3,1,0,3,0,0,0,0,0,0,13,12,0,5,0,4,0,0,2,0,0,4,5,0,5,0,1,5,1,0,0,1,0,0,0,0,5,4,1,0,5,3,6,6,4,5,3,0,0,0,0,0,0,31,14,0,4,0,5,0,0,1,0,0,5,5,0,4,0,3,5,3,0,0,1,0,0,0,0,5,3,2,0,5,1,0,0,3,3,1,0,0,0,0,0,0,0,27);
}*/