package Lv2;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

public class Solution_2_1 {
    public static void main(String[] args) {
        Source.main(args);
    }
}

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
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getPassengersBooked() {
        return passengersBooked;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public String toString() {
        return flightNumber;
    }
}

// 3. Logic Class
class AirlineAnalytics {

    public List<Flight> findAvailableFlights(List<Flight> flights, String dest, int minSeats) {
        return flights.stream()
                .filter(
                        flight -> flight.getStatus() != FlightStatus.CANCELLED
                                && flight.getDestination().equals(dest)
                                && (flight.getTicketPrice() - flight.getPassengersBooked()) > minSeats)
                .toList();
    }

    // Filter -> DestinationMap
    public Map<String, Double> calculateRevenueByDestination(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getStatus() != FlightStatus.CANCELLED)
                .collect(
                        Collectors.groupingBy(
                                Flight::getDestination,
                                Collectors.summingDouble(
                                        flight -> flight.getPassengersBooked() * flight.getTicketPrice())));
    }

    public Map<FlightStatus, Optional<Flight>> getLongestFlightPerStatus(List<Flight> flights) {
        return flights.stream().collect(
                Collectors.groupingBy(
                        Flight::getStatus,
                        Collectors.maxBy(Comparator.comparing(Flight::getDurationMinutes))));
    }

    public Map<Boolean, List<Flight>> partitionFlightsByOccupancy(List<Flight> flights, double thresholdPercentage) {
        return flights.stream().collect(
                Collectors.partitioningBy(
                        flight -> (flight.getPassengersBooked() / flight.getTotalSeats()) >= thresholdPercentage));
    }

    public List<String> identifyUnderperformingOrigins(List<Flight> flights, double minAverageRevenue) {
        return flights.stream().collect(
                Collectors.groupingBy(
                        Flight::getOrigin,
                        Collectors.averagingDouble(flight -> flight.getPassengersBooked() * flight.getTicketPrice())))
                .entrySet().stream().filter(map -> map.getValue() < minAverageRevenue).map(Map.Entry::getKey).sorted()
                .toList();
    }
}

class Source {
    public static void main(String[] args) {
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
    }
}
