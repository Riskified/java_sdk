package com.riskified.notifications;

import com.google.gson.Gson;
import com.riskified.RiskifedError;
import com.riskified.SHA256Handler;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class NotificationHandler {
    private Gson gson;
	private SHA256Handler sha256Handler;

    public NotificationHandler(String authKey) throws RiskifedError {
        gson = new Gson();
        this.sha256Handler = new SHA256Handler(authKey);
    }

    public Notification toObject(String postBody, String hash) throws AuthError {
    	if (sha256Handler.isHmacCorrect(postBody, hash))
    		return gson.fromJson(postBody, Notification.class);
    	else
    		throw new AuthError(hash, sha256Handler.createSHA256(postBody));
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
