package utils;


import java.util.Arrays;
import java.util.stream.Stream;

public class IO {
    static public void printf(Object obj) {
        System.out.print(obj + ", ");
    }

    static public void printf(int[] array) {
        System.out.println(Arrays.toString(array) + ", ");
    }

    static public void printf(String[] array) {
        System.out.println(Arrays.toString(array) + ", ");
    }

    static public void printf(Object[] array) {
        System.out.println(Arrays.toString(array) + ", ");
    }

    static public void printf(Stream<Object> stream) {
        stream.forEach(IO::printf);
        nextln();
    }

    static public void nextln() {
        System.out.println();

    }
}