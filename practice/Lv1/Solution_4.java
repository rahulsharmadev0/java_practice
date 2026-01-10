package Lv1;

import java.util.*;
import java.util.stream.Collectors;

public class Solution_4 {
    public static void main(String[] args) {
        Source.main(args);
    }
}

enum Department {
    ENGINEERING, HR, SALES, MARKETING
}

class Employee {

    private String name;
    private Department department;
    private double salary;
    private double performanceScore;

    public Employee(String name, Department department, double salary, double performanceScore) {
        if (performanceScore < 0.0 || performanceScore > 5.0)
            throw new IllegalArgumentException("Perfomance Scale should be in range of  1.0 to 5.0");
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceScore = performanceScore;
    }

    // Define public constructor

    String getName() {
        return name;
    }

    Department getDepartment() {
        return department;
    }

    double getSalary() {
        return salary;
    }

    double getPerformanceScore() {
        return performanceScore;
    }

    @Override
    public String toString() {
        return name;
    }
}

class CorporateAnalyzer {

    public List<Employee> identifyTopTalent(List<Employee> employees) {
        // Use Stream API to filter by score >= 4.5 and salary > 80000
        return employees.stream().filter(emp -> emp.getPerformanceScore() >= 4.5 && emp.getSalary() > 80000).toList();
    }

    public Optional<Employee> getHighestPaidInDept(List<Employee> employees, Department targetDept) {
        // Use Stream API to find max salary within a specific department
        return employees.stream().filter(emp -> emp.getDepartment() == targetDept)
                .max(Comparator.comparingDouble(Employee::getSalary));
    }
}

class Source {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        /* You can verify your implementation using the Sample Input logic */

        List<Employee> staff = new ArrayList<>();
        staff.add(new Employee("Alice", Department.ENGINEERING, 90000, 4.8));
        staff.add(new Employee("Bob", Department.SALES, 50000, 3.5));
        staff.add(new Employee("Charlie", Department.ENGINEERING, 120000, 4.2));
        staff.add(new Employee("Diana", Department.MARKETING, 85000, 4.9));
        staff.add(new Employee("Eve", Department.SALES, 95000, 4.7));

        CorporateAnalyzer analyzer = new CorporateAnalyzer();

        // Find employees with score >= 4.5 and salary > 80k
        System.out.println(analyzer.identifyTopTalent(staff));

        // Find the highest paid person in SALES
        System.out.println(analyzer.getHighestPaidInDept(staff, Department.SALES));
    }
}