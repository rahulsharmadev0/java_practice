package Lv1;

import java.util.regex.Pattern;

public class Solution_1_0 {

    public static void main(String[] args) {
        Employee employee = new Employee("Rahul", "Sharma", "324412");

        System.out.println(employee.validateSsn("243sdf2412"));
        System.out.println(employee.validateSsn(""));
        System.out.println(employee.validateSsn("sd23f3"));
        System.out.println(employee.validateSsn("3sd"));

    }

}

class Employee {

    private String firstName;
    private String lastName;
    private String ssn;

    public Employee() {
        this.firstName = null;
        this.lastName = null;
        this.ssn = null;
    }

    public Employee(String firstName, String lastName, String ssn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String validateSsn(String ssn) {
        return ssn != null && Pattern.matches("\\d.*\\d", ssn) ? "Valid String" : "Invalid String";
    }

    public String validateName(String firstName, String lastName) {
        try {
            if (firstName == null || lastName == null)
                throw new NullPointerException("Entry Missing");
            if (firstName.isEmpty() || lastName.isEmpty())
                throw new StringIndexOutOfBoundsException("Index out of bound");
            if (Character.isDigit(firstName.charAt(0)) || Character.isDigit(lastName.charAt(0)))
                throw new IllegalArgumentException("First Character is Invalid");
            this.firstName = firstName;
            this.lastName = lastName;
            return "Valid String";
        } catch (NullPointerException e) {
            return e.getMessage();
        } catch (StringIndexOutOfBoundsException e) {
            return e.getMessage();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }

}
