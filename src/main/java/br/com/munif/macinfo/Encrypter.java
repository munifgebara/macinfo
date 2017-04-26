package br.com.munif.macinfo;

import java.net.NetworkInterface;
import java.security.spec.KeySpec;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import java.util.Base64;

public class Encrypter {

    public static final String ENC_DEFAULT_KEY = "YUNWEUYSKHWKHFABCUEKWYRNUI";
    public static final String DES_ENCRYPTION_SCHEME = "DES";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec keySpec;
    private SecretKeyFactory keyFactory;
    private Cipher cipher;
    private static final String ENCODING = "UTF8";

    public Encrypter() {
        this("DES", getMacValue());
    }

    public Encrypter(String key) {
        this("DES", key);
    }

    public Encrypter(String encryptionScheme, String encryptionKey) {
        if (encryptionKey == null) {
            throw new IllegalArgumentException("encryption key was invalid");
        }
        try {
            final byte[] keyAsBytes = encryptionKey.getBytes(ENCODING);
            if (encryptionScheme.equals(DESEDE_ENCRYPTION_SCHEME)) {
                keySpec = new DESedeKeySpec(keyAsBytes);
            } else if (encryptionScheme.equals(DES_ENCRYPTION_SCHEME)) {
                keySpec = new DESKeySpec(keyAsBytes);
            } else {
                throw new IllegalArgumentException("Encryption scheme not supported: " + encryptionScheme);
            }
            keyFactory = SecretKeyFactory.getInstance(encryptionScheme);
            cipher = Cipher.getInstance(encryptionScheme);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String bytes2String(byte[] bytes) {
        final StringBuffer buf = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            buf.append((char) bytes[i]);
        }
        return buf.toString();
    }

    public String decrypt(String encstr) {
        if (encstr == null || encstr.trim().length() <= 0) {
            throw new IllegalArgumentException("encrypted string was null or empty");
        }
        try {
            final SecretKey key = keyFactory.generateSecret(keySpec);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plaintxt=Base64.getDecoder().decode(encstr);
            byte[] ciphertext = cipher.doFinal(plaintxt);
            return bytes2String(ciphertext);
        } catch (final Exception e) {
            throw new RuntimeException ("Problem on decrypt!");
        }

    }

    public String encrypt(String decrstr) {
        if (decrstr == null || decrstr.trim().length() == 0) {
            throw new IllegalArgumentException("unencrypted string was null or empty");
        }
        try {
            SecretKey key = keyFactory.generateSecret(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cleartext = decrstr.getBytes(ENCODING);
            byte[] ciphertext = cipher.doFinal(cleartext);
            return Base64.getEncoder().encodeToString(ciphertext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMacValue() {
        String toReturn = "";
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface network = networkInterfaces.nextElement();
                byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X", mac[i]));
                }
                toReturn += sb.toString();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (toReturn.length() == 0) {
            throw new RuntimeException("Impossible to find NetworkInterface");
        }
        return toReturn;
    }
}
