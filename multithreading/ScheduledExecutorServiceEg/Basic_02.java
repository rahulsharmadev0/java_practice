import java.util.TimeZone;
import java.util.Timer;
import java.util.concurrent.*;

import java.util.concurrent.*;

public class Basic_02 {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule task to run every 2 seconds (total 5 times)
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println(System.currentTimeMillis());
            latch.countDown();
        }, 0, 2, TimeUnit.SECONDS);

        try {
            latch.await(); // Wait until latch reaches 0
            scheduler.shutdown();
            System.out.println("Done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
