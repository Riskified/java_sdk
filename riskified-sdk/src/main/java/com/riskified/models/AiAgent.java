package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum AiAgent {

    @SerializedName("chatgpt")
    chatgpt,
    @SerializedName("gemini")
    gemini,
    @SerializedName("copilot")
    copilot,
    @SerializedName("perplexity")
    perplexity

}
