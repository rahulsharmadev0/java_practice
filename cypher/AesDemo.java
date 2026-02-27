
import javax.crypto.*;
import javax.crypto.spec.*;

import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

public class AesDemo {

    static Key securKey;
    static Cipher cipher;

    static {
        try {
            Security.getAlgorithms("MAC").forEach(System.out::println);
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // Generate 256-bit AES key
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            securKey = keyGen.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        var file = "symmetric-encryption.md";

        var ciphertext = encrypt(file.getBytes(), securKey);

        System.out.println(bytesToString(ciphertext));

        var legibleText = decrypt(ciphertext, securKey);

        System.out.println();
        System.out.println(bytesToString(legibleText));

    }

    static String bytesToString(byte[] bytes) {
        return new String(bytes);
    }

    // legible data --encrypt(key)--> Ciphertext
    static byte[] encrypt(byte[] message, Key securKey) throws Exception {
        byte[] iv = new byte[16]; // 16-byte IV for CBC
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, securKey, ivSpec);

        var legibleData = cipher.doFinal(message);

        // combine IV + ciphertext
        byte[] finalCipher = new byte[iv.length + legibleData.length];
        System.arraycopy(iv, 0, finalCipher, 0, iv.length);
        System.arraycopy(legibleData, 0, finalCipher, iv.length, legibleData.length);
        
        return finalCipher;
    }

    // Ciphertext --decrypt(key)--> legible data
    static byte[] decrypt(byte[] message, Key securKey) throws Exception {
        byte[] iv = new byte[16]; // 16-byte IV for CBC
        byte[] ciphertext = new byte[message.length - 16];

        new SecureRandom().nextBytes(iv);

        System.arraycopy(message, 0, iv, 0, 16);
        System.arraycopy(message, 16, ciphertext, 0, ciphertext.length);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, securKey, ivSpec);
        return cipher.doFinal(ciphertext);
    }

}