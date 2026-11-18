package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum DecisionType {

    @SerializedName("approved")
    approved,
    @SerializedName("declined")
    declined,
    /**
     * @deprecated The API doesn't accept {@code checkout} as an external status.
     */
    @Deprecated
    @SerializedName("checkout")
    checkout,
    @SerializedName("checkout_denied")
    checkoutDenied,
    @SerializedName("cancelled")
    cancelled,
    @SerializedName("declined_fraud")
    declined_fraud,
    @SerializedName("declined_business")
    declinedBusiness,
    @SerializedName("suspected_fraud")
    suspectedFraud,
    @SerializedName("chargeback")
    chargeback,
    @SerializedName("chargeback_fraud")
    chargebackFraud,
    @SerializedName("chargeback_not_fraud")
    chargebackNotFraud
}
