package com.riskified.notifications;

import com.google.gson.Gson;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class NotificationFormater {
    private Gson gson;
    private Mac encoder;

    public NotificationFormater(String authKey) throws InvalidKeyException, NoSuchAlgorithmException {
        gson = new Gson();
        encoder = createSHA256Key(authKey);
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        formatter.close();
        return sb.toString();
    }

    /**
     * @param authKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private Mac createSHA256Key(String authKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Key sk = new SecretKeySpec(authKey.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance(sk.getAlgorithm());
        mac.init(sk);
        return mac;
    }

    public Notification toObject(String postBody, String hash) throws AuthError {
        String calcHash = createSHA256(postBody);
        if (!hash.equals(calcHash)) {
            throw new AuthError(hash, calcHash);
        }
        return gson.fromJson(postBody, Notification.class);
    }

    private String createSHA256(String string) {
        final byte[] hmac = encoder.doFinal(string.getBytes());
        return toHexString(hmac);
    }

    public Notification parseServletPostRequest(HttpServletRequest req) throws IOException, AuthError {
        StringBuffer jb = getPostBody(req);
        String hash = req.getHeader("X-Riskified-Hmac-Sha256");
        return this.toObject(jb.toString(), hash);
    }

    /**
     * @param req
     * @return
     * @throws IOException
     */
    private StringBuffer getPostBody(HttpServletRequest req) throws IOException {
        StringBuffer jb = new StringBuffer();
        String line;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }
        return jb;
    }

}
