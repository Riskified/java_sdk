package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum SocialType {

    @SerializedName("facebook")
    facebook,
    @SerializedName("google")
    google,
    @SerializedName("linkedin")
    linkedin,
    @SerializedName("twitter")
    twitter,
    @SerializedName("yahoo")
    yahoo,
    @SerializedName("other")
    other

}
