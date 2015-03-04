package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum AuthorizationErrorType {
    @SerializedName("incorrect_number")
    incorrectNumber,
    @SerializedName("invalid_number")
    invalidNumber,
    @SerializedName("invalid_expiry_date")
    invalidExpiryDate,
    @SerializedName("invalid_cvc")
    invalidCvc,
    @SerializedName("expired_card")
    expiredCard,
    @SerializedName("incorrect_cvc")
    incorrectCvc,
    @SerializedName("incorrect_zip")
    incorrectZip,
    @SerializedName("incorrect_address")
    incorrectAddress,
    @SerializedName("card_declined")
    cardDeclined,
    @SerializedName("processing_error")
    processingError,
    @SerializedName("call_issuer")
    callIssuer,
    @SerializedName("pick_up_card")
    pickUpCard
}
