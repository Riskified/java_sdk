package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum VerificationStatus {

    @SerializedName("sent")
    sent,
    @SerializedName("success")
    success,
    @SerializedName("unauthorized")
    unauthorized,
    @SerializedName("failure")
    failure,
    @SerializedName("expired")
    expired,

}
