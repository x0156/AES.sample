package xyz.x0156.aes.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
/**
 * 
 * @author x0156
 */
public class AesUtil {

    private final String salt = "56d7c4c857d73be3a046af1c1a3eb444";
    private final String iv = "efc29528417b31a006d0aec4df640a62";

    private final int keySize;
    private final int iterationCount;
    private final Cipher cipher;

    public AesUtil(int keySize, int iterationCount) throws Exception {
        this.keySize = keySize;
        this.iterationCount = iterationCount;
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

    }

    public String encrypt(String passphrase, String plaintext) throws Exception {
        return encrypt(this.salt, this.iv, passphrase, plaintext);
    }

    public String encrypt(String salt, String iv, String passphrase, String plaintext) throws Exception {
        SecretKey key = generateKey(salt, passphrase);
        byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, iv, plaintext.getBytes("UTF-8"));
        return encode(encrypted);
    }

    public String decrypt(String passphrase, String ciphertext) throws Exception {
        return decrypt(this.salt, this.iv, passphrase, ciphertext);
    }

    public String decrypt(String salt, String iv, String passphrase, String ciphertext) throws Exception {

        SecretKey key = generateKey(salt, passphrase);
        byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, decode(ciphertext));
        return new String(decrypted, "UTF-8");

    }

    private byte[] doFinal(int encryptMode, SecretKey key, String iv, byte[] bytes) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(encryptMode, key, new IvParameterSpec(toHex(iv)));
        return cipher.doFinal(bytes);
    }

    private SecretKey generateKey(String salt, String passphrase) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), toHex(salt), iterationCount, keySize);
        SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return key;
    }

    private static String encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    private static byte[] decode(String str) {
        return Base64.decodeBase64(str);
    }

    private static byte[] toHex(String str) {
        try {
            return Hex.decodeHex(str.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalStateException(e);
        }
    }

}
