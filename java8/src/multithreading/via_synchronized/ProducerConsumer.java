package multithreading;


import java.util.*;

/*
Producer-Consumer Using wait/notify
- Difficulty: Medium to Hard
- Concepts: synchronized, wait(), notifyAll(), shared queue
Problem Statement:
Implement a producer-consumer system using a shared queue with bounded capacity.
    - Producer should wait if queue is full
    - Consumer should wait if queue is empty
Key Skills Tested:
    - Shared resource protection
    - Blocking via wait/notify
    - Thread cooperation
*/
public class ProducerConsumer {
    static Random rand = new Random();

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer producer = new ProducerConsumer();
        Thread[] consumerThreads = new Thread[]{
                new Thread(producer::consumerRunnable, "ConsumerThread-1"),
                new Thread(producer::consumerRunnable, "ConsumerThread-2"),
                new Thread(producer::consumerRunnable, "ConsumerThread-3")};
        producer.producerThread.start();
        for (Thread consumerThread : consumerThreads)
            consumerThread.start();

        try {
            producer.producerThread.join();
            for (Thread consumerThread : consumerThreads)
                consumerThread.join();
        } catch (InterruptedException e) {
            return;
        }
    }

    Queue<Integer> productList = new LinkedList<>();
    int MAX = 20;
    final Object LOCK = new Object();

    Thread producerThread = new Thread(() -> {
        int randInt;
        while (true) {
            synchronized (LOCK) {
                while (MAX == productList.size()) { // Wait as long as productList is empty
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }

                productList.offer(randInt = rand.nextInt()); // Produce product
                System.out.println("Produce: " + randInt);
                LOCK.notifyAll();
            }
            try {
                Thread.sleep(10); // take time to produce
            } catch (InterruptedException e) {
                return;
            }
        }
    });


    public void consumerRunnable() {
        while (true) {
            synchronized (LOCK) {
                while (productList.isEmpty()) { // if No Product then Wait.
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                String str = Thread.currentThread().getName() + ": " + productList.poll(); // Consume
                System.out.println(str);
                LOCK.notifyAll(); // notify all the thread
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                return;
            }
        }
    }

}
