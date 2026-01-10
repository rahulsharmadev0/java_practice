## Coding Challenge: Financial Portfolio & Risk Analytics

### Problem Statement

You are building the core engine for a **High-Frequency Trading (HFT)** risk management system. The system receives a massive stream of `Trade` and `MarketData` events. Your goal is to compute complex financial metrics using advanced **Stream API** techniques, custom **Collectors**, and parallel processing.

This level requires you to move beyond simple grouping. You must implement **single-pass statistics**, **weighted averages**, and **custom reduction logic** without using mutable external state.

---

### 🧠 Analytics Tasks

#### 1. `Map<String, Double> calculateVWAPByTicker(List<Trade> trades)`

- **Goal**: Calculate the **Volume Weighted Average Price (VWAP)** for each ticker.
- **Formula**: `VWAP = Sum(Price * Volume) / Sum(Volume)`
- **Constraint**: This must be done efficiently, preferably in a single pass per ticker group.

#### 2. `Map<Sector, Double> getSectorVolatility(List<Stock> stocks)`

- **Goal**: specific sector volatility.
- **Input**: A list of stocks, where each stock has a list of historical closing prices.
- **Metric**: Volatility is defined here as the **Standard Deviation** of the closing prices.
- **Requirement**: You must flatten the data structures appropriately and compute the standard deviation for the sector's combined price movements (simplification: assume distinct price points contribute to sector volatility). _Hint: You might need a custom collector or `Collectors.teeing` (if available) or `Collector.of` for single-pass variance._

#### 3. `PortfolioStats computePortfolioStats(List<Position> positions)`

- **Goal**: Compute a composite object `PortfolioStats` containing:
  - Total Value
  - Most Valuable Asset
  - Least Valuable Asset
- **Requirement**: Use a single stream pipeline. Do NOT stream the list three times. Use `Collectors.teeing` or a custom `Collector`.

#### 4. `Map<Boolean, List<Transaction>> detectWashTrades(List<Transaction> transactions, long timeWindowMillis)`

- **Goal**: Identify potential "Wash Trades" (buying and selling the same instrument within a short window).
- **Partition**:
  - `true`: Trades that are part of a wash pair.
  - `false`: Legitimate trades.
- **Logic**: A wash trade is defined here as a BUY and a SELL for the same Ticker, same Quantity, within `timeWindowMillis` of each other.
- **Constraint**: This is a tricky temporal problem. You may need to group by Ticker first, then process lists.

---

### 🏗️ Starter Code Stub

```java
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// 1. Data Models
enum Sector {
    TECHNOLOGY, HEALTHCARE, FINANCE, ENERGY, CONSUMER_DISCRETIONARY
}

class Trade {
    String ticker;
    double price;
    int volume;
    long timestamp;

    public Trade(String ticker, double price, int volume, long timestamp) {
        this.ticker = ticker;
        this.price = price;
        this.volume = volume;
        this.timestamp = timestamp;
    }
    public String getTicker() { return ticker; }
    public double getPrice() { return price; }
    public int getVolume() { return volume; }
    public long getTimestamp() { return timestamp; }
}

class Stock {
    String ticker;
    Sector sector;
    List<Double> closingPrices;

    public Stock(String ticker, Sector sector, List<Double> closingPrices) {
        this.ticker = ticker;
        this.sector = sector;
        this.closingPrices = closingPrices;
    }
    public Sector getSector() { return sector; }
    public List<Double> getClosingPrices() { return closingPrices; }
}

class Position {
    String ticker;
    double quantity;
    double currentPrice;

    public Position(String ticker, double quantity, double currentPrice) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }
    public double getValue() { return quantity * currentPrice; }
}

record PortfolioStats(double totalValue, Position bestAsset, Position worstAsset) {}

class Transaction {
    String id;
    String ticker;
    String type; // "BUY" or "SELL"
    int quantity;
    long timestamp;

    // Constructor/Getters omitted for brevity
}

// 2. Logic Class
class RiskEngine {

    public Map<String, Double> calculateVWAPByTicker(List<Trade> trades) {
        // TODO: Implement using Stream API (groupingBy + custom aggregation)
        return null;
    }

    public Map<Sector, Double> getSectorVolatility(List<Stock> stocks) {
        // TODO: Implement sector-wide volatility (StdDev)
        return null;
    }

    public PortfolioStats computePortfolioStats(List<Position> positions) {
        // TODO: Single-pass reduction finding Sum, Max, and Min
        return null;
    }

    public Map<Boolean, List<Transaction>> detectWashTrades(List<Transaction> transactions, long timeWindowMillis) {
        // TODO: Advanced temporal matching
        return null;
    }
}

public class Source {
    public static void main(String[] args) {
        // Verify implementation
    }
}
```
