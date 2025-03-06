
/// How Many ways to create Thread
public class HowToUseThread {
    public static void main(String[] args) {

        // anonymous class
        Runnable myRunnable = new Runnable() {
            public void run() {
                int i = 0;
                while (i < 10) {
                    System.out.println(++i);
                }
            }

        };

        Thread thread = new Thread(new MyThread2());
        System.out.println(thread.getState());
        thread.start();

        System.out.println("End");
    }

    //
    //
    static public class MyThread extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (i < 10) {
                System.out.println(++i);
            }
        }
    }

    static public class MyThread2 implements Runnable {
        @Override
        public void run() {
            int i = 0;
            while (i < 10) {
                System.out.println(++i);
            }
        }
    }

}