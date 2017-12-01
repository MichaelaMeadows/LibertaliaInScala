package Driver;

import py4j.GatewayServer;

public class PythonGateway {

  public int getState(int first, int second) {
    return first + second;
  }
  
  

  public static void main(String[] args) {
	  PythonGateway app = new PythonGateway();
    // app is now the gateway.entry_point
    GatewayServer server = new GatewayServer(app);
    server.start();
  }
}