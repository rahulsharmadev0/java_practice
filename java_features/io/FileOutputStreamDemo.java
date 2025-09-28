package io;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamDemo {
    public static void main(String[] args) throws IOException {
        String path = System.getProperty("user.dir");
        path += "/assets/questions.txt";

        String newQuestions = """
                Replace a lambda with a static method reference to calculate square.
                Convert a lambda that prints strings to a method reference.
                Use an instance method reference on a particular object to format strings.
                Create a constructor reference to create new Employee objects.
                Convert `List<String>` to `List<Integer>` using method reference.
                Demonstrate constructor reference using `Supplier`.
                Use `BiFunction` with method reference to call an instance method.
                """;
        FileOutputStream fos = new FileOutputStream(path);

        for (char ch : newQuestions.toCharArray())
            fos.write((int) ch); // add char one by one

        fos.close();

    }
}
