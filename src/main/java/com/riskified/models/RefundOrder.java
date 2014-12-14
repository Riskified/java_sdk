package main.java.com.riskified.models;

import java.util.ArrayList;

public class RefundOrder {

	public String id;
	public ArrayList<RefundDetails> refunds;
	
	public RefundOrder() {
		refunds = new ArrayList<RefundDetails>();
	}
}
