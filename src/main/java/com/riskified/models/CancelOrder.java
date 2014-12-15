package main.java.com.riskified.models;

import java.util.Date;

import main.java.com.riskified.JSONFormmater;

public class CancelOrder implements JsonObject {
  public String id;
  public String cancelReason;
  public Date cancelledAt;

  @Override
  public String toJson() {
    return JSONFormmater.toJson(this);
  }

}
