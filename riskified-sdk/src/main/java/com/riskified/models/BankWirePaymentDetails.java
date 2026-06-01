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
    private int daysSinceAccountOpening;
    private int daysWithNegativeBalanceCount;
    private boolean isSavingsOrMoneyMarketAccount;
    private int nsfOverdraftTransactionsCount;
    private int unauthorizedTransactionsCount;
    private PlaidScores plaidScores;
    private AccountIdentity accountIdentity;
    private AccountBalance accountBalance;

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

    public Date getStoredPaymentUpdatedAt(){
        return storedPaymentUpdatedAt;
    }

    public void setStoredPaymentUpdatedAt(Date storedPaymentUpdatedAt){
        this.storedPaymentUpdatedAt = storedPaymentUpdatedAt;
    }

    public PaymentType getPaymentType() { return paymentType; }

    public int getDaysSinceAccountOpening() {
        return daysSinceAccountOpening;
    }

    public void setDaysSinceAccountOpening(int daysSinceAccountOpening) {
        this.daysSinceAccountOpening = daysSinceAccountOpening;
    }

    public int getDaysWithNegativeBalanceCount() {
        return daysWithNegativeBalanceCount;
    }

    public void setDaysWithNegativeBalanceCount(int daysWithNegativeBalanceCount) {
        this.daysWithNegativeBalanceCount = daysWithNegativeBalanceCount;
    }

    public boolean getIsSavingsOrMoneyMarketAccount() {
        return isSavingsOrMoneyMarketAccount;
    }

    public void setIsSavingsOrMoneyMarketAccount(boolean isSavingsOrMoneyMarketAccount) {
        this.isSavingsOrMoneyMarketAccount = isSavingsOrMoneyMarketAccount;
    }

    public int getNsfOverdraftTransactionsCount() {
        return nsfOverdraftTransactionsCount;
    }

    public void setNsfOverdraftTransactionsCount(int nsfOverdraftTransactionsCount) {
        this.nsfOverdraftTransactionsCount = nsfOverdraftTransactionsCount;
    }

    public int getUnauthorizedTransactionsCount() {
        return unauthorizedTransactionsCount;
    }

    public void setUnauthorizedTransactionsCount(int unauthorizedTransactionsCount) {
        this.unauthorizedTransactionsCount = unauthorizedTransactionsCount;
    }

    public PlaidScores getPlaidScores() {
        return plaidScores;
    }

    public void setPlaidScores(PlaidScores plaidScores) {
        this.plaidScores = plaidScores;
    }

    public AccountIdentity getAccountIdentity() {
        return accountIdentity;
    }

    public void setAccountIdentity(AccountIdentity accountIdentity) {
        this.accountIdentity = accountIdentity;
    }

    public AccountBalance getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(AccountBalance accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.accountNumber, "Bank Account Number");
            Validate.notNullOrEmpty(this, this.routingNumber, "Bank Routing Number");
        }
    }
    
}