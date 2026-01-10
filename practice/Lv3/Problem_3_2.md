## Coding Challenge: Global Logistics & Inventory Reconciliation

### Problem Statement

You are managing a global supply chain where inventory is scattered across multiple warehouses. Orders are pouring in from different regions. You need to reconcile inventory levels, calculate regional demand, and identify product shortages.

This problem forces you to handle **Map Merging**, **Cross-referencing datasets** within a stream, and performing **multi-dimensional aggregations**.

---

### 🧠 Analytics Tasks

#### 1. `Map<String, Integer> mergeWarehouseInventories(List<Warehouse> warehouses)`

- **Goal**: Create a single "Global View" of inventory.
- **Input**: A list of Warehouses, where each Warehouse has a `Map<String, Integer>` (Product ID -> Quantity).
- **Requirement**: Merge all maps into one. If a product exists in multiple warehouses, sum the quantities. _Hint: `Collectors.toMap` with a merge function or `Stream.of` entries._

#### 2. `Map<String, Double> calculateFillRate(List<Order> orders, Map<String, Integer> globalInventory)`

- **Goal**: Calculate the "Fill Rate" percentage for each Product ID requested in the orders.
- **Logic**:
  - `Total Demanded` = Sum of quantity in all orders for a product.
  - `Fill Rate` = `Min(Total Demanded, Available in Inventory) / Total Demanded`.
  - If Total Demanded is 0, Fill Rate is 1.0 (or 0.0, your choice, but handle it).
- **Return**: A Map of Product ID -> Fill Rate.

#### 3. `Map<Region, Map<Category, Double>> generateRevenueHeatmap(List<Order> orders)`

- **Goal**: Analyze revenue distribution.
- **Structure**:
  - Outer Key: `Region`
  - Inner Key: `Category`
  - Value: Total Revenue (Price \* Quantity)
- **Requirement**: Use `groupingBy` with a `groupingBy` downstream, finally using `summingDouble`.

#### 4. `List<ShipmentGroup> bundleOrders(List<Order> orders, int maxBundleSize)`

- **Goal**: Bundle small orders into shipment groups to save costs.
- **Logic**:
  - Group orders by `Destination`.
  - Within each destination, split orders into chunks of `maxBundleSize`.
  - _Note: This is hard to do cleanly in pure streams without custom index logic. Try to use `Collectors.collectingAndThen` with a custom list partitioner or integer division on list indices._

---

### 🏗️ Starter Code Stub

```java
import java.util.*;
import java.util.stream.*;

// 1. Data Models
enum Region {
    NA, EMEA, APAC, LATAM
}

enum Category {
    ELECTRONICS, CLOTHING, HOME, BOOKS
}

class Warehouse {
    String id;
    Map<String, Integer> inventory; // ProductID -> Count

    public Warehouse(String id, Map<String, Integer> inventory) {
        this.id = id;
        this.inventory = inventory;
    }
    public Map<String, Integer> getInventory() { return inventory; }
}

class Order {
    String orderId;
    String productId;
    Category category;
    Region destination;
    int quantity;
    double unitPrice;

    // Constructor + Getters
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public Region getDestination() { return destination; }
    public Category getCategory() { return category; }
    public double getUnitPrice() { return unitPrice; }
}

class ShipmentGroup {
    Region destination;
    List<Order> orders;

    public ShipmentGroup(Region destination, List<Order> orders) {
        this.destination = destination;
        this.orders = orders;
    }
}

// 2. Logic Class
class LogisticsEngine {

    public Map<String, Integer> mergeWarehouseInventories(List<Warehouse> warehouses) {
        // TODO: Merge maps using stream reduction or collection
        return null;
    }

    public Map<String, Double> calculateFillRate(List<Order> orders, Map<String, Integer> globalInventory) {
        // TODO: Aggregate demand, compare with inventory
        return null;
    }

    public Map<Region, Map<Category, Double>> generateRevenueHeatmap(List<Order> orders) {
        // TODO: Nested grouping
        return null;
    }

    public List<ShipmentGroup> bundleOrders(List<Order> orders, int maxBundleSize) {
        // TODO: Advanced partitioning
        return null;
    }
}

public class Source {
    public static void main(String[] args) {
        // Validate logic
    }
}
```
