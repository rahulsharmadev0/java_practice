package collections_arrays;

import java.util.Comparator;
import java.util.List;
import java.util.function.*;;

public class CompratorDemo {

    public static void main(String[] args) {

        var sort = Comparator.comparingDouble(Employee::salary).reversed().thenComparing(Employee::name);

        Function<String, Comparator<Person>> dynamicComparator = (String type) -> switch (type) {
            case "name" -> Comparator.comparing(Person::name);
            case "age" -> Comparator.comparingInt(Person::age);
            case "salary" -> Comparator.comparingDouble(Person::salary).reversed();
            default -> null;
        };

        var nullLast = Comparator.nullsLast(Comparator.comparing(Book::title, Comparator.nullsLast(String::compareTo)));

        Comparator<Employee> complexComparator = Comparator.comparing(
                Employee::department,
                Comparator.nullsLast(String::compareTo) // null departments last
        ).thenComparing(
                Comparator.comparing(
                    Employee::salary, 
                    Comparator.nullsLast(Comparator.reverseOrder())))
          .thenComparing(
                Comparator.comparing(
                    Employee::age,
                    Comparator.nullsLast(Integer::compareTo) // null ages last
                    ))
          .thenComparing(
                Comparator.comparing(
                    Employee::name,
                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER) // null names last
                    ));

    }

}

record Employee(String name, int age, double salary, String department) {
}

record Book(String title) {
}

record Person(String name, int age, Double salary) {
}