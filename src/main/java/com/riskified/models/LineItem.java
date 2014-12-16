package main.java.com.riskified.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes;

public class LineItem {
  
  public LineItem(double price, int quantity, String title, String productId, String sku) {
    properties = new ArrayList<Attributes>();
    taxLines = new ArrayList<TaxLines>();
    this.price = price;
    this.quantity = quantity;
    this.title = title;
    this.productId = productId;
    this.sku = sku; 
  }
  
  public double price;
  public Integer quantity;
  public String title;
  public String sku;
  public String productId;
  public String fulfillmentService;
  public String fulfillmentStatus;
  public Float grams;
  public String id;
  public String variantId;
  public String variantTitle;
  public String variantInventoryManagement;
  public String vendor;
  public String name;
  public Boolean requiresShipping;
  public Boolean taxable;
  public Boolean productExists;
  public List<Attributes> properties;
  public List<TaxLines> taxLines;
  public String eventSubCategoryName;
  public String eventName;
  public String eventSectionName;
  public Date eventDate;
}
