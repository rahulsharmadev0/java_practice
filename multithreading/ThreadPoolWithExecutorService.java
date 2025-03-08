import java.util.concurrent.*;

public class ThreadPoolWithExecutorService {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            final int task = i;
            pool.execute(() -> {
                try {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " work " + String.valueOf(task) + " ✅ Done");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        pool.shutdown();
        try {
            pool.awaitTermination(100, TimeUnit.SECONDS);
            System.out.println("All tasks are done");
        } catch (Exception e) {

        }
    }
}
