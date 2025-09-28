package multithreading.thread_pooling;

/*
 * Try to do mannully by usin array, but we can't reuse same thread again
 */
public class ThreadPool {
    public static void main(String[] args) {
        Thread[] threads = new Thread[15];
        for (int i = 0; i < 15; i++) {
            threads[i] = new Thread(() -> {
                try {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " work ✅ Done");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
