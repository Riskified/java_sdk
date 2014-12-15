package main.java.com.riskified.models;


public class OrderWrapper<T> {
  
  T order;
  
  public OrderWrapper(T data) {
    order = data;
  }

}
