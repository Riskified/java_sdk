package com.riskified;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.riskified.notifications.AuthError;

public class SHA256Handler {

	private Mac mac;
	
	public SHA256Handler(String authKay) throws RiskifedError {
		mac = createSHA256Key(authKay);
	}
	
    public String createSHA256(String data) {
        final byte[] hmac = mac.doFinal(data.getBytes());
        return toHexString(hmac);
    }
    
    public Boolean isHmacCorrect(String data, String hmac) {
        String calcHash = createSHA256(data);
        return hmac.equals(calcHash);
    }
    
    private Mac createSHA256Key(String authKey) throws RiskifedError {
        Key sk = new SecretKeySpec(authKey.getBytes(), "HmacSHA256");
        
        try {
            mac = Mac.getInstance(sk.getAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            throw new RiskifedError(e);
        }
        try {
            mac.init(sk);
        } catch (InvalidKeyException e) {
            throw new RiskifedError(e);
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
