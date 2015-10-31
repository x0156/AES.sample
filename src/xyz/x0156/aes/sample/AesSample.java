
package xyz.x0156.aes.sample;

import xyz.x0156.aes.util.AesUtil;

/**
 *
 * @author x0156
 */
public class AesSample {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        String passphrase = "0123456789abcdf";
        int iterationCount = 156;
        int keySize = 128;
        String salt = "f7104946a3aefc11c6468162a2b64d55";
        String iv = "c1089fea078d8b99d15219b0b2dd351c";
        String ciphertext = "fP9h/RWm0XJ6fdFHRJWyGQ==";

        AesUtil aesUtil = new AesUtil(keySize, iterationCount);
        String plaintext = aesUtil.decrypt(salt, iv, passphrase, ciphertext);
        System.out.println(plaintext);

    }
}
