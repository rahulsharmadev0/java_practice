## Coding Challenge: Warehouse Inventory Monitor

### **Problem Statement**

You are building an inventory monitoring system for a large e-commerce warehouse. You need to model a `Product` entity and implement a `WarehouseManager` class. The system must process inventory data to generate restocking alerts, value reports, and category analytics using the **Java Stream API**.

### **Class Specifications**

#### 1. Enum: `Category`

- Define an enum named `Category` with the constants: `ELECTRONICS`, `CLOTHING`, `GROCERY`, `FURNITURE`, `TOYS`.

#### 2. Class: `Product`

- **Data Members (Private):**
- `String sku` (Stock Keeping Unit, e.g., "ELEC-101")
- `String name`
- `Category category`
- `double price`
- `int stockQuantity`
- `double rating` (Customer rating, 0.0 to 5.0)

- **Constructor:**
- Initialize all fields.

- **Methods:**
- Public getters for all fields.
- `toString()` is provided in the stub.

#### 3. Class: `WarehouseManager`

You must implement the following **5 methods**.
_Note: Do not use loops (for/while). All logic must be implemented using functional programming paradigms._

1. `List<Product> findRestockNeeded(List<Product> products, int threshold)`

- **Goal:** Identify products that need restocking.
- **Criteria:** Return a list of products where `stockQuantity` is strictly **less than** the provided `threshold`.
- **Return:** A filtered list of `Product` objects.

2. `Map<Category, Double> calculateTotalValueByCategory(List<Product> products)`

- **Goal:** Calculate the total inventory value for each category.
- **Criteria:** Inventory value for a single product is `price * stockQuantity`. Sum these values for each category.
- **Return:** A Map where the key is the `Category` and the value is the total monetary value.

3. `Map<Category, Optional<Product>> getHighestRatedProductPerCategory(List<Product> products)`

- **Goal:** Find the highest-rated product in each category.
- **Criteria:** Group by `Category` and find the product with the maximum `rating`.
- **Return:** A Map where the key is the category and the value is an `Optional` containing the highest-rated product.

4. `Map<Boolean, List<String>> partitionProductNamesByPrice(List<Product> products, double priceLimit)`

- **Goal:** Separate product **names** into "Cheap" and "Expensive" lists.
- **Criteria:**
- `true`: Products with `price > priceLimit`.
- `false`: Products with `price <= priceLimit`.

- **Return:** A Map where the values are **Lists of Strings** (product names), not Product objects. _Hint: You'll need mapping after or during partitioning._

5. `List<String> getPremiumCategories(List<Product> products, double minAvgPrice)`

- **Goal:** Identify categories that are considered "Premium".
- **Criteria:** A category is premium if the **average price** of all products in that category is strictly **greater than** `minAvgPrice`.
- **Return:** A sorted list (alphabetical) of category names (Strings).

---

### **Sample Input Structure**

```java
List<Product> inventory = new ArrayList<>();
inventory.add(new Product("TV001", "Smart TV", Category.ELECTRONICS, 800.0, 10, 4.5));
inventory.add(new Product("LPT02", "Laptop", Category.ELECTRONICS, 1200.0, 5, 4.8));
inventory.add(new Product("SOFA1", "Leather Sofa", Category.FURNITURE, 1500.0, 2, 4.2));
inventory.add(new Product("APL01", "Apple", Category.GROCERY, 1.5, 500, 4.9));
inventory.add(new Product("TSH01", "T-Shirt", Category.CLOTHING, 20.0, 100, 3.8));

WarehouseManager manager = new WarehouseManager();

// Execute methods to test logic
manager.findRestockNeeded(inventory, 10);
manager.calculateTotalValueByCategory(inventory);
manager.getHighestRatedProductPerCategory(inventory);
manager.partitionProductNamesByPrice(inventory, 100.0);
manager.getPremiumCategories(inventory, 500.0);

```

---

### **Starter Code Stub**

```java
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

// 1. Enum Definition
enum Category {
    ELECTRONICS, CLOTHING, GROCERY, FURNITURE, TOYS
}

// 2. Entity Class
class Product {
    private String sku;
    private String name;
    private Category category;
    private double price;
    private int stockQuantity;
    private double rating;

    public Product(String sku, String name, Category category, double price, int stockQuantity, double rating) {
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.rating = rating;
    }

    // Getters
    public String getSku() { return sku; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public double getRating() { return rating; }

    @Override
    public String toString() {
        return name + " (" + sku + ")";
    }
}

// 3. Logic Class
class WarehouseManager {

    public List<Product> findRestockNeeded(List<Product> products, int threshold) {
        // TODO: Implement logic
        return null;
    }

    public Map<Category, Double> calculateTotalValueByCategory(List<Product> products) {
        // TODO: Implement logic
        return null;
    }

    public Map<Category, Optional<Product>> getHighestRatedProductPerCategory(List<Product> products) {
        // TODO: Implement logic
        return null;
    }

    public Map<Boolean, List<String>> partitionProductNamesByPrice(List<Product> products, double priceLimit) {
        // TODO: Implement logic
        return null;
    }

    public List<String> getPremiumCategories(List<Product> products, double minAvgPrice) {
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
