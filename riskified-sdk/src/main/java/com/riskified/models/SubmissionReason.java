package com.riskified.models;

import com.google.gson.annotations.SerializedName;

/*
 * 
 *  We depreciated this class to allow merchant to be more flexible with submission reason values   
 */
public enum SubmissionReason {

    @SerializedName("pilot")
    pilot,
    @SerializedName("agent_cancellation")
    agent_cancellation,
    @SerializedName("failed_verification")
    failedVerification,
    @SerializedName("rule_decision")
    ruleDecision,
    @SerializedName("third_party")
    thirdParty,
    @SerializedName("manual_decision")
    manualDecision,
    @SerializedName("policy_decision")
    policyDecision
}
