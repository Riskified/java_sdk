package main.java.com.riskified.notifications;

public class Notification {

  public NotificationOrder order;

  public class NotificationOrder {
    public String id;
    public String status;
    public String old_status;
    public String description;

  }
}
