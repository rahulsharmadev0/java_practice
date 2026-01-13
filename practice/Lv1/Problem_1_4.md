# Coding Challenge: Corporate Employee Analyzer

### **Problem Statement**

You are developing a dashboard for an HR system. You need to model an `Employee` entity and create a `CorporateAnalyzer` class that uses the **Stream API** to identify top talent and analyze departmental performance.

### **Class Specifications**

#### **1. Enum: `Department**`

- Define an enum named `Department` with the constants: `ENGINEERING`, `HR`, `SALES`, `MARKETING`.

#### **2. Class: `Employee**`

- **Data Members (Private):**
- `String name`
- `Department department`
- `double salary`
- `double performanceScore` (Scale of 1.0 to 5.0)

- **Constructor:**
- `Employee(String name, Department department, double salary, double performanceScore)`: Initialize all fields.

- **Methods:**
- Public getters for all data members.
- `toString()` is provided in the stub.

#### **3. Class: `CorporateAnalyzer**`

Implement the below methods for this class using **Stream API** methods:

- **`List<Employee> identifyTopTalent(List<Employee> employees)`**
- **Logic:** Filter the list to find employees who are considered "Top Talent."
- **Criteria:** An employee is Top Talent if their `performanceScore` is **greater than or equal to 4.5** AND their `salary` is **greater than 80,000**.
- **Return:** A list of these filtered employees.

- **`Optional<Employee> getHighestPaidInDept(List<Employee> employees, Department targetDept)`**
- **Logic:** Search through the employees specifically within the `targetDept` and find the one with the maximum `salary`.
- **Return:** An `Optional` containing the highest-paid employee in that department, or an empty `Optional` if no one exists in that department.

---

### **Sample Execution**

**Input:**

```java
List<Employee> staff = new ArrayList<>();
staff.add(new Employee("Alice", Department.ENGINEERING, 90000, 4.8));
staff.add(new Employee("Bob", Department.SALES, 50000, 3.5));
staff.add(new Employee("Charlie", Department.ENGINEERING, 120000, 4.2));
staff.add(new Employee("Diana", Department.MARKETING, 85000, 4.9));
staff.add(new Employee("Eve", Department.SALES, 95000, 4.7));

CorporateAnalyzer analyzer = new CorporateAnalyzer();

// Find employees with score >= 4.5 and salary > 80k
analyzer.identifyTopTalent(staff);

// Find the highest paid person in SALES
analyzer.getHighestPaidInDept(staff, Department.SALES);

```

**Output:**

```text
[Alice, Diana, Eve]
Optional[Eve]

```

---

### **Starter Code Stub**

```java
import java.util.*;
import java.util.stream.Collectors;

enum Department {
    ENGINEERING, HR, SALES, MARKETING
}

class Employee {
    // Define private data members

    // Define public constructor

    // Define public getters

    @Override
    public String toString() {
        return name; // Simplified for output matching
    }
}

class CorporateAnalyzer {

    public List<Employee> identifyTopTalent(List<Employee> employees) {
        // TODO: Implement logic
        return null;
    }

    public Optional<Employee> getHighestPaidInDept(List<Employee> employees, Department targetDept) {
        // TODO: Implement logic
        return null;
    }
}

public class Source {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        /* You can verify your implementation using the Sample Input logic */
    }
}

```
