
/*
  "Thread communication" refers to the mechanism by which multiple threads
   within a single program can interact and exchange data with each other,
   allowing them to coordinate their actions and work together on a task

   Most Popular problem is Producer-Consumer problem
*/

import java.util.Random;

import javax.xml.crypto.Data;

public class ThreadCommunication {
    String data = null;
    Random rand = new Random();

    boolean hasData() {
        return data != null;
    }

    public static void main(String[] args) {
        ThreadCommunication tc = new ThreadCommunication();

        Thread amazon = new Thread(() -> {
            while (true) {
                try {
                    tc.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread boy = new Thread(() -> {
            while (true) {
                tc.consumer();
            }
        });

        amazon.start();
        boy.start();

    }

    synchronized public void producer() throws InterruptedException {
        if (!hasData()) {
            Thread.sleep(1000);
            data = String.valueOf(rand.nextInt(10000));
            System.out.println("🛠️ Produce: " + data);

        }

    }

    synchronized public void consumer() {
        if (hasData()) {
            System.out.println("🚀 Consume: " + data);
            data = null;
        }
    }
}
