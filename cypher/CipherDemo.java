import java.security.*;
import java.security.KeyPairGenerator;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;







public class CipherDemo {

    public static void main(String[] args) throws Exception {

        byte[] msg = "hi, Rahul".getBytes();

        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();

        var cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

        var cipherBytes =  cipher.doFinal(msg);

        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

        var rawBytes = cipher.doFinal(cipherBytes);


        System.out.println(new String(rawBytes));
    }

    public static void sync() throws Exception {
        var msg = "hi, Rahul";
        var masterKey = KeyGenerator.getInstance("AES").generateKey();

        var crypto = Cipher.getInstance("AES");

        crypto.init(Cipher.ENCRYPT_MODE, masterKey);
        var cipherBytes = crypto.doFinal(msg.getBytes());

        // FOR Sending Data
        var cipherText =  Base64.getEncoder().encodeToString(cipherBytes);

        cipherBytes = Base64.getDecoder().decode(cipherText); 

        // Reverse;
        crypto.init(Cipher.DECRYPT_MODE, masterKey);
        var rawBytes = crypto.doFinal(cipherBytes);

        System.out.println(new String(rawBytes));

    }

}
