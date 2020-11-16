package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum AuthenticationMechanism {

    @SerializedName("magic_link")
    magicLink,
    @SerializedName("password")
    password,

}

