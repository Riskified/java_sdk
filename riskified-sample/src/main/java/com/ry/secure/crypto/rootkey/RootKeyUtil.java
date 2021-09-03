package com.ry.secure.crypto.rootkey;

import com.ry.secure.crypto.CommCryptUtil;
import com.ry.secure.crypto.codec.AegisDecoderException;
import com.ry.secure.crypto.pbkdf2.Pbkdf2CryptUtil;

import java.io.IOException;


public class RootKeyUtil {
    private static final int ITERATION_COUNT = 10000;
    private byte[] rootKey = null;

    private RootKeyUtil() {
    }

    private int getMinValue(int len1, int len2, int len3) {
        int min = len1;
        if (len2 < len1) {
            min = len2;
        }

        if (len3 < min) {
            min = len3;
        }

        return min;
    }

    private boolean isKeyAndSaltValid(int keyLength, byte[] salt) {
        boolean retVal1 = CommCryptUtil.isKeyLengthValid(keyLength);
        boolean retVal2 = CommCryptUtil.isKeyLengthValid(salt);
        return retVal1 & retVal2;
    }

    public static RootKeyUtil newInstance(String first, String second, String third, String salt) throws IOException {
        RootKeyUtil keyObj = new RootKeyUtil();

        try {
            keyObj.initRootKey(first, second, third, salt);
            return keyObj;
        } catch (AegisDecoderException var6) {
            throw new IOException("invalid hex string.");
        }
    }


    private void initRootKey(String first, String second, String third, String salt) throws AegisDecoderException {
        this.initRootKey(first, second, third, CommCryptUtil.hexStr2Byte(salt));
    }

    private void initRootKey(String first, String second, String third, byte[] salt) throws AegisDecoderException {
        byte[] c1 = CommCryptUtil.hexStr2Byte(first);
        byte[] c2 = CommCryptUtil.hexStr2Byte(second);
        byte[] c3 = CommCryptUtil.hexStr2Byte(third);
        int len = this.getMinValue(c1.length, c2.length, c3.length);
        if (!this.isKeyAndSaltValid(len, salt)) {
            throw new IllegalArgumentException("key length must be more than 128bit.");
        } else {
            char[] combined = new char[len];

            for(int i = 0; i < len; ++i) {
                combined[i] = (char)(c1[i] ^ c2[i] ^ c3[i]);
            }

            this.rootKey = Pbkdf2CryptUtil.encryptBytePBKDF2WithSHA256(combined, salt, 10000, 16);
        }
    }

    public byte[] getRootKey() {
        return (byte[])this.rootKey.clone();
    }

}