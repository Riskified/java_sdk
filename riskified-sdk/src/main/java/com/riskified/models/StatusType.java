package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum StatusType {

    @SerializedName("pending")
    pending,
    @SerializedName("success")
    success,
    @SerializedName("failure")
    failure

}
