## 🔹 1. **Lambda Expressions**

1. Write a lambda expression to compare two integers and return the larger one.
2. Create a lambda expression to calculate the square of a number.
3. Write a program using lambda to sort a list of strings by their length.
4. Create a functional interface that checks if a number is prime using lambda.
5. Write a lambda expression to count the number of vowels in a string.
6. Use a lambda to filter out all empty strings from a list.
7. Implement a lambda to check if a string is a palindrome.
8. Create a list of employees and use lambda to filter those with salary > 50,000.

//! TODO ⇩
9. Use lambda to find the sum of squares of even numbers in a list.
10. Use a lambda expression to convert a list of strings to uppercase.
11. Create a lambda that takes two numbers and returns true if one is divisible by the other.
12. Use a lambda to replace all null values in a list with a default string.
13. Write a lambda that returns the factorial of a number.
14. Create a lambda expression that removes all whitespace from a string.
15. Write a lambda to return the reverse of a string.
16. Create a list of student objects and use a lambda to sort by name.
17. Use lambda to filter names starting with a specific letter.
18. Create a lambda to count how many times a character appears in a string.
19. Implement a lambda to return the longest string in a list.
20. Write a program using lambda to calculate the average of a list of doubles.

---

## 🔹 2. **Method References**

1. Replace a lambda with a static method reference to calculate square.
2. Convert a lambda that prints strings to a method reference.
3. Use an instance method reference on a particular object to format strings.
4. Create a constructor reference to create new Employee objects.
5. Convert a lambda that parses strings to integers to a method reference.
6. Replace a lambda with a method reference to sort strings ignoring case.
7. Use method reference with `map()` to convert to uppercase.
8. Demonstrate usage of all 4 types of method references with examples.
9. Use a constructor reference to generate a list of objects from names.
10. Create a utility class with a static method and use it as a reference in stream.
11. Use method reference with `forEach()` to print elements.
12. Replace `Function<T, R>` lambda with a method reference.
13. Use a method reference for an instance method in a comparator.
14. Write a class with a method and use its reference in `Stream.map`.
15. Replace lambda that calculates string length with a method reference.
16. Convert `List<String>` to `List<Integer>` using method reference.
17. Demonstrate constructor reference using `Supplier`.
18. Use `BiFunction` with method reference to call an instance method.
19. Create method references for overloaded methods and explain ambiguity.
20. Use method reference to call `String::toLowerCase` on a list.

---

## 🔹 3. **Functional Interfaces**

1. Create a custom functional interface and implement it using lambda.
2. Implement `Function<T, R>` to double an integer.
3. Use `Consumer<T>` to print each item of a list.
4. Implement `Supplier<T>` to return a random number.
5. Create a `Predicate<String>` to check if the string contains a digit.
6. Use `BiFunction<String, Integer, String>` to repeat a string N times.
7. Use `BiConsumer<String, Integer>` to format and print messages.
8. Write a `UnaryOperator<String>` to remove punctuation from a string.
9. Create a `BinaryOperator<Integer>` to find the GCD of two numbers.
10. Demonstrate how to use `@FunctionalInterface` annotation.
11. Combine two predicates using `Predicate.and()`.
12. Chain `Function` calls to convert string to integer, then square it.
13. Write a function to extract domain from an email address.
14. Implement a functional interface using an anonymous inner class.
15. Create a generic functional interface for comparing two elements.
16. Demonstrate how `default` and `static` methods coexist in a functional interface.
17. Create a list of `Function<String, Integer>` and apply all on a string.
18. Write a utility method that takes `Predicate<T>` as a parameter.
19. Implement chaining of `UnaryOperator<String>` for data cleansing.
20. Compare the execution of lambda vs method reference with a custom interface.

---

## 🔹 4. **Stream API**

1. Use stream to find the longest word in a list.
2. Use `filter()` and `collect()` to get all even numbers from a list.
3. Convert a list of strings to a list of their lengths.
4. Flatten a list of list of integers using `flatMap()`.
5. Sort a list of integers in descending order using streams.
6. Find the average of numbers using `reduce()`.
7. Use `peek()` to debug a pipeline of transformations.
8. Remove duplicates and collect in a `Set`.
9. Use `skip()` and `limit()` to implement pagination.
10. Count how many names start with 'A' using `filter()` and `count()`.
11. Check if any word in a list is a palindrome using `anyMatch()`.
12. Use `collect(Collectors.joining())` to concatenate all strings.
13. Implement a stream pipeline that chains at least 5 methods.
14. Use `findFirst()` and `orElse()` to return a default value.
15. Demonstrate lazy evaluation with an infinite stream.
16. Write a program to group names by their length using collectors.
17. Write a function that takes a list and returns its sorted square values.
18. Remove null values from a list using streams.
19. Convert a stream of strings into a comma-separated string.
20. Create a list of employees and group them by department using streams.

---

## 🔹 5. **Optional Class**

1. Create an `Optional<String>` and print value if present.
2. Write code that avoids NPE using Optional chaining.
3. Use `Optional.ofNullable()` with a null value.
4. Demonstrate use of `orElse()` with a default string.
5. Use `map()` with Optional to transform string to upper case.
6. Create nested Optionals and use `flatMap()` to access inner value.
7. Check if a value is present using `isPresent()`.
8. Use `ifPresent()` to conditionally execute a block.
9. Use `orElseThrow()` to throw a custom exception.
10. Write a method that returns `Optional<Integer>` if list is not empty.
11. Combine multiple Optionals using flatMap.
12. Chain multiple transformations and return result using Optional.
13. Demonstrate difference between `orElse()` and `orElseGet()`.
14. Use `filter()` to keep optional value that starts with "A".
15. Demonstrate bad use of Optional in a field and explain why.
16. Use `Optional` to retrieve city from optional user and address.
17. Simulate legacy code handling nulls vs Optional.
18. Use Optional with a collection: check first element presence.
19. Write method that returns `Optional<Double>` if divisor is not zero.
20. Rewrite a method with manual null checks using Optional instead.