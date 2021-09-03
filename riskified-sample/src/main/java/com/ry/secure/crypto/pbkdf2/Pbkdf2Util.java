package com.ry.secure.crypto.pbkdf2;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.ry.secure.crypto.CommCryptUtil;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

public class Pbkdf2Util {
    private static final int SALT_LENGTH = 16;
    private static final int ITERATION_COUNT = 10000000;

    public Pbkdf2Util() {
    }

    public static String encryptPBKDF2WithSHA256(char[] plainKey, byte[] salt, int iterationCount, int cipherLen) {
        byte[] hash = encryptBytePBKDF2WithSHA256(plainKey, salt, iterationCount, cipherLen);
        return "security:" + CommCryptUtil.byte2HexStr(hash);
    }

    public static String encryptPBKDF2WithSHA256(String plainKey, byte[] salt, int iterationCount, int cipherLen) {
        return plainKey == null ? null : encryptPBKDF2WithSHA256(plainKey.toCharArray(), salt, iterationCount, cipherLen);
    }

    public static byte[] encryptBytePBKDF2WithSHA256(char[] plainKey, byte[] salt, int iterationCount, int cipherLen) {
        if (salt != null && salt.length >= 16) {
            PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator(new SHA256Digest());
            generator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(plainKey), salt, iterationCount);
            KeyParameter key1 = (KeyParameter)generator.generateDerivedMacParameters(cipherLen * 8);
            return key1.getKey();
        } else {
            throw new IllegalArgumentException("input salt length is shorter than 16");
        }
    }

    public static String encryptPBKDF2WithSHA256(char[] plainKey, byte[] salt) {
        return encryptPBKDF2WithSHA256((char[])plainKey, salt, 10000000, 32);
    }

    public static String encryptPBKDF2WithSHA256(String plainKey, byte[] salt) {
        return encryptPBKDF2WithSHA256((String)plainKey, salt, 10000000, 32);
    }
}
