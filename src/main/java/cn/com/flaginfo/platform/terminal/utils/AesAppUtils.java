package cn.com.flaginfo.platform.terminal.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class AesAppUtils {

    public static final String NAME = "AES";

    public AesAppUtils() {
    }

    public static String encode(String key, String content) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, new SecretKeySpec(Hex.decodeHex(key.toCharArray()), "AES"));
            return new String(Hex.encodeHex(cipher.doFinal(content.getBytes())));
        } catch (Exception var3) {
            throw new IllegalStateException("AES Encode Error Caused", var3);
        }
    }

    public static String genKey() {
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException var2) {
            throw new IllegalStateException("No AES Algorithm Found", var2);
        }

        kg.init(128);
        SecretKey sk = kg.generateKey();
        return new String(Hex.encodeHex(sk.getEncoded()));
    }

    public static String decode(String key, String result) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, new SecretKeySpec(Hex.decodeHex(key.toCharArray()), "AES"));
            return new String(cipher.doFinal(Hex.decodeHex(result.toCharArray())));
        } catch (Exception var3) {
            throw new IllegalStateException("AES Decode Error Caused", var3);
        }
    }

}
