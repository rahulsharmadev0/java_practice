# Coding Challenge: Employee Management System

#### **Class Definitions:**

**Employee:**

**Class Variables:**

- `private String firstName`
- `private String lastName`
- `private String ssn`

**Constructor:**

- **`Employee()`:** Empty constructor to initialize the instance variable as `null`. A testcase will check for the creation of empty constructor.
- Visibility: `public`

- **`Employee(firstName, lastName, ssn)`:** Parameterized constructor to initialize the instance variables.
- Visibility: `public`

**Getter Methods:**

- **`getFirstName()`:** Return the `firstName`.
- Visibility: `public`
- Return type: `String`

- **`getLastName()`:** Return the `lastName`. _(Inferred from "three getter methods" requirement)_
- Visibility: `public`
- Return type: `String`

- **`getSsn()`:** Return the `ssn`.
- Visibility: `public`
- Return type: `String`

**Methods:**

- **`validateName(String firstName, String lastName)`:** Implement this function with three Exceptions (explained below in Task section).
- Use a `try-catch` block to implement exception handling and return the suitable Exception Messages from the catch block.
- If the `firstName` and `lastName` is valid, then assign the `firstName`, `lastName` to the appropriate Class variable and return `"Valid String"`.
- Visibility: `public`
- Return type: `String`

- **`validateSsn(String ssn)`:** Check if the first and last character of the `ssn` is a digit.
- If yes, return `"Valid String"`, else return `"Invalid String"`.
- Visibility: `public`
- Return-type: `String`

---

#### **Your Task is to:**

- Implement the **`Employee`** class according to the above specification.
- **`Employee`** class has three private variables: **`firstName`**, **`lastName`**, **`ssn`**.
- **`Employee`** Class contains three getter methods. Implement the getter methods first and then implement the validation methods. Strictly follow the above specification order.

**Validation Methods Details:**

**1. `validateName(String firstName, String lastName)`:**
The three exceptions to be checked are:

- First, if the `firstName` or `lastName` is **`null`**, throw **`NullPointerException`** with message `"Entry Missing"`.
- Second, if the `firstName` or `lastName` length is zero, throw **`StringIndexOutOfBoundsException`** with message `"Index out of bound"`.
- Third, if the `firstName` or `lastName` starts with a number, throw **`IllegalArgumentException`** with message `"First Character is Invalid"`.
- If the `firstName` and `lastName` are valid, then assign them to the appropriate `Employee` Class variables and return `"Valid String"`.

_Note:_ Use a **`try`** block to check for the three exceptions and use a **`catch`** block to return the suitable exception message. For each exception, messages are given in the specification (e.g., for `StringIndexOutOfBoundsException` the return message should be `"Index out of bound"`).

**2. `validateSsn(String ssn)`:**

- Check if the first and last character of the `ssn` is a digit (0-9).
- Return `"Valid String"` if true, else return `"Invalid String"`.

**Important:**

- To check your program you have to use the **`main()`** function (in `Source` class) given in the stub. You can make suitable function calls and use the **RUN CODE** button to check your **`main()`** function output.

**Execution time limit:** 10 seconds

---

#### **Stub Code:**

```java
import java.util.*;

class Employee {
    // Implement the class according to the specification given in the stub..
}

public class Source {
    public static void main(String[] args) {
        //Implement main() to check your program...
        //Don't remove the main() function or RUN CODE will not work..
    }
}

```
