package com.riskified;

public class ProxyClientDetails {

	private String proxyUrl;
    private int proxyPort;
    private String proxyUsername;
    private String proxyPassword;
	
	public ProxyClientDetails(String proxyUrl, int proxyPort, String proxyUserName, String proxyPassword) {
		this.proxyUrl = proxyUrl;
		this.proxyPort = proxyPort;
		this.proxyUsername = proxyUserName;
		this.proxyPassword = proxyPassword;
	}
    public String getProxyUrl() {
		return proxyUrl;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public String getProxyUsername() {
		return proxyUsername;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

}
