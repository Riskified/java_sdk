package com.riskified.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public abstract class BaseOrder implements IValidated {

	protected String id;
    protected String email;
    protected Date createdAt;
    protected Date updatedAt;
    protected String currency;
    protected String gateway;
    protected Double totalPrice;
    protected String browserIp;
    protected Customer customer;
    protected List<LineItem> lineItems;
    protected String name;
    protected List<String> additionalEmails;
    protected String note;
    protected String number;
    protected String orderNumber;
    protected String cancelReason;
    protected Date cancelledAt;
    protected Date closedAt;
    protected String cartToken;
    protected String checkoutToken;
    protected String token;
    protected String referringSite;
    protected Boolean confirmed;
    protected Boolean buyerAcceptsMarketing;
    protected String financialStatus;
    protected String fulfillmentStatus;
    protected String landingSite;
    protected String landingSiteRef;
    protected String locationId;
    protected String source;
    protected String sourceIdentifier;
    protected String sourceName;
    protected String sourceUrl;
    protected Double subtotalPrice;
    protected Boolean taxesIncluded;
    protected Double totalDiscounts;
    protected Double totalLineItemsPrice;
    protected Double totalPriceUsd;
    protected Double totalTax;
    protected Double totalWeight;
    protected String userId;
    protected String processingMethod;
    protected String checkoutId;
    protected String tags;
    protected String vendorId;
    protected String vendorName;
    protected Address shippingAddress;
    protected Address billingAddress;
    protected IPaymentDetails paymentDetails;
    protected ClientDetails clientDetails;
    protected List<DiscountCode> discountCodes;
    protected List<ShippingLine> shippingLines;
    protected List<Attributes> noteAttributes;
    protected List<TaxLine> taxLines;
    protected DecisionDetails decision;

    
    public BaseOrder() {
        lineItems = new ArrayList<LineItem>();
        additionalEmails = new ArrayList<String>();
        discountCodes = new ArrayList<DiscountCode>();
        shippingLines = new ArrayList<ShippingLine>();
        noteAttributes = new ArrayList<Attributes>();
        taxLines = new ArrayList<TaxLine>();
    }
    

	public void validate(Validation validationType)
			throws FieldBadFormatException {
		
		Validate.stringNotNullOrEmpty(this, this.id, "Id");
		
		if(validationType == Validation.all) { // Validated required fields
			
			Validate.stringNotNullOrEmpty(this, this.name, "Name");
			Validate.stringNotNullOrEmpty(this, this.email, "Email");
			Validate.notNull(this, this.createdAt, "Created At");
			Validate.notNull(this, this.closedAt, "Closed At");
			Validate.notNull(this, this.updatedAt, "Updated At");
			Validate.stringNotNullOrEmpty(this, this.gateway, "Gateway");
			Validate.stringNotNullOrEmpty(this, this.browserIp, "Browser IP");
			Validate.notNull(this, this.totalPrice, "Total Price");
			Validate.notNull(this, this.totalDiscounts, "Total Discounts");
			Validate.notNull(this, this.lineItems, "Line Items");
			Validate.notNull(this, this.discountCodes, "Discount Codes");
			Validate.notNull(this, this.shippingLines, "Shipping Lines");
			Validate.notNull(this, this.paymentDetails, "Payment Details");
			Validate.notNull(this, this.customer, "Customer");
			Validate.notNull(this, this.billingAddress, "Billing Address");
			Validate.notNull(this, this.shippingAddress, "Shipping Address");
		}
		
		if(this.totalPrice != null) {
			Validate.mustBePositive(this, this.totalPrice, "Total Price");
		}
		
		if(this.browserIp != null) {
			Validate.ipAddressWellFormed(this, this.browserIp, "Browser IP");
		}
		
		if(this.currency != null) {
			Validate.currencyCodeWellFormed(this, currency, "Currency");
		}
		
		if(this.email != null) {
			Validate.emailAddressWellFormed(this, this.email, "Email");
		}
		
		if(this.lineItems != null) {
			for(LineItem lineItem : this.lineItems) {
				lineItem.validate(validationType);
			}
		}
		
		if(this.discountCodes != null) {
			for(DiscountCode discountCode : this.discountCodes) {
				discountCode.validate(validationType);
			}
		}
		
		
		if(this.shippingLines != null) {
			for(ShippingLine shippingLine : this.shippingLines) {
				shippingLine.validate(validationType);
			}
		}
		
		
		if(this.paymentDetails != null) {
			this.paymentDetails.validate(validationType);
		}
		
		
		if(this.customer != null) {
			this.customer.validate(validationType);
		}
		
		
		if(this.billingAddress != null) {
			this.billingAddress.validate(validationType);
		}
		
		
		if(this.shippingAddress != null) {
			this.shippingAddress.validate(validationType);
		}
		
		if(this.decision != null) {
			this.decision.validate(validationType);
		}
	}
	
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBrowserIp() {
        return browserIp;
    }

    public void setBrowserIp(String browserIp) {
        this.browserIp = browserIp;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAdditionalEmails() {
        return additionalEmails;
    }

    public void setAdditionalEmails(List<String> additionalEmails) {
        this.additionalEmails = additionalEmails;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Date getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Date cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public String getCartToken() {
        return cartToken;
    }

    public void setCartToken(String cartToken) {
        this.cartToken = cartToken;
    }

    public String getCheckoutToken() {
        return checkoutToken;
    }

    public void setCheckoutToken(String checkoutToken) {
        this.checkoutToken = checkoutToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReferringSite() {
        return referringSite;
    }

    public void setReferringSite(String referringSite) {
        this.referringSite = referringSite;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getBuyerAcceptsMarketing() {
        return buyerAcceptsMarketing;
    }

    public void setBuyerAcceptsMarketing(Boolean buyerAcceptsMarketing) {
        this.buyerAcceptsMarketing = buyerAcceptsMarketing;
    }

    public String getFinancialStatus() {
        return financialStatus;
    }

    public void setFinancialStatus(String financialStatus) {
        this.financialStatus = financialStatus;
    }

    public String getFulfillmentStatus() {
        return fulfillmentStatus;
    }

    public void setFulfillmentStatus(String fulfillmentStatus) {
        this.fulfillmentStatus = fulfillmentStatus;
    }

    public String getLandingSite() {
        return landingSite;
    }

    public void setLandingSite(String landingSite) {
        this.landingSite = landingSite;
    }

    public String getLandingSiteRef() {
        return landingSiteRef;
    }

    public void setLandingSiteRef(String landingSiteRef) {
        this.landingSiteRef = landingSiteRef;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public double getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(double subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public Boolean getTaxesIncluded() {
        return taxesIncluded;
    }

    public void setTaxesIncluded(Boolean taxesIncluded) {
        this.taxesIncluded = taxesIncluded;
    }

    public double getTotalDiscounts() {
        return totalDiscounts;
    }

    public void setTotalDiscounts(double totalDiscounts) {
        this.totalDiscounts = totalDiscounts;
    }

    public double getTotalLineItemsPrice() {
        return totalLineItemsPrice;
    }

    public void setTotalLineItemsPrice(double totalLineItemsPrice) {
        this.totalLineItemsPrice = totalLineItemsPrice;
    }

    public double getTotalPriceUsd() {
        return totalPriceUsd;
    }

    public void setTotalPriceUsd(double totalPriceUsd) {
        this.totalPriceUsd = totalPriceUsd;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProcessingMethod() {
        return processingMethod;
    }

    public void setProcessingMethod(String processingMethod) {
        this.processingMethod = processingMethod;
    }

    public String getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public IPaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(IPaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    public List<DiscountCode> getDiscountCodes() {
        return discountCodes;
    }

    public void setDiscountCodes(List<DiscountCode> discountCodes) {
        this.discountCodes = discountCodes;
    }

    public List<ShippingLine> getShippingLines() {
        return shippingLines;
    }

    public void setShippingLines(List<ShippingLine> shippingLines) {
        this.shippingLines = shippingLines;
    }

    public List<Attributes> getNoteAttributes() {
        return noteAttributes;
    }

    public void setNoteAttributes(List<Attributes> noteAttributes) {
        this.noteAttributes = noteAttributes;
    }

    public List<TaxLine> getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(List<TaxLine> taxLines) {
        this.taxLines = taxLines;
    }


	public DecisionDetails getDecision() {
		return decision;
	}


	public void setDecision(DecisionDetails decision) {
		this.decision = decision;
	}
}
