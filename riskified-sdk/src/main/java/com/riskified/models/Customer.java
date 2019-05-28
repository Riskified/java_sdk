package com.riskified.models;

import java.util.*;

import com.riskified.validations.*;

public class Customer implements IValidated {
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
    private Gender gender; // male or female
    private Address address;
    private String accountType;
    private Integer linkedAccounts;
    
    public Customer() {
    	
    }
    
    public Customer(String email, String firstName, String lastName) {
    	this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
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

    public void validate(Validation validationType) throws FieldBadFormatException {

        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.email, "Email");
            Validate.notNullOrEmpty(this, this.firstName, "First Name");
            Validate.notNullOrEmpty(this, this.lastName, "Last Name");
            Validate.notNullOrEmpty(this, this.id, "Id");
            Validate.notNull(this, this.createdAt, "Created At");
            Validate.notNull(this, this.verifiedEmail, "Verified Email");
        }

        if (this.email != null) {
            Validate.emailAddress(this, email, "Email");
        }

        if (this.social != null) {
            for (SocialDetails socialDetails : this.social) {
                socialDetails.validate(validationType);
            }
        }
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public void setId(String id) {
        this.id = id;
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

    public void setVerifiedEmail(Boolean verifiedEmail) {
        this.verifiedEmail = verifiedEmail;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Integer getLinkedAccounts() {
        return linkedAccounts;
    }

    public void setLinkedAccounts(Integer linkedAccounts) { this.linkedAccounts = linkedAccounts; }
}
