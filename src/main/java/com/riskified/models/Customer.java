package main.java.com.riskified.models;

import java.util.Date;

public class Customer {
  String email;
  String firstName;
  String lastName;
  Date createdAt;
  Date updatedAt;
  String id;
  String groupId;
  String groupName;
  String note;
  Integer ordersCount;
  Boolean verifiedEmail;
  Boolean acceptsMarketing;
  String lastOrderId;
  String lastOrderName;
  String state;
  Float totalSpent;
  String tags;
  Address defaultAddress;
}
