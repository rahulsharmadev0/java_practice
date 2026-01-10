# Coding Challenge: Airline Fleet Intelligence System

## **Problem Statement**

You are developing the analytics backend for a global airline. You need to model a `Flight` entity and implement an `AirlineAnalytics` class. The system must process live flight data to generate revenue reports, monitor operational efficiency, and identify critical routes using the **Java Stream API**.

## **Class Specifications**

### 1. Enum: `FlightStatus`

- Define an enum named `FlightStatus` with the constants: `ON_TIME`, `DELAYED`, `CANCELLED`, `BOARDING`, `LANDED`.

### 2. Class: `Flight`

- **Data Members (Private):**
- `String flightNumber` (e.g., "AA123")
- `String origin` (Airport Code, e.g., "JFK")
- `String destination` (Airport Code, e.g., "LHR")
- `FlightStatus status`
- `double ticketPrice`
- `int totalSeats`
- `int passengersBooked`
- `int durationMinutes`

- **Constructor:**
- Initialize all fields.

- **Methods:**
- Public getters for all fields.
- `toString()` is provided in the stub.

### 3. Class: `AirlineAnalytics`

You must implement the following **5 methods**.
_Note: Do not use loops (for/while). All logic must be implemented using functional programming paradigms._

1. `List<Flight> findAvailableFlights(List<Flight> flights, String dest, int minSeats)`

- **Goal:** Retrieve a list of flights traveling to the specified `dest`.
- **Criteria:** The flight must match the destination, its status must **not** be `CANCELLED`, and it must have at least `minSeats` remaining (calculated as `totalSeats - passengersBooked`).

2. `Map<String, Double> calculateRevenueByDestination(List<Flight> flights)`

- **Goal:** Calculate the total generated revenue for each unique destination.
- **Criteria:** Revenue for a single flight is `passengersBooked * ticketPrice`. Cancelled flights should be excluded from the calculation.
- **Return:** A Map where the key is the `destination` code and the value is the total revenue.

3. `Map<FlightStatus, Optional<Flight>> getLongestFlightPerStatus(List<Flight> flights)`

- **Goal:** Identify the single flight with the longest `durationMinutes` for each `FlightStatus`.
- **Return:** A Map where the key is the status, and the value is an `Optional` containing the longest flight found for that status.

4. `Map<Boolean, List<Flight>> partitionFlightsByOccupancy(List<Flight> flights, double thresholdPercentage)`

- **Goal:** Split the flights into two groups based on their occupancy rate.
- **Criteria:** Occupancy rate is `passengersBooked / totalSeats`.
- `true`: Flights where occupancy is **greater than or equal to** `thresholdPercentage`.
- `false`: Flights where occupancy is **below** the threshold.

5. `List<String> identifyUnderperformingOrigins(List<Flight> flights, double minAverageRevenue)`

- **Goal:** Return a list of `origin` airport codes that are underperforming financially.
- **Criteria:** An origin is considered underperforming if the **average revenue** of all flights departing from it is strictly **less than** `minAverageRevenue`.
- **Return:** A sorted list (alphabetical) of these origin codes.

---

### **Sample Input Structure**

```java
List<Flight> schedule = new ArrayList<>();
schedule.add(new Flight("FL001", "JFK", "LHR", FlightStatus.ON_TIME, 500.0, 200, 180, 420));
schedule.add(new Flight("FL002", "JFK", "LHR", FlightStatus.DELAYED, 450.0, 200, 50, 420));
schedule.add(new Flight("FL003", "LHR", "DXB", FlightStatus.BOARDING, 700.0, 300, 290, 480));
schedule.add(new Flight("FL004", "DXB", "JFK", FlightStatus.CANCELLED, 800.0, 250, 0, 800));
schedule.add(new Flight("FL005", "JFK", "TKYO", FlightStatus.ON_TIME, 1200.0, 280, 140, 850));

AirlineAnalytics analytics = new AirlineAnalytics();

// Execute methods to test logic
analytics.findAvailableFlights(schedule, "LHR", 10);
analytics.calculateRevenueByDestination(schedule);
analytics.getLongestFlightPerStatus(schedule);
analytics.partitionFlightsByOccupancy(schedule, 0.80);
analytics.identifyUnderperformingOrigins(schedule, 100000.0);

```

---

## **Starter Code Stub**

```java
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

// 1. Enum Definition
enum FlightStatus {
    ON_TIME, DELAYED, CANCELLED, BOARDING, LANDED
}

// 2. Entity Class
class Flight {
    private String flightNumber;
    private String origin;
    private String destination;
    private FlightStatus status;
    private double ticketPrice;
    private int totalSeats;
    private int passengersBooked;
    private int durationMinutes;

    public Flight(String flightNumber, String origin, String destination, FlightStatus status,
                  double ticketPrice, int totalSeats, int passengersBooked, int durationMinutes) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
        this.ticketPrice = ticketPrice;
        this.totalSeats = totalSeats;
        this.passengersBooked = passengersBooked;
        this.durationMinutes = durationMinutes;
    }

    // Getters
    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public FlightStatus getStatus() { return status; }
    public double getTicketPrice() { return ticketPrice; }
    public int getTotalSeats() { return totalSeats; }
    public int getPassengersBooked() { return passengersBooked; }
    public int getDurationMinutes() { return durationMinutes; }

    @Override
    public String toString() {
        return flightNumber;
    }
}

// 3. Logic Class
class AirlineAnalytics {

    public List<Flight> findAvailableFlights(List<Flight> flights, String dest, int minSeats) {
        // TODO: Implement logic
        return null;
    }

    public Map<String, Double> calculateRevenueByDestination(List<Flight> flights) {
        // TODO: Implement logic
        return null;
    }

    public Map<FlightStatus, Optional<Flight>> getLongestFlightPerStatus(List<Flight> flights) {
        // TODO: Implement logic
        return null;
    }

    public Map<Boolean, List<Flight>> partitionFlightsByOccupancy(List<Flight> flights, double thresholdPercentage) {
        // TODO: Implement logic
        return null;
    }

    public List<String> identifyUnderperformingOrigins(List<Flight> flights, double minAverageRevenue) {
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
