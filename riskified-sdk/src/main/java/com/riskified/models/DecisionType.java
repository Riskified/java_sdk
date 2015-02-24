package com.riskified.models;

import com.google.gson.annotations.SerializedName;

public enum DecisionType {

	@SerializedName("approved")
	approved,
	@SerializedName("checkout")
	checkout,
	@SerializedName("cancelled")
	cancelled,
	@SerializedName("declined")
	declined,
	@SerializedName("chargedback_fraud")
	chargedbackFraud,
	@SerializedName("chargedback_not_fraud")
	chargedbackNotFraud
}
