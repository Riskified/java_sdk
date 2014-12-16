package main.java.com.riskified.models;

public class ShippingLine {
  public double price;
  public String title;
  public String code;
  public String source;
  public TaxLines taxLines;
  
  public ShippingLine(double price, String title){
    this.price = price;
    this.title = title;
  }
}
