package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum AuthorizationType {

    @SerializedName("verification")
    verification,
    @SerializedName("authorization")
    authorization,

}
