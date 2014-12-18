package com.riskified.models;

public class DiscountCodes {

    private String code;
    private double amount;

    public DiscountCodes(double amount, String code) {
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

    @Override
    public String toString() {
        return "DiscountCodes{" +
                "code='" + code + '\'' +
                ", amount=" + amount +
                '}';
    }
}
