package design_patterns;

import utils.IO;

import java.util.Collections;
import java.util.*;

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

        List<Integer> dummayList = Arrays.asList(19,20,30,20,45,6,78,96,57,39,10,112,11,3,234,35,43);

      /*
      1. Sorting
      2. Reverse
      3. Shuffling (mix-up)
      4. Swaping (SWAP)
      5. lastIndexOfSubList
      6. indexOfSubList
      7. search (BinarySearch)
       */

        // Sorting (Sort)
        dummayList.sort((a,b)->a>b?1:-1);
        IO.printf(dummayList);
        IO.nextln();

        dummayList.sort(Collections.reverseOrder());
        IO.printf(dummayList);
        IO.nextln();

        Collections.sort(dummayList, (a,b)->a>b?1:-1);
        IO.printf(dummayList);
        IO.nextln();

        Collections.sort(dummayList, Collections.reverseOrder());
        IO.printf(dummayList);
        IO.nextln();
        









    }
}
