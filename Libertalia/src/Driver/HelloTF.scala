package Driver;

import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.TensorFlow;

object HelloTF {

  def main(args: Array[String]): Unit = {
      var g:Graph = new Graph()
      val value:String = "Hello from " + TensorFlow.version();

      // Construct the computation graph with a single operation, a constant
      // named "MyConst" with a value "value".
      var t:Tensor = Tensor.create(value.getBytes("UTF-8"));
      g.opBuilder("Const", "MyConst").setAttr("dtype", t.dataType()).setAttr("value", t).build();

      // Execute the "MyConst" operation in a Session.
      var s:Session = new Session(g);
      var output:Tensor = s.runner().fetch("MyConst").run().get(0);
      System.out.println(new String(output.bytesValue(), "UTF-8"));
      var trainedModel:SavedModelBundle = SavedModelBundle.load("/tmp/mymodel", "serve");
      var session:Session = trainedModel.session();
      //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      var inputs:Tensor = Tensor.create(x$1, x$2, x$3)//new Tensor(tensorflow.DT_FLOAT, new TensorShape(2,5));
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
      }
  }
}