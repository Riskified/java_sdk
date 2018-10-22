package com.riskified.notifications;

import java.io.*;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.*;
import com.riskified.RiskifiedError;
import com.riskified.SHA256Handler;

/**
 * Converting string / servlet to notifaction object.
 * see http://apiref.riskified.com/curl/#notifications
 */
public class NotificationHandler {
    private Gson gson;
    private SHA256Handler sha256Handler;

    /**
     * Converting string / servlet to notifaction object.
     * @param authKey From the advance settings in Riskified web site
     * @throws RiskifiedError When there was a critical error, look at the exception to see more data
     */
    public NotificationHandler(String authKey) throws RiskifiedError {
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        this.sha256Handler = new SHA256Handler(authKey);
    }

    /**
     * Convert string to notification object
     * @param data the string to convert
     * @param hash the sha256 of the string
     * @return Notification
     * @throws AuthError the hash doesn't match the was calced sha256 for the string
     * @throws UnsupportedEncodingException unsupported encoding exception
     * @throws IllegalStateException illegal state exception
     * @throws JsonSyntaxException json syntax exception
     */
    public Notification toObject(String data, String hash) throws AuthError, JsonSyntaxException, IllegalStateException, UnsupportedEncodingException {
        if (sha256Handler.isHmacCorrect(data, hash))
            return gson.fromJson(data, Notification.class);
        else
            throw new AuthError(hash, sha256Handler.createSHA256(data));
    }

    /**
     * Convert servlet to notification object 
     * @param req the servlet request
     * @return Notification
     * @throws IOException error reading the post body
     * @throws AuthError the hash doesn't match the was calced sha256 for the string
     */
    public Notification parseServletPostRequest(HttpServletRequest req) throws AuthError, IOException {
        StringBuffer jb = getPostBody(req);
        String hash = req.getHeader("X-Riskified-Hmac-Sha256");
        return this.toObject(jb.toString(), hash);
    }

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
