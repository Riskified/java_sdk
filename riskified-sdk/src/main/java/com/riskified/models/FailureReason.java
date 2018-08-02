package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum FailureReason {

    @SerializedName("wrong password")
    wrongPassword,
    @SerializedName("captcha")
    captcha,
    @SerializedName("disabled account")
    disabledAccount,
    @SerializedName("nonexistent account")
    nonexistentAccount,
    @SerializedName("other")
    other

}
