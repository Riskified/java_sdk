package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum ResetPasswordStatusType {

    @SerializedName("pending")
    pending,
    @SerializedName("success")
    success,
    @SerializedName("failed")
    failed

}