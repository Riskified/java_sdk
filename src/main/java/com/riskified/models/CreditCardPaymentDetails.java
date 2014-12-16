package main.java.com.riskified.models;

public class CreditCardPaymentDetails implements IPaymentDetails {
  public String creditCardBin;
  public String avsResultCode;
  public String cvvResultCode;
  public String creditCardNumber;
  public String creditCardCompany;
  public String authorization_id;
  
  public CreditCardPaymentDetails(String creditCardBin, String avsResultCode, String cvvResultCode, String creditCardNumber, String creditCardCompany) {
    this.creditCardBin = creditCardBin;
    this.avsResultCode = avsResultCode;
    this.cvvResultCode = cvvResultCode;
    this.creditCardNumber = creditCardNumber;
    this.creditCardCompany = creditCardCompany;
  }
  
}