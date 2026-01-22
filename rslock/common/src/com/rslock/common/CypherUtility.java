package com.rslock.common;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.logging.Logger;

public final class CypherUtility {
	private static final Logger logger = Logger.getLogger(CypherUtility.class.getName());

	static {
		// Try to register Bouncy Castle provider if available
		try {
			Class<?> bcProvider = Class.forName("org.bouncycastle.jce.provider.BouncyCastleProvider");
			Security.addProvider((Provider) bcProvider.getDeclaredConstructor().newInstance());
		} catch (Exception e) {
			// BC not available, will use default providers
			System.err.println("Warning: Bouncy Castle provider not available, using default JCA providers");
		}
	}

	private CypherUtility() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	/**
	 * Generate a new AES key with the specified key size
	 */
	public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
		logger.fine(String.format("     Generating new AES key (Size: %d bits)", RsConstraints.AES_KEY_SIZE));
		KeyGenerator keyGenerator = KeyGenerator.getInstance(RsConstraints.AES_ALGORITHM);
		keyGenerator.init(RsConstraints.AES_KEY_SIZE);
		return keyGenerator.generateKey();
	}

	/**
	 * Generate a random Initialization Vector (IV) for AES CBC mode
	 */
	public static IvParameterSpec generateIV() {
		logger.fine("     Generating new Initialization Vector (IV)");
		byte[] ivBytes = new byte[RsConstraints.IV_SIZE];
		SecureRandom random = new SecureRandom();
		random.nextBytes(ivBytes);
		return new IvParameterSpec(ivBytes);
	}

	/**
	 * Encrypt AES key using RSA public key
	 */
	public static byte[] encryptAESKeyWithRSA(SecretKey aesKey, PublicKey publicKey) throws Exception {
		logger.fine("     Encrypting AES key with RSA public key");
		Cipher cipher = Cipher.getInstance(RsConstraints.RSA_TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(aesKey.getEncoded());
	}

	/**
	 * Decrypt AES key using RSA private key
	 */
	public static SecretKey decryptAESKeyWithRSA(byte[] encryptedKey, PrivateKey privateKey) throws Exception {
		logger.fine("     Decrypting AES key with RSA private key");
		Cipher cipher = Cipher.getInstance(RsConstraints.RSA_TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedKey = cipher.doFinal(encryptedKey);
		return new SecretKeySpec(decryptedKey, RsConstraints.AES_ALGORITHM);
	}

	/**
	 * Create cipher output stream for AES encryption
	 */
	public static CipherOutputStream createEncryptStream(OutputStream out, SecretKey aesKey, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(RsConstraints.AES_TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);
		return new CipherOutputStream(out, cipher);
	}

	/**
	 * Create cipher input stream for AES decryption
	 */
	public static CipherInputStream createDecryptStream(InputStream in, SecretKey aesKey, IvParameterSpec iv)
			throws Exception {
		Cipher cipher = Cipher.getInstance(RsConstraints.AES_TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, aesKey, iv);
		return new CipherInputStream(in, cipher);
	}

	/**
	 * Write encryption header to output stream
	 * Format: [IV_SIZE:4][IV:16][ENCRYPTED_KEY_SIZE:4][ENCRYPTED_AES_KEY:variable]
	 */
	public static void writeEncryptionHeader(OutputStream out, IvParameterSpec iv, byte[] encryptedAESKey)
			throws Exception {
		DataOutputStream dos = new DataOutputStream(out);
		byte[] ivBytes = iv.getIV();

		logger.fine(String.format("     Writing encryption header: IV_Length=%d, Encrypted_Key_Length=%d",
				ivBytes.length, encryptedAESKey.length));

		if (ivBytes.length <= 0 || ivBytes.length > 64) {
			throw new IllegalArgumentException("Invalid IV length: " + ivBytes.length);
		}
		if (encryptedAESKey.length <= 0 || encryptedAESKey.length > 8192) {
			throw new IllegalArgumentException("Invalid encrypted AES key size: " + encryptedAESKey.length);
		}

		// Write IV
		dos.writeInt(ivBytes.length);
		dos.write(ivBytes);

		// Write encrypted AES key
		dos.writeInt(encryptedAESKey.length);
		dos.write(encryptedAESKey);
		dos.flush();
	}

	/**
	 * Read encryption header from input stream
	 * Format: [IV_SIZE:4][IV:16][ENCRYPTED_KEY_SIZE:4][ENCRYPTED_AES_KEY:variable]
	 */
	public static byte[][] readEncryptionHeader(InputStream in) throws Exception {
		DataInputStream dis = new DataInputStream(in);

		int ivLength = dis.readInt();
		logger.fine("     Reading encryption header: IV_Length=" + ivLength);

		if (ivLength <= 0 || ivLength > 64) {
			throw new IllegalArgumentException("Invalid IV length: " + ivLength);
		}

		byte[] ivBytes = new byte[ivLength];
		dis.readFully(ivBytes);

		// Now read the encrypted AES key size
		int keyLength = dis.readInt();
		logger.fine("     Reading encryption header: Encrypted_Key_Length=" + keyLength);

		if (keyLength <= 0 || keyLength > 8192) {
			throw new IllegalArgumentException("Invalid encrypted AES key size: " + keyLength);
		}

		// Read encrypted AES key
		byte[] encryptedKey = new byte[keyLength];
		dis.readFully(encryptedKey);

		return new byte[][] { ivBytes, encryptedKey };
	}

	/**
	 * Load Public Key from KeyStore
	 */
	public static PublicKey loadPublicKey(KeyStore keyStore, String alias) throws Exception {
		logger.fine("Loading public key for alias: " + alias);
		Certificate certificate = keyStore.getCertificate(alias);
		if (certificate == null) {
			throw new KeyStoreException("Certificate not found for alias: " + alias);
		}
		return certificate.getPublicKey();
	}

	/**
	 * Load Private Key from KeyStore
	 */
	public static PrivateKey loadPrivateKey(KeyStore keyStore, String alias, char[] password) throws Exception {
		logger.fine("Loading private key for alias: " + alias);
		Key key = keyStore.getKey(alias, password);
		if (!(key instanceof PrivateKey)) {
			throw new KeyStoreException("Private key not found for alias: " + alias);
		}
		return (PrivateKey) key;
	}

	/**
	 * Generate a new PKCS12 keystore with RSA key pair using keytool command
	 * 
	 * @param keystorePath Path where keystore will be saved
	 * @param password     Password for the keystore
	 * @param alias        Alias for the key entry
	 * @return true if keystore was created successfully
	 */
	public static boolean generateKeyStore(java.nio.file.Path keystorePath, char[] password, String alias) {
		logger.fine("Generating new keystore at: " + keystorePath);
		try {
			String javaHome = System.getProperty("java.home");
			java.nio.file.Path keytoolPath = java.nio.file.Path.of(javaHome, "bin", "keytool");

			java.util.List<String> command = java.util.List.of(
					keytoolPath.toString(),
					"-genkeypair",
					"-alias", alias,
					"-keyalg", "RSA",
					"-keysize", "2048",
					"-keystore", keystorePath.toAbsolutePath().toString(),
					"-storetype", "PKCS12",
					"-storepass", new String(password),
					"-keypass", new String(password),
					"-dname", "CN=" + alias + ", O=RSLock, C=US",
					"-validity", "3650");

			ProcessBuilder pb = new ProcessBuilder(command);
			pb.redirectErrorStream(true);

			Process process = pb.start();

			// Read output from keytool
			try (java.io.BufferedReader reader = new java.io.BufferedReader(
					new java.io.InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					logger.fine("[keytool] " + line);
				}
			}

			int exitCode = process.waitFor();
			if (exitCode != 0) {
				logger.severe("Keytool failed with exit code " + exitCode);
				return false;
			}
			logger.fine("Keystore generated successfully");
			return true;
		} catch (Exception e) {
			logger.severe("Failed to generate keystore: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

}
