package com.riskified.models;

/**
 * A crypto wallet, used by {@link Customer#getCryptoWallets()} and {@link Recipient#getCryptoWallet()}.
 */
public class CryptoWallet {

    private String address;
    private String network;
    private String type;

    /**
     * @param address The wallet address
     * @param network The blockchain network, e.g. "ethereum"
     */
    public CryptoWallet(String address, String network) {
        this.address = address;
        this.network = network;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    /** One of: primary, secondary, custodial, non_custodial, hardware, exchange. */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
