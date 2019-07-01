package com.riskified;

public enum TranStatusReason {
	Zero_One("01"),
	Zero_TWO("02"),
	Zero_Three("03"),
	Zero_Four("04"),
	Zero_Five("05"),
	Zero_Six("06"),
	Zero_Seven("07"),
	Zero_Eight("08"),
	Zero_Nine("09"),
	Zero_Ten("10"),
	Eleven("11"),
	Twelve("12"),
	Thirteen("13"),
	Fourteen("14"),
	Fifteen("15"),
	Sixteen("16"),
	Seventeen("17"),
	Eighteen("18"),
	Nineteen("19"),
	Twenty("20"),
	Twenty_One("21"),
	Twenty_Two("22"),
	Twenty_Three("23"),
	Twenty_Four("24"),
	Twenty_Five("25"),
	Twenty_Six("26"),
	Twenty_Seven("27"),
	Eighty("80"),
	Ninety_Nine("99");
	
	

	private final String tranStatusReason;
	
	TranStatusReason(String tranStatusReason) {
	        this.tranStatusReason = tranStatusReason;
	        }
	
	public String getValue() {
	        return tranStatusReason;
	        }
	
}
