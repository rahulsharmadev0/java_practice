
[Link](https://codefarm0.medium.com/scenario-based-java-multithreading-interview-questions-with-a-focus-on-completablefuture-e749bcbb7dbb)
## Scenario 1: Stock Market Live Data Aggregation

“A financial application retrieves stock prices from three different APIs and needs to compute the average stock price. If any API fails, the system should return a default value for that source. How would you implement this using_ `_CompletableFuture_`_?"

## Scenario 2: E-Commerce Order Processing with Parallel Tasks

In an e-commerce system, when a user places an order, multiple tasks must run concurrently:

1.  Validate the order.
2.  Process the payment.
3.  Update the inventory.
4.  Send a confirmation email.

These tasks should execute asynchronously, and the system should proceed only when all are completed. How would you implement this using `CompletableFuture`?"\_


## Scenario 3: Real-time Fraud Detection in Payment Transactions

A banking system must analyze multiple parameters asynchronously (transaction amount, location, past history) and flag suspicious transactions if a fraud score exceeds a threshold. How would you implement this?


## Scenario 4: Parallel Data Processing in a Big Data Pipeline

A data pipeline receives raw sensor data from IoT devices and must process it in parallel through multiple stages:

1.  Cleanse the data (remove duplicates, invalid values).
2.  Enrich the data (add metadata, location info).
3.  Aggregate the data (compute averages, detect anomalies).

How would you implement this efficiently using `CompletableFuture`?


## Scenario 5: Asynchronous Caching with Auto-Refresh

A high-traffic application uses an in-memory cache for frequently accessed data (e.g., product details). The cache should:

1.  Fetch fresh data from the database only when stale.
2.  Auto-refresh data every 10 minutes asynchronously without blocking requests.

How would you implement this using `CompletableFuture`?

## Scenario 6: Distributed Computing with Worker Nodes

A large dataset must be processed across multiple worker nodes in a distributed environment. Each node processes a subset of data, and the final results are aggregated centrally. How would you implement this?

---
## 1. Bounded Blocking Queue Implementation
   Write a thread-safe class `BoundedBlockingQueue<T>` with the following methods:

   ```java
   public class BoundedBlockingQueue<T> {
       public BoundedBlockingQueue(int capacity) { … }
       public void enqueue(T item) throws InterruptedException { … }
       public T dequeue() throws InterruptedException { … }
       public int size();  // returns current number of elements
   }
   ```

   Behavior requirements:

   * If `enqueue` is called when the queue is full, it should block until space is available.
   * If `dequeue` is called when the queue is empty, it should block until an item is available.
   * Ensure no lost signals, avoid deadlock, and support concurrency from multiple producers and consumers.
   * `size()` should return the correct number (even under concurrency).

   **Examples / scenarios**
   a. Capacity = 2. Thread A enqueues `1`, `2`. A third call to `enqueue(3)` should block until one `dequeue` occurs.
   b. Two consumer threads call `dequeue()` on an empty queue: both should block until producers enqueue elements.
   c. Mixed sequence: enqueue, dequeue, enqueue, etc., verifying correctness and absence of data races.

## 2. Thread-Safe LRU Cache**
   Design and implement a thread-safe least-recently-used (LRU) cache in Java. The class should support:

   ```java
   public class ThreadSafeLRUCache<K, V> {
       public ThreadSafeLRUCache(int capacity) { … }
       public V get(K key);      // returns value or null if not present
       public void put(K key, V value);
   }
   ```

   Behavior constraints:

   * If capacity is exceeded, evict the least recently used entry.
   * Accessing (via `get`) an item should update its recency.
   * Must support concurrent `get` and `put` from multiple threads without corruption.
   * Optimize for performance (minimize locking overhead).

   **Examples / scenarios**
   a. Capacity = 2. `put("A", 1)`, `put("B", 2)`, then `get("A")` (makes “A” most recent), then `put("C", 3)` → should evict “B”.
   b. Concurrent threads: one does repeated `get`, another does `put` updates, verifying no structural corruption.
   c. Edge: `put` an existing key should update the value and move it to most-recent, not increase size.

## 3. Dining Philosophers Variation
   You have **N** philosophers sitting in a circle. Between each pair is a shared chopstick (i.e. N chopsticks). Each philosopher repeatedly does: think → pick up left chopstick → pick up right chopstick → eat → put down both chopsticks.
   Implement a solution in Java that avoids deadlock and ensures all philosophers get a chance to eat eventually (no starvation). Use threads to simulate philosophers.

   **Examples / scenarios**
   a. N = 5 philosophers, simulation run for a fixed time; verify no deadlock.
   b. Ensure fairness: one philosopher should not starve while others eat repeatedly.
   c. N = 2 philosophers (trivial case) should also work.

## 4. Cyclic Barrier with a Timeout
   Implement a class `MyCyclicBarrier` (without using java.util.concurrent.CyclicBarrier) that supports:

   ```java
   public class MyCyclicBarrier {
       public MyCyclicBarrier(int parties) { … }
       public void await() throws InterruptedException;  
       public boolean await(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException;
   }
   ```

   Behavior:

   * `await()` blocks until exactly `parties` threads call `await()`, then all are released and barrier resets (reusable).
   * The timed `await(timeout, unit)` throws `TimeoutException` if the waiting thread times out before all parties arrive.
   * If one thread times out or is interrupted, the barrier should break and release all waiting threads (throwing an exception or indicating failure).
   * The barrier must be reusable: once all parties are released, it resets for the next round.

   **Examples / scenarios**
   a. parties = 3. Three threads call `await()` (no timeout): all proceed together.
   b. parties = 3. Two threads call `await(timeout)` and one delays: the two timeout, barrier is broken, all unblock.
   c. Next round: after barrier reset, again 3 threads call `await()` — works again.

## 5. Ordered Thread Execution (FizzBuzz style)
   Suppose there are three threads: Thread A prints “A”, thread B prints “B”, thread C prints “C”. You need to coordinate them so that the output is always `ABCABCABC…` repeated **n** times. Design and implement a Java class that supports this. For example:

   ```java
   public class OrderedPrinter {
       public OrderedPrinter(int n) { … }
       public void printA(Runnable printA) throws InterruptedException { … }
       public void printB(Runnable printB) throws InterruptedException { … }
       public void printC(Runnable printC) throws InterruptedException { … }
   }
   ```

   Each thread repeatedly calls its respective method; the implementation must ensure strict order A → B → C → A → B → C …

   **Examples / scenarios**
   a. n = 2: threads calling in parallel must produce “ABCABC”.
   b. Threads may be started in any order — still it must enforce ABC sequence.
   c. If one thread is slower, others must wait as needed to preserve ordering.

---

## Hard (5 questions)

## 6. Thread-safe Read-Write Lock Implementation
   Implement your own `ReadWriteLock` interface (similar to `java.util.concurrent.locks.ReadWriteLock`) in Java, supporting the methods:

   ```java
   public interface MyReadWriteLock {
       Lock readLock();
       Lock writeLock();
   }
   ```

   The returned `Lock` has methods `lock()` and `unlock()`. Requirements:

   * Multiple readers can hold the lock simultaneously, as long as no writer holds it.
   * Writer is exclusive.
   * Writers should not starve (i.e. if many readers keep coming, a waiting writer must eventually acquire).
   * Support reentrancy (optional, but bonus).

   **Examples / scenarios**
   a. Start several reader threads (concurrently) — they should not block each other.
   b. A writer thread must wait until all active readers release, then proceed exclusively.
   c. After a writer completes, new readers and writers should continue working in subsequent rounds without deadlock or starvation.

## 7. Thread Pool Executor with Priority + Preemption
   Design and implement a custom thread pool in Java that supports **prioritized tasks** and **preemption**:

   * Tasks have priority levels (e.g. integer).
   * Higher-priority tasks should displace (preempt) currently running lower-priority tasks (i.e. pause/suspend them, run the higher-priority task, then resume).
   * The thread pool has a fixed number of worker threads.
   * Ensure correct handling of task pausing, resuming, cancellation, and ordering.

   **Examples / scenarios**
   a. Submit long-running low-priority task, then submit high-priority task → high-priority should preempt the low-priority one.
   b. Multiple tasks of different priority levels submitted concurrently.
   c. Preemption/resume bookkeeping: no data corruption or inconsistent state when resumed.

## 8. Concurrent Merge Sort
   Write a Java method `public void parallelMergeSort(int[] arr, int threshold)` that sorts `arr` in-place using merge sort, but uses multiple threads:

   * If the subarray length exceeds `threshold`, you split and sort halves in parallel (spawn two threads) and then merge.
   * If length is ≤ threshold, sort sequentially.
   * Limit the maximum number of concurrent threads (say by using a thread-pool or tracking active thread count) to avoid oversubscription.

   **Examples / scenarios**
   a. arr = [5,2,9,1,5,6], threshold = 2 → final sorted = [1,2,5,5,6,9].
   b. arr = large random array, threshold tuned to give performance gain.
   c. edge: already sorted, reverse sorted, duplicates, empty.

## 9. Deadlock Detection at Runtime
   Suppose you have many threads acquiring multiple locks (via `synchronized` or `ReentrantLock`). You are to build a **runtime deadlock detection** utility in Java:

   * It should periodically scan the thread-lock graph and detect if there is a cycle (deadlock) among threads waiting.
   * If a deadlock is detected, it should log the cycle (which threads, which locks) or optionally try to break it (e.g. by interrupting one thread).
   * Design the data structures and algorithm (you do not necessarily need to instrument JVM, but you can assume you have hooks / wrappers around lock acquisitions).

   **Examples / scenarios**
   a. Two threads: T1 acquires Lock A then waits for Lock B; T2 acquires Lock B and waits for Lock A — detect deadlock.
   b. Three threads: A waits on B, B waits on C, C waits on A — detect 3-cycle.
   c. No deadlock: chain but no cycle; should not falsely report.

## 10. Parallel Graph Traversal with Thread Coordination
    Given a directed graph with **N** nodes, implement a method that visits all reachable nodes from a given start node using concurrency, under these constraints:

    * Multiple worker threads traverse different parts in parallel.
    * No node should be visited more than once (i.e. maintain a visited set).
    * Must coordinate work-stealing or load balancing among threads (i.e. dynamic distribution of frontier).
    * Avoid excessive locking overhead (lock contention on visited set).

    Define interfaces and classes; implement `public Set<Node> parallelTraverse(Node start)`.

    **Examples / scenarios**
    a. Simple acyclic graph: start at root, all reachable nodes visited.
    b. Cyclic graph: ensure you don’t loop infinitely.
    c. Large dense graph, many threads working concurrently, verify correctness and performance.

---

## 11. Lock-Free Concurrent Data Structure (Stack or Queue)
    Implement a **lock-free** (non-blocking) thread-safe stack (e.g. Treiber stack) or queue (e.g. Michael-Scott queue) in Java using atomic primitives (`AtomicReference`, `compareAndSet`, etc.). Provide `push` and `pop` (or enqueue/dequeue) methods, and explain how you handle ABA problem (e.g. using versioned pointers or `AtomicStampedReference`).

    **Examples / scenarios**
    a. Concurrent threads push and pop randomly — correctness (no lost data, consistent stack or queue).
    b. Test for ABA scenario: try to simulate a pop–push–pop interleaving to see if CAS fails or misbehaves.
    c. High throughput scenario to show scalability.

## 12. Transactional Memory Simulation in Java
    Design and implement a simple in-memory transactional system for objects:

    * Clients can start a transaction, make reads and writes to shared objects, and either commit or abort.
    * Use optimistic concurrency control: at commit time, validate there is no conflict; if conflict detected, abort and retry.
    * You must support multiple concurrent transactions, isolation (e.g. serializable), and correctness under concurrency.
    * Provide APIs like `beginTransaction()`, `read(obj)`, `write(obj, value)`, `commit()`, `abort()`.

    **Examples / scenarios**
    a. Two transactions both update same object → one must abort or one delay commit.
    b. Read-write conflict: one reads a value while another updates before commit → first must detect conflict.
    c. Multi-object transaction: writing to multiple objects; commit must validate all.

## 13. Scalable Lock Striping / Sharding for Concurrent Map
    Design a concurrent map implementation that uses lock striping or sharding to reduce contention, while still maintaining consistency. For example: split the map into multiple buckets or segments, each with its own lock, but support rehashing or resizing, and concurrent iteration. Provide methods `put`, `get`, `remove`, and an iterator or traversal that sees a consistent snapshot or weak consistency. Explain how resizing is handled without blocking all segments, concurrency pitfalls, and consistency semantics.

    **Examples / scenarios**
    a. Many concurrent `get` and `put` operations on different keys — ensure minimal contention.
    b. Iterating the map while writes are ongoing — behavior defined (weakly consistent).
    c. Resizing scenario: crossing a threshold triggers expansion — must coordinate among shards safely.

## 14. Parallel Deadlock-Free Transaction Scheduler / Two-Phase Locking
    You have **M** shared resources and **T** concurrent transactions, each transaction needs to lock a certain subset of resources (in any order). You need to design a **scheduler** that allows as much parallelism as possible while preventing deadlocks, using a variant of two-phase locking with deadlock avoidance (e.g. ordering, wait-die, wound-wait, or locking ordering). Implement the scheduling mechanism and resource allocation in Java, supporting `acquire(transactionId, resourceId)`, `release(...)`. The scheduler must detect and avoid deadlock by aborting or delaying.

    **Examples / scenarios**
    a. Two transactions: Tx1 needs resource A then B; Tx2 needs B then A — scheduler must avoid deadlock (e.g. using global ordering).
    b. More transactions with overlapping resource sets.
    c. Long-running transactions need fairness: no starvation.

## 15. Memory Model & Happens-Before Violations Detection (Static or Dynamic)
    Propose and (optionally) implement a tool or library that can dynamically detect **memory consistency / reordering / visibility violations** in multithreaded Java code. That is, at runtime, detect if a shared field is being read in a way that violates Java Memory Model guarantees (due to missing `volatile`, synchronization). You may rely on instrumentation (e.g. bytecode rewriting) or wrappers around volatile / locks. Explain how you track read/write happens-before edges, vector clocks, or version stamps, and how you can detect a violation.

    **Examples / scenarios**
    a. Thread A writes `x = 1` (non-volatile) then sets `flag = true` (non-volatile). Thread B reads `flag` and then reads `x` — the tool should detect a possible reorder (i.e. B sees `flag = true` but stale `x = 0`).
    b. Concurrent writes and reads to multiple variables across threads, detect cycles in the happens-before graph.
    c. Code with correct synchronization should not be flagged.
