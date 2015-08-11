package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum DecisionType {

    @SerializedName("approved")
    approved,
    @SerializedName("checkout")
    checkout,
    @SerializedName("cancelled")
    cancelled,
    @SerializedName("declined_fraud")
    declined_fraud,
    @SerializedName("chargeback_fraud")
    chargebackFraud,
    @SerializedName("chargeback_not_fraud")
    chargebackNotFraud
}
