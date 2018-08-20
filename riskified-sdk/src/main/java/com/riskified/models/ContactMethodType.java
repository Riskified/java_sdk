package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum ContactMethodType {

    @SerializedName("email")
    email,
    @SerializedName("website_chat")
    websiteChat,
    @SerializedName("facebook")
    facebook,
    @SerializedName("phone")
    phone,
    @SerializedName("other")
    other

}
