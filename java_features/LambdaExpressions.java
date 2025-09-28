import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import utils.IO;


public class LambdaExpressions {


    public static void main(String[] args) {

        System.out.println(findLarger.apply(3, 34));

        String[] listOfStrings = {"", "wwe", "a", "WeW", "Madam", "  ", "Rahul", "google"};


        IO.printf(Arrays.stream(sortListViaLength.apply(listOfStrings)));

        IO.printf(Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 7}).mapToObj((a) -> a + "=" + primeChecker.apply(a)));

        IO.printf(Arrays.stream(listOfStrings).map(countVowels));

        IO.printf(filterEmptyStrings.apply(listOfStrings));

        IO.printf(Arrays.stream(listOfStrings).map(isPalindrome));

        IO.printf(filterGreaterThan.apply(new int[]{1000, 111011, 20000, 50000, 50001}));

        IO.printf(Arrays.stream(new Integer[]{12, 45, 68, 79, 90}).map(grade));

        IO.printf(Arrays.stream(new Character[]{'s', '@', 'b', '1'}).map(isAlphaNumSym));

        IO.printf(Arrays.stream(new Character[]{'s', 'a', 'A', 'q'}).map(isVowel));

        Arrays.stream(new Integer[]{12, 45, 68, 79, 90}).forEach(isEven);

        IO.printf(sumOfSqOfEvenNumber.apply(new int[]{12, 45, 68, 79, 90}));
        IO.nextln();

        IO.printf(convertUpperCase.apply(listOfStrings));

        IO.printf(isDivisible.test(12, 2));
        IO.printf(isDivisible.test(2, 21));

        IO.nextln();
        List<Integer> ls = Arrays.asList(12, null, 45, 68, null, 79, 90);
        IO.printf(ls);
        setDefaultOnNull.accept(ls, 0);

        Integer[] listOfInt = new Integer[]{1, 2, 3, 4, 5, 6, 7};

        IO.nextln();
        for (int i : listOfInt)
            IO.printf(factorial.apply(i));
        IO.nextln();

        IO.printf(binarySearch.apply(listOfInt, 1));
        IO.printf(binarySearch.apply(listOfInt, 2));
        IO.printf(binarySearch.apply(listOfInt, 3));
        IO.printf(binarySearch.apply(listOfInt, 4));
        IO.nextln();
        IO.printf(factors.apply(90));
        IO.printf(factors.apply(35));
        IO.printf(factors.apply(136));
        String dummayStr = "Template Method is a behavioral design pattern that defines the skeleton of an algorithm in a superclass and lets subclasses override specific steps of the algorithm without changing its structure.";
        IO.printf(removeAllWhiteSpaces.apply(dummayStr));
        IO.nextln();
        IO.printf(Arrays.stream(listOfInt).map(isPrime));

        IO.printf(reverseString.apply("RAhul Sharma"));

        IO.nextln();
        Student[] students = {new Student("Rahul"), new Student("Anuj"), new Student("Raaam")};
        IO.printf(sortByName.apply(students));


        /// isHavingZeroExceptFirst
        IO.printf(isHavingZeroExceptFirst.apply(90998));
        IO.printf(isHavingZeroExceptFirst.apply(99980));
        IO.printf(isHavingZeroExceptFirst.apply(9998));
        IO.printf(isHavingZeroExceptFirst.apply(06));

        IO.printf(01);
        IO.printf(02);
        IO.printf(04);
        IO.printf(06);
        IO.printf(07);
//        IO.printf(08);
//        IO.printf(09);

    }


    // 1. Write a lambda expression to compare two integers and return the larger one.
    static BinaryOperator<Integer> findLarger = (a, b) -> a > b ? a : b;


    // 2. Create a lambda expression to calculate the square of a number.
    static UnaryOperator<Integer> square = (a) -> a * a;

    // 3. Write a program using lambda to sort a list of strings by their length.
    static UnaryOperator<String[]> sortListViaLength = (ls) ->
            Stream.of(ls).sorted(Comparator.comparingInt(String::length)).toArray(String[]::new);


    // 4. Create a functional interface that checks if a number is prime using lambda.
    static Function<Integer, Boolean> primeChecker = (a) ->
            a > 1 && IntStream.range(2, a - 1).noneMatch((i) -> a % i == 0);


    //5. Write a lambda expression to count the number of vowels in a string.
    static Function<String, Integer> countVowels = (str) ->
            (int) str.toLowerCase().chars().filter(ch -> ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o').count();


    //6. Use a lambda to filter out all empty strings from a list.
    static Function<String[], String[]> filterEmptyStrings = (ls) ->
            Arrays.stream(ls).filter(s -> s != null && !s.isBlank()).toArray(String[]::new);

    //7. Implement a lambda to check if a string is a palindrome.
    static Function<String, Boolean> isPalindrome = (String str) -> {
        str = str.toLowerCase();
        return (new StringBuffer(str)).reverse().toString().equals(str);
    };

    //8. Create a list of employees and use lambda to filter those with salary > max value.
    static Function<int[], int[]> filterGreaterThan = (ls) ->
            Arrays.stream(ls).filter(s -> s > 50000).toArray();

    //9. WAP to decide grade of the student based on obtained marks
    static Function<Integer, String> grade = (a) -> {
        if (80 <= a) return "A";
        else if (a >= 60) return "B";
        else if (a >= 40) return "C";
        else if (a >= 35) return "D";
        else return "E";

    };

    //10. WAP to check a given char is Alphabets , number, symbols
    static Function<Character, String> isAlphaNumSym = (ch) -> {
        if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')
            return "Alphabets";
        else if (ch >= '0' && ch <= '9')
            return "Number";
        else
            return "Special Char.";
    };

    //11. WAP to check is alphabet vowel or not
    static Function<Character, String> isVowel = (ch) -> {
        if (ch >= 'A' && ch <= 'Z') ch = (char) (ch + 32);
        return switch (ch) {
            case 'a', 'e', 'i', 'o', 'u' -> "Vowel";
            default -> "Consonant";
        };
    };

    // 12.
    static Consumer<Integer> isEven = (a) -> {
        switch ((a / 2) * 2 / a) {
            case 1 -> System.out.println("Even");
            default -> System.out.println("Odd");
        }
    };

    //    9. Use lambda to find the sum of squares of even numbers in a list.
    static Function<int[], Integer> sumOfSqOfEvenNumber = (ls) ->
            Arrays.stream(ls).filter(s -> s % 2 == 0).reduce(0, (a, b) -> a + b * b);


    //   10. Use a lambda expression to convert a list of strings to uppercase.
    static UnaryOperator<String[]> convertUpperCase = (ls) ->
            Arrays.stream(ls).map(String::toUpperCase).toArray(String[]::new);

    //   11. Create a lambda that takes two numbers and returns true if one is divisible by the other.
    static BiPredicate<Integer, Integer> isDivisible = (a, b) -> a % b == 0 || b % a == 0;

//   12. Use a lambda to replace all null values in a list with a default string.

    static BiConsumer<List<Integer>, Integer> setDefaultOnNull = (ls, def) -> {
        for (int i = 0; i < ls.size(); i++)
            if (ls.get(i) == null) ls.set(i, def);
    };

    //   13. Write a lambda that returns the factorial of a number.
    static UnaryOperator<Integer> factorial = (n) ->
            IntStream.range(1, ++n).reduce(1, (r, i) -> r * i);


    // 14.
    static BiFunction<Integer[], Integer, Integer> binarySearch = (array, a) -> {
        int start = 0;
        int end = array.length - 1;
        boolean isAscending = array[start] < array[end];
        while (start <= end) {
            int mid = (start + end) / 2;

            if (Objects.equals(a, array[mid])) return mid;

            if (isAscending) {
                if (a < array[mid]) end = mid - 1;
                else start = mid + 1;
            } else {
                if (a > array[mid]) end = mid - 1;
                else start = mid + 1;
            }
        }
        return -1;
    };


    class Stone {
        private int weight;
        private String color;

        Stone(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public String getColor() {
            return color;
        }
    }

    // 15. Get Total Weight of Stones
    public static Function<Stone[], Integer> getTotalWeight = (stones) -> {
        return Arrays.stream(stones).reduce(0, (total, s) -> total + s.getWeight(), Integer::sum);
    };


    // 16.
    public static Function<Stone[], Stone[]> getRedStone = (stones) -> {
        return Arrays.stream(stones).filter(s -> s.getColor().equals("red")).toArray(Stone[]::new);
    };

    //    17.
    public static Function<Integer, int[]> factors = (n) -> {

        return IntStream.range(1, n / 2).filter(i -> n % i == 0).toArray();
    };

    // 18. Create a lambda expression that removes all whitespace from a string.
    public static UnaryOperator<String> removeAllWhiteSpaces = (s) -> {
        return s.chars().filter(i -> !Character.isWhitespace(i)).mapToObj(Character::toString).collect(Collectors.joining());
    };

    // 19.
    public static Function<Integer, Boolean> isPrime = (n) -> {
        if (n == 1) return false;
        return IntStream.range(2, (int) Math.sqrt(n) + 1).filter(i -> n % i == 0).count() == 0;
    };


    // 20. Write a lambda to return the reverse of a string.
    public static UnaryOperator<String> reverseString = (s) ->
            Arrays.stream(s.split("")).reduce("", (a, b) -> b + a);


    record Student(String name) {
    }

    // 21. Create a list of student objects and use a lambda to sort by name.
    public static UnaryOperator<Student[]> sortByName = (ls) ->
            Arrays.stream(ls).sorted(Comparator.comparing(s -> s.name)).toArray(Student[]::new);

    // 22. Use lambda to filter names starting with a specific letter.
    public static BiFunction<String[], String, String[]> fil = (ls, str) ->
            Arrays.stream(ls).filter(s -> s.startsWith(str)).toArray(String[]::new);

//    Create a lambda to count how many times a character appears in a string.

//    Implement a lambda to return the longest string in a list.

//    Write a program using lambda to calculate the average of a list of doubles.


    //? is their any zero in a num except at 0 index;
    //? eg.  0972 -> false
    //! NOTE:
    //! eg. 0 - 7 = Then compiler think number is Octal representation
    //! eg. 0 - 9 = Then compiler think number is Decimal representation (But number can not start with zero
    //! eg. 0 - 0x = Then compiler think number is HaxDecimal Representation
    static Function<Integer, Boolean> isHavingZeroExceptFirst=(Integer n)->{
        do if(n%10==0) return true;
        while((n/=10)!=0);
        return false;
    };


}