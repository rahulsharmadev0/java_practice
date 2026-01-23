package com.rslock.encryptor;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.rslock.common.*;
import com.rslock.common.executor.ExecutionResult;
import com.rslock.common.executor.ParallelFileExecutor;
import com.rslock.common.executor.ExecutionResult.FileResult;

/**
 * Main entry point for RSLock file encryption.
 * Responsibility: Coordinate the encryption workflow and define encryption
 * logic
 */
public class RsfileEncryptor {

	private static final String LOG_FILENAME = "rslock-encryptor";
	private static final int PROGRESS_LOG_INTERVAL = 10; // Log progress every 10%

	private static final Logger LOG = Logger.getLogger(RsfileEncryptor.class.getName());

	public static void main(String[] args) {
		RsLogger.init(LOG_FILENAME, Level.INFO, Level.ALL);
		LOG.info("=== RSLock File Encryptor ===\n");
		try {

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

		LOG.info("Source files: " + sourceFiles.size());
		for (Path src : sourceFiles) {
			LOG.info("  - " + src.getFileName());
		}

		if (config.getDestinationDir() != null) {
			LOG.info("Destination: " + config.getDestinationDir());
		} else {
			LOG.info("Destination: Same as source files (per-file)");
		}
		LOG.info("Keystore: " + config.getKeystorePath());

		// Load keystore and keys
		LOG.info("Loading keystore...");
		KeyStore keystore = CypherUtility.getKeystore(config.getKeystorePath());

		LOG.info("✓ Keystore loaded");

		PublicKey publicKey = CypherUtility.loadPublicKey(keystore, config.getAlias());
		LOG.info("✓ Public key loaded\n");

		// Pre-calculate all file destination mappings
		Map<Path, Path> fileDestinationMapping = config.getFileDestinationMapping();

		// Use generic ParallelFileExecutor with encryption task
		ExecutionResult result = ParallelFileExecutor.executeInParallel(
				fileDestinationMapping,
				(sourceFile, destDir) -> encryptFile(sourceFile, destDir, publicKey));

		// Print clean summary using common utility
		Utilities.printSummary(result, "Encryption", LOG);

		} catch (Exception e) {
			LOG.severe("Fatal error: " + e.getMessage());
			throw new RuntimeException(e);
		}
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

		LOG.fine("Input file: " + outputFile.toString());
		LOG.fine("Encrypted size: " + Utilities.formatBytes(fileSize));

		SecretKey aesKey = CypherUtility.generateAESKey();

		IvParameterSpec iv = CypherUtility.generateIV();

		byte[] encryptedAESKey = CypherUtility.encryptAESKeyWithRSA(aesKey, publicKey);

		// Encrypt the file
		LOG.fine("Encrypting file data...");
		try (InputStream fileInput = Files.newInputStream(sourceFile);
				OutputStream fileOutput = Files.newOutputStream(outputFile);
				CipherOutputStream cipherOutput = CypherUtility.createEncryptStream(fileOutput, aesKey, iv)) {

			// Write header: IV and encrypted AES key
			CypherUtility.writeEncryptionHeader(fileOutput, iv, encryptedAESKey);

			// Write encrypted file data
			long bytesCopied = 0;
			byte[] buffer = new byte[RsConstraints.DEFAULT_BUFFER_SIZE];
			int bytesRead;
			int lastProgressPercent = 0;

			while ((bytesRead = fileInput.read(buffer)) != -1) {
				cipherOutput.write(buffer, 0, bytesRead);
				bytesCopied += bytesRead;

				// Show progress percentage
				int progressPercent = (int) ((bytesCopied * 100) / fileSize);
				if (progressPercent > lastProgressPercent && progressPercent % PROGRESS_LOG_INTERVAL == 0) {
					LOG.fine("     Progress: " + progressPercent + "%");
					lastProgressPercent = progressPercent;
				}
			}
		}

		long encryptedSize = Files.size(outputFile);

		LOG.fine("     Output size: " + Utilities.formatBytes(encryptedSize));
		LOG.fine("     Output file: " + outputFile.getFileName());

		return new FileResult(sourceFile.getFileName().toString(), outputFileName, encryptedSize);
	}
}
