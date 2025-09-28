package multithreading.via_synchronized;

/*
------------------------
    Synchronized Method Behaviour
ç
 When a thread is executing a static synchronized method, other threads:

 CANNOT execute:
  - The SAME or OTHER static synchronized method

 CAN execute:
  1. Static non-synchronized methods
  2. Non-static synchronized methods (via object)
  3. Non-static non-synchronized methods (via object)
 */

import utils.Try;

public class SynchronizedMethodBehaviour {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread( SynchronizedMethodBehaviour::synchronizedMethod_1, "T1");
        Thread t2 = new Thread(SynchronizedMethodBehaviour::synchronizedMethod_2, "T2"); // Can't execute since T1 Thread is running synchronized method above.
        Thread t3 = new Thread(SynchronizedMethodBehaviour::nonSynchronizedMethod, "T3"); // Execute since executing non-synchronized method

        SynchronizedMethodBehaviour obj = new SynchronizedMethodBehaviour();
        Thread t4 = new Thread(obj::nonStaticSynchronizedMethod, "T4");
        Thread t5 = new Thread(obj::nonStaticSynchronizedMethod, "T5"); // Can't execute since T4 Thread is running synchronized method above.
        Thread t6 = new Thread(obj::nonStaticMethod, "T6"); // Execute since executing non-synchronized method
        Thread t7 = new Thread(obj::nonStaticMethod, "T7"); // Execute since executing non-synchronized method

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();

    }

    static synchronized void synchronizedMethod_1() {
        while (true) {
            System.out.println("synchronizedMethod_1 on " + Thread.currentThread().getName() + " is Running...");
            Try.run(() -> Thread.sleep(1000));
        }
    }

    static synchronized void synchronizedMethod_2() {
        while (true) {
            System.out.println("synchronizedMethod_2 on " + Thread.currentThread().getName() + " is Running...");
            Try.run(() -> Thread.sleep(1000));
        }
    }

    static void nonSynchronizedMethod() {
        while (true) {
            System.out.println("nonSynchronizedMethod on " + Thread.currentThread().getName() + " is Running...");
            Try.run(() -> Thread.sleep(1000));
        }
    }
     synchronized void nonStaticSynchronizedMethod() {
        while (true) {
            System.out.println("nonSynchronizedMethod on " + Thread.currentThread().getName() + " is Running...");
            Try.run(() -> Thread.sleep(1000));
        }
    }

    void nonStaticMethod() {
        while (true) {
            System.out.println("nonSynchronizedMethod on " + Thread.currentThread().getName() + " is Running...");
            Try.run(() -> Thread.sleep(1000));
        }
    }
}
