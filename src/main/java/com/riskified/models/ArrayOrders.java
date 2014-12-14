package main.java.com.riskified.models;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArrayOrders implements JsonObject {
	
	public ArrayList<Order> orders;
	
	public ArrayOrders() {
		orders = new ArrayList<Order>();
	}

	@Override
	public String toJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this); 
	}

}
