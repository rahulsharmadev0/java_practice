package Lv2;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

public class Solution_2_2 {

    public static void main(String[] args) {
        Source.main(args);
    }

}

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
    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public Department getDepartment() {
        return department;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    @Override
    public String toString() {
        return courseCode + ": " + title;
    }
}

// 3. Logic Class
class CourseAnalytics {

    public List<CourseSession> findFullCourses(List<CourseSession> sessions) {
        return sessions.stream().filter(
                session -> session.getCapacity() == session.getEnrolledStudents()).toList();
    }

    public Map<Department, Long> countCoursesByDepartment(List<CourseSession> sessions) {
        return sessions.stream().collect(
                Collectors.groupingBy(CourseSession::getDepartment,
                        Collectors.counting()));
    }

    public Map<String, Double> getAverageGradeByInstructor(List<CourseSession> sessions) {
        return sessions.stream().collect(
                Collectors.groupingBy(
                        CourseSession::getInstructorName,
                        Collectors.averagingDouble(CourseSession::getAverageGrade)));
    }

    public Map<Department, Optional<CourseSession>> getHardestCoursePerDept(List<CourseSession> sessions) {
        return sessions.stream().collect(
                Collectors.groupingBy(
                        CourseSession::getDepartment,
                        Collectors.minBy(Comparator.comparing(CourseSession::getAverageGrade))));
    }

    public List<String> getPopularDepartments(List<CourseSession> sessions, int minTotalEnrollment) {
        return sessions.stream().collect(
                Collectors.groupingBy(CourseSession::getDepartment,
                        Collectors.summingDouble(CourseSession::getEnrolledStudents)))
                .entrySet().stream().filter(entity -> entity.getValue() > minTotalEnrollment)
                .map(entity -> entity.getKey().name()).toList();
    }
}

class Source {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */

        List<CourseSession> semester = new ArrayList<>();
        semester.add(
                new CourseSession("CS101", "Intro to Java", Department.COMPUTER_SCIENCE, "Dr. Smith", 100, 100, 3.2));
        semester.add(
                new CourseSession("CS102", "Data Structures", Department.COMPUTER_SCIENCE, "Dr. Smith", 60, 50, 2.8));
        semester.add(new CourseSession("MATH201", "Calculus I", Department.MATHEMATICS, "Prof. Jones", 80, 80, 2.5));
        semester.add(new CourseSession("PHYS101", "Mechanics", Department.PHYSICS, "Dr. Brown", 50, 30, 3.0));
        semester.add(new CourseSession("LIT305", "Shakespeare", Department.LITERATURE, "Prof. White", 40, 40, 3.8));

        CourseAnalytics analytics = new CourseAnalytics();

        // Execute methods to test logic
        System.out.println(analytics.findFullCourses(semester));
        System.out.println(analytics.countCoursesByDepartment(semester));
        System.out.println(analytics.getAverageGradeByInstructor(semester));
        System.out.println(analytics.getHardestCoursePerDept(semester));
        System.out.println(analytics.getPopularDepartments(semester, 120));
    }
}
