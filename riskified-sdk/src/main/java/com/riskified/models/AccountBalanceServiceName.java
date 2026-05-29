package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum AccountBalanceServiceName {

    @SerializedName("plaid")
    PLAID,
    @SerializedName("mx")
    MX,
    @SerializedName("stripe")
    STRIPE,
    @SerializedName("truelayer")
    TRUELAYER,
    @SerializedName("klarna")
    KLARNA,
    @SerializedName("visa")
    VISA,
    @SerializedName("mastercard")
    MASTERCARD,
    @SerializedName("yodlee")
    YODLEE,

}
