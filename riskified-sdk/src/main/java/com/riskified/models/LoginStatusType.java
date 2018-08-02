package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum LoginStatusType {

    @SerializedName("success")
    success,
    @SerializedName("failure")
    failure

}
