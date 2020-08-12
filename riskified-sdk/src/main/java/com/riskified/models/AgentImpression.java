package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum AgentImpression {

    @SerializedName("positive")
    positive,
    @SerializedName("neutral")
    neutral,
    @SerializedName("negative")
    negative
}