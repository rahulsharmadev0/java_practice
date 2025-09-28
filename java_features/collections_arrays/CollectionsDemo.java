package collections_arrays;

import utils.IO;

import java.awt.*;
import java.text.CollationElementIterator;
import java.util.Collections;
import java.util.*;
import java.util.List;

public class CollectionsDemo {

    public static void main(String[] args) {
        // Empty Immutable List
        List<Integer> emptyList = Collections.emptyList();

        // Empty Immutable Set
        Set<Integer> emptySet = Collections.emptySet();

        // Empty Immutable Map
        Map<Integer, Integer> emptyMap = Collections.emptyMap();

        // Only One element Immutable List
        List<Integer> oneList = Collections.singletonList(3);

        // Only One element Immutable Set
        Set<Integer> oneSet = Collections.singleton(1);

        // Only One pair Immutable Map
        Map<Integer, Integer> singlePairMap = Collections.singletonMap(1, 1);

        //------------------Operations ----------------------

        List<Integer> dummayList = Arrays.asList(19, 20, 30, 20, 45, 6, 78, 96, 57, 39, 10, 112, 11, 20, 45, 6, 78, 3, 234, 35, 43);
        List<Integer> dublicateDummayList = new ArrayList<>(dummayList);

      /*
      1. Sorting
      2. Reverse
      3. Shuffling (mix-up)
      4. Swaping (SWAP element)
      5. lastIndexOfSubList
      6. indexOfSubList
      7. search (BinarySearch)
      8. Rotation (Rotate)
      10. Copy
      11. Fill
      12. ReplaceAll
      13. Frequency
      14. Disjoint
      15. Min & Max
       */

        // Sorting (Sort)
        dummayList.sort((a, b) -> a > b ? 1 : -1);
        IO.printf(dummayList);


        dummayList.sort(Collections.reverseOrder());
        IO.printf(dummayList);


        Collections.sort(dummayList, (a, b) -> a > b ? 1 : -1);
        IO.printf(dummayList);


        Collections.sort(dummayList, Collections.reverseOrder());
        IO.printf(dummayList);


        // Shuffling (shuffle)
        Collections.shuffle(dummayList, new Random(1000));
        IO.printf(dummayList);


        // Reversing (reverse)
        IO.printf(dummayList.reversed()); // ".reversed() method return, reversed List as new Object"


        Collections.reverse(dummayList); // reverse the exist list.
        IO.printf(dummayList);


        // Swaping (swap)
        Collections.swap(dummayList, 0, 3);
        IO.printf(dummayList);


        // Rotation (rotate)
        Collections.rotate(dummayList, 3);
        IO.printf(dummayList);


        // IndexOfSubList
        int index = Collections.indexOfSubList(dublicateDummayList, Arrays.asList(20, 45, 6, 78));
        IO.printf("Your give list found from index: " + index);


        // lastIndexOfSubList
        index = Collections.lastIndexOfSubList(dublicateDummayList, Arrays.asList(20, 45, 6, 78));
        IO.printf("Your give list found from last index: " + index);


        // Min & Max Value
        // Min
        IO.printf("Min: " + Collections.min(dummayList));

        //Max
        IO.printf("Max: " + Collections.max(dummayList));


        // Frequency
        new HashSet<>(dummayList).forEach(s -> {
            IO.printf("Frequency of " + s + ": " + Collections.frequency(dummayList, s));

        });

        // Binary Search (Only apply on sorted List)
        Collections.sort(dummayList);
        IO.printf(dummayList);
        IO.printf("Searched Index: " + Collections.binarySearch(dummayList, 11));


        // Replace All
        dummayList.replaceAll(s -> s == 20 ? 2000 : s);
        IO.printf(dummayList);


        Collections.replaceAll(dummayList, 2000, 2001);
        IO.printf(dummayList);


        // DisJoint (True if No Common Elements)
        IO.printf("Is Disjoint(Any Common Element): " + Collections.disjoint(dummayList, dublicateDummayList));
        IO.printf("Is Disjoint(Any Common Element): " + Collections.disjoint(Arrays.asList(11, 22), Arrays.asList(2, 1)));


        // Fill (Replace all value with given value)
        Collections.fill(dummayList, 0);
        IO.printf(dummayList);

        // Copy One list int another list
        var ls = Arrays.asList(1,2);
        var ls2= Arrays.asList(3,7);
        Collections.copy(ls, ls2);
        IO.printf(ls);
    }
}
