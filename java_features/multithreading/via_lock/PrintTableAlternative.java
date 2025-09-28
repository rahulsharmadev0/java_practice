package multithreading.via_lock;

import utils.Try;

import java.util.concurrent.locks.ReentrantLock;

public class PrintTableAlternative {


    public static void main(String[] args) {
        PrintTableAlternative pta = new PrintTableAlternative(3, 5, 3);
        pta.start();
    }

    PrintTableAlternative(int a, int b, int max) {
        this.a = a;
        this.b = b;
        this.max = max;
        t1 = new Thread(this::printTableA);
        t2 = new Thread(this::printTableB);
    }

    private int i = 1, a, b, max = 10;
    private Thread t1, t2;
    ReentrantLock lock = new ReentrantLock();
    volatile boolean isATurn = true;

    private void printTableA() {
        do {
            while (i <= max && !lock.isLocked() && isATurn) {
                lock.lock();
                Try.run(() -> Thread.sleep(1000));
                System.out.println(a + " x " + i + " = " + (a * i));
                isATurn = false;
                lock.unlock();
            }
        } while (i <= max);
    }

    private void printTableB() {
        do {
            while (i <= max && !lock.isLocked() && !isATurn) {
                lock.lock();
                Try.run(() -> Thread.sleep(1000));
                System.out.println(b + " x " + i + " = " + (b * i));
                i++;
                isATurn = true;
                lock.unlock();
            }
        } while (i <= max);
    }

    void start() {
        t2.start();
        t1.start();
    }
}
