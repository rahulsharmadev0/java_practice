import java.util.TimeZone;
import java.util.Timer;
import java.util.concurrent.*;

public class Basic {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        scheduler.scheduleWithFixedDelay(
                () -> {
                    System.out.println(System.currentTimeMillis());
                },
                5, 3, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            scheduler.shutdownNow();
        }, 10, TimeUnit.SECONDS);

        try {
            scheduler.awaitTermination(100, TimeUnit.SECONDS);
        } catch (Exception e) {

        }

    }
}
