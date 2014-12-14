package notifactionServer.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notifications.Notification;
import notifications.Notification.NotificationOrder;
import notifications.NotificationFormmater;

public class NotifiactionServlet  extends HttpServlet {

	private NotificationFormmater formatter;

	public NotifiactionServlet(String authKey) {
		formatter = new NotificationFormmater(authKey);
	}
	
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
        {
    		PrintWriter out = resp.getWriter();
			try {
				NotificationOrder notification = formatter.parseServletPostRequest(req).order;
	    		
	            out.println("<HTML><BODY>Merchant Received Notification For Order " + notification.id + " with status " + notification.status +  " and description " + notification.description + "</BODY></HTML>");
			
			} catch (Exception e) {
				out.println("<HTML><BODY>Merchant couldn't parse notification message</BODY></HTML>");
				e.printStackTrace();
			}


        }


}
