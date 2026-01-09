package utils;

import java.util.Arrays;
import java.util.stream.Stream;

public class IO {
    static public void printf(Object obj) {
        System.out.println(obj);
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
        stream.forEach(s->{System.out.print(s.toString() + ", ");});
        nextln();
    }

    static public void nextln() {
        System.out.println();

    }
}