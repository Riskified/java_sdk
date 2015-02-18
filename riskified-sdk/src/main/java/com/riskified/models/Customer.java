package com.riskified.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
    private String email;
    private String firstName;
    private String lastName;
    private Date createdAt;
    private Date updatedAt;
    private String id;
    private String groupId;
    private String groupName;
    private String note;
    private Integer ordersCount;
    private Boolean verifiedEmail;
    private Boolean acceptsMarketing;
    private String lastOrderId;
    private String lastOrderName;
    private String state;
    private Float totalSpent;
    private String tags;
    private Address defaultAddress;
    private List<SocialDetails> social;
    
    public Customer(String email, String firstName, String lastName, String id, Date createdAt, Boolean verifiedEmail, Integer ordersCount) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.createdAt = createdAt;
        this.verifiedEmail = verifiedEmail;
        this.ordersCount = ordersCount;
        this.setSocial(new ArrayList<SocialDetails>());
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getOrdersCount() {
        return ordersCount;
    }

    public Boolean getVerifiedEmail() {
        return verifiedEmail;
    }

    public Boolean getAcceptsMarketing() {
        return acceptsMarketing;
    }

    public void setAcceptsMarketing(Boolean acceptsMarketing) {
        this.acceptsMarketing = acceptsMarketing;
    }

    public String getLastOrderId() {
        return lastOrderId;
    }

    public void setLastOrderId(String lastOrderId) {
        this.lastOrderId = lastOrderId;
    }

    public String getLastOrderName() {
        return lastOrderName;
    }

    public void setLastOrderName(String lastOrderName) {
        this.lastOrderName = lastOrderName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Float getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Float totalSpent) {
        this.totalSpent = totalSpent;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Address getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Address defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

	public List<SocialDetails> getSocial() {
		return social;
	}

	public void setSocial(List<SocialDetails> social) {
		this.social = social;
	}

}
