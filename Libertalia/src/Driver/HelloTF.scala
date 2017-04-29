package Driver;

import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.TensorFlow;
import java.io._;

object HelloTF {

  def main(args: Array[String]): Unit = {
     // var g:Graph = new Graph()

    //  var s:Session = new Session(g);
      //var output:Tensor = s.runner().fetch("MyConst").run().get(0);
      //System.out.println(new String(output.bytesValue(), "UTF-8"));
      //System.out.println(new File(".").getCanonicalPath());
     // System.out.println(new File("C:/model/savedModel/qefqefqegqg").getCanonicalPath());
      var trainedModel:SavedModelBundle = SavedModelBundle.load("C:/model/test/backup", "serve");
      var session:Session = trainedModel.session();
      var matrix:Array[Array[Float]] = Array.ofDim[Float](1, 299);
      var input:Tensor = Tensor.create(matrix);
      var g:Graph = trainedModel.graph();
      
      var output:Array[Array[Float]] = Array.ofDim[Float](1, 2);
      
      session.runner().feed("dnn/input_from_feature_columns/input_from_feature_columns/concat", input)
      .fetch("dnn/binary_logistic_head/predictions/probabilities").run()
      .get(0).copyTo(output);
      
      println(output(0).foreach(f => println(f)));

      //session.Runner
      //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*  var inputs:Tensor = Tensor.create(x$1, x$2, x$3)//new Tensor(tensorflow.DT_FLOAT, new TensorShape(2,5));
      var x:FloatBuffer = inputs.createBuffer();
      x.put(Array(1f,2f,3f,4f,5f));
      x.put(Array(1f,2f,3f,4f,5f));
      var keepall:keepall = new Tensor(tensorflow.DT_FLOAT, new TensorShape(2,1));
      keepall.createBuffer().put(Array(1f, 1f));
      TensorVector outputs = new TensorVector();
      // to predict each time, pass in values for placeholders
      outputs.resize(0);
      s = session.Run(new StringTensorPairVector(Array("Placeholder", "Placeholder_2"), Array(inputs, keepall)),
          new StringVector("Sigmoid"), new StringVector(), outputs);
      if (!s.ok()) {
        throw new RuntimeException(s.error_message().getString());
      }
      // this is how you get back the predicted value from outputs
      FloatBuffer output = outputs.get(0).createBuffer();
      for (k <- 0 to output.limit()){
        System.out.println("prediction=" + output.get(k));
      }*/
  }
}