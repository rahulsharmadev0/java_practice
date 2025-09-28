package multithreading.via_synchronized;

import utils.IO;
import utils.Try;


/*
------------------------
    Synchronized Block Behaviour
------------------------
 When a thread is executing a synchronized block with lock 'LOCK', other threads:

 CANNOT execute:
  - The SAME or OTHER synchronized block with 'LOCK'
 */


public class SynchronizedBlockBehaviour {


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(SynchronizedBlockBehaviour::method_1);
        Thread t2 = new Thread(SynchronizedBlockBehaviour::method_2);

        t1.start();
        t2.start();

    }

    static String run = "method_1";
    static final Object lock = new Object();
    static void  switchMethod(){
        run = switch (run) {
            case "method_1" -> "method_2";
            default -> "method_1";
        };
    }

    static void method_1() {
        int i = 0;
        while (true) { // For Working continuously in loop
            synchronized (lock) {
                while (!run.equals("method_1")) {
                    Try.run(lock::wait);
                }  // so that method can't terminate
                IO.printf("method_1 on " + Thread.currentThread().getName() + " is Running...");
                Try.run(() -> Thread.sleep(1000));

                if (++i == 3) { // 3 means after 3000ms
                    switchMethod();
                    lock.notifyAll();  // Immediately Notify all threads
                    i = 0;
                }
            }
        }
    }



    static void method_2() {
        int i = 0;
        while (true) {
            synchronized (lock) {
                while (!run.equals("method_2"))
                    Try.run(lock::wait); // so that method can't terminate
                IO.printf("method_2 on " + Thread.currentThread().getName() + " is Running...");
                Try.run(() -> Thread.sleep(1000));
                if (++i == 3) { // 3 means after 3000ms
                    switchMethod();
                    lock.notifyAll(); // Immediately Notify all threads
                    i = 0;
                }
            }
        }
    }
}
