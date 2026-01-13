# Coding Challenge: Product Price Insights (sorted, min, max, distinct — composite)

### **Problem Statement**

Create a `Product` model and implement analytics that exercise comparison-based stream operations by combining them with filters and mappings (e.g., `sorted` + `filter` + `limit`, `min` + mapping, `max` after filtering). The candidate should chain multiple operations to get correct results.

### **Class Specifications**

1. Class: `Product`

- Private data members:
  - `String name`
  - `String category`
  - `double price`
  - `double rating`

- Constructor: `Product(String name, String category, double price, double rating)`
- Public getters.
- `toString()` returns `name`.

2. Class: `ProductAnalytics`

Implement the following methods using Streams. Each method should combine at least two stream operations.

- `List<Product> topNByPrice(List<Product> products, int n)`
  - Logic: Filter out products with non-positive price, then sort by price descending and return the top `n` products.

- `Optional<Product> cheapestProduct(List<Product> products)`
  - Logic: Return the product with minimum price (use `min`), ignoring products with non-positive price.

- `Optional<Product> mostExpensiveInCategory(List<Product> products, String category)`
  - Logic: Filter products to the provided category (case-insensitive), require rating >= 4.0, then return the `max` by price.

- `List<String> distinctCategories(List<Product> products)`
  - Logic: Map products to their category, preserve first-seen order, remove duplicates using `distinct()`, collect to a `List`.

---

### **Sample Execution**

```java
List<Product> items = new ArrayList<>();
items.add(new Product("Laptop", "Electronics", 1200.0, 4.6));
items.add(new Product("Phone", "Electronics", 800.0, 4.5));
items.add(new Product("Desk", "Furniture", 200.0, 4.0));
items.add(new Product("Chair", "Furniture", 150.0, 3.9));

ProductAnalytics analytics = new ProductAnalytics();

analytics.topNByPrice(items, 2);               // [Laptop, Phone]
analytics.cheapestProduct(items);              // Optional[Chair]
analytics.mostExpensiveInCategory(items, "Furniture"); // Optional[Desk]
analytics.distinctCategories(items);           // [Electronics, Furniture]
```

---

### **Starter Code Stub**

```java
import java.util.*;
import java.util.stream.Collectors;

class Product {
    // Define private data members

    // Define public constructor and getters

    @Override
    public String toString() {
        return name;
    }
}

class ProductAnalytics {

    public List<Product> topNByPrice(List<Product> products, int n) {
        // TODO: Implement logic
        return null;
    }

    public Optional<Product> cheapestProduct(List<Product> products) {
        // TODO: Implement logic
        return null;
    }

    public Optional<Product> mostExpensiveInCategory(List<Product> products, String category) {
        // TODO: Implement logic
        return null;
    }

    public List<String> distinctCategories(List<Product> products) {
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
