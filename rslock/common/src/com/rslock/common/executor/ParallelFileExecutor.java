package com.rslock.common.executor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.rslock.common.executor.ExecutionResult.FileResult;



/**
 * Generic utility for executing file processing tasks in parallel using a
 * thread pool.
 * Accepts a FileTask functional interface to define the actual processing
 * logic.
 * Can be used by both encryptor and decryptor (or any other file processor).
 */
public class ParallelFileExecutor {

    private static final Logger LOG = Logger.getLogger(ParallelFileExecutor.class.getName());
    private static final long TERMINATION_TIMEOUT_HOURS = 1;

    /**
     * Executes file processing tasks in parallel using a thread pool
     * 
     * @param fileDestinationMapping Map of source files to their corresponding destination directories
     *                               Pre-calculated mappings eliminate per-file overhead
     * @param task                   FileTask implementation defining the processing logic
     * @return ExecutionResult containing details of all processed files
     * @throws RuntimeException if execution is interrupted
     */
    public static ExecutionResult executeInParallel(
            Map<Path, Path> fileDestinationMapping,
            FileTask task) {

        int totalFiles = fileDestinationMapping.size();
        int threadPoolSize = Math.min(Runtime.getRuntime().availableProcessors(), totalFiles);

        LOG.info(String.format("Creating thread pool with %d threads for parallel processing\n", threadPoolSize));
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        ExecutionResult result = new ExecutionResult(totalFiles, threadPoolSize);
        List<Future<FileResult>> futures = new ArrayList<>();

        // Submit tasks to thread pool
        for (Map.Entry<Path, Path> entry : fileDestinationMapping.entrySet()) {
            Path sourceFile = entry.getKey();
            Path destinationDir = entry.getValue();
            
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

        // wait for completion & Shutdown executor.
        executorService.shutdown();

        LOG.info(String.format("Processing %d file(s) in parallel...\n", totalFiles));

        try {
            // Timeout to avoid indefinite blocking
            if (!executorService.awaitTermination(TERMINATION_TIMEOUT_HOURS, TimeUnit.HOURS)) {
                LOG.warning("Tasks did not complete within timeout, forcing shutdown...");
                executorService.shutdownNow();
            }

            // Collect results from all tasks
            for (Future<FileResult> future : futures) {
                try {
                    result.addFileResult(future.get());
                } catch (Exception e) {
                    String errorMessage = "Failed to retrieve result: " + e.getMessage();
                    LOG.severe(errorMessage);
                    result.addFileResult(new FileResult("unknown", errorMessage));
                }
            }

        } catch (InterruptedException e) {
            String errorMessage = "Execution was interrupted";
            LOG.severe(errorMessage);
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            throw new RuntimeException(errorMessage, e);
        }

        return result;
    }
}
