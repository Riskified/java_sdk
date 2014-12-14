package notifactionServer.socket;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public static void main(String args[]) throws Exception {

    ServerSocket Server = new ServerSocket(5000, 10, InetAddress.getByName("127.0.0.1"));
    System.out.println("HTTP Server Waiting for client on port 5000");

    while (true) {
      Socket connected = Server.accept();
      (new HTTPPOSTServer(connected)).start();
    }
  }
}
