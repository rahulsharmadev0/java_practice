package com.rslock.common;

/**
 * Represents the result of processing a single file
 */
public class FileResult {
    private final String sourceFileName;
    private final String outputFileName;
    private final long outputSize;
    private final boolean success;
    private final String errorMessage;

    // Success constructor
    public FileResult(String sourceFileName, String outputFileName, long outputSize) {
        this.sourceFileName = sourceFileName;
        this.outputFileName = outputFileName;
        this.outputSize = outputSize;
        this.success = true;
        this.errorMessage = null;
    }

    // Failure constructor
    public FileResult(String sourceFileName, String errorMessage) {
        this.sourceFileName = sourceFileName;
        this.outputFileName = null;
        this.outputSize = 0;
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public long getOutputSize() {
        return outputSize;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
