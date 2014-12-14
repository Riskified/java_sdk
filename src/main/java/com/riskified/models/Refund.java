package main.java.com.riskified.models;

import main.java.com.riskified.JSONFormmater;

public class Refund implements JsonObject {

  public RefundOrder order;

  @Override
  public String toJson() {
    return JSONFormmater.toJson(this);
  }

}
