package com.ry.secure.crypto.rootkey.aes;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.ry.secure.crypto.CommCryptUtil;
import com.ry.secure.crypto.codec.AegisDecoderException;
import com.ry.secure.crypto.rootkey.RootKeyUtil;
import com.ry.secure.crypto.workkey.WorkKeyCryptUtil;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

public class AesCryptUtil {
    private static final String AES_KEY_ALGORITHM = "AES";
    private static final String AES_CIPHER_CBC_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String AES_CIPHER_GCM_ALGORITHM = "AES/GCM/NoPadding";

    public AesCryptUtil() {
    }

    /** @deprecated */
    @Deprecated
    private static byte[] encryptAES(byte[] content, byte[] secret, byte[] salt, String aesType) throws GeneralSecurityException {
        Cipher cipher = null;
        byte[] keyBytes = Arrays.copyOf(secret, 16);
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
        if (isAesCipherGcm(aesType)) {
            cipher = genAesEncryptCipher(skeySpec, new GCMParameterSpec(128, Arrays.copyOf(salt, 12)), "AES/GCM/NoPadding");
        } else {
            cipher = genAesEncryptCipher(skeySpec, new IvParameterSpec(Arrays.copyOf(salt, 16)), "AES/CBC/PKCS5Padding");
        }

        return cipher.doFinal(content);
    }

    private static boolean isAesCipherGcm(String aesType) {
        return "AES_GCM".equals(aesType);
    }

    private static byte[] encryptAes(byte[] content, byte[] secret, byte[] salt, String aesType) throws GeneralSecurityException {
        Cipher cipher = null;
        SecretKeySpec skeySpec = new SecretKeySpec(secret, "AES");
        if (isAesCipherGcm(aesType)) {
            cipher = genAesEncryptCipher(skeySpec, new GCMParameterSpec(128, Arrays.copyOf(salt, 12)), "AES/GCM/NoPadding");
        } else {
            cipher = genAesEncryptCipher(skeySpec, new IvParameterSpec(Arrays.copyOf(salt, 16)), "AES/CBC/PKCS5Padding");
        }

        return cipher.doFinal(content);
    }

    private static Cipher genAesCipher(int cipherMode, SecretKeySpec skeySpec, AlgorithmParameterSpec algorithmParaSpec, String cipherType) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance(cipherType);
        cipher.init(cipherMode, skeySpec, algorithmParaSpec);
        return cipher;
    }

    private static Cipher genAesEncryptCipher(SecretKeySpec skeySpec, AlgorithmParameterSpec algorithmParaSpec, String cipherType) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
        return genAesCipher(1, skeySpec, algorithmParaSpec, cipherType);
    }

    private static Cipher genAesDecryptCipher(SecretKeySpec skeySpec, AlgorithmParameterSpec algorithmParaSpec, String cipherType) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
        return genAesCipher(2, skeySpec, algorithmParaSpec, cipherType);
    }

    /** @deprecated */
    @Deprecated
    private static byte[] decryptAES(byte[] content, byte[] key, byte[] iv, String aesType) throws GeneralSecurityException {
        byte[] keyBytes = Arrays.copyOf(key, 16);
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = null;
        if (isAesCipherGcm(aesType)) {
            cipher = genAesDecryptCipher(skeySpec, new GCMParameterSpec(128, iv), "AES/GCM/NoPadding");
        } else {
            cipher = genAesDecryptCipher(skeySpec, new IvParameterSpec(iv), "AES/CBC/PKCS5Padding");
        }

        return cipher.doFinal(content);
    }

    private static byte[] decryptAes(byte[] content, byte[] key, byte[] iv, String aesType) throws GeneralSecurityException {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        if (isAesCipherGcm(aesType)) {
            cipher = genAesDecryptCipher(skeySpec, new GCMParameterSpec(128, iv), "AES/GCM/NoPadding");
        } else {
            cipher = genAesDecryptCipher(skeySpec, new IvParameterSpec(iv), "AES/CBC/PKCS5Padding");
        }

        return cipher.doFinal(content);
    }

    /** @deprecated */
    @Deprecated
    public static String encryptAES(String plainKey, byte[] key, byte[] salt, String aesType) throws GeneralSecurityException {
        if (plainKey != null && key != null && salt != null) {
            if (!CommCryptUtil.isKeyLengthValid(key)) {
                throw new IllegalArgumentException("key length must be more than 128bit.");
            } else {
                byte[] cipherKey = encryptAES(plainKey.getBytes(CommCryptUtil.DEFAULT_CHARSET), key, salt, aesType);
                String composeKey = "security:" + CommCryptUtil.byte2HexStr(salt) + ':' + CommCryptUtil.byte2HexStr(cipherKey);
                return composeKey;
            }
        } else {
            return null;
        }
    }

    public static String encryptAes(String plainKey, byte[] key, byte[] salt, String aesType) throws GeneralSecurityException {
        if (plainKey != null && key != null && salt != null) {
            if (!CommCryptUtil.isKeyLengthValid(key)) {
                throw new IllegalArgumentException("key length must be more than 128bit.");
            } else {
                byte[] cipherKey = encryptAes(plainKey.getBytes(CommCryptUtil.DEFAULT_CHARSET), key, salt, aesType);
                String composeKey = "security:" + CommCryptUtil.byte2HexStr(salt) + ':' + CommCryptUtil.byte2HexStr(cipherKey);
                return composeKey;
            }
        } else {
            return null;
        }
    }

    /** @deprecated */
    @Deprecated
    public static byte[] encryptAesRetbytes(String plainKey, byte[] key, byte[] salt, String aesType) throws GeneralSecurityException {
        if (plainKey != null && key != null && salt != null) {
            if (!CommCryptUtil.isKeyLengthValid(key)) {
                throw new IllegalArgumentException("key length must be more than 128bit.");
            } else {
                byte[] cipherKey = encryptAES(plainKey.getBytes(CommCryptUtil.DEFAULT_CHARSET), key, salt, aesType);
                byte[] retEncryptDat = Arrays.copyOf("security:".getBytes(CommCryptUtil.DEFAULT_CHARSET), "security:".length() + salt.length + ":".length() + cipherKey.length);
                System.arraycopy(salt, 0, retEncryptDat, "security:".length(), salt.length);
                System.arraycopy(":".getBytes(CommCryptUtil.DEFAULT_CHARSET), 0, retEncryptDat, "security:".length() + salt.length, ":".length());
                System.arraycopy(cipherKey, 0, retEncryptDat, "security:".length() + salt.length + ":".length(), cipherKey.length);
                return retEncryptDat;
            }
        } else {
            return null;
        }
    }

    public static byte[] encryptAesRetbyte(String plainKey, byte[] key, byte[] salt, String aesType) throws GeneralSecurityException {
        if (plainKey != null && key != null && salt != null) {
            if (!CommCryptUtil.isKeyLengthValid(key)) {
                throw new IllegalArgumentException("key length must be more than 128bit.");
            } else {
                return encryptAesRetbyteComm(plainKey.getBytes(CommCryptUtil.DEFAULT_CHARSET), key, salt, aesType);
            }
        } else {
            return null;
        }
    }

    public static byte[] encryptAesRetbyte(byte[] plainKey, byte[] key, byte[] salt, String aesType) throws GeneralSecurityException {
        if (plainKey != null && key != null && salt != null) {
            if (!CommCryptUtil.isKeyLengthValid(key)) {
                throw new IllegalArgumentException("key length must be more than 128bit.");
            } else {
                return encryptAesRetbyteComm(plainKey, key, salt, aesType);
            }
        } else {
            return null;
        }
    }

    private static byte[] encryptAesRetbyteComm(byte[] plainKey, byte[] key, byte[] salt, String aesType) throws GeneralSecurityException {
        byte[] cipherKey = encryptAes(plainKey, key, salt, aesType);
        byte[] retEncryptDat = Arrays.copyOf("security:".getBytes(CommCryptUtil.DEFAULT_CHARSET), "security:".length() + salt.length + ":".length() + cipherKey.length);
        System.arraycopy(salt, 0, retEncryptDat, "security:".length(), salt.length);
        System.arraycopy(":".getBytes(CommCryptUtil.DEFAULT_CHARSET), 0, retEncryptDat, "security:".length() + salt.length, ":".length());
        System.arraycopy(cipherKey, 0, retEncryptDat, "security:".length() + salt.length + ":".length(), cipherKey.length);
        return retEncryptDat;
    }

    /** @deprecated */
    @Deprecated
    public static String encryptAESCbc(String plainText, String encryptKey) throws GeneralSecurityException, AegisDecoderException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return encryptAES(plainText, key.getBytes(CommCryptUtil.DEFAULT_CHARSET), CommCryptUtil.genSecureRandomByte(16), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static String encryptByAESCbc(String plainText, String encryptKey) throws GeneralSecurityException, AegisDecoderException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return encryptAES(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(16), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static String encryptAesCbc(String plainText, String encryptKey) throws GeneralSecurityException, AegisDecoderException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return encryptAes(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(16), "AES_CBC");
    }

    public static String encryptAesCbc(String plainText, String encryptKey, RootKeyUtil rootKey) throws GeneralSecurityException, AegisDecoderException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return encryptAes(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(16), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static String encryptByAESCbc(String plainText, byte[] encryptKey) throws GeneralSecurityException, AegisDecoderException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return encryptAES(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(16), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static String encryptAesCbc(String plainText, byte[] encryptKey) throws GeneralSecurityException, AegisDecoderException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return encryptAes(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(16), "AES_CBC");
    }

    public static String encryptAesCbc(String plainText, byte[] encryptKey, RootKeyUtil rootKey) throws GeneralSecurityException, AegisDecoderException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return encryptAes(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(16), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static byte[] encryptAesCbc(byte[] plainText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return encryptAesRetbyte(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(16), "AES_CBC");
    }

    public static byte[] encryptAesCbc(byte[] plainText, byte[] encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return encryptAesRetbyte(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(16), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static String decryptAES(String encryptText, String encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return decryptAES(encryptText, key.getBytes(CommCryptUtil.DEFAULT_CHARSET), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static String decryptByAES(String encryptText, String encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return decryptAES(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static String decryptByAes(String encryptText, String encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return decryptAes(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_CBC");
    }

    public static String decryptByAes(String encryptText, String encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return decryptAes(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static String decryptByAES(String encryptText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return decryptAES(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static String decryptByAes(String encryptText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return decryptAes(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_CBC");
    }

    public static String decryptByAes(String encryptText, byte[] encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return decryptAes(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static byte[] decryptByAes(byte[] encryptText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return decryptAesRetByte(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_CBC");
    }

    public static byte[] decryptByAes(byte[] encryptText, byte[] encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return decryptAesRetByte(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static String decryptByAesCbc(String encryptText, String encryptKey) throws AegisDecoderException, GeneralSecurityException {
        return decryptByAES(encryptText, encryptKey);
    }

    /** @deprecated */
    @Deprecated
    public static String decryptAesCbc(String encryptText, String encryptKey) throws AegisDecoderException, GeneralSecurityException {
        return decryptByAes(encryptText, encryptKey);
    }

    public static String decryptAesCbc(String encryptText, String encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        return decryptByAes(encryptText, encryptKey, rootKey);
    }

    /** @deprecated */
    @Deprecated
    public static String decryptByAesCbc(String encryptText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        return decryptByAES(encryptText, encryptKey);
    }

    /** @deprecated */
    @Deprecated
    public static String decryptAesCbc(String encryptText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        return decryptByAes(encryptText, encryptKey);
    }

    public static String decryptAesCbc(String encryptText, byte[] encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        return decryptByAes(encryptText, encryptKey, rootKey);
    }

    /** @deprecated */
    @Deprecated
    public static byte[] decryptAesCbc(byte[] encryptText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        return decryptByAes(encryptText, encryptKey);
    }

    public static byte[] decryptAesCbc(byte[] encryptText, byte[] encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        return decryptByAes(encryptText, encryptKey, rootKey);
    }

    /** @deprecated */
    @Deprecated
    public static String decryptAES(String encryptText, byte[] key, String aesType) throws GeneralSecurityException, AegisDecoderException {
        if (encryptText != null && key != null) {
            String subEncryptText = CommCryptUtil.stripCryptHead(encryptText);
            if (subEncryptText == null) {
                return null;
            } else {
                int index = subEncryptText.indexOf(58);
//                byte[] salt = null;
//                byte[] cipherKey = null;
                if (index >= 24) {
                    byte[] salt = CommCryptUtil.hexStr2Byte(subEncryptText.substring(0, index));
                    byte[] cipherKey = CommCryptUtil.hexStr2Byte(subEncryptText.substring(index + 1));
                    byte[] plainKey = decryptAES(cipherKey, key, salt, aesType);
                    return new String(plainKey, CommCryptUtil.DEFAULT_CHARSET);
                } else {
                    throw new IllegalArgumentException("missing colon");
                }
            }
        } else {
            return null;
        }
    }

    public static String decryptAes(String encryptText, byte[] key, String aesType) throws GeneralSecurityException, AegisDecoderException {
        if (encryptText != null && key != null) {
            String subEncryptText = CommCryptUtil.stripCryptHead(encryptText);
            if (subEncryptText == null) {
                return null;
            } else {
                int index = subEncryptText.indexOf(58);
//                byte[] salt = null;
//                byte[] cipherKey = null;
                if (index >= 24) {
                    byte[] salt = CommCryptUtil.hexStr2Byte(subEncryptText.substring(0, index));
                    byte[] cipherKey = CommCryptUtil.hexStr2Byte(subEncryptText.substring(index + 1));
                    byte[] plainKey = decryptAes(cipherKey, key, salt, aesType);
                    return new String(plainKey, CommCryptUtil.DEFAULT_CHARSET);
                } else {
                    throw new IllegalArgumentException("missing colon");
                }
            }
        } else {
            return null;
        }
    }

    /** @deprecated */
    @Deprecated
    public static String decryptAES(byte[] encryptText, byte[] key, String aesType) throws GeneralSecurityException, AegisDecoderException {
        if (encryptText != null && key != null) {
            byte[] subEncryptText = CommCryptUtil.stripCryptHead(encryptText);
            if (subEncryptText == null) {
                return null;
            } else {
                int index = findByteIndexFromIv(subEncryptText, aesType);
//                byte[] salt = null;
//                byte[] cipherKey = null;
                if (index >= 0) {
                    byte[] salt = Arrays.copyOf(subEncryptText, index);
                    int cipherKeyLen = subEncryptText.length - salt.length - ":".length();
                    byte[] cipherKey = new byte[cipherKeyLen];
                    System.arraycopy(subEncryptText, index + 1, cipherKey, 0, cipherKeyLen);
                    byte[] plainKey = decryptAES(cipherKey, key, salt, aesType);
                    return new String(plainKey, CommCryptUtil.DEFAULT_CHARSET);
                } else {
                    throw new IllegalArgumentException("missing colon");
                }
            }
        } else {
            return null;
        }
    }

    public static String decryptAes(byte[] encryptText, byte[] key, String aesType) throws GeneralSecurityException {
        return new String(decryptAesRetByte(encryptText, key, aesType), CommCryptUtil.DEFAULT_CHARSET);
    }

    public static byte[] decryptAesRetByte(byte[] encryptText, byte[] key, String aesType) throws GeneralSecurityException {
        if (encryptText != null && key != null) {
            byte[] subEncryptText = CommCryptUtil.stripCryptHead(encryptText);
            if (subEncryptText == null) {
                return null;
            } else {
                int index = findByteIndexFromIv(subEncryptText, aesType);
//                byte[] salt = null;
//                byte[] cipherKey = null;
                if (index >= 0) {
                    byte[] salt = Arrays.copyOf(subEncryptText, index);
                    int cipherKeyLen = subEncryptText.length - salt.length - ":".length();
                    byte[] cipherKey = new byte[cipherKeyLen];
                    System.arraycopy(subEncryptText, index + 1, cipherKey, 0, cipherKeyLen);
                    return decryptAes(cipherKey, key, salt, aesType);
                } else {
                    throw new IllegalArgumentException("missing colon");
                }
            }
        } else {
            return null;
        }
    }

    private static int findByteIndexFromIv(byte[] ivText, String aesType) {
        int index = aesType.equals("AES_CBC") ? 16 : 12;
        return ivText[index] == 58 ? index : -1;
    }

    /** @deprecated */
    @Deprecated
    public static String encryptByAesGcm(String plainText, String encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return encryptAES(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(12), "AES_GCM");
    }

    /** @deprecated */
    @Deprecated
    public static String encryptAesGcm(String plainText, String encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return encryptAes(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(12), "AES_GCM");
    }



    public static String encryptAesGcm(String plainText, String encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return encryptAes(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(12), "AES_GCM");
    }

    /** @deprecated */
    @Deprecated
    public static String encryptByAesGcm(String plainText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return encryptAES(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(12), "AES_GCM");
    }

    /** @deprecated */
    @Deprecated
    public static String encryptAesGcm(String plainText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return encryptAes(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(12), "AES_GCM");
    }

    public static String encryptAesGcm(String plainText, byte[] encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return encryptAes(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(12), "AES_GCM");
    }

    /** @deprecated */
    @Deprecated
    public static byte[] encryptAesGcm(byte[] plainText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return encryptAesRetbyte(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(12), "AES_GCM");
    }

    public static byte[] encryptAesGcm(byte[] plainText, byte[] encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return encryptAesRetbyte(plainText, CommCryptUtil.hexStr2Byte(key), CommCryptUtil.genSecureRandomByte(12), "AES_GCM");
    }

    /** @deprecated */
    @Deprecated
    public static String decryptByAesGcm(String encryptText, String encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return decryptAES(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_GCM");
    }

    /** @deprecated */
    @Deprecated
    public static String decryptAesGcm(String encryptText, String encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return decryptAes(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_GCM");
    }

    public static String decryptAesGcm(String encryptText, String encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return decryptAes(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_GCM");
    }

    /** @deprecated */
    @Deprecated
    public static String decryptByAesGcm(String encryptText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return decryptAES(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_GCM");
    }

    /** @deprecated */
    @Deprecated
    public static String decryptAesGcm(String encryptText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return decryptAes(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_GCM");
    }

    public static String decryptAesGcm(String encryptText, byte[] encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return decryptAes(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_GCM");
    }

    /** @deprecated */
    @Deprecated
    public static byte[] decryptAesGcm(byte[] encryptText, byte[] encryptKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey);
        return decryptAesRetByte(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_GCM");
    }

    public static byte[] decryptAesGcm(byte[] encryptText, byte[] encryptKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        String key = WorkKeyCryptUtil.decryptWorkKey(encryptKey, rootKey);
        return decryptAesRetByte(encryptText, CommCryptUtil.hexStr2Byte(key), "AES_GCM");
    }
}
