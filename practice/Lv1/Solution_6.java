package Lv1;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution_6 {
    public static void main(String[] args) {
        Source.main(args);
    }

}

// ---------------------------------------------------------
// 1. Data Models (Enums and Classes)
// ---------------------------------------------------------

enum Exchange {
    NYSE, // New York
    LSE, // London
    JPX, // Tokyo
    HKEX // Hong Kong
}

class Trade {
    private String ticker;
    private Exchange exchange;
    private double price;
    private int volume;
    private long timestamp;

    public Trade(String ticker, Exchange exchange, double price, int volume, long timestamp) {
        this.ticker = ticker;
        this.exchange = exchange;
        this.price = price;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    public String getTicker() {
        return ticker;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public double getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return ticker + " (" + exchange + ")";
    }
}

/**
 * A summary class to hold aggregated data for a specific ticker.
 */
class TickerSummary {
    private String ticker;
    private double averagePrice;
    private long totalVolume;
    private double highestPrice;

    public TickerSummary(String ticker, double averagePrice, long totalVolume, double highestPrice) {
        this.ticker = ticker;
        this.averagePrice = averagePrice;
        this.totalVolume = totalVolume;
        this.highestPrice = highestPrice;
    }

    public String getTicker() {
        return ticker;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public long getTotalVolume() {
        return totalVolume;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    @Override
    public String toString() {
        return String.format("{%s: Vol=%d, Avg=%.2f, Max=%.2f}",
                ticker, totalVolume, averagePrice, highestPrice);
    }
}

// ---------------------------------------------------------
// 2. Logic Class (The Interview Task)
// ---------------------------------------------------------

class MarketAnalyzer {

    public Map<Exchange, List<TickerSummary>> generateExchangeReport(List<Trade> trades, long startTime, long endTime) {
        return trades.stream().collect(Collectors.groupingBy(Exchange::name)).
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

// ---------------------------------------------------------
// 3. Execution (Main Method)
// ---------------------------------------------------------
class Source {
    public static void main(String[] args) {
        // Sample Data
        List<Trade> trades = Arrays.asList(
                new Trade("AAPL", Exchange.NYSE, 150.0, 100, 1000L),
                new Trade("GOOG", Exchange.NYSE, 2000.0, 50, 1005L),
                new Trade("AAPL", Exchange.NYSE, 160.0, 200, 1010L),
                new Trade("VOD", Exchange.LSE, 120.0, 500, 1002L),
                new Trade("MSFT", Exchange.NYSE, 300.0, 5, 1020L),
                new Trade("SONY", Exchange.JPX, 90.0, 1000, 1005L));

        MarketAnalyzer analyzer = new MarketAnalyzer();

        System.out.println("--- Task 1: Exchange Report ---");
        System.out.println(analyzer.generateExchangeReport(trades, 0, 5000));

        System.out.println("\n--- Task 2: Most Active by Exchange ---");
        System.out.println(analyzer.getMostActiveTickerByExchange(trades));

        System.out.println("\n--- Task 3: Partition Small Trades (Threshold 100) ---");
        System.out.println(analyzer.partitionSmallTrades(trades, 100));

        System.out.println("\n--- Task 4: Volatile Tickers ---");
        // Example: Tickers that fluctuated by more than 5%
        System.out.println(analyzer.getVolatileTickers(trades, 0.05));
    }
}