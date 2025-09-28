package multithreading.via_lock;

import utils.IO;
import utils.Try;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


public class ProducerConsumer {
    public static void main(String[] args) {
        ProducerConsumer  psc = new ProducerConsumer();
        psc.startConsumingAndProducingTogether();
    }

    Thread producer, consumer;
    volatile List<Integer> products = new ArrayList<>();
    ReentrantLock lock = new ReentrantLock();

    ProducerConsumer() {
        producer = new Thread(this::producing);
        consumer = new Thread(this::consuming);
    }

    private void consuming() {
        while (true) {
            while (!lock.isLocked() && !products.isEmpty()) {
                lock.lock();
                Try.run(() -> Thread.sleep(1000));
                IO.printf("(" + Thread.currentThread().getName() + ") Consumed: " + products.removeFirst());
                lock.unlock();
            }
        }
    }

    private void producing() {
        while (true) {
            while (!lock.isLocked()) {
                lock.lock();
                Try.run(() -> Thread.sleep(1000));
                var randInt = new Random().nextInt();
                products.add(randInt);
                IO.printf("(" + Thread.currentThread().getName() + ") Produced: " + randInt);
                lock.unlock();
            }
        }
    }

    void startConsumingAndProducingTogether() {
        consumer.start();
        producer.start();
    }


}
