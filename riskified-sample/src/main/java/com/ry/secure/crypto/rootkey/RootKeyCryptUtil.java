package com.ry.secure.crypto.rootkey;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.ry.secure.crypto.CommCryptUtil;
import com.ry.secure.crypto.codec.AegisDecoderException;
import com.ry.secure.crypto.pbkdf2.Pbkdf2Util;

public class RootKeyCryptUtil {
    private static final int ITERATION_COUNT = 10000;
    private static byte[] rootKey = null;

    public RootKeyCryptUtil() {
    }

    private static int getMinValue(int len1, int len2, int len3) {
        int min = len1;
        if (len2 < len1) {
            min = len2;
        }

        if (len3 < min) {
            min = len3;
        }

        return min;
    }

    private static boolean isKeyAndSaltValid(int keyLength, byte[] salt) {
        boolean retVal1 = CommCryptUtil.isKeyLengthValid(keyLength);
        boolean retVal2 = CommCryptUtil.isKeyLengthValid(salt);
        return retVal1 & retVal2;
    }

    public static String getRootKey(String first, String second, String third, String salt) throws AegisDecoderException {
        return getRootKey(first, second, third, CommCryptUtil.hexStr2Byte(salt));
    }

    public static String getRootKey(String first, String second, String third, byte[] salt) throws AegisDecoderException {
        byte[] c1 = CommCryptUtil.hexStr2Byte(first);
        byte[] c2 = CommCryptUtil.hexStr2Byte(second);
        byte[] c3 = CommCryptUtil.hexStr2Byte(third);
        int len = getMinValue(c1.length, c2.length, c3.length);
        if (!isKeyAndSaltValid(len, salt)) {
            throw new IllegalArgumentException("key length must be more than 128bit.");
        } else {
            char[] combined = new char[len];

            for(int i = 0; i < len; ++i) {
                combined[i] = (char)(c1[i] ^ c2[i] ^ c3[i]);
            }

            rootKey = Pbkdf2Util.encryptBytePBKDF2WithSHA256(combined, salt, 10000, 16);
            return "security:" + CommCryptUtil.byte2HexStr(rootKey);
        }
    }

    public static byte[] getRootKey() {
        return (byte[])rootKey.clone();
    }
}
