package Lv2;

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
    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return name + " (" + sku + ")";
    }
}

// 3. Logic Class
class WarehouseManager {

    public List<Product> findRestockNeeded(List<Product> products, int threshold) {
        return products.stream().filter(
                product -> product.getStockQuantity() < threshold).toList();
    }

    public Map<Category, Double> calculateTotalValueByCategory(List<Product> products) {
        return products.stream().collect(
                Collectors.groupingBy(Product::getCategory,
                        Collectors.summingDouble(product -> product.getPrice() * product.getStockQuantity())));
    }

    public Map<Category, Optional<Product>> getHighestRatedProductPerCategory(List<Product> products) {
        return products.stream().collect(
                Collectors.groupingBy(Product::getCategory,
                        Collectors.maxBy(Comparator.comparing(Product::getRating))));
    }

    public Map<Boolean, List<String>> partitionProductNamesByPrice(List<Product> products, double priceLimit) {
        return products.stream()
                .collect(Collectors.partitioningBy(
                        product -> product.getPrice() > priceLimit,
                        Collectors.mapping(Product::getName, Collectors.toList())));
    }

    public List<String> getPremiumCategories(List<Product> products, double minAvgPrice) {
        return products.stream().collect(
                Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.averagingDouble(Product::getPrice)))
                .entrySet().stream().filter(entity -> entity.getValue() > minAvgPrice).map(
                        entity -> entity.getKey().name())
                .toList();
    }
}

class Source {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
    }
}

public class Solution_2_3 {
    public static void main(String[] args) {
        Source.main(args);
    }

}
