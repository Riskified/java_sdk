package main.java.com.riskified.models;

import java.util.Date;

public class Customer {
  String email;
  String first_name;
  String last_name;
  Date created_at;
  Date updated_at;
  String id;
  String group_id;
  String group_name;
  String note;
  Integer orders_count;
  Boolean verified_email;
  Boolean accepts_marketing;
  String last_order_id;
  String last_order_name;
  String state;
  Float total_spent;
  String tags;
  Address default_address;
}
