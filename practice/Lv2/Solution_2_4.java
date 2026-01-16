package Lv2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ---------------------------------------------------------
// 1. Enum Definitions
// ---------------------------------------------------------

enum PlanType {
    FREE, BASIC, PRO, ENTERPRISE
}

enum UsageType {
    API_CALL, STORAGE_GB, COMPUTE_HOUR
}

// ---------------------------------------------------------
// 2. Entity Classes
// ---------------------------------------------------------

class Subscription {
    private String organizationId;
    private PlanType planType;
    private double monthlyCost;
    private LocalDate startDate;
    private LocalDate endDate;

    public Subscription(String organizationId, PlanType planType,
            double monthlyCost, LocalDate startDate, LocalDate endDate) {
        this.organizationId = organizationId;
        this.planType = planType;
        this.monthlyCost = monthlyCost;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}

class UsageEvent {
    private String organizationId;
    private String userId;
    private UsageType usageType;
    private double unitsConsumed;
    private LocalDate eventDate;

    public UsageEvent(String organizationId, String userId,
            UsageType usageType, double unitsConsumed, LocalDate eventDate) {
        this.organizationId = organizationId;
        this.userId = userId;
        this.usageType = usageType;
        this.unitsConsumed = unitsConsumed;
        this.eventDate = eventDate;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getUserId() {
        return userId;
    }

    public UsageType getUsageType() {
        return usageType;
    }

    public double getUnitsConsumed() {
        return unitsConsumed;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }
}

class SubscriptionAnalytics {

    public Map<PlanType, List<String>> getActiveOrganizationsByPlan(
            List<Subscription> subscriptions, LocalDate referenceDate) {
        return subscriptions.stream().collect(
                Collectors.groupingBy(
                        Subscription::getPlanType,
                        Collectors.filtering(
                                sub -> referenceDate.isAfter(sub.getStartDate())
                                        && referenceDate.isBefore(sub.getEndDate()),
                                Collectors.mapping(
                                        Subscription::getOrganizationId,
                                        Collectors.toList()))));
    }

    public Map<PlanType, Double> calculateMonthlyRevenueByPlan(
            List<Subscription> subscriptions) {
        return subscriptions.stream().collect(
                Collectors.groupingBy(
                        Subscription::getPlanType,
                        Collectors.filtering(
                                sub -> sub.getMonthlyCost() > 0,
                                Collectors.summingDouble(Subscription::getMonthlyCost))));
    }

    public Map<String, Optional<String>> getHighestUsageUserPerOrganization(
            List<UsageEvent> events) {
        return events.stream().collect(
                Collectors.groupingBy(
                        UsageEvent::getOrganizationId,
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(UsageEvent::getUserId,
                                        Collectors.summingDouble(UsageEvent::getUnitsConsumed)),
                                sumUserMap -> sumUserMap.entrySet().stream().max(
                                        Map.Entry.comparingByValue()).map(Map.Entry::getKey))));

    }

    public Map<Boolean, List<String>> partitionOrganizationsByUsageEfficiency(
            List<Subscription> subscriptions,
            List<UsageEvent> usageEvents,
            double minUnitsPerDollar) {
        var orgByUnit = usageEvents.stream().collect(
                Collectors.groupingBy(
                        UsageEvent::getOrganizationId,
                        Collectors.summingDouble(UsageEvent::getUnitsConsumed)));
        var orgByCost = subscriptions.stream().collect(
                Collectors.groupingBy(
                        Subscription::getOrganizationId,
                        Collectors.summingDouble(Subscription::getMonthlyCost)));

        return Stream.concat(orgByCost.keySet().stream(), orgByUnit.keySet().stream())
                .distinct()
                .collect(
                        Collectors.partitioningBy(
                                key -> {
                                    var cost = orgByCost.getOrDefault(key, 0.0);
                                    var unit = orgByUnit.get(key);
                                    if (unit == null || cost <= 0.0)
                                        return false;
                                    return (unit / cost) >= minUnitsPerDollar;

                                }));

    }

    // TODO: Work in Progress
    public List<String> identifyAtRiskOrganizations(
            List<Subscription> subscriptions,
            List<UsageEvent> usageEvents,
            double usageDropPercentage) {
        LocalDate now = LocalDate.now();

        var activeSubsByOrgId = subscriptions.stream().filter(
                sub -> !sub.getStartDate().isBefore(now) && !sub.getEndDate().isAfter(now)).collect(
                        Collectors.toMap(
                                Subscription::getOrganizationId,
                                s -> s,
                                (a, b) -> a.getEndDate().isAfter(b.getEndDate()) ? a : b));

        var UserEventsByOrgId = usageEvents.stream().collect(
                Collectors.groupingBy(
                        UsageEvent::getOrganizationId));
        
        Set<String> atRisks = new HashSet<>();
        for (UsageEvent event : usageEvents) {

            event.get
            
        }

        var avgMonthlyUsage = subscriptions.stream().collect(
                Collectors.groupingBy(
                        Subscription::getOrganizationId,
                        Collectors.averagingDouble(
                                sub -> {
                                    var halfDays = (ChronoUnit.DAYS.between(sub.getStartDate(), sub.getEndDate()) + 1)
                                            / 2;
                                    LocalDate midpoint = sub.getStartDate().plusDays(halfDays);
                                    double secondHalfMonths = ChronoUnit.DAYS.between(midpoint,
                                            sub.getEndDate().plusDays(1)) / 30.0;
                                    return secondHalfMonths * sub.getMonthlyCost();
                                })));

        return null;
    }
}

public class Solution_2_4 {
    public static void main(String[] args) {
        Source.main(args);
    }
}
