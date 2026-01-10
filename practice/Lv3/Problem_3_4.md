## Coding Challenge: Real-Time Fraud Detection Engine

### Problem Statement

You are building a fraud detection system for a payment gateway. You have a window of transaction data (e.g., the last 10 minutes). Your goal is to identify high-risk patterns using the **Stream API**.

This challenge checks your ability to handle **Sorted Stream aggregations**, **pairwise comparisons** (often tricky in streams), and **complex partitioning**.

---

### 🧠 Analytics Tasks

#### 1. `List<String> findHighFrequencyUsers(List<Transaction> transactions, int threshold)`

- **Goal**: Identify users who have made more than `threshold` transactions.
- **Simple**: Group count and filter.

#### 2. `Map<String, List<Transaction>> detectImpossibleTravel(List<Transaction> transactions)`

- **Goal**: Identify users who have transactions in different locations that are physically impossible to traverse in the time difference.
- **Logic**:
  - Group by User.
  - Sort transactions by timestamp.
  - Compare adjacent transactions ($T_i$ and $T_{i+1}$).
  - If `Distance(Loc_i, Loc_{i+1}) / TimeDiff > MaxSpeed`, flag both.
- **Constraint**: This requires maintaining state or accessing "previous" elements. Use a custom `Collector` or `collectingAndThen` with a sequential scan inside.

#### 3. `Map<RiskLevel, Double> calculateTotalExposureByRisk(List<Transaction> transactions)`

- **Goal**: Classify transactions into Risk Levels (LOW, MEDIUM, HIGH) based on rules, then sum their amounts.
- **Rules**:
  - Amount > 10,000 -> HIGH
  - Amount > 5,000 -> MEDIUM
  - Else -> LOW
  - _Note_: If a transaction comes from a "High Risk Country" (use a helper set), upgrade risk one level (LOW->MED, MED->HIGH).

#### 4. `Set<String> findConstructedCycle(List<Transaction> transactions)`

- **Goal**: Detect "Structuring" (many small transactions summing to a large amount).
- **Logic**: Find users whose _total_ amount is > 50,000 BUT their _average_ transaction is < 2,000.
- **Requirement**: Use `Collectors.teeing` (if using Java 12+) or a custom collector to compute Sum and Count in one pass per user.

---

### 🏗️ Starter Code Stub

```java
import java.util.*;
import java.util.stream.*;

// 1. Data Models
class Transaction {
    String id;
    String userId;
    double amount;
    long timestamp; // Epoch millis
    String countryCode;

    // Constructor + Getters
    public String getUserId() { return userId; }
    public double getAmount() { return amount; }
    public long getTimestamp() { return timestamp; }
    public String getCountryCode() { return countryCode; }
}

enum RiskLevel { LOW, MEDIUM, HIGH }

// 2. Logic Class
class FraudEngine {

    // Helper for Distance (Euclidean or simple grid assumed for this problem)
    // Assume we have a method getDistance(loc1, loc2) - for this problem, use a mock "Locations" map or similar

    public List<String> findHighFrequencyUsers(List<Transaction> transactions, int threshold) {
        // TODO: Grouping and filtering
        return null;
    }

    public Map<String, List<Transaction>> detectImpossibleTravel(List<Transaction> transactions) {
        // TODO: The "Pairwise" challenge
        return null;
    }

    public Map<RiskLevel, Double> calculateTotalExposureByRisk(List<Transaction> transactions) {
        // TODO: Complex classification + Summing
        return null;
    }

    public Set<String> findConstructedCycle(List<Transaction> transactions) {
        // TODO: Teeing collector for Sum AND Count
        return null;
    }
}

public class Source {
    public static void main(String[] args) {
        // Test with mock data
    }
}
```
