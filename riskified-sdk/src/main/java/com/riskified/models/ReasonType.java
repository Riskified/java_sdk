package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum ReasonType {

    @SerializedName("user_requested")
    userRequested,
    @SerializedName("forgot_password")
    forgotPassword,
    @SerializedName("forced_reset")
    forcedReset

}