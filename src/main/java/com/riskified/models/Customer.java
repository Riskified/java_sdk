package com.riskified.models;

import java.util.Date;

public class Customer {
    public String email;
    public String firstName;
    public String lastName;
    public Date createdAt;
    public Date updatedAt;
    public String id;
    public String groupId;
    public String groupName;
    public String note;
    public Integer ordersCount;
    public Boolean verifiedEmail;
    public Boolean acceptsMarketing;
    public String lastOrderId;
    public String lastOrderName;
    public String state;
    public Float totalSpent;
    public String tags;
    public Address defaultAddress;
}
