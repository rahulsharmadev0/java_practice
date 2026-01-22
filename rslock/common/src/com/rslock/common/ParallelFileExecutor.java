package com.rslock.common;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Generic utility for executing file processing tasks in parallel using a
 * thread pool.
 * Accepts a FileTask functional interface to define the actual processing
 * logic.
 * Can be used by both encryptor and decryptor (or any other file processor).
 */
public class ParallelFileExecutor {

    private static final Logger LOG = Logger.getLogger(ParallelFileExecutor.class.getName());

    /**
     * Executes file processing tasks in parallel using a thread pool
     * 
     * @param sourceFiles    List of files to process
     * @param destinationDir Destination directory for output files
     * @param task           FileTask implementation defining the processing logic
     * @return ExecutionResult containing details of all processed files
     * @throws RuntimeException if execution is interrupted
     */
    public static ExecutionResult executeInParallel(
            List<Path> sourceFiles,
            Path destinationDir,
            FileTask task) {

        int totalFiles = sourceFiles.size();
        int threadPoolSize = Math.min(Runtime.getRuntime().availableProcessors(), totalFiles);

        LOG.info("Creating thread pool with " + threadPoolSize + " threads for parallel processing\n");
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        ExecutionResult result = new ExecutionResult(totalFiles, threadPoolSize);
        List<Future<FileResult>> futures = new ArrayList<>();

        // Submit tasks to thread pool
        for (Path sourceFile : sourceFiles) {
            Future<FileResult> future = executorService.submit(() -> {
                try {
                    return task.execute(sourceFile, destinationDir);
                } catch (Exception e) {
                    LOG.severe("Error processing " + sourceFile.getFileName() + ": " + e.getMessage());
                    return new FileResult(sourceFile.getFileName().toString(), e.getMessage());
                }
            });

            futures.add(future);
        }

        // Shutdown executor and wait for completion
        executorService.shutdown();

        LOG.info("Processing " + totalFiles + " file(s) in parallel...\n");

        try {
            if (!executorService.awaitTermination(1, TimeUnit.HOURS)) {
                LOG.warning("Tasks did not complete within timeout, forcing shutdown...");
                executorService.shutdownNow();
            }

            // Collect results from all tasks
            for (Future<FileResult> future : futures) {
                try {
                    FileResult fileResult = future.get();
                    result.addFileResult(fileResult);
                } catch (Exception e) {
                    LOG.severe("Failed to get result: " + e.getMessage());
                }
            }

        } catch (InterruptedException e) {
            LOG.severe("Execution was interrupted");
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            throw new RuntimeException("Execution was interrupted", e);
        }

        return result;
    }
}
