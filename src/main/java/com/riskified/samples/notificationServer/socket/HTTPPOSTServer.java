package com.riskified.samples.notificationServer.socket;

import com.riskified.notifications.Notification.NotificationOrder;
import com.riskified.notifications.NotificationFormater;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class HTTPPOSTServer extends Thread {

    Socket connectedClient = null;
    BufferedReader inFromClient = null;
    BufferedWriter outToClient = null;

    public HTTPPOSTServer(Socket client) {
        connectedClient = client;
    }

    public void run() {

        int contentLength = 0;
        String hash = null;

        try {
            inFromClient = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));
            outToClient = new BufferedWriter(new OutputStreamWriter(connectedClient.getOutputStream()));

            String headerLine = inFromClient.readLine();
            StringTokenizer tokenizer = new StringTokenizer(headerLine);
            String httpMethod = tokenizer.nextToken();

            if (httpMethod.equals("POST")) {

                String userInput;
                while ((userInput = inFromClient.readLine()) != null) {
                    if (userInput.equals("")) {
                        break;
                    }
                    if (userInput.startsWith("Content-Length:")) {
                        contentLength = Integer.parseInt(userInput.split(" ")[1]);
                    }
                    if (userInput.startsWith("X-Riskified-Hmac-Sha256:")) {
                        hash = userInput.split(" ")[1];
                    }
                }

                int read;
                String body = "";
                while ((read = inFromClient.read()) != -1) {
                    body = body + (char) read;
                    if (body.length() == contentLength) {
                        break;
                    }
                }
                NotificationFormater formatter = new NotificationFormater("123");
                NotificationOrder notification = formatter.toObject(body, hash).order;
                sendResponse(200, "<HTML><BODY>Merchant Received Notification For Order" + notification.id
                        + "with status" + notification.status + "and description " + notification.description
                        + "</BODY></HTML>");

            }
        } catch (Exception e) {
            sendResponse(500, "<HTML><BODY>Merchant couldn't parse notification message</BODY></HTML>");

            e.printStackTrace();
        }

    }

    public void sendResponse(int statusCode, String responseString) {

        String statusLine = null;
        String contentLengthLine = null;
        String contentTypeLine = "Content-Type: text/html" + "\r\n";

        if (statusCode == 200) {
            statusLine = "HTTP/1.1 200 OK" + "\r\n";
        } else {
            statusLine = "HTTP/1.1 404 Not Found" + "\r\n";
        }

        contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";

        try {
            outToClient.write(statusLine);
            outToClient.write(contentTypeLine);
            outToClient.write(contentLengthLine);
            outToClient.write("Connection: close\r\n");
            outToClient.write("\r\n");

            outToClient.write(responseString);

            outToClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
