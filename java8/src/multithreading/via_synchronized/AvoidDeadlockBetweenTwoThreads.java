package multithreading;

/*
----------------------------------------
Avoid Deadlock Between Two Threads
----------------------------------------
You have **two shared objects** being used by **two threads**.
Each thread tries to acquire locks on **both objects**, but in **opposite order**:
    - Thread A: acquires lock on A, then lock on B
    - Thread B: acquires lock on B, then lock on A

This setup can lead to a **deadlock** if both threads hold their first lock and **wait forever** for the second.
Your task is to **analyze this setup** and **prevent deadlock** through proper coordination or locking strategy.


## 🧰 Real-World Use Case
Imagine a **bank transfer system**:
    - Thread A transfers money from **Account 1** to **Account 2**
    - Thread B transfers money from **Account 2** to **Account 1**

Each thread:
    - First locks the source account
    - Then locks the destination account

If locking is done without coordination, **both threads can deadlock** by waiting on each other.

*/

import utils.IO;
import utils.Try;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AvoidDeadlockBetweenTwoThreads {
    public static void main(String[] args) {
//        Account rahul = new Account(30000);
//        Account prince = new Account(50000);
//        Account sachin = new Account(10000);
//
//        Runnable runnable = () -> {
//            Account.transfer(rahul, prince, 2000);
//            Account.transfer(prince, sachin, 3000);
//            Account.transfer(rahul, sachin, 4000);
//        };
//
//
//        List<Thread> threads = List.of(new Thread(runnable),new Thread(runnable), new Thread(runnable));
//
//        for (Thread t : threads)
//            t.start();
//
//        for (Thread t : threads)
//            Try.run(t::join);
//
//
//
//
//        IO.printf(rahul.getBalance());
//        IO.printf(prince.getBalance());
//        IO.printf(sachin.getBalance());

        Account A = new Account(1000);
        Account B = new Account(1000);

        Thread t1 = new Thread(() -> {
            System.out.println("T1: Transferring A -> B");
            Account.transfer(A, B, 100);
            System.out.println("T1: Done");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("T2: Transferring B -> A");
            Account.transfer(B, A, 100);
            System.out.println("T2: Done");
        });

        t1.start();
        t2.start();

        Try.run(t1::join);
        Try.run(t2::join);

        IO.printf(A.getBalance());
        IO.printf(B.getBalance());

    }

}

class Account {
    private int balance = 0;

    Account(int openingBalance) {
        balance = openingBalance;
    }

    public Object getLock() {
        return this;
    }

    public int getBalance() {
        return balance;
    }

    synchronized void deposit(int amount) {
        Try.run(() -> Thread.sleep(100));
        balance += amount;
        System.out.println("deposited " + amount);
    }

    synchronized void withdraw(int amount) {
        if (amount > balance) {
            return;
        }
        Try.run(() -> Thread.sleep(500));
        balance -= amount;
        System.out.println("withdraw " + amount);
    }

    static void transfer(Account from, Account to, int amount) {

        // Step 1: Ensure consistent lock order to prevent deadlock
        // We compare the identity hash codes of both account objects.
        // Always lock the object with the smaller hash code first.
        // If 'from' has a higher hash than 'to', we swap them.
        //
        // 'It ensures that every thread always locks the accounts in the same order,
        // based on their hash codes — from smaller to larger.'
        if (from.hashCode() > to.hashCode()) {
            var tmp = from;
            from = to;
            to = tmp;
        }

        // Step 2: Lock the first account (based on consistent order)
        synchronized (from.getLock()) {
            // Step 3: Lock the second account after acquiring the first lock
            synchronized (to.getLock()) {
                // Step 4: Perform the transfer — withdraw from one, deposit to the other
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }




}
