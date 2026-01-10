package tasks;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.function.Predicate;

public class Task5 {

    static final PerformOperation performOperation = new PerformOperation();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            String line = scanner.nextLine();
            Integer linesCount = Integer.parseInt(line);

            while (linesCount-- > 0) {
                line = scanner.nextLine().trim();
                if (line.matches("^\\D.*$")) {
                    System.out.println("Invalid Input");
                    continue;
                }
                StringTokenizer tokenizer = new StringTokenizer(line);

                Integer operation = Integer.parseInt(tokenizer.nextToken());
                Integer number = Integer.parseInt(tokenizer.nextToken());

                String result = switch (operation) {
                    case 1 -> checkOdd(number);
                    case 2 -> checkPrime(number);
                    case 3 -> checkPalindrome(number);
                    default -> "Invalid Operation";
                };

                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null)
                scanner.close();
        }

    }

    static String checkOdd(int n) {
        return performOperation.isOdd.test(n) ? "ODD" : "EVEN";
    }

    static String checkPrime(int n) {
        return performOperation.isPrime.test(n) ? "PRIME" : "COMPOSITE";
    }

    static String checkPalindrome(int n) {
        return performOperation.isPalindrome.test(n) ? "PALINDROME" : "NOT_PALINDROME";
    }

}

class PerformOperation {

    public Predicate<Integer> isOdd = (x) -> x % 2 != 0;

    public Predicate<Integer> isPrime = (num) -> {
        if (num < 2)
            return false;

        int sqrt = (int) Math.sqrt(num);
        for (int i = 2; i <= sqrt; i++)
            if (num % i == 0)
                return false;
        return true;
    };

    public Predicate<Integer> isPalindrome = (num) -> {
        if (num < 0)
            throw new IllegalArgumentException("Invalid Value");
        int reverseNum = 0, n = num;
        do
            reverseNum = reverseNum * 10 + (n % 10);
        while ((n /= 10) > 0);
        return reverseNum == num;
    };

}