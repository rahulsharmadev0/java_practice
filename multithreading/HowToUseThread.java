import java.util.stream.IntStream;


// Create a class that implements Runnable interface
class CustomRunnableObj implements Runnable {
    @Override
    public void run() {
        IntStream.rangeClosed(1, 10).forEach(System.out::println);
    }
}

// Create a class that extends Thread class
class MyThread extends Thread {
    MyThread() {
        super("My Thread");
    }
    @Override
    public void run() {
        IntStream.rangeClosed(1, 10).forEach(System.out::println);
    }
}

/// How Many ways to create Thread
public class HowToUseThread {
    
    public static void main(String[] args) {

        //--------Ways of creating Runnable Object for Thread--------

        // 1. By using Anonymous Inner Class
        Runnable runnable1 = new Runnable(){
            @Override
            public void run() {
                IntStream.rangeClosed(1, 10).forEach(System.out::println);
            }
        };
        
        // 2. By using lambda expression (Java 8+)  
        Runnable runnable2 = () -> IntStream.rangeClosed(1, 10).forEach(System.out::println);

        
        // 3. By using Class that implements Runnable interface (Traditional way)
        Runnable runnable3 = new CustomRunnableObj();

        //--------Ways of creating Thread--------
        Thread heroThread = new Thread(runnable2, "Hero Thread"); //  <---- With Runnable object

        Thread heroThread2 = new MyThread(); // <--- Without Runnable object, (extends Thread class)


        System.out.println(heroThread2.getState());
        heroThread.start(); // <--- It will call run() method of Runnable object
        System.out.println(heroThread22.getState());
        System.out.println("End");
    }
}





/*
 * 1. Extend Thread class
 * 2. Implement Runnable Interface
 *     2.1 Use Lambda Expression (Java 8+)
 * 3. Use ExecutorService (Java 5+)
 *
 *
 */