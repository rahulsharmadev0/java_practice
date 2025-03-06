
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* 
* How to use Shared Resourses accros Multithreading
* Types of Look
*   1. Intrinsic (by using synchronized keyword)
*   2. Explicit (by using Lock class)
*/
public class SharedResoursesExample {

    public static void main(String[] args) {
        NotePad notepad = new NotePad(""); // Shared Resourese

        Writer rahul = new Writer("rahul", notepad);
        Writer prince = new Writer("prince", notepad);
        Writer sachin = new Writer("sachin", notepad);
        Writer ramesh = new Writer("ramesh", notepad);
        // Start time
        long startTime = System.nanoTime();
        rahul.start();
        prince.start();
        sachin.start();
        ramesh.start();

        try {
            rahul.join();
            prince.join();
            sachin.join();
            ramesh.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(notepad.read());

        // End time
        long endTime = System.nanoTime();
        // Calculate execution time
        double duration = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Execution time: " + duration + " seconds");

    }

    static public class Writer extends Thread {

        String[] words = {
                "A thread is a path that is followed", "during a program’s execution.",
                "The majority of programs written nowadays run as a single thread.",
                "For example, a program is not capable",
                " of reading keystrokes while making drawings."
        };
        NotePad notepad;

        public Writer(String name, NotePad notepad) {
            this.notepad = notepad;
            super.setName(name);
        }

        @Override
        // Synchronizing on a shared resource
        public void run() {
            Random random = new Random();
            int randomIndex = random.nextInt(words.length);
            long ms = random.nextInt(2000);
            notepad.write("\n[" + ms + "]" + getName() + " : " + words[randomIndex], ms);
        }
    }

    static public class NotePad {
        String text;
        private final Lock lock = new ReentrantLock();

        public NotePad(String text) {
            this.text = text;
        }

        String read() {
            return text;
        }

        void write(String text, long ms) {
            try {
                /*
                 * Behaviour:
                 * Every time whenever some one come to write within 1 sec. after last write
                 * that event is droped. However, the last write event not be dropped if no new
                 * thread attempts to write after it, as there is no subsequent event to trigger
                 * its removal.
                 */
                if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                    try {
                        Thread.sleep(ms);
                        this.text += text;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
