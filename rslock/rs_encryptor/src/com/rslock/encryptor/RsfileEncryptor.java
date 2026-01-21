package com.rslock.encryptor;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.PublicKey;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.rslock.common.*;

public class RsfileEncryptor {

	private static final int BUFFER_SIZE = 8192;

	private static final String LOG_FILENAME = "rslock-encryptor.log";

	private static final Logger LOG = Logger.getLogger(RsfileEncryptor.class.getName());

	public static void main(String[] args) throws Exception {
		RsLogger.init(LOG_FILENAME, Level.INFO, Level.FINE);

		LOG.info(() -> "=== RSLock File Encryptor ===\n");

		// Parse command line arguments
		RsCommandLineArgs cmdArgs = RsCommandLineArgs.parse(args);

		List<Path> sourceFiles = cmdArgs.getSourceFiles();
		Path destinationDir = cmdArgs.getDestinationDir();
		Path keystorePath = cmdArgs.getKeystorePath();

		// Default destination to source file's directory if not provided
		if (destinationDir == null && !sourceFiles.isEmpty()) {
			destinationDir = sourceFiles.get(0).toAbsolutePath().getParent();
			final Path finalDestDir = destinationDir;
			LOG.info(() -> "Using default destination: " + finalDestDir);
		}

		// Default keystore path if not provided
		if (keystorePath == null) {
			keystorePath = Path.of(CypherConstraints.DEFAULT_KEYSTORE_FILENAME);
			final Path finalKeystorePath = keystorePath;
			LOG.info(() -> "Using default keystore: " + finalKeystorePath.toAbsolutePath());
		}

		// Auto-generate keystore if it doesn't exist
		if (!Files.exists(keystorePath)) {
			LOG.info(() -> "Keystore not found, generating new keystore...");
			final Path finalKeystorePathForGen = keystorePath;
			char[] keystorePassword = getKeystorePassword();
			boolean generated = CypherUtility.generateKeyStore(
				finalKeystorePathForGen, 
				keystorePassword, 
				"rslock-key"
			);
			if (generated) {
				LOG.info(() -> "✓ Keystore generated successfully: " + finalKeystorePathForGen.toAbsolutePath());
			} else {
				throw new RuntimeException("Failed to generate keystore. Please create it manually using keytool.");
			}
		}

		final Path finalDestinationDir = destinationDir;
		final Path finalKeystorePath2 = keystorePath;

		LOG.info(() -> "Source files: " + sourceFiles.size());
		for (Path src : sourceFiles) {
			LOG.info(() -> "  - " + src.getFileName());
		}
		LOG.info(() -> "Destination: " + finalDestinationDir.toString());
		LOG.info(() -> "Keystore: " + finalKeystorePath2.toString());

		// Get keystore password securely
		char[] keystorePassword = getKeystorePassword();

		// Load keystore and keys
		LOG.info(() -> "Loading keystore...");
		KeyStore keystore = KeyStore.getInstance(CypherConstraints.KEYSTORE_TYPE);

		try (InputStream keystoreInput = Files.newInputStream(finalKeystorePath2)) {
			keystore.load(keystoreInput, keystorePassword);
		}

		LOG.info(() -> "✓ Keystore loaded");
		String alias;
		try {
			List<String> aliasList = Collections.list(keystore.aliases());
			LOG.info(() -> "Available aliases: " + aliasList);
			alias = aliasList.get(0);
		} catch (Exception e) {
			LOG.warning("Could not list aliases: " + e.getMessage());
			throw new RuntimeException("Failed to get keystore alias", e);
		}
		LOG.info(() -> "Using alias: " + alias);

		PublicKey publicKey = CypherUtility.loadPublicKey(keystore, alias);
		LOG.info(() -> "✓ Public key loaded\n");

		// Process each source file sequentially
		int totalFiles = sourceFiles.size();
		int processedFiles = 0;

		for (Path sourceFile : sourceFiles) {
			processedFiles++;
			final int currentFile = processedFiles;
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
				byte[] buffer = new byte[BUFFER_SIZE];
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

	/**
	 * Get keystore password securely from console or use default for demo
	 */
	private static char[] getKeystorePassword() {
		// TODO: In production, use Console for secure password input:
		// Console console = System.console();
		// if (console != null) {
		// return console.readPassword("Enter keystore password: ");
		// }

		// For demo purposes only
		return "password".toCharArray();
	}

}
