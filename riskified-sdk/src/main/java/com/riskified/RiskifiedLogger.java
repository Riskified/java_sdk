package com.riskified;

public class RiskifiedLogger implements RiskifiedLogListener {

	public void getRequestLogs(String jsonLog) {
		System.out.println(" Riskified's request payload = "  + jsonLog);
	}
	
}

