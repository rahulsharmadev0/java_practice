package com.rslock.common;

import java.util.logging.Logger;

/**
 * General utility methods for the rslock application.
 * Combines formatting and printing utilities.
 */
public final class Utilities {

    // Prevent instantiation
    private Utilities() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Format bytes to human-readable format
     * 
     * @param bytes Number of bytes to format
     * @return Formatted string (e.g., "1.50 MB")
     */
    public static String formatBytes(long bytes) {
        if (bytes <= 0)
            return "0 B";
        final String[] units = new String[] { "B", "KB", "MB", "GB" };
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return String.format("%.2f %s", bytes / Math.pow(1024, digitGroups), units[digitGroups]);
    }

    /**
     * Prints a formatted summary of execution results
     * 
     * @param result    ExecutionResult to print
     * @param operation Name of operation (e.g., "Encryption", "Decryption")
     * @param log       Logger to use for output
     */
    public static void printSummary(ExecutionResult result, String operation, Logger log) {
        log.info("=== " + operation + " Complete ===");

        for (var fileResult : result.getFileResults()) {
            if (fileResult.isSuccess()) {
                log.info(String.format("✓ %s → %s (%s)",
                        fileResult.getSourceFileName(),
                        fileResult.getOutputFileName(),
                        formatBytes(fileResult.getOutputSize())));
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
