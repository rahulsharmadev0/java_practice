# Coding Challenge: Library Collector & Joiner (Intermediate)

### **Problem Statement**

Build utilities for a small library system that demonstrate composing multiple collector and stream operations in a single method (e.g., `joining` combined with `map`, `filter`, `distinct`, `sorted`, and `toCollection`). The goal is to require candidates to chain multiple stream operations to produce the final result.

### **Class Specifications**

1. Class: `Book`

- Private data members:
  - `String title`
  - `String author`
  - `int year`
  - `List<String> tags`

- Constructor: `Book(String title, String author, int year, List<String> tags)`
- Public getters for all fields.
- `toString()` returns `title`.

2. Class: `LibraryUtils`

Implement the following methods. Each method should combine multiple stream operations (filter, map, distinct, sorted, flatMap, and appropriate collectors) to produce the final result.

- `String joinTitlesSince(List<Book> books, int sinceYear)`
  - Logic: Consider only books with `year >= sinceYear`. Extract titles, trim whitespace, remove duplicates, sort alphabetically (case-insensitive), then join using `Collectors.joining(", ")`.

- `Set<String> authorsWithMultipleBooks(List<Book> books)`
  - Logic: Identify authors who have authored more than one book in the list. Use `Collectors.groupingBy` and then collect matching author names into a `Set` using `Collectors.toSet()`.

- `Collection<String> collectNormalizedTags(List<Book> books)`
  - Logic: Flatten all tag lists, normalize tags to lowercase, remove tags shorter than 3 characters, collect into a sorted, unique `TreeSet` using `Collectors.toCollection(TreeSet::new)`.

---

### **Sample Execution**

```java
List<Book> shelf = new ArrayList<>();
shelf.add(new Book(" 1984 ", "Orwell", 1949, List.of("dystopia","classic")));
shelf.add(new Book("Animal Farm", "Orwell", 1945, List.of("satire","classic")));
shelf.add(new Book("Clean Code", "Martin", 2008, List.of("programming","best-practices","pr")));

LibraryUtils utils = new LibraryUtils();

utils.joinTitlesSince(shelf, 1950);            // "Clean Code"
utils.authorsWithMultipleBooks(shelf);         // [Orwell]
utils.collectNormalizedTags(shelf);            // [best-practices, classic, dystopia, programming, satire]
```

---

### **Starter Code Stub**

```java
import java.util.*;
import java.util.stream.Collectors;

class Book {
    // Define private data members

    // Define public constructor

    // Define public getters

    @Override
    public String toString() {
        return title; // Simplified for output matching
    }
}

class LibraryUtils {

    public String joinTitlesSince(List<Book> books, int sinceYear) {
        // TODO: Implement logic
        return null;
    }

    public Set<String> authorsWithMultipleBooks(List<Book> books) {
        // TODO: Implement logic
        return null;
    }

    public Collection<String> collectNormalizedTags(List<Book> books) {
        // TODO: Implement logic
        return null;
    }
}

public class Source {
    public static void main(String[] args) {
        /* Example usage */
    }
}
```
