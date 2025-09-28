package multithreading;

import utils.IO;

import java.io.Serializable;
import java.util.Random;

/*
------------------------------------------
    Create a Thread-Safe Singleton Class
------------------------------------------
Difficulty: Easy to Medium
Concepts: Lazy initialization, synchronized, volatile, double-checked locking
Problem Statement:
Implement a thread-safe singleton class using lazy initialization.

Variants:
 - Synchronized method
 - Synchronized block with double-checked locking
 - Using ReentrantLock
Key Skills Tested:
 - Class-level locking
 - Initialization-on-demand
 - Volatile usage
 - */
public class ThreadSafeSingletonClass {

    public static void main(String[] args) {
        Runnable runnable = ()->{
            ThreadSafeObject.getInstance().setObject(new Random().nextInt());
            IO.printf(ThreadSafeObject.getInstance() +" : "+Thread.currentThread().getName());
        };
        for (int i = 0; i < 5; i++)
             new Thread(runnable).start();

    }

}


final class ThreadSafeObject implements Serializable{
    Object object;
    private ThreadSafeObject(){}

    private volatile static ThreadSafeObject instance;

    public static ThreadSafeObject getInstance(){
          synchronized (ThreadSafeObject.class){
              if(instance==null)
                 return instance= new ThreadSafeObject();
              return  instance;
        }
    }

    public Object getObject(){
        return object;
    }
    public synchronized void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return object.toString();
    }
}


