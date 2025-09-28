package collections_arrays;

import utils.IO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArraysDemo {
    public static void main(String[] args) {

        /*
        1. copyOf
        2. copyOfRange
        3. fill
        4. setAll
        5. binarySearch
        6. sort
        7. equals
        8. misMatch
        9. compare
        10. stream
        11. spliterator
         */

        int[] dummayArray = {19, 20, 30, 20, 45, 6, 78, 96, 57, 39, 10, 112, 11, 20, 45, 6, 78, 3, 234, 35, 43};
        int[] dublicateDummayArray = dummayArray;

        // copyOf: copy the existing array with a specific length and create new Object.
        int[] copyedList = Arrays.copyOf(dummayArray, 5);
        IO.printf(copyedList);

        // copyOfRange: start from 3 but end before 10,(n-1)
        int[] copyedList2 = Arrays.copyOfRange(dummayArray, 3, 10);
        IO.printf(copyedList2);

        // fill
        Arrays.fill(copyedList2, 0);
        IO.printf(copyedList2);

        Arrays.fill(copyedList2, 2, 5, 10);
        IO.printf(copyedList2);

        // setAll
        Arrays.setAll(copyedList2, i -> {
            if (i == 0) return 0;
            if (i == 1) return 1;
            return copyedList2[i - 2] + copyedList2[i - 1];
        });

        // sorting (sort)
        Arrays.sort(dummayArray, 0, 4);
        IO.printf(dummayArray);

        Arrays.sort(dummayArray);
        IO.printf(dummayArray);

        // binarySearch
        IO.printf("Element found on Index: " + Arrays.binarySearch(dummayArray, 10));

        IO.printf("Element found on Index: " + Arrays.binarySearch(dummayArray, 0, 5, 20));

        // equals: check for exact same array
        IO.printf(Arrays.equals(new int[]{1, 2}, new int[]{1, 2}));
        IO.printf(Arrays.equals(new int[]{2, 1}, new int[]{1, 2}));
        IO.printf(Arrays.equals(new int[]{1, 2, 3}, new int[]{1, 2}));

        // misMatch: return first misMatch Index in both array
        IO.printf(Arrays.mismatch(new int[]{1, 2}, new int[]{1, 2}));
        IO.printf(Arrays.mismatch(new int[]{2, 1}, new int[]{1, 2}));
        IO.printf(Arrays.mismatch(new int[]{1, 2, 3}, new int[]{1, 2}));

        // Compare

        IO.printf(Arrays.compare(dummayArray, dummayArray));

        IO.printf(Arrays.compare(dummayArray, dublicateDummayArray));

        IO.printf(Arrays.compare(new int[]{1, 2}, new int[]{1, 2})); // 0
        IO.printf(Arrays.compare(new int[]{3, 1}, new int[]{1, 2})); // 1
        IO.printf(Arrays.compare(new int[]{1, 2, 3}, new int[]{1, 2})); // 1 (3 > null )
        IO.printf(Arrays.compare(new int[]{6, 2}, new int[]{30, 30})); // -1 (6 < 30)


        // Spliterator: Create Spliterator Object
        IO.printf(dummayArray);
        Spliterator<Integer> sp = Arrays.spliterator(dummayArray, 0, 4);
        sp.forEachRemaining(System.out::println);

        // Stream: Create Stream Object
        IntStream stream = Arrays.stream(dummayArray);


    }
}
