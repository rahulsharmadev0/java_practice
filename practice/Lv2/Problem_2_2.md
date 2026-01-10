## Coding Challenge: University Course Analytics

### **Problem Statement**

You are designing a module for a University Registrar system. You need to model a `CourseSession` entity and implement a `CourseAnalytics` class. The system must process course data to identify enrollment trends, faculty performance, and popular subjects using the **Java Stream API**.

### **Class Specifications**

#### 1. Enum: `Department`

- Define an enum named `Department` with the constants: `COMPUTER_SCIENCE`, `MATHEMATICS`, `PHYSICS`, `LITERATURE`, `HISTORY`.

#### 2. Class: `CourseSession`

- **Data Members (Private):**
- `String courseCode` (e.g., "CS101")
- `String title`
- `Department department`
- `String instructorName`
- `int capacity`
- `int enrolledStudents`
- `double averageGrade` (The average grade achieved by students in this session, 0.0 to 4.0)

- **Constructor:**
- Initialize all fields.

- **Methods:**
- Public getters for all fields.
- `toString()` is provided in the stub.

#### 3. Class: `CourseAnalytics`

You must implement the following **5 methods**.
_Note: Do not use loops (for/while). All logic must be implemented using functional programming paradigms._

1. `List<CourseSession> findFullCourses(List<CourseSession> sessions)`

- **Goal:** Retrieve a list of courses that are completely full.
- **Criteria:** A course is full if `enrolledStudents` is equal to `capacity`.
- **Return:** A list of `CourseSession` objects.

2. `Map<Department, Long> countCoursesByDepartment(List<CourseSession> sessions)`

- **Goal:** Count how many course sessions are being offered by each department.
- **Return:** A Map where the key is the `Department` and the value is the count of sessions.

3. `Map<String, Double> getAverageGradeByInstructor(List<CourseSession> sessions)`

- **Goal:** Calculate the average grade given by each instructor across all their courses.
- **Criteria:** Group by `instructorName` and find the average of `averageGrade`.
- **Return:** A Map where the key is the instructor's name and the value is the calculated average.

4. `Map<Department, Optional<CourseSession>> getHardestCoursePerDept(List<CourseSession> sessions)`

- **Goal:** Identify the "hardest" course in each department.
- **Criteria:** The hardest course is defined as the one with the **lowest** `averageGrade`.
- **Return:** A Map where the key is the `Department` and the value is an `Optional` containing the session with the lowest grade.

5. `List<String> getPopularDepartments(List<CourseSession> sessions, int minTotalEnrollment)`

- **Goal:** Identify departments that are popular among students.
- **Criteria:** A department is popular if the sum of `enrolledStudents`across all its sessions exceeds`minTotalEnrollment`.
- **Return:** A list of department names (Strings), sorted alphabetically.

---

### **Sample Input Structure**

```java
List<CourseSession> semester = new ArrayList<>();
semester.add(new CourseSession("CS101", "Intro to Java", Department.COMPUTER_SCIENCE, "Dr. Smith", 100, 100, 3.2));
semester.add(new CourseSession("CS102", "Data Structures", Department.COMPUTER_SCIENCE, "Dr. Smith", 60, 50, 2.8));
semester.add(new CourseSession("MATH201", "Calculus I", Department.MATHEMATICS, "Prof. Jones", 80, 80, 2.5));
semester.add(new CourseSession("PHYS101", "Mechanics", Department.PHYSICS, "Dr. Brown", 50, 30, 3.0));
semester.add(new CourseSession("LIT305", "Shakespeare", Department.LITERATURE, "Prof. White", 40, 40, 3.8));

CourseAnalytics analytics = new CourseAnalytics();

// Execute methods to test logic
analytics.findFullCourses(semester);
analytics.countCoursesByDepartment(semester);
analytics.getAverageGradeByInstructor(semester);
analytics.getHardestCoursePerDept(semester);
analytics.getPopularDepartments(semester, 120);

```

---

### **Starter Code Stub**

```java
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

// 1. Enum Definition
enum Department {
    COMPUTER_SCIENCE, MATHEMATICS, PHYSICS, LITERATURE, HISTORY
}

// 2. Entity Class
class CourseSession {
    private String courseCode;
    private String title;
    private Department department;
    private String instructorName;
    private int capacity;
    private int enrolledStudents;
    private double averageGrade;

    public CourseSession(String courseCode, String title, Department department, String instructorName,
                         int capacity, int enrolledStudents, double averageGrade) {
        this.courseCode = courseCode;
        this.title = title;
        this.department = department;
        this.instructorName = instructorName;
        this.capacity = capacity;
        this.enrolledStudents = enrolledStudents;
        this.averageGrade = averageGrade;
    }

    // Getters
    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public Department getDepartment() { return department; }
    public String getInstructorName() { return instructorName; }
    public int getCapacity() { return capacity; }
    public int getEnrolledStudents() { return enrolledStudents; }
    public double getAverageGrade() { return averageGrade; }

    @Override
    public String toString() {
        return courseCode + ": " + title;
    }
}

// 3. Logic Class
class CourseAnalytics {

    public List<CourseSession> findFullCourses(List<CourseSession> sessions) {
        // TODO: Implement logic
        return null;
    }

    public Map<Department, Long> countCoursesByDepartment(List<CourseSession> sessions) {
        // TODO: Implement logic
        return null;
    }

    public Map<String, Double> getAverageGradeByInstructor(List<CourseSession> sessions) {
        // TODO: Implement logic
        return null;
    }

    public Map<Department, Optional<CourseSession>> getHardestCoursePerDept(List<CourseSession> sessions) {
        // TODO: Implement logic
        return null;
    }

    public List<String> getPopularDepartments(List<CourseSession> sessions, int minTotalEnrollment) {
        // TODO: Implement logic
        return null;
    }
}

public class Source {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
    }
}

```
