# Coding Challenge: Orders & Stream Transformations (flatMap, peek, combined pipelines)

### **Problem Statement**

Model `Order` and `Item` classes and implement stream operations which demonstrate `flatMap`, `peek`, `filter`, `distinct`, `sorted`, and mutation inside a stream pipeline. Each method should require composing multiple operations to solve the task.

### **Class Specifications**

1. Class: `Item`

- Private data members:
  - `String name`
  - `double price`

- Constructor: `Item(String name, double price)`
- Public getters and setters for both fields.
- `toString()` returns `name`.

2. Class: `Order`

- Private data members:
  - `String orderId`
  - `List<Item> items`

- Constructor: `Order(String orderId, List<Item> items)`
- Public getters.
- `toString()` returns `orderId`.

3. Class: `OrderProcessor`

Implement the following methods using Streams. Each method must compose at least three stream operations.

- `List<String> listAllItemNames(List<Order> orders)`
  - Logic: Use `flatMap` to flatten items, map to trimmed names, remove duplicates, sort alphabetically, and return the resulting list.

- `void applyDiscountToExpensiveItems(List<Order> orders, double threshold, double discountPercent)`
  - Logic: Use `flatMap` across orders to get items, filter items with price > `threshold`, use `peek` to apply a price reduction of `discountPercent` percent (mutating the `Item`), and then collect or traverse the stream to ensure the pipeline executes (the method mutates items in-place).

- `Optional<Item> mostExpensiveItem(List<Order> orders)`
  - Logic: Flatten all items, filter out items with non-positive price, and return the `max` by price.

---

### **Sample Execution**

```java
List<Item> items1 = List.of(new Item("Pen", 2.5), new Item("Notebook", 5.0));
List<Item> items2 = List.of(new Item("Headphones", 120.0), new Item("Mouse", 25.0));
List<Order> orders = List.of(new Order("A1", new ArrayList<>(items1)), new Order("B2", new ArrayList<>(items2)));

OrderProcessor proc = new OrderProcessor();
proc.listAllItemNames(orders);               // [Headphones, Mouse, Notebook, Pen]
proc.applyDiscountToExpensiveItems(orders, 50.0, 10.0);
// Headphones price becomes 108.0
proc.mostExpensiveItem(orders);             // Optional[Headphones]
```

---

### **Starter Code Stub**

```java
import java.util.*;
import java.util.stream.Collectors;

class Item {
    // Define private data members

    // Define public constructor, getters, and setters

    @Override
    public String toString() {
        return name;
    }
}

class Order {
    // Define private data members and constructor

    // Define public getters

    @Override
    public String toString() {
        return orderId;
    }
}

class OrderProcessor {

    public List<String> listAllItemNames(List<Order> orders) {
        // TODO: Implement logic
        return null;
    }

    public void applyDiscountToExpensiveItems(List<Order> orders, double threshold, double discountPercent) {
        // TODO: Implement logic
    }

    public Optional<Item> mostExpensiveItem(List<Order> orders) {
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
