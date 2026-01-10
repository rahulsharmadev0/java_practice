## Coding Challenge: Enterprise Subscription & Usage Analytics Engine

### Problem Statement

You are designing an **analytics engine** for a SaaS platform that sells **subscriptions** to organizations.
Each organization may have multiple users, and each user generates usage events.

The system needs to analyze **subscription health, revenue efficiency, and usage behavior** using **Java Collections and Stream API**.

The analytics module must:

- avoid mutating input data
- work correctly with large datasets
- produce deterministic results
- follow functional programming principles

### 🧠 Analytics Tasks

Implement the following methods inside `SubscriptionAnalytics`.

---

## 1. `getActiveOrganizationsByPlan`

```java
public Map<PlanType, List<String>> getActiveOrganizationsByPlan(
        List<Subscription> subscriptions,
        LocalDate referenceDate);
```

### Goal

For each subscription plan:

- list **organization IDs**
- whose subscription is **active on `referenceDate`**
- each organization must appear **only once per plan**

---

## 2. `calculateMonthlyRevenueByPlan`

```java
public Map<PlanType, Double> calculateMonthlyRevenueByPlan(
        List<Subscription> subscriptions);
```

### Goal

For each plan type:

- calculate **total monthly revenue**
- ignore subscriptions with zero or negative cost

---

## 3. `getHighestUsageUserPerOrganization`

```java
public Map<String, Optional<String>> getHighestUsageUserPerOrganization(
        List<UsageEvent> events);
```

### Goal

For each organization:

- determine the **user who consumed the highest total units**
- across **all usage types**
- return `Optional.empty()` if an organization has no events

---

## 4. `partitionOrganizationsByUsageEfficiency`

```java
public Map<Boolean, List<String>> partitionOrganizationsByUsageEfficiency(
        List<Subscription> subscriptions,
        List<UsageEvent> usageEvents,
        double minUnitsPerDollar);
```

### Goal

Partition organizations into:

- `true` → organizations whose **total usage units per dollar spent**
  is **≥ `minUnitsPerDollar`**
- `false` → all others

Rules:

- Usage must be aggregated **per organization**
- Cost must be derived from subscription data
- Organizations missing either data source must still be handled correctly

---

## 5️⃣ `identifyAtRiskOrganizations`

```java
public List<String> identifyAtRiskOrganizations(
        List<Subscription> subscriptions,
        List<UsageEvent> usageEvents,
        double usageDropPercentage);
```

### Goal

Identify organizations considered **“at risk”**.

An organization is **at risk** if:

- it has **active subscription**
- AND its **average monthly usage in the second half of the subscription**
  dropped by more than `usageDropPercentage`
  compared to the **first half**

Rules:

- Time-based aggregation is required
- Usage must be normalized per month
- Each organization appears only once

---

# 🏗️ Logic Class Stub

```java
import java.time.LocalDate;
import java.util.*;
import java.util.Optional;

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

    public String getOrganizationId() { return organizationId; }
    public PlanType getPlanType() { return planType; }
    public double getMonthlyCost() { return monthlyCost; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
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

    public String getOrganizationId() { return organizationId; }
    public String getUserId() { return userId; }
    public UsageType getUsageType() { return usageType; }
    public double getUnitsConsumed() { return unitsConsumed; }
    public LocalDate getEventDate() { return eventDate; }
}

class SubscriptionAnalytics {

    public Map<PlanType, List<String>> getActiveOrganizationsByPlan(
            List<Subscription> subscriptions, LocalDate referenceDate) {
        // TODO
        return null;
    }

    public Map<PlanType, Double> calculateMonthlyRevenueByPlan(
            List<Subscription> subscriptions) {
        // TODO
        return null;
    }

    public Map<String, Optional<String>> getHighestUsageUserPerOrganization(
            List<UsageEvent> events) {
        // TODO
        return null;
    }

    public Map<Boolean, List<String>> partitionOrganizationsByUsageEfficiency(
            List<Subscription> subscriptions,
            List<UsageEvent> usageEvents,
            double minUnitsPerDollar) {
        // TODO
        return null;
    }

    public List<String> identifyAtRiskOrganizations(
            List<Subscription> subscriptions,
            List<UsageEvent> usageEvents,
            double usageDropPercentage) {
        // TODO
        return null;
    }
}
```
