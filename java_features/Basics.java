import utils.IO;

import java.io.PrintStream;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Basics {
    public static void main(String[] arg) {

        int n = -2323;
        printTableAlternative(2,3);
//        do IO.printf(n%10); while ((n/=10)!=0);
//
//        IO.printf(gcdOfEachDigit(-628));
//        IO.printf(gcdOfEachDigit(-68));
//        IO.printf(gcdOfEachDigit(982));
//        IO.printf(gcdOfEachDigit(12));
//        IO.printf(ReverseLastTwoDigit(535));
//         IO.printf(isArmStrong(123)); //false
//         IO.printf(isArmStrong(121)); // false
//         IO.printf(isArmStrong(1211)); // false
//         IO.printf(isArmStrong(153)); // true
//         IO.printf(isArmStrong(1634)); //true
//         IO.printf(isArmStrong(0)); // true
//         IO.printf(isArmStrong(1)); //true
//         IO.printf(ascii('2')); // true
//         IO.printf(ascii('a')); // true
//         IO.printf(ascii('A')); // true
//         IO.printf(ascii('@')); // true
//         IO.printf(near(2.4f)); // true
//         IO.printf(near(2.5f)); // true
//         IO.printf(near(2.6f)); // true

//        IO.printf(gcd(40, 60));
//        IO.printf(gcd(30, 60));
//        IO.printf(squareRoot(3, 6));
//        IO.printf(squareRoot(25, 6));
//        IO.printf(squareRoot(36, 6));
//        IO.printf(sum(2, 3, 4, 6, 54, 556, 56));
//


//        IO.printf("-----");
//        IO.printf(greatestAmong3IfSameReturnAvg(1,2,4));
//        IO.printf(greatestAmong3IfSameReturnAvg(56,56,23));
//        IO.printf(greatestAmong3IfSameReturnAvg(10,2,2));
//        IO.printf("-----");
//        IO.printf(complex(-4217));
//        IO.printf(complex(-248));
//        IO.printf(complex(-434));
    }

    private static void myMethod() {
        System.out.println("Hello World");
    }

    private static void sum(int a, int b) {
        IO.printf(a + b);
    }

    public static int reverseNumber(int n) {
        for (int r = 0; n != 0; n /= 10)
            r = r * 10 + (n % 10);
        return 0;  // Error
    }

    public static boolean isArmStrong(int n) {
        final int cache = n;
        int len = 0;
        do len++; while ((n /= 10) != 0);
        long s = 0;
        for (s = 0, n = cache; n != 0; n /= 10)
            s += pow(n % 10, len);
        return s == cache;
    }

    public static long pow(int a, int b) {
        if (b == 0) return 1;
        return a * pow(a, b - 1);
    }

    public static int ascii(char c) {
        return (int) c;
    }

    public static int near(float f) {
        return (int) f + (f - (int) f > 0.5 ? 1 : 0);
    }


    // Q1 find square root of given number (method, prec. 3)
    static float squareRoot(int n, int precision) {
        int start = 0, end = n;
        float ans = 0;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (n == mid * mid) {
                ans = mid;
                break;
            } else if (start > mid * mid) {
                start = mid + 1;
                ans = mid;
            } else end = mid - 1;
        }
        float increment = 0.1f;
        for (int i = 0; i < precision; i++) {
            while (ans * ans <= n) ans += increment;
            ans -= increment;
            increment /= 10; // since we found value of ith position of fractional need to move further
        }
        return ans;
    }

    // Q2 WJP to find the greatest int. number among 3. but if the greatest
    // two no. are same then return the average of three?
    static double greatestAmong3IfSameReturnAvg(int a, int b, int c) {
        int[] arrays = {a, b, c};
        Arrays.sort(arrays);
        if (arrays[1] == arrays[2])
            return (double) (a * b * c) / 3;
        return arrays[2];
    }

    // Q3 WJP  to find product of each digit of given no. if it is negative then
    // return the biggest positive no. which divisible of all digit of product (except 0 and 1) else return 1;

    static int complex(int n) {
        int p = 1;
        do p *= n % 10; while ((n /= 10) != 0);
        if (p > 0) return p;
        int _gcd = gcdOfEachDigit(p);
        return _gcd < 2 ? 1 : _gcd;
    }

    static int gcdOfEachDigit(final int num) {
        int i = 0, n;
        while (++i < 9) {
            n = num;
            do if ((n % 10) % i != 0) return --i;
            while ((n /= 10) != 0);
        }
        return -1;
    }

    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    static int ReverseLastTwoDigit(int num) {
        int limit = 2, lst = num % 100;
        do limit--; while ((num /= 10) != 0 && limit > 0);
        return num * 100 + lst;
    }


    // var Argument
    static int sum(int... array) {

        // Way not AutoBoxing
//       return Stream.of(array).reduce(0, Integer::sum);


        return Arrays.stream(array).sum();
    }

    static void printTableAlternative(int a, int b) {
        printTable(a,b, true,0);
    }
    static void printTable2(int a, int i) {
        if (i > 10|| i<0) return;
            System.out.println(
                    a + " x " + i + " = " + (a * i));
    }
    static void printTable(int a, int b, boolean isA, int i) {
        if (i > 10|| i<0) return;
        if (isA) {
            System.out.println(
                    a + " x " + i + " = " + (a * i));
            printTable(a,b, false, i);
        } else {
            System.out.println(
                    b + " x " + i + " = " + (b * i));
            printTable(a,b, true, i+1);
        }
    }


}
