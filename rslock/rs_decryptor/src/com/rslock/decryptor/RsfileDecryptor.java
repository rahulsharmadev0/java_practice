package com.rslock.decryptor;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.rslock.common.*;

public class RsfileDecryptor {

    private static final int BUFFER_SIZE = 8192;

    private static final String LOG_FILENAME = "rslock-decryptor.log";

    private static final Logger LOG = Logger.getLogger(RsfileDecryptor.class.getName());

    public static void main(String[] args) throws Exception {
        RsLogger.init(LOG_FILENAME, Level.INFO, Level.FINE);

        LOG.info(() -> "=== RSLock File Decryptor ===\n");

        // Parse command line arguments
        RsLockConfig config = RsLockConfig.fromArgs(args);

        config.validate();

        List<Path> sourceFiles = config.getSourceFiles();
        Path keystorePath = config.getKeystorePath();

        final Path finalKeystorePath2 = keystorePath;

        LOG.info(() -> "Source files: " + sourceFiles.size());
        for (Path src : sourceFiles) {
            LOG.info(() -> "  - " + src.getFileName());
        }
 
        LOG.info(() -> "Keystore: " + finalKeystorePath2.toString());

        // Load keystore and keys
        LOG.info(() -> "Loading keystore...");
        KeyStore keystore = KeyStore.getInstance(RsConstraints.KEYSTORE_TYPE);

        try (InputStream keystoreInput = Files.newInputStream(finalKeystorePath2)) {
            keystore.load(keystoreInput, RsConstraints.DEFAULT_KEYSTORE_PASSWORD);
        }

        LOG.info(() -> "✓ Keystore loaded");

        PrivateKey privateKey = CypherUtility.loadPrivateKey(keystore, config.getAlias(),
                RsConstraints.DEFAULT_KEYSTORE_PASSWORD);


        LOG.info(() -> "✓ Private key loaded\n");

        // Process each source file sequentially
        int totalFiles = sourceFiles.size();
        int processedFiles = 0; // for logging only

        for (Path sourceFile : sourceFiles) {
            processedFiles++;
            final int currentFile = processedFiles; // for logging only
            LOG.info(() -> "[" + currentFile + "/" + totalFiles + "] Processing: " + sourceFile.getFileName());

            try {
                decryptFile(sourceFile, config.generateDestinationDir(sourceFile), privateKey);
                LOG.info(() -> "     ✓ Decrypted successfully\n");
            } catch (Exception e) {
                LOG.warning("     ✗ Error: " + e.getMessage());
                throw new RuntimeException("Failed to decrypt: " + sourceFile, e);
            }
        }

        final int finalProcessedFiles = processedFiles;
        LOG.info(() -> "=== Decryption Complete ===");
        LOG.info(() -> "Total files decrypted: " + finalProcessedFiles);
    }

    /**
     * Decrypts a single file using hybrid decryption:
     * - Reads encrypted AES key and IV from file header
     * - Decrypts AES key with RSA private key
     * - Decrypts file data with AES-CBC
     * - Restores original file without .rslocked extension
     */
    private static void decryptFile(Path sourceFile, Path destinationDir, PrivateKey privateKey)
            throws Exception {

        // Validate input file has .rslocked extension
        String fileName = sourceFile.getFileName().toString();
        if (!fileName.endsWith(".rslocked")) {
            throw new IllegalArgumentException("File must have .rslocked extension: " + fileName);
        }

        // Generate output file path by removing .rslocked extension
        String outputFileName = fileName.substring(0, fileName.length() - ".rslocked".length());
        Path outputFile = destinationDir.resolve(outputFileName);

        long fileSize = Files.size(sourceFile);
        LOG.info(() -> "     Encrypted size: " + Utility.formatBytes(fileSize));

        // Decrypt the file
        try (InputStream fileInput = Files.newInputStream(sourceFile)) {

            // Read header: IV and encrypted AES key
            LOG.info(() -> "     Reading encryption header...");
            byte[][] header = CypherUtility.readEncryptionHeader(fileInput);
            byte[] ivBytes = header[0];
            byte[] encryptedAESKey = header[1];

            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            // Decrypt the AES key with RSA private key
            LOG.info(() -> "     Decrypting AES key with RSA...");
            SecretKey aesKey = CypherUtility.decryptAESKeyWithRSA(encryptedAESKey, privateKey);

            // Decrypt the file data
            LOG.info(() -> "     Decrypting file data...");
            try (CipherInputStream cipherInput = CypherUtility.createDecryptStream(fileInput, aesKey, iv);
                    OutputStream fileOutput = Files.newOutputStream(outputFile)) {

                long bytesCopied = 0;
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                int lastProgressPercent = 0;

                while ((bytesRead = cipherInput.read(buffer)) != -1) {
                    fileOutput.write(buffer, 0, bytesRead);
                    bytesCopied += bytesRead;

                    // Show progress percentage (approximate since we don't know decrypted size)
                    int progressPercent = (int) ((bytesCopied * 100) / fileSize);
                    if (progressPercent > lastProgressPercent && progressPercent % 10 == 0) {
                        LOG.fine("     Progress: " + progressPercent + "%");
                        lastProgressPercent = progressPercent;
                    }
                }

                fileOutput.flush();
            }
        }

        long decryptedSize = Files.size(outputFile);

        LOG.info(() -> "     Output size: " + Utility.formatBytes(decryptedSize));
        LOG.info(() -> "     Output file: " + outputFile.getFileName());
    }

}
