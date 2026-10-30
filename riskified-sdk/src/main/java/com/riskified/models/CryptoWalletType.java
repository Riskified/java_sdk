package com.riskified.models;

/**
 * Documented values for {@code CryptoWallet.type}.
 *
 * @see CryptoWallet#setType(String)
 */
public final class CryptoWalletType {

    public static final String PRIMARY = "primary";
    public static final String SECONDARY = "secondary";
    public static final String CUSTODIAL = "custodial";
    public static final String NON_CUSTODIAL = "non_custodial";
    public static final String HARDWARE = "hardware";
    public static final String EXCHANGE = "exchange";

    private CryptoWalletType() {
    }
}
