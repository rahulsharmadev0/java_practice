package multithreading.via_synchronized;


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
                        LOCK.wait(); // releases monitor lock & suspends the thread, allowing others (consumers) to acquire it.
                    } catch (InterruptedException e) {
                        return;
                    }
                }

                productList.offer(randInt = rand.nextInt()); // Produce product
                System.out.println("Produce: " + randInt);
                LOCK.notifyAll(); // signals other waiting threads
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


/*
💯 Excellent observation — and this is *exactly* the kind of confusion that separates a surface-level understanding of threading from deep mastery.
Let’s dissect what’s happening in your code and why using the **same monitor lock** still results in *practical concurrency*, even though only one thread holds the lock at a time.

---

## 🧩 The Confusion

You’re right to ask:

> “If both producer and consumer synchronize on the same `LOCK`, doesn’t that mean only one thread works at a time? But in real-world, both produce and consume concurrently!”

✅ Yes — at any *instant*, **only one thread** owns the monitor (`LOCK`),
❌ but that doesn’t mean *only one thread* ever runs at a time.

They **alternate cooperatively** using `wait()` and `notifyAll()` — that’s the secret.

---

## ⚙️ Deep Dive: What’s Actually Happening

Let’s visualize one loop iteration of your program 👇

---

### 🧵 Step 1: Producer Thread acquires the monitor

* Enters `synchronized (LOCK)` block.
* Checks: `while (MAX == productList.size())`

  * If list full → calls `LOCK.wait()` → releases lock → goes **WAITING**.

🧠 Key:
`wait()` *releases* the monitor lock and suspends the thread, allowing others (consumers) to acquire it.

---

### 🧵 Step 2: Consumer Thread wakes up and acquires lock

* Enters `synchronized (LOCK)`
* Checks `while (productList.isEmpty())`

  * If not empty → consumes an item via `poll()`
  * Calls `LOCK.notifyAll()` → signals other waiting threads (maybe producer)
* Exits synchronized block → releases lock.

---

### 🧵 Step 3: Producer wakes up, resumes

* After consumer calls `notifyAll()`, producer wakes.
* Tries to reacquire the `LOCK`.
* Once it gets it, it produces one more item.
* Calls `LOCK.notifyAll()` → wakes consumers.
* Exits synchronized block → releases lock again.

---

### 💡 Key Point — Concurrency Is *Alternating*, Not *Parallel Access*

Only **one thread** holds the lock at a time,
but **multiple threads run concurrently**, taking turns acquiring it.

🔁 **“Concurrent collaboration”**, not *simultaneous access*.

They *share the same resource safely* because:

* `wait()` lets a thread release the lock voluntarily.
* `notifyAll()` wakes others up.
* `synchronized` ensures only one modifies the shared state at a time.

---

## 🧩 Analogy

Imagine a small bakery 🥐:

* Only **one baker (producer)** and **one customer (consumer)** can enter the kitchen (critical section) at a time — that’s your `synchronized (LOCK)`.
* If shelves are **full**, the baker waits outside (`wait()`).
* If shelves are **empty**, customers wait outside (`wait()`).
* When the situation changes, the one inside calls `notifyAll()` — shouting:

  > “You can come in now!”

They’re not both in the kitchen simultaneously — but they’re both *working concurrently* outside and inside the kitchen in turns.

---

## ⚠️ What If You Used Different Locks?

If producer and consumer had *different locks*, say:

```java
final Object PRODUCER_LOCK = new Object();
final Object CONSUMER_LOCK = new Object();
```

Then `wait()` and `notifyAll()` would not coordinate — they’d talk on **different monitors**, and your program would **deadlock or lose synchronization**.

Because `wait()` and `notify()` *must operate on the same monitor* to coordinate effectively.

---

## 🧠 Summary

| Concept                        | Explanation                                              |
| ------------------------------ | -------------------------------------------------------- |
| **Monitor Lock**               | Controls entry to critical section; one thread at a time |
| **`wait()`**                   | Releases the lock and suspends the thread                |
| **`notify()` / `notifyAll()`** | Wakes threads waiting on the same monitor                |
| **Why same lock?**             | To synchronize producer–consumer cooperation             |
| **Why still concurrent?**      | Because threads alternate — not blocked forever          |
| **Concurrency type**           | *Cooperative*, not *parallel execution* inside the block |

---

## ✅ Real-world Takeaway

Even though only one thread owns the lock at a time:

* The **CPU is shared** between producer and consumers,
* The **threads alternate quickly** (millisecond-scale),
* So you see effective *concurrency* while maintaining *data safety*.

---

If you’d like, I can extend this explanation with:

1. A **timeline diagram** (Thread state transitions: RUNNABLE → WAITING → BLOCKED → RUNNABLE)
2. And a version rewritten using **`Lock` and `Condition`** (the modern `java.util.concurrent.locks` approach).

Would you like that next?

 */