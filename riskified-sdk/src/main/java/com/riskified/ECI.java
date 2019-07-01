package com.riskified;

public enum ECI {
	Zero_Zero("00"),
	Zero_One("01"),
	Zero_Two("02"),
	Zero_Five("05"),
	Zero_Six("06"),
	Zero_Seven("07");
	
	private final String eciCode;
	
	ECI(String eciCode) {
	        this.eciCode = eciCode;
	        }
	
	public String getValue() {
	        return eciCode;
	        }
	
}
