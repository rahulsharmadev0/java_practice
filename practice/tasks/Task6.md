# Coding Challenge: `MarketAnalyzer**`

#### **1. `Map<Exchange, Optional<TickerSummary>> getMostActiveTickerByExchange(List<Trade> trades)**`

- **Goal:** Find the single "Most Active" ticker (highest total volume) for every exchange.
- **Logic:**

1. Group trades by `Exchange`.
2. Downstream, group by `ticker` to sum volumes.
3. Find the ticker with the maximum total volume.

- **Stream Challenge:** This requires nested grouping and a `collectingAndThen` or `maxBy` operation to reduce the inner groups into a single result.

#### **2. `Map<Boolean, List<Trade>> partitionSmallTrades(List<Trade> trades, int volumeThreshold)**`

- **Goal:** Split the trades into two lists: "Small Trades" (volume < threshold) and "Large Trades" (volume >= threshold).
- **Logic:**
- Use `Collectors.partitioningBy`.

- **Stream Challenge:** Understanding the difference between `groupingBy` and `partitioningBy` (which always returns a Map with keys `true` and `false`).

#### **3. `List<String> getVolatileTickers(List<Trade> trades, double percentageDiff)**`

- **Goal:** Return a list of ticker names that are considered "volatile."
- **Criteria:** A ticker is volatile if the difference between its **Highest Price** and **Lowest Price** is greater than `percentageDiff` of its lowest price.
- Formula:

- **Stream Challenge:** Requires `summarizingDouble` to get min/max stats in one pass, followed by a filter on those stats.

---

### **Updated Code Stub**

Here is the complete class structure with the new methods added.

```java
import java.util.*;
import java.util.stream.Collectors;

// ... (Enum Exchange and Class Trade remain the same) ...

class MarketAnalyzer {
    public Map<Exchange, List<TickerSummary>> generateExchangeReport(List<Trade> trades, long startTime, long endTime) {
        return null;
    }

    public Map<Exchange, Optional<TickerSummary>> getMostActiveTickerByExchange(List<Trade> trades) {
        return null;
    }
    public Map<Boolean, List<Trade>> partitionSmallTrades(List<Trade> trades, int volumeThreshold) {
        return null;
    }

    public List<String> getVolatileTickers(List<Trade> trades, double percentageDiff) {
        return null;
    }
}

```
