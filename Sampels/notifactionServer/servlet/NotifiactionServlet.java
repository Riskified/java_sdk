package notifactionServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.riskified.notifications.Notification.NotificationOrder;
import main.java.com.riskified.notifications.NotificationFormmater;

public class NotifiactionServlet extends HttpServlet {

  private NotificationFormmater formatter;

  public NotifiactionServlet(String authKey) throws InvalidKeyException, NoSuchAlgorithmException {
    formatter = new NotificationFormmater(authKey);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    PrintWriter out = resp.getWriter();
    try {
      NotificationOrder notification = formatter.parseServletPostRequest(req).order;

      out.println("<HTML><BODY>Merchant Received Notification For Order " + notification.id
          + " with status " + notification.status + " and description " + notification.description
          + "</BODY></HTML>");

    } catch (Exception e) {
      resp.sendError(500, "<HTML><BODY>Merchant couldn't parse notification message</BODY></HTML>");
      e.printStackTrace();
    }

  }

}
