import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.*;
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

        IO.printf(filterGreaterThan.apply(new int[]{1000,111011, 20000, 50000, 50001}));

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
    static Function<int[],int[]> filterGreaterThan =  (ls)->
            Arrays.stream(ls).filter(s-> s>50000).toArray();

}