//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.ry.secure.crypto.pbkdf2;

import com.ry.secure.crypto.CommCryptUtil;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

public class Pbkdf2CryptUtil {
    public Pbkdf2CryptUtil() {
    }




    public static byte[] encryptBytePBKDF2WithSHA256(char[] plainKey, byte[] salt, int iterationCount, int cipherLen) {
        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator(new SHA256Digest());
        generator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(plainKey), salt, iterationCount);
        KeyParameter key1 = (KeyParameter)generator.generateDerivedMacParameters(cipherLen * 8);
        return key1.getKey();
    }

}
