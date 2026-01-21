import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

import sun.security.tools.keytool.CertAndKeyGen;

public class GenerateTestKeystore {
    public static void main(String[] args) throws Exception {
        System.out.println("Generating test keystore...");

        // Create keystore
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(null, null);

        // Generate RSA key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();

        // Create self-signed certificate
        CertAndKeyGen certGen = new CertAndKeyGen("RSA", "SHA256WithRSA", null);
        certGen.generate(2048);
        X509Certificate cert = certGen.getSelfCertificate(
                new X500Principal("CN=Test,O=RSLock,L=Test,ST=Test,C=US"),
                365 * 24 * 60 * 60 // 1 year validity
        );

        // Store in keystore
        keystore.setKeyEntry(
                "rslock-key",
                certGen.getPrivateKey(),
                "".toCharArray(),
                new Certificate[] { cert });

        // Save keystore
        try (FileOutputStream fos = new FileOutputStream("test_data/test-keystore.p12")) {
            keystore.store(fos, "".toCharArray());
        }

        System.out.println("✓ Keystore generated: test_data/test-keystore.p12");
        System.out.println("  Alias: rslock-key");
        System.out.println("  Password: (empty)");
    }
}
