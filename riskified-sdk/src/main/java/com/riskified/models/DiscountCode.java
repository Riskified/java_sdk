package com.riskified.models;

public class DiscountCode {

    private String code;
    private Double amount;

    public DiscountCode(double amount, String code) {
        this.code = code;
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
