## Coding Challenge: Multi-Vendor Marketplace Intelligence System

### **Problem Statement**

You are the Lead Engineer for a global multi-vendor E-commerce platform. The system processes thousands of product listings across various vendors, regions, and categories. Your objective is to design a high-performance analytics engine that consumes a `Stream` of `Product` objects to extract deep business insights.

This challenge requires you to handle complex data transformations, multi-level aggregations, and high-order functional programming. You must manage a hybrid of **Collections** and the **Stream API** to solve architectural data problems without using any iterative loops (`for`, `while`, `do-while`) or manual state management variables outside the stream context.

---

### **Goal of the Methods**

#### 1. `List<Product> getGlobalBestsellers(List<Product> products, double minRating, int minStock)`

- **Goal:** Filter the global catalog for "Elite" products.
- **Criteria:** Include only products with a `rating` strictly above `minRating` and a `stockQuantity` higher than `minStock`. The final list must be sorted by `rating` in descending order.

#### 2. `Map<Category, DoubleSummaryStatistics> getPriceAnalyticsByCategory(List<Product> products)`

- **Goal:** Generate a comprehensive financial snapshot of each category.
- **Criteria:** Group products by `Category` and produce a statistical summary (min, max, average, count, and sum) of the `price` field.
- **Return:** A map where each category points to its statistical breakdown.

#### 3. `Map<String, List<String>> getVendorProductManifest(List<Product> products)`

- **Goal:** Create a directory of product names associated with each vendor.
- **Criteria:** Group by `vendorName`. The values should not be the `Product` objects themselves, but a `List` of the product `name` strings, where each name is converted to uppercase.

#### 4. `Map<Boolean, Long> getMarketShareByPricePoint(List<Product> products, double luxuryThreshold)`

- **Goal:** Analyze the distribution of "Luxury" vs. "Economy" items in the marketplace.
- **Criteria:** Partition the data based on whether the `price` is greater than or equal to the `luxuryThreshold`.
- **Return:** A map where `true` maps to the **count** of luxury items and `false` maps to the **count** of economy items.

#### 5. `Optional<String> getTopPerformingVendor(List<Product> products)`

- **Goal:** Identify the "MVP" (Most Valuable Vendor) based on total potential inventory value.
- **Criteria:**
  - 1. Calculate the total value for every vendor (`price * stockQuantity` summed across all their products).
  - 2. Identify the vendor with the highest total value.
- **Return:** An `Optional` containing the name of the vendor.

---

---

### **Starter Code Stub**

```java
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

// 1. Enum Definition
enum Category {
    ELECTRONICS, FASHION, HOME_DECOR, BEAUTY, AUTOMOTIVE, SPORTS
}

// 2. Entity Class
class Product {
    private String productID;
    private String name;
    private Category category;
    private String vendorName;
    private double price;
    private int stockQuantity;
    private double rating;

    public Product(String productID, String name, Category category, String vendorName,
                   double price, int stockQuantity, double rating) {
        this.productID = productID;
        this.name = name;
        this.category = category;
        this.vendorName = vendorName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.rating = rating;
    }

    // Getters
    public String getProductID() { return productID; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
    public String getVendorName() { return vendorName; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public double getRating() { return rating; }

    @Override
    public String toString() {
        return name + " [" + productID + "]";
    }
}

// 3. Logic Class
class MarketplaceAnalytics {

    public List<Product> getGlobalBestsellers(List<Product> products, double minRating, int minStock) {
        // TODO: Implement Logic
        return null;
    }

    public Map<Category, DoubleSummaryStatistics> getPriceAnalyticsByCategory(List<Product> products) {
        // TODO: Implement Logic
        return null;
    }

    public Map<String, List<String>> getVendorProductManifest(List<Product> products) {
        // TODO: Implement Logic
        return null;
    }

    public Map<Boolean, Long> getMarketShareByPricePoint(List<Product> products, double luxuryThreshold) {
        // TODO: Implement Logic
        return null;
    }

    public Optional<String> getTopPerformingVendor(List<Product> products) {
        // TODO: Implement Logic
        return null;
    }
}

public class Source {
    public static void main(String[] args) {
        /* Verify your implementation with complex mock data sets */
        MarketplaceAnalytics analytics = new MarketplaceAnalytics();
    }
}

```
