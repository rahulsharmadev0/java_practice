import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
     2. ExecutorService - Parallel Sum Computation
    Write a Java program that splits an array into M parts and uses ExecutorService to compute the sum of each part in parallel. Then, combine the results to get the final sum.
    
    🔹 Requirements:
        1. Use ExecutorService with a fixed thread pool.
        2. Each thread should compute the sum of a sub-array.
        3. Use Future<Integer> to retrieve results from each thread.
        4. Print the final sum after all threads complete execution.
 */
public class ComputeParallelSum {

    public static void main(String[] args) {
        int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int spliter = 4;

        final ComputeParallelSum computeParallelSum = new ComputeParallelSum();
        computeParallelSum.solve(array, spliter);
        computeParallelSum.printSum();

    }

    // -----Class Implementation---

    private int sum = 0;

    void printSum() {
        System.out.println(sum);
    }

    private synchronized void increment(int n) {
        sum += n;
    }

    void solve(final int[] array, final int columns) {
        int rows = (int) Math.ceil((double) array.length / columns);
        ExecutorService service = Executors.newFixedThreadPool(rows);
        for (int i = 0; i < rows; i++) {
            final int _i = i * columns;
            service.submit(() -> {
                for (int j = _i; j < _i + columns && j < array.length; j++)
                    increment(array[j]);
            });

        }

        try {
            service.shutdown();
            service.awaitTermination(1000, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}