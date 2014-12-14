package main.java.com.riskified.models;

import java.util.Date;

import main.java.com.riskified.JSONFormmater;


public class CancelOrder implements JsonObject {
	public String id;
	public String cancel_reason;
	public Date cancelled_at;
	
	@Override
	public String toJson() {
		return JSONFormmater.toJson(this); 
	}

}
