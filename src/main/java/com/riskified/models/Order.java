package main.java.com.riskified.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.com.riskified.JSONFormmater;

/**
 * @author omer
 *
 */
public class Order implements JsonObject {

  public Order() {
    lineItems = new ArrayList<LineItems>();
    additionalEmails = new ArrayList<String>();
    discountCodes = new ArrayList<DiscountCodes>();
    shippingLines = new ArrayList<ShippingLines>();
    noteAttributes = new ArrayList<Attributes>();
    taxLines = new ArrayList<TaxLines>();
  }

  public String id;
  public Date createdAt;
  public Date updatedAt;
  public String currency;
  public String gateway;
  public Float totalPrice;
  public String browserIp;
  public Customer customer;
  public List<LineItems> lineItems;
  public String name;
  public List<String> additionalEmails;
  public String note;
  public String number;
  public String orderNumber;
  public String cancelReason;
  public Date cancelledAt;
  public Date closedAt;
  public String cartToken;
  public String checkoutToken;
  public String token;
  public String referringSite;
  public Boolean confirmed;
  public Boolean buyerAcceptsMarketing;
  public String financialStatus;
  public String fulfillmentStatus;
  public String landingSite;
  public String landingSiteRef;
  public String locationId;
  public String source;
  public String sourceIdentifier;
  public String sourceName;
  public String sourceUrl;
  public Float subtotalPrice;
  public Boolean taxesIncluded;
  public Float totalDiscounts;
  public Float totalLineItemsPrice;
  public Float totalPriceUsd;
  public Float totalTax;
  public Float totalWeight;
  public String userId;
  public String processingMethod;
  public String checkoutId;
  public String tags;
  public String vendor;
  public Address shippingAddress;
  public Address billingAddress;
  public PaymentDetails paymentDetails;
  public ClientDetails clientDetails;
  public List<DiscountCodes> discountCodes;
  public List<ShippingLines> shippingLines;
  public List<Attributes> noteAttributes;
  public List<TaxLines> taxLines;

  public String toJson() {
    return JSONFormmater.toJson(this);
  }

}
