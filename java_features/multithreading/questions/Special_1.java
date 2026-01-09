package questions;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

//! Warning: might be acquire more than 8GBRAM or Java not responding state (for 1 Cr thread)
public class Special_1 {
    public static void main(String[] args) throws Exception {

        int threads = 100_00_000; // 1 crore
        var tasks = IntStream.range(0, threads).mapToObj(i -> (Callable<Void>) () -> {
            System.out.println(i);
            return null;
        }).toList();

        // ExecutorService service = Executors.newFixedThreadPool(threads); // thread pool 
        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor(); // by using virtual thread
        
        service.invokeAll(tasks);
        service.shutdown();
    }
}
