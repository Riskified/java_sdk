package com.riskified;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Formatter;

public class SHA256Handler {

    private Mac mac;

    public SHA256Handler(String authKay) throws RiskifiedError {
        mac = createSHA256Key(authKay);
    }

    public synchronized String createSHA256(byte[] data) throws IllegalStateException {
        final byte[] hmac = mac.doFinal(data);
        return toHexString(hmac);
    }

    private Mac createSHA256Key(String authKey) throws RiskifiedError {
        // UTF-8, never the platform default charset: the default makes the signature depend on
        // a JVM locale setting. Riskified tokens are hex, so UTF-8 and the reference
        // implementation's ASCII (Riskified.SDK/Utils/HttpUtils.cs:152) agree byte for byte.
        Key sk = new SecretKeySpec(authKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac;
        try {
            mac = Mac.getInstance(sk.getAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            throw new RiskifiedError(e);
        }
        try {
            mac.init(sk);
        } catch (InvalidKeyException e) {
            throw new RiskifiedError(e);
        }
        return mac;
    }

    private String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        formatter.close();

        return sb.toString();
    }
}
