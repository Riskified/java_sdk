error id: file://<WORKSPACE>/riskified-sdk/src/main/java/com/riskified/models/BankWirePaymentDetails.java:java/util/Date#
file://<WORKSPACE>/riskified-sdk/src/main/java/com/riskified/models/BankWirePaymentDetails.java
empty definition using pc, found symbol in pc: java/util/Date#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1363
uri: file://<WORKSPACE>/riskified-sdk/src/main/java/com/riskified/models/BankWirePaymentDetails.java
text:
```scala
package com.riskified.models;

import com.riskified.validations.*;

import java.util.Date;

public class BankWirePaymentDetails implements IPaymentDetails {
    private String accountNumber;
    private String routingNumber;
    private String token;
    private Date storedPaymentCreatedAt;
    private Date storedPaymentUpdatedAt;
    private PaymentType paymentType = PaymentType.BANK_TRANSFER;
    private PaymentDetails paymentDetails;

    public BankWirePaymentDetails(String accountNumber, String routingNumber) {
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public Date getStoredPaymentCreatedAt(){
        return storedPaymentCreatedAt;
    }

    public void setStoredPaymentCreatedAt(Date storedPaymentCreatedAt){
        this.storedPaymentCreatedAt = storedPaymentCreatedAt;
    }

    public D@@ate getStoredPaymentUpdatedAt(){
        return storedPaymentUpdatedAt;
    }

    public void setStoredPaymentUpdatedAt(Date storedPaymentUpdatedAt){
        this.storedPaymentUpdatedAt = storedPaymentUpdatedAt;
    }

    public PaymentType getPaymentType() { return paymentType; }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.accountNumber, "Bank Account Number");
            Validate.notNullOrEmpty(this, this.routingNumber, "Bank Routing Number");
        }
    }

    
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: java/util/Date#