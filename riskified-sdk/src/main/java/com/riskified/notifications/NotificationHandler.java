package com.riskified.notifications;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

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
        return toObject(data.getBytes("UTF-8"), hash);
    }

    /**
     * Convert servlet to notification object.
     * The signature is checked against the raw request body, so the body is read with
     * {@code req.getInputStream()}; nothing may have read it with {@code req.getReader()} before.
     * @param req the servlet request
     * @return Notification
     * @throws IOException error reading the post body
     * @throws AuthError the hash doesn't match the was calced sha256 for the string
     */
    public Notification parseServletPostRequest(HttpServletRequest req) throws AuthError, IOException {
        byte[] body = readBody(req);
        String hash = req.getHeader("X-Riskified-Hmac-Sha256");
        return toObject(body, hash);
    }

    private Notification toObject(byte[] body, String hash) throws AuthError {
        String calcHash = sha256Handler.createSHA256(body);
        if (MessageDigest.isEqual(hash.getBytes(), calcHash.getBytes()))
            return gson.fromJson(new String(body, StandardCharsets.UTF_8), Notification.class);
        else
            throw new AuthError();
    }

    // Reads the exact bytes Riskified signed. Reading lines would drop newlines, and getReader()
    // decodes with the request charset (ISO-8859-1 when none is declared), so either breaks the signature.
    private static byte[] readBody(HttpServletRequest req) throws IOException {
        InputStream in = req.getInputStream();
        ByteArrayOutputStream body = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int read;
        while ((read = in.read(buffer)) != -1) {
            body.write(buffer, 0, read);
        }
        return body.toByteArray();
    }

}
