package main.java.com.riskified.models;

import java.util.Date;

import main.java.com.riskified.JSONFormmater;

/**
 * Cancel Order details
 * @see http://apiref.riskified.com/curl/#actions-cancel
 */
public class CancelOrder {
  public String id;
  public String cancelReason;
  public Date cancelledAt;

}
