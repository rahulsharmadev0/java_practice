import java.util.Timer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * One way to avoid Circular Wait Deadlock
 * before locking ensure that all upcoming or resoure is unlock. and code work synchronously 
 */

public class CircularWaitDeadLockResolve_02 {
    public static void main(String[] args) {
        CircularWaitDeadLockResolve_02 instance = new CircularWaitDeadLockResolve_02();

        Thread thread_1 = new Thread(() -> instance.taskA(), "Thread-1");
        Thread thread_2 = new Thread(() -> instance.taskB(), "Thread-2");

        thread_1.start();
        thread_2.start();

    }

    private final Lock lockA = new ReentrantLock();
    private final Lock lockB = new ReentrantLock();

    synchronized void taskA() {
        synchronized (lockB) {
            lockA.lock();
            System.out.println(Thread.currentThread().getName() + " 🔒 Resource A");

            try {
                Thread.sleep(100); // Simulating some work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lockB.lock();
            System.out.println(Thread.currentThread().getName() + " 🔒 Resource B");

            lockA.unlock();
            lockB.unlock();
            System.out.println(Thread.currentThread().getName() + " 🔓 Resource A & B");
        }
    }

    synchronized void taskB() {
        synchronized (lockA) {
            lockB.lock();
            System.out.println(Thread.currentThread().getName() + " 🔒 Resource B");

            try {
                Thread.sleep(100); // Simulating some work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lockA.lock();
            System.out.println(Thread.currentThread().getName() + " 🔒 Resource A");

            lockA.unlock();
            lockB.unlock();
            System.out.println(Thread.currentThread().getName() + " 🔓 Resource A & B");
        }
    }
}
