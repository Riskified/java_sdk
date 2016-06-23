package main.java.com.riskified.samples.notificationServer.servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class SampleServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler myContext = new ServletContextHandler(ServletContextHandler.SESSIONS);

        myContext.setContextPath("/");
        server.setHandler(myContext);

        myContext.addServlet(new ServletHolder(new NotificationServlet("636b3045e083eddf4ea9f0b7f2ed4a26")), "/*");

        server.start();
        server.join();
    }
}
