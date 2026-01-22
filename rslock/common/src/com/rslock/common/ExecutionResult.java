package com.rslock.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the overall result of a parallel file processing operation
 */
public class ExecutionResult {
    private final int totalFiles;
    private final int threadPoolSize;
    private final List<FileResult> fileResults;

    public ExecutionResult(int totalFiles, int threadPoolSize) {
        this.totalFiles = totalFiles;
        this.threadPoolSize = threadPoolSize;
        this.fileResults = new ArrayList<>();
    }

    public void addFileResult(FileResult result) {
        fileResults.add(result);
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public List<FileResult> getFileResults() {
        return new ArrayList<>(fileResults); // Defensive copy
    }

    public int getSuccessCount() {
        return (int) fileResults.stream().filter(FileResult::isSuccess).count();
    }

    public int getFailureCount() {
        return (int) fileResults.stream().filter(r -> !r.isSuccess()).count();
    }

    public static class FileResult {
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

}
