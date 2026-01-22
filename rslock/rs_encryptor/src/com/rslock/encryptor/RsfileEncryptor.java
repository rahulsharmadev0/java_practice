package com.rslock.encryptor;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.PublicKey;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.rslock.common.*;

public class RsfileEncryptor {

	private static final String LOG_FILENAME = "rslock-encryptor.log";

	private static final Logger LOG = Logger.getLogger(RsfileEncryptor.class.getName());

	public static void main(String[] args) throws Exception {
		RsLogger.init(LOG_FILENAME, Level.INFO, Level.FINE);

		LOG.info(() -> "=== RSLock File Encryptor ===\n");

		// Parse command line arguments
		RsLockConfig config = RsLockConfig.fromArgs(args);

		if (!config.isKeystoreExists()) {
			LOG.info(() -> "Keystore not found, generating new keystore...");
			final Path finalKeystorePathForGen = config.getKeystorePath();

			boolean generated = CypherUtility.generateKeyStore(
					finalKeystorePathForGen,
					RsConstraints.DEFAULT_KEYSTORE_PASSWORD,
					config.getAlias());

			if (generated) {
				LOG.info(() -> "✓ Keystore generated successfully: " + finalKeystorePathForGen.toAbsolutePath());
			} else {
				LOG.severe(() -> "✗ Failed to generate keystore.");
				throw new RuntimeException("Failed to generate keystore. Please create it manually using keytool.");
			}
		}

		config.validate();

		List<Path> sourceFiles = config.getSourceFiles();
		Path destinationDir = config.getDestinationDir();
		Path keystorePath = config.getKeystorePath();

		// Default destination to source file's directory if not provided
		if (destinationDir == null && !sourceFiles.isEmpty()) {
			destinationDir = sourceFiles.get(0).toAbsolutePath().getParent();
			final Path finalDestDir = destinationDir;
			LOG.info(() -> "Using default destination: " + finalDestDir);
		}

		final Path finalDestinationDir = destinationDir;
		final Path finalKeystorePath2 = keystorePath;

		LOG.info(() -> "Source files: " + sourceFiles.size());
		for (Path src : sourceFiles) {
			LOG.info(() -> "  - " + src.getFileName());
		}

		LOG.info(() -> "Destination: " + finalDestinationDir.toString());
		LOG.info(() -> "Keystore: " + finalKeystorePath2.toString());

		// Load keystore and keys
		LOG.info(() -> "Loading keystore...");
		KeyStore keystore = KeyStore.getInstance(RsConstraints.KEYSTORE_TYPE);

		try (InputStream keystoreInput = Files.newInputStream(finalKeystorePath2)) {
			keystore.load(keystoreInput, RsConstraints.DEFAULT_KEYSTORE_PASSWORD);
		}

		LOG.info(() -> "✓ Keystore loaded");

		PublicKey publicKey = CypherUtility.loadPublicKey(keystore, config.getAlias());
		LOG.info(() -> "✓ Public key loaded\n");

		// Process each source file sequentially
		int totalFiles = sourceFiles.size();
		int processedFiles = 0; // for logging only

		for (Path sourceFile : sourceFiles) {
			processedFiles++;
			final int currentFile = processedFiles; // for logging only
			LOG.info(() -> "[" + currentFile + "/" + totalFiles + "] Processing: " + sourceFile.getFileName());

			try {
				encryptFile(sourceFile, finalDestinationDir, publicKey);
				LOG.info(() -> "     ✓ Encrypted successfully\n");
			} catch (Exception e) {
				LOG.warning("     ✗ Error: " + e.getMessage());
				throw new RuntimeException("Failed to encrypt: " + sourceFile, e);
			}
		}

		final int finalProcessedFiles = processedFiles;
		LOG.info(() -> "=== Encryption Complete ===");
		LOG.info(() -> "Total files encrypted: " + finalProcessedFiles);
	}

	/**
	 * Encrypts a single file using hybrid encryption:
	 * - Generates random AES key for this file
	 * - Encrypts file data with AES-CBC
	 * - Encrypts AES key with RSA public key
	 * - Stores encrypted key + IV + encrypted data in .rslocked file
	 */
	private static void encryptFile(Path sourceFile, Path destinationDir, PublicKey publicKey)
			throws Exception {

		// Generate output file path with .rslocked extension
		String outputFileName = sourceFile.getFileName().toString() + ".rslocked";
		Path outputFile = destinationDir.resolve(outputFileName);

		long fileSize = Files.size(sourceFile);
		LOG.info(() -> "     Source size: " + Utility.formatBytes(fileSize));

		// Generate unique AES key for this file
		LOG.info(() -> "     Generating AES key...");
		SecretKey aesKey = CypherUtility.generateAESKey();

		// Generate unique IV for this file
		LOG.info(() -> "     Generating IV...");
		IvParameterSpec iv = CypherUtility.generateIV();

		// Encrypt the AES key with RSA public key
		LOG.info(() -> "     Encrypting AES key with RSA...");
		byte[] encryptedAESKey = CypherUtility.encryptAESKeyWithRSA(aesKey, publicKey);

		// Encrypt the file
		LOG.info(() -> "     Encrypting file data...");
		try (InputStream fileInput = Files.newInputStream(sourceFile);
				OutputStream fileOutput = Files.newOutputStream(outputFile)) {

			// Write header: IV and encrypted AES key
			CypherUtility.writeEncryptionHeader(fileOutput, iv, encryptedAESKey);

			// Write encrypted file data
			try (CipherOutputStream cipherOutput = CypherUtility.createEncryptStream(fileOutput, aesKey, iv)) {
				long bytesCopied = 0;
				byte[] buffer = new byte[RsConstraints.DEFAULT_BUFFER_SIZE];
				int bytesRead;
				int lastProgressPercent = 0;

				while ((bytesRead = fileInput.read(buffer)) != -1) {
					cipherOutput.write(buffer, 0, bytesRead);
					bytesCopied += bytesRead;

					// Show progress percentage
					int progressPercent = (int) ((bytesCopied * 100) / fileSize);
					if (progressPercent > lastProgressPercent && progressPercent % 10 == 0) {
						LOG.fine("     Progress: " + progressPercent + "%");
						lastProgressPercent = progressPercent;
					}
				}

				cipherOutput.flush();
			}
		}

		long encryptedSize = Files.size(outputFile);

		LOG.info(() -> "     Output size: " + Utility.formatBytes(encryptedSize));
		LOG.info(() -> "     Output file: " + outputFile.getFileName());
	}

}
