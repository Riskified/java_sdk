package models;

import java.util.ArrayList;

import riskified.JSONFormmater;


public class Refund implements JsonObject {
	
	public RefundOrder order;
	
	@Override
	public String toJson() {
		return JSONFormmater.toJson(this); 
	}

}
