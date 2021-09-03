package com.ry.secure.crypto.workkey;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.ry.secure.crypto.CommCryptUtil;
import com.ry.secure.crypto.codec.AegisDecoderException;
import com.ry.secure.crypto.rootkey.RootKeyCryptUtil;
import com.ry.secure.crypto.rootkey.RootKeyUtil;
import com.ry.secure.crypto.rootkey.aes.AesCryptUtil;

import java.security.GeneralSecurityException;

public class WorkKeyCryptUtil {
    public WorkKeyCryptUtil() {
    }



    /** @deprecated */
    @Deprecated
    public static String decryptWorkKey(String enWorkKey) throws AegisDecoderException, GeneralSecurityException {
        return AesCryptUtil.decryptAes(enWorkKey, RootKeyCryptUtil.getRootKey(), "AES_CBC");
    }


    public static String decryptWorkKey(String enWorkKey, RootKeyUtil rootKey) throws AegisDecoderException, GeneralSecurityException {
        return AesCryptUtil.decryptAes(enWorkKey, rootKey.getRootKey(), "AES_CBC");
    }

    /** @deprecated */
    @Deprecated
    public static String decryptWorkKey(byte[] enWorkKeyBytes) throws GeneralSecurityException {
        return AesCryptUtil.decryptAes(enWorkKeyBytes, RootKeyCryptUtil.getRootKey(), "AES_CBC");
    }

    public static String decryptWorkKey(byte[] enWorkKeyBytes, RootKeyUtil rootKey) throws GeneralSecurityException {
        return AesCryptUtil.decryptAes(enWorkKeyBytes, rootKey.getRootKey(), "AES_CBC");
    }
}
