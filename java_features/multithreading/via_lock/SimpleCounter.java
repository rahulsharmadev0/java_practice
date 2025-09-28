package multithreading.via_lock;

import utils.IO;
import utils.Try;

import java.util.concurrent.locks.*;

// Simple Counter
// via ReentrantLock increase the value of i, infinite incremental algo.
// before modifying the 'i' lock the resource after the working complete
// unlock the 'ReentrantLock' (Release the resource)
public class SimpleCounter {
    int i = 0;
    final Lock lock = new ReentrantLock();

    void increment() {
        lock.lock(); // Lock the critical section
        Try.run(() -> Thread.sleep(1000));
        i++;
        IO.printf(Thread.currentThread().getName());
        lock.unlock(); // Unlock it, so that other

    }

    public static void main(String[] args) {
        SimpleCounter simpleCounter = new SimpleCounter();

        for (int i = 0; i < 10; i++)
            new Thread(simpleCounter::increment).start();

    }
}
