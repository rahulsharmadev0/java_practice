package com.rslock.decryptor;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.rslock.common.*;
import com.rslock.common.executor.ExecutionResult;
import com.rslock.common.executor.ParallelFileExecutor;
import com.rslock.common.executor.ExecutionResult.FileResult;

/**
 * Main entry point for RSLock file decryption.
 * Responsibility: Coordinate the decryption workflow and define decryption
 * logic
 */
public class RsfileDecryptor {

    private static final String LOG_FILENAME = "rslock-decryptor.log";
    private static final int PROGRESS_LOG_INTERVAL = 10; // Log progress every 10%

    private static final Logger LOG = Logger.getLogger(RsfileDecryptor.class.getName());

    public static void main(String[] args) {
        RsLogger.init(LOG_FILENAME, Level.INFO, Level.ALL);
        LOG.info("=== RSLock File Decryptor ===\n");
        try {
            // Get Configuration from command line arguments
            RsLockConfig config = RsLockConfig.fromArgs(args);

            // Validate configuration e
            config.validate();

            List<Path> sourceFiles = config.getSourceFiles();

            LOG.info("Source files: " + sourceFiles.size());

            for (Path src : sourceFiles)
                LOG.info("  - " + src.getFileName());

            LOG.info("Keystore: " + config.getKeystorePath());

            KeyStore keystore = CypherUtility.getKeystore(config.getKeystorePath());

            LOG.info("✓ Keystore loaded");

            PrivateKey privateKey = CypherUtility.loadPrivateKey(keystore, config.getAlias());

            LOG.info("✓ Private key loaded\n");

            // Use generic ParallelFileExecutor with decryption task
            ExecutionResult result = ParallelFileExecutor.executeInParallel(
                    sourceFiles,
                    null, // destinationDir determined per file
                    (sourceFile, destDir) -> RsfileDecryptor.decryptFile(sourceFile,
                            config.generateDestinationDir(sourceFile),
                            privateKey));

            // Print clean summary
            Utilities.printSummary(result, "Decryption", LOG);

        } catch (Exception e) {
            LOG.severe("Fatal error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Decrypts a single file using hybrid decryption.
     * This is the actual decryption logic specific to this decryptor.
     */
    private static FileResult decryptFile(Path sourceFile, Path destinationDir, PrivateKey privateKey)
            throws Exception {

        // Validate input file has .rslocked extension
        String fileName = sourceFile.getFileName().toString();
        if (!fileName.endsWith(RsConstraints.DEFAULT_ENCRYPTED_FILE_EXTENSION)) {
            throw new IllegalArgumentException(
                    "File must have " + RsConstraints.DEFAULT_ENCRYPTED_FILE_EXTENSION + " extension: " + fileName);
        }

        // Generate output file path by removing .rslocked extension
        String outputFileName = fileName.substring(0,
                fileName.length() - RsConstraints.DEFAULT_ENCRYPTED_FILE_EXTENSION.length());
        Path outputFile = destinationDir.resolve(outputFileName);

        long fileSize = Files.size(sourceFile);


        LOG.info("     Input file: " + outputFile.toString());
        LOG.info("     Encrypted size: " + Utilities.formatBytes(fileSize));

        // Decrypt the file
        try (InputStream fileInput = Files.newInputStream(sourceFile)) {

            // Read header: IV & encrypted AES key
            LOG.fine("     Reading encryption header...");
            byte[][] header = CypherUtility.readEncryptionHeader(fileInput);
            byte[] ivBytes = header[0];
            byte[] encryptedAESKey = header[1];

            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            // Decrypt the AES key with RSA private key
            LOG.fine("     Decrypting AES key with RSA...");
            SecretKey aesKey = CypherUtility.decryptAESKeyWithRSA(encryptedAESKey, privateKey);

            // Decrypt the file data
            LOG.fine("     Decrypting file data...");
            try (CipherInputStream cipherInput = CypherUtility.createDecryptStream(fileInput, aesKey, iv);
                    OutputStream fileOutput = Files.newOutputStream(outputFile)) {

                long bytesCopied = 0;
                byte[] buffer = new byte[RsConstraints.DEFAULT_BUFFER_SIZE];
                int bytesRead;
                int lastProgressPercent = 0;

                while ((bytesRead = cipherInput.read(buffer)) != -1) {
                    fileOutput.write(buffer, 0, bytesRead);
                    bytesCopied += bytesRead;

                    // Show progress percentage (approximate since we don't know decrypted size)
                    int progressPercent = (int) ((bytesCopied * 100) / fileSize);
                    if (progressPercent > lastProgressPercent && progressPercent % PROGRESS_LOG_INTERVAL == 0) {
                        LOG.fine("     Progress: " + progressPercent + "%");
                        lastProgressPercent = progressPercent;
                    }
                }
            }
        }

        long decryptedSize = Files.size(outputFile);

        LOG.fine("     Output size: " + Utilities.formatBytes(decryptedSize));
        LOG.fine("     Output file: " + outputFile.getFileName());

        return new FileResult(sourceFile.getFileName().toString(), outputFileName, decryptedSize);
    }
}
