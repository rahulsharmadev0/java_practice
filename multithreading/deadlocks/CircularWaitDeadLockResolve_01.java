import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * One way to avoid Circular Wait Deadlock
 * before locking another resourse release existing resourse
 */
public class CircularWaitDeadLockResolve_01 {
    public static void main(String[] args) {
        CircularWaitDeadLockResolve_01 instance = new CircularWaitDeadLockResolve_01();

        Thread thread_1 = new Thread(() -> instance.taskA(), "Thread-1");
        Thread thread_2 = new Thread(() -> instance.taskB(), "Thread-2");

        thread_1.start();
        thread_2.start();

    }

    private final ReentrantLock lockA = new ReentrantLock();
    private final ReentrantLock lockB = new ReentrantLock();

    void taskA() {
        lockA.lock();
        System.out.println(Thread.currentThread().getName() + " 🔒 Resource A");

        try {
            Thread.sleep(100); // Simulating some work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lockA.unlock();
        lockB.lock();
        System.out.println(Thread.currentThread().getName() + "🔓 Resource A,  🔒 Resource B");

        lockB.unlock();
    }

    void taskB() {
        lockB.lock();
        System.out.println(Thread.currentThread().getName() + " 🔒 Resource B");

        try {
            Thread.sleep(100); // Simulating some work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lockB.unlock();
        lockA.lock();
        System.out.println(Thread.currentThread().getName() + "🔓 Resource B,  🔒 Resource A");

        lockA.unlock();
    }
}
