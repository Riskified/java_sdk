package com.ry.secure.crypto;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.ry.secure.crypto.codec.AegisDecoderException;
import com.ry.secure.crypto.codec.HexHelper;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.prng.SP800SecureRandomBuilder;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class CommCryptUtil {
    public static final int KEY_96BIT_SIZE = 12;
    public static final int KEY_128BIT_SIZE = 16;
    public static final int KEY_256BIT_SIZE = 32;
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    public static final String KEY_HEAD = "security:";
    public static final String AES_CIPHER_GCM = "AES_GCM";
    public static final String AES_CIPHER_CBC = "AES_CBC";
    private static volatile SecureRandom secRandom = null;
    private static final int KEY_MIN_VALID_LENGTH = 16;

    public CommCryptUtil() {
    }


    public static String byte2HexStr(byte[] array) {
        return array == null ? null : new String(HexHelper.encodeHex(array, false));
    }


    public static byte[] hexStr2Byte(String hexStr) throws AegisDecoderException {
        return hexStr == null ? new byte[0] : HexHelper.decodeHex(hexStr.toCharArray());
    }


    public static SecureRandom genSecRandom() throws GeneralSecurityException {
        if (secRandom == null) {
            Class var0 = SecureRandom.class;
            synchronized(SecureRandom.class) {
                if (secRandom == null) {
                    secRandom = genSecureRandom();
                }
            }
        }

        return secRandom;
    }

    private static SecureRandom genSecureRandom() throws GeneralSecurityException {
        SecureRandom source;
        try {
            source = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException var8) {
            throw new GeneralSecurityException(var8);
        }

        boolean predictionResistant = true;
        BlockCipher cipher = new AESEngine();
        int cipherLen = 256;
        int entropyBitesRequired = 384;
        byte[] nonce = new byte[cipherLen / 8];
        source.nextBytes(nonce);
        boolean reSeed = false;
        SecureRandom random = (new SP800SecureRandomBuilder(source, predictionResistant)).setEntropyBitsRequired(entropyBitesRequired).buildCTR(cipher, cipherLen, nonce, reSeed);
        random.nextInt();
        return random;
    }

    public static byte[] genSecureRandomByte(int byteSize) throws GeneralSecurityException {
        genSecRandom();
        byte[] bytes = new byte[byteSize];
        secRandom.nextBytes(bytes);
        return bytes;
    }

    public static byte[] genSaltBytes() throws GeneralSecurityException {
        return genSecureRandomByte(16);
    }

    public static String stripCryptHead(String encryptText) {
        int index = encryptText.indexOf("security:");
        return index == -1 ? null : encryptText.substring("security:".length());
    }

    public static byte[] stripCryptHead(byte[] encryptText) {
        String encryptStr = new String(encryptText, DEFAULT_CHARSET);
        if (!encryptStr.startsWith("security:")) {
            return null;
        } else if (encryptText.length > "security:".length()) {
            byte[] newStr = new byte[encryptText.length - "security:".length()];
            System.arraycopy(encryptText, "security:".length(), newStr, 0, newStr.length);
            return newStr;
        } else {
            return null;
        }
    }

    public static boolean isKeyLengthValid(byte[] keyByte) {
        return keyByte.length >= 16;
    }


    public static boolean isKeyLengthValid(int keyLength) {
        return keyLength >= 16;
    }
}
