import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.cert.CertificateFactory;

public class DigitalSignDemo {
    
    public static void main(String[] args) throws Exception {

        byte[] message = "This is a secret message".getBytes();
        KeyPair pair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        Signature signInstance = Signature.getInstance("SHA256withRSA");

        // Signing [data + private key]
        signInstance.initSign(pair.getPrivate());
        signInstance.update(message);
        byte[] digitalSignature = signInstance.sign();

        // Verification [data + signature + public key]
        signInstance.initVerify(pair.getPublic());
        signInstance.update(message);
        boolean isVerified = signInstance.verify(digitalSignature);

        // Output the result
        System.out.println("Digital Signature Verified: " + isVerified);
    }
}
