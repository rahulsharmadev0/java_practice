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

/**
 * Main entry point for RSLock file encryption.
 * Responsibility: Coordinate the encryption workflow and define encryption
 * logic
 */
public class RsfileEncryptor {

	private static final String LOG_FILENAME = "rslock-encryptor.log";

	private static final Logger LOG = Logger.getLogger(RsfileEncryptor.class.getName());

	public static void main(String[] args) throws Exception {
		RsLogger.init(LOG_FILENAME, Level.INFO, Level.FINE);

		LOG.info("=== RSLock File Encryptor ===\n");

		// Parse command line arguments
		RsLockConfig config = RsLockConfig.fromArgs(args);

		if (!config.isKeystoreExists()) {
			LOG.info("Keystore not found, generating new keystore...");

			boolean generated = CypherUtility.generateKeyStore(
					config.getKeystorePath(),
					RsConstraints.DEFAULT_KEYSTORE_PASSWORD,
					config.getAlias());

			if (generated) {
				LOG.info("✓ Keystore generated successfully: " + config.getKeystorePath().toAbsolutePath());
			} else {
				LOG.severe("✗ Failed to generate keystore.");
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
			LOG.info("Using default destination: " + destinationDir);
		}

		LOG.info("Source files: " + sourceFiles.size());
		for (Path src : sourceFiles) {
			LOG.info("  - " + src.getFileName());
		}

		LOG.info("Destination: " + destinationDir.toString());
		LOG.info("Keystore: " + keystorePath.toString());

		// Load keystore and keys
		LOG.info("Loading keystore...");
		KeyStore keystore = KeyStore.getInstance(RsConstraints.KEYSTORE_TYPE);

		try (InputStream keystoreInput = Files.newInputStream(keystorePath)) {
			keystore.load(keystoreInput, RsConstraints.DEFAULT_KEYSTORE_PASSWORD);
		}

		LOG.info("✓ Keystore loaded");

		PublicKey publicKey = CypherUtility.loadPublicKey(keystore, config.getAlias());
		LOG.info("✓ Public key loaded\n");

		// Use generic ParallelFileExecutor with encryption task
		final Path finalDestinationDir = destinationDir;
		final PublicKey finalPublicKey = publicKey;

		ExecutionResult result = ParallelFileExecutor.executeInParallel(
				sourceFiles,
				finalDestinationDir,
				(sourceFile, destDir) -> encryptFile(sourceFile, destDir, finalPublicKey));

		// Print clean summary
		printSummary(result);
	}

	/**
	 * Encrypts a single file using hybrid encryption.
	 * This is the actual encryption logic specific to this encryptor.
	 */
	private static FileResult encryptFile(Path sourceFile, Path destinationDir, PublicKey publicKey)
			throws Exception {

		// Generate output file path with .rslocked extension
		String outputFileName = sourceFile.getFileName().toString() + RsConstraints.DEFAULT_ENCRYPTED_FILE_EXTENSION;
		Path outputFile = destinationDir.resolve(outputFileName);

		long fileSize = Files.size(sourceFile);
		LOG.fine("     Source size: " + Utility.formatBytes(fileSize));

		// Generate unique AES key for this file
		LOG.fine("     Generating AES key...");
		SecretKey aesKey = CypherUtility.generateAESKey();

		// Generate unique IV for this file
		LOG.fine("     Generating IV...");
		IvParameterSpec iv = CypherUtility.generateIV();

		// Encrypt the AES key with RSA public key
		LOG.fine("     Encrypting AES key with RSA...");
		byte[] encryptedAESKey = CypherUtility.encryptAESKeyWithRSA(aesKey, publicKey);

		// Encrypt the file
		LOG.fine("     Encrypting file data...");
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

		LOG.fine("     Output size: " + Utility.formatBytes(encryptedSize));
		LOG.fine("     Output file: " + outputFile.getFileName());

		return new FileResult(sourceFile.getFileName().toString(), outputFileName, encryptedSize);
	}

	/**
	 * Prints a clean summary of encryption results
	 */
	private static void printSummary(ExecutionResult result) {
		LOG.info("=== Encryption Complete ===");

		for (FileResult fileResult : result.getFileResults()) {
			if (fileResult.isSuccess()) {
				LOG.info("✓ " + fileResult.getSourceFileName() + " → " + fileResult.getOutputFileName() +
						" (" + Utility.formatBytes(fileResult.getOutputSize()) + ")");
			} else {
				LOG.warning("✗ " + fileResult.getSourceFileName() + " (Error: " + fileResult.getErrorMessage() + ")");
			}
		}

		LOG.info("Total files encrypted: " + result.getSuccessCount() + "/" + result.getTotalFiles());
		LOG.info("Thread pool size used: " + result.getThreadPoolSize());
	}
}
