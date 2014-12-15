package main.java.com.riskified.models;

import main.java.com.riskified.JSONFormmater;

public class OrderWrapper<T> implements JsonObject {
  
  T order;
  
  public OrderWrapper(T data) {
    order = data;
  }

  @Override
  public String toJson() {
    return JSONFormmater.toJson(this);
  }

}
