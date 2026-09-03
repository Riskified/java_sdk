package com.riskified.samples.notificationServer.socket;

import com.riskified.samples.notificationServer.SampleAuthToken;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {

        // Resolved once, up front, so a misconfigured environment fails at startup rather than
        // on the first notification - and so the token is not re-read per connection.
        String authKey = SampleAuthToken.fromEnvironment();

        ServerSocket server = new ServerSocket(5000, 10, InetAddress.getByName("127.0.0.1"));
        System.out.println("HTTP Server Waiting for client on port 5000");

        while (true) {
            Socket connected = server.accept();
            (new HTTPPOSTServer(connected, authKey)).start();
        }
    }
}
