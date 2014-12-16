package main.java.com.riskified.models;

public class PaypalPaymentDetails implements IPaymentDetails {
  
  public String payerEmail;
  public String payerStatus;
  public String payerAddressStatus;
  public String protectionEligibility;
  public String paymentStatus;
  public String pendingReason;
  public String authorizationId;
  
  public PaypalPaymentDetails(String payerEmail, String payerStatus, String payerAddressStatus, String protectionEligibility){
    this.payerEmail = payerEmail;
    this.payerStatus = payerStatus;
    this.payerAddressStatus = payerAddressStatus;
    this.protectionEligibility = protectionEligibility;
  }
  

}