package com.riskified.samples.notificationServer.servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class SampleServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler myContext = new ServletContextHandler(ServletContextHandler.SESSIONS);

        myContext.setContextPath("/");
        server.setHandler(myContext);

        myContext.addServlet(new ServletHolder(new NotificationServlet("26faa0eb6eacf889e300944c297640b68789b11c")), "/*");

        server.start();
        server.join();
    }
}
