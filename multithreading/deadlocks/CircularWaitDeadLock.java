import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CircularWaitDeadLock {

    public static void main(String[] args) {
        CircularWaitDeadLock instance = new CircularWaitDeadLock();

        Thread thread_1 = new Thread(() -> instance.taskA(), "Thread-1");
        Thread thread_2 = new Thread(() -> instance.taskB(), "Thread-2");

        thread_1.start();
        thread_2.start();
    }

    private final Lock lockA = new ReentrantLock();
    private final Lock lockB = new ReentrantLock();

    void taskA() {

        System.out.println(Thread.currentThread().getName() + " Need Resource A");
        lockA.lock();
        System.out.println(Thread.currentThread().getName() + " 🔒 Resource A");

        try {
            Thread.sleep(100); // Simulating some work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " Need Resource B");
        lockB.lock(); // Thread-1 tries to lock Resource B (held by Thread-2)
        System.out.println(Thread.currentThread().getName() + " 🔒 Resource B");

        lockB.unlock();
        lockA.unlock();
    }

    void taskB() {
        System.out.println(Thread.currentThread().getName() + " Need Resource B");
        lockB.lock();
        System.out.println(Thread.currentThread().getName() + " 🔒 Resource B");

        try {
            Thread.sleep(100); // Simulating some work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " Need Resource A");
        lockA.lock(); // Thread-2 tries to lock Resource A (held by Thread-1)
        System.out.println(Thread.currentThread().getName() + " 🔒 Resource A");

        lockA.unlock();
        lockB.unlock();
    }
}
