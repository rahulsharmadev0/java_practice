package com.rslock.common;

import java.util.logging.Logger;

/**
 * Utility for printing execution results in a consistent format.
 * Eliminates code duplication between encryptor and decryptor.
 */
public class ResultPrinter {

    /**
     * Prints a formatted summary of execution results
     * 
     * @param result    ExecutionResult to print
     * @param operation Name of operation (e.g., "Encryption", "Decryption")
     * @param log       Logger to use for output
     */
    public static void printSummary(ExecutionResult result, String operation, Logger log) {
        log.info("=== " + operation + " Complete ===");

        for (FileResult fileResult : result.getFileResults()) {
            if (fileResult.isSuccess()) {
                log.info(String.format("✓ %s → %s (%s)",
                        fileResult.getSourceFileName(),
                        fileResult.getOutputFileName(),
                        Utility.formatBytes(fileResult.getOutputSize())));
            } else {
                log.warning(String.format("✗ %s (Error: %s)",
                        fileResult.getSourceFileName(),
                        fileResult.getErrorMessage()));
            }
        }

        log.info(String.format("Total files %s: %d/%d",
                operation.toLowerCase() + "ed",
                result.getSuccessCount(),
                result.getTotalFiles()));
        log.info("Thread pool size used: " + result.getThreadPoolSize());
    }
}
