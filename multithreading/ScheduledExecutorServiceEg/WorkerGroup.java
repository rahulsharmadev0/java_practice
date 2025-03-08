import java.util.TimeZone;
import java.util.Timer;
import java.util.concurrent.*;

import java.util.concurrent.*;

/*
    You are given N workers who must complete their individual tasks before proceeding to the next stage together. Implement a Java program using CyclicBarrier, where:

    Each worker takes a random amount of time to complete their task.
    Once all workers finish, a "Stage Complete" message is printed before moving to the next round.
    🔹 Requirements:
        1. Use ExecutorService to manage threads.
        2. Use CyclicBarrier to synchronize workers.
        3. Each worker should print "Worker X finished task" before waiting at the barrier.
        4. After all workers reach the barrier, print "All workers finished. Proceeding to next stage...".
 */
public class WorkerGroup {
    public static void main(String[] args) {
        final int[][] works = {
                { 100, 3000, 200, 400, 1000, 300 }, // stage-1
                { 100, 2000, 200, 400, 1000, 200 }, // stage-2
                { 400, 300, 2000, 400, 1000, 300 }, // stage-3
                { 700, 2000, 200, 400, 2000, 500 } // stage-4

        };

        WorkerGroup workGroup = new WorkerGroup();
        workGroup.startWork(works);

    }

    void startWork(int[][] works) {
        final ExecutorService service = Executors.newFixedThreadPool(works[0].length);

        for (int[] work : works) {
            final CyclicBarrier barrier = new CyclicBarrier(work.length,
                    () -> System.out.println("All workers finished. Proceeding to next stage..."));

            for (int i = 0; i < work.length; i++) {
                final int idx = i;
                final int delay = work[i];
                service.submit(() -> {
                    try {
                        Thread.sleep(delay);
                        System.out.println("Worker " + idx + " finished task");
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            }
        }

        try {
            service.shutdown();
            service.awaitTermination(1000, TimeUnit.SECONDS);
            System.out.println("All workers finished");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
