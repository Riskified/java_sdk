package com.riskified.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author omer
 *
 */
public class Order {

    private String id;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private String currency;
    private String gateway;
    private double totalPrice;
    private String browserIp;
    private Customer customer;
    private List<LineItem> lineItems;
    private String name;
    private List<String> additionalEmails;
    private String note;
    private String number;
    private String orderNumber;
    private String cancelReason;
    private Date cancelledAt;
    private Date closedAt;
    private String cartToken;
    private String checkoutToken;
    private String token;
    private String referringSite;
    private Boolean confirmed;
    private Boolean buyerAcceptsMarketing;
    private String financialStatus;
    private String fulfillmentStatus;
    private String landingSite;
    private String landingSiteRef;
    private String locationId;
    private String source;
    private String sourceIdentifier;
    private String sourceName;
    private String sourceUrl;
    private double subtotalPrice;
    private Boolean taxesIncluded;
    private double totalDiscounts;
    private double totalLineItemsPrice;
    private double totalPriceUsd;
    private double totalTax;
    private double totalWeight;
    private String userId;
    private String processingMethod;
    private String checkoutId;
    private String tags;
    private String vendor;
    private Address shippingAddress;
    private Address billingAddress;
    private IPaymentDetails paymentDetails;
    private ClientDetails clientDetails;
    private List<DiscountCodes> discountCodes;
    private List<ShippingLine> shippingLines;
    private List<Attributes> noteAttributes;
    private List<TaxLines> taxLines;

    public Order() {
        lineItems = new ArrayList<LineItem>();
        additionalEmails = new ArrayList<String>();
        discountCodes = new ArrayList<DiscountCodes>();
        shippingLines = new ArrayList<ShippingLine>();
        noteAttributes = new ArrayList<Attributes>();
        taxLines = new ArrayList<TaxLines>();
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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
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

    public List<DiscountCodes> getDiscountCodes() {
        return discountCodes;
    }

    public void setDiscountCodes(List<DiscountCodes> discountCodes) {
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

    public List<TaxLines> getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(List<TaxLines> taxLines) {
        this.taxLines = taxLines;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", currency='" + currency + '\'' +
                ", gateway='" + gateway + '\'' +
                ", totalPrice=" + totalPrice +
                ", browserIp='" + browserIp + '\'' +
                ", customer=" + customer +
                ", lineItems=" + lineItems +
                ", name='" + name + '\'' +
                ", additionalEmails=" + additionalEmails +
                ", note='" + note + '\'' +
                ", number='" + number + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", cancelReason='" + cancelReason + '\'' +
                ", cancelledAt=" + cancelledAt +
                ", closedAt=" + closedAt +
                ", cartToken='" + cartToken + '\'' +
                ", checkoutToken='" + checkoutToken + '\'' +
                ", token='" + token + '\'' +
                ", referringSite='" + referringSite + '\'' +
                ", confirmed=" + confirmed +
                ", buyerAcceptsMarketing=" + buyerAcceptsMarketing +
                ", financialStatus='" + financialStatus + '\'' +
                ", fulfillmentStatus='" + fulfillmentStatus + '\'' +
                ", landingSite='" + landingSite + '\'' +
                ", landingSiteRef='" + landingSiteRef + '\'' +
                ", locationId='" + locationId + '\'' +
                ", source='" + source + '\'' +
                ", sourceIdentifier='" + sourceIdentifier + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", subtotalPrice=" + subtotalPrice +
                ", taxesIncluded=" + taxesIncluded +
                ", totalDiscounts=" + totalDiscounts +
                ", totalLineItemsPrice=" + totalLineItemsPrice +
                ", totalPriceUsd=" + totalPriceUsd +
                ", totalTax=" + totalTax +
                ", totalWeight=" + totalWeight +
                ", userId='" + userId + '\'' +
                ", processingMethod='" + processingMethod + '\'' +
                ", checkoutId='" + checkoutId + '\'' +
                ", tags='" + tags + '\'' +
                ", vendor='" + vendor + '\'' +
                ", shippingAddress=" + shippingAddress +
                ", billingAddress=" + billingAddress +
                ", paymentDetails=" + paymentDetails +
                ", clientDetails=" + clientDetails +
                ", discountCodes=" + discountCodes +
                ", shippingLines=" + shippingLines +
                ", noteAttributes=" + noteAttributes +
                ", taxLines=" + taxLines +
                '}';
    }
}
