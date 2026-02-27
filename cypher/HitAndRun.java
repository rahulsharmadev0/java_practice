
import java.security.Key;
import java.security.SecureRandom;
import java.util.*;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;

public class HitAndRun {

    public static void main(String[] args) throws Exception {

        byte[] msg = "Hi, Rahul".getBytes();


        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); // Object of Cipher and tell the algorithm

        Key masterKey = KeyGenerator.getInstance("AES").generateKey(); // Generating Key


        // 2. Generate IV (12 bytes is recommended for GCM)
        /// Manually Ensure the Confidentiality
        byte[] iv = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        
        cipher.init(Cipher.ENCRYPT_MODE, masterKey, spec); // Tell What to Do (ENCRYPT_MODE)

        byte[] cipherBytes = cipher.doFinal(msg); // Encrypting the message

        System.out.println("Encoded Message: " + Base64.getEncoder().encodeToString(cipherBytes));

        // Decrypting the message


        cipher.init(Cipher.DECRYPT_MODE, masterKey, spec); // Tell What to Do (DECRYPT_MODE)

        byte [] decodedBytes = cipher.doFinal(cipherBytes); // Decrypting the message

        System.out.println("Decoded Message: " + new String(decodedBytes));


        
    }








    public static void main2(String[] args) {

        byte[] msg = "Hi, Rahul".getBytes();
        
        System.out.println("Before Encoded: " + new String(msg));

        String encodedMsg = Base64.getEncoder().encodeToString(msg);

        System.out.println("Encoded Message: " + encodedMsg);

        byte[] decodedBytes = Base64.getDecoder().decode(encodedMsg);

        System.out.println("Decoded Message: " + new String(decodedBytes));

    }

    
}