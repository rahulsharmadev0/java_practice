package com.rslock.common;

import java.nio.file.Path;

import com.rslock.common.ExecutionResult.FileResult;

/**
 * Functional interface for file processing tasks.
 * Implementations define the actual processing logic (encrypt, decrypt, etc.)
 */
@FunctionalInterface
public interface FileTask {
    /**
     * Process a single file
     * 
     * @param sourceFile     Source file to process
     * @param destinationDir Destination directory for output
     * @return FileResult containing processing outcome
     * @throws Exception if processing fails
     */
    FileResult execute(Path sourceFile, Path destinationDir) throws Exception;
}
