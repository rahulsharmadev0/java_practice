## Coding Challenge: Industrial IoT Sensor Grid

### Problem Statement

You are optimizing the telemetry engine for a smart factory. Thousands of sensors emit readings every second. The system uses a hierarchical structure: `Factory` -> `Department` -> `Machine` -> `Sensor`.

You need to process these nested structures to detect anomalies, calculate efficiency metrics, and aggregate data for real-time dashboards. Speed is critical—you must utilize **Parallel Streams** where appropriate and handle potentially massive datasets.

---

### 🧠 Analytics Tasks

#### 1. `List<Double> getHotspotTemperatures(List<Factory> factories)`

- **Goal**: Extract all temperature readings that exceed warning thresholds across all machines.
- **Challenge**: The data is deeply nested (`Factory` has `Departments`, which have `Machines`, which have `Sensors`, which have `Readings`).
- **Requirement**: Use `flatMap` to drill down to the `Reading` level and return a sorted list of unique critical temperatures.

#### 2. `Map<String, Double> getAveragePowerPerMachine(List<Factory> factories)`

- **Goal**: Calculate the average power consumption for every Machine ID.
- **Requirement**: The result map keys should be Machine IDs. Values are the average of their power readings.
- **Parallelism**: This operation must be performed using a **parallel stream**. Ensure your collector is efficient (e.g., `groupingByConcurrent`).

#### 3. `Map<String, Map<String, Long>> getAlarmCountByDeptAndSeverity(List<LogEntry> logs)`

- **Goal**: Create a nested breakdown of system alarms.
- **Structure**: Outer Map Key = Department Name, Inner Map Key = Severity Level (INFO, WARNING, CRITICAL). Value = Count of alarms.
- **Requirement**: Use **Cascading Grouping** (`groupingBy` inside `groupingBy`).

#### 4. `SensorStats aggregateSensorStats(List<Sensor> sensors)`

- **Goal**: In ONE pass, compute:
  - Total Count of sensors
  - Sum of active hours
  - Global Max usage value
- **Requirement**: Define a custom `Collector` or usage of `reduce` with a custom Identity/Accumulator/Combiner to build a `SensorStats` object. This tests your understanding of the reduce operation's combiner logic for parallel streams.

---

### 🏗️ Starter Code Stub

```java
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.*;

// 1. Data Models
class Factory {
    String id;
    List<Department> departments;
    // Constructor + Getters
}

class Department {
    String name;
    List<Machine> machines;
    // Constructor + Getters
}

class Machine {
    String id;
    List<Sensor> sensors;
    // Constructor + Getters
}

class Sensor {
    String id;
    String type; // "TEMP", "POWER", "PRESSURE"
    List<Double> readings;
    // Constructor + Getters
}

class LogEntry {
    String department;
    String severity; // "CRITICAL", "WARNING", "INFO"
    String message;
    // Constructor + Getters
}

record SensorStats(long count, double totalActiveHours, double maxUsage) {}

// 2. Logic Class
class TelemetryEngine {

    public List<Double> getHotspotTemperatures(List<Factory> factories) {
        // TODO: Deep flatMap chain + distinct + sorted
        return null;
    }

    public Map<String, Double> getAveragePowerPerMachine(List<Factory> factories) {
        // TODO: Parallel aggregation
        return null;
    }

    public Map<String, Map<String, Long>> getAlarmCountByDeptAndSeverity(List<LogEntry> logs) {
        // TODO: Nested grouping
        return null;
    }

    public SensorStats aggregateSensorStats(List<Sensor> sensors) {
        // TODO: Custom reduction
        return null;
    }
}

public class Source {
    public static void main(String[] args) {
        // Test parallel performance and correctness
    }
}
```
