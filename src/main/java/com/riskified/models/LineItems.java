package main.java.com.riskified.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.jar.Attributes;

public class LineItems {
  Float price;
  Integer quantity;
  String title;
  String sku;
  String productId;
  String fulfillmentService;
  String fulfillmentStatus;
  Float grams;
  String id;
  String variantId;
  String variantTitle;
  String variantInventoryManagement;
  String vendor;
  String name;
  Boolean requiresShipping;
  Boolean taxable;
  Boolean productExists;
  ArrayList<Attributes> properties;
  ArrayList<TaxLines> taxLines;
  String eventSubCategoryName;
  String eventName;
  String eventSectionName;
  Date eventDate;
}
