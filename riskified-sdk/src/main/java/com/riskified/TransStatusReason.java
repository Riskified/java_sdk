package com.riskified;

import com.google.gson.annotations.SerializedName;

public enum TransStatusReason {
	@SerializedName("01")
	Zero_One("01"),
	@SerializedName("02")
	Zero_TWO("02"),
	@SerializedName("03")
	Zero_Three("03"),
	@SerializedName("04")
	Zero_Four("04"),
	@SerializedName("05")
	Zero_Five("05"),
	@SerializedName("06")
	Zero_Six("06"),
	@SerializedName("07")
	Zero_Seven("07"),
	@SerializedName("08")
	Zero_Eight("08"),
	@SerializedName("09")
	Zero_Nine("09"),
	@SerializedName("10")
	Zero_Ten("10"),
	@SerializedName("11")
	Eleven("11"),
	@SerializedName("12")
	Twelve("12"),
	@SerializedName("13")
	Thirteen("13"),
	@SerializedName("14")
	Fourteen("14"),
	@SerializedName("15")
	Fifteen("15"),
	@SerializedName("16")
	Sixteen("16"),
	@SerializedName("17")
	Seventeen("17"),
	@SerializedName("18")
	Eighteen("18"),
	@SerializedName("19")
	Nineteen("19"),
	@SerializedName("20")
	Twenty("20"),
	@SerializedName("21")
	Twenty_One("21"),
	@SerializedName("22")
	Twenty_Two("22"),
	@SerializedName("23")
	Twenty_Three("23"),
	@SerializedName("24")
	Twenty_Four("24"),
	@SerializedName("25")
	Twenty_Five("25"),
	@SerializedName("26")
	Twenty_Six("26"),
	@SerializedName("27")
	Twenty_Seven("27"),
	@SerializedName("80")
	Eighty("80"),
	@SerializedName("99")
	Ninety_Nine("99");
	
	

	private final String transStatusReason;
	
	TransStatusReason(String transStatusReason) {
	        this.transStatusReason = transStatusReason;
	        }
	
	public String getValue() {
	        return transStatusReason;
	        }
	
}
