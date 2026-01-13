# Coding Challenge: Sensor Health Checks (allMatch, anyMatch, noneMatch — composite checks)

### **Problem Statement**

Model `Sensor` objects and write checks that use predicate matchers. Each method should combine several stream operations (for example, combining `allMatch` on sensors with `allMatch` on inner readings, or flattening readings and applying `noneMatch`) so the candidate demonstrates nested and flattened matching logic.

### **Class Specifications**

1. Class: `Sensor`

- Private data members:
  - `String id`
  - `boolean online`
  - `List<Double> readings` (numeric sensor readings)

- Constructor: `Sensor(String id, boolean online, List<Double> readings)`
- Public getters.
- `toString()` returns `id`.

2. Class: `SensorInspector`

Implement the following methods using Streams and matchers. Each method should combine at least two stream operations.

- `boolean allReadingsWithin(List<Sensor> sensors, double low, double high)`
  - Logic: Return `true` only if every sensor is `online` and all of its readings are between `low` and `high` inclusive. Use `allMatch` for sensors and `allMatch` for inner readings.

- `boolean anySensorOfflineWithCriticalReading(List<Sensor> sensors, double criticalThreshold)`
  - Logic: Return `true` if there exists at least one sensor that is offline **and** has any reading >= `criticalThreshold`. Use a combination of `anyMatch` and inner reading checks.

- `boolean noneHaveCriticalReading(List<Sensor> sensors, double criticalThreshold)`
  - Logic: Use a flattened view of all readings (via `flatMap`) and `noneMatch` to ensure no reading across any sensor is >= `criticalThreshold`.

---

### **Sample Execution**

```java
List<Sensor> list = new ArrayList<>();
list.add(new Sensor("S1", true, List.of(0.5, 1.2, 0.9)));
list.add(new Sensor("S2", true, List.of(0.6, 1.0, 1.4)));

SensorInspector inspector = new SensorInspector();
inspector.allReadingsWithin(list, 0.0, 2.0);    // true
inspector.anySensorOfflineWithCriticalReading(list, 5.0); // false
inspector.noneHaveCriticalReading(list, 5.0);    // true
```

---

### **Starter Code Stub**

```java
import java.util.*;
import java.util.stream.Collectors;

class Sensor {
    // Define private data members

    // Define public constructor and getters

    @Override
    public String toString() {
        return id;
    }
}

class SensorInspector {

    public boolean allReadingsWithin(List<Sensor> sensors, double low, double high) {
        // TODO: Implement logic
        return false;
    }

    public boolean anySensorOfflineWithCriticalReading(List<Sensor> sensors, double criticalThreshold) {
        // TODO: Implement logic
        return false;
    }

    public boolean noneHaveCriticalReading(List<Sensor> sensors, double criticalThreshold) {
        // TODO: Implement logic
        return false;
    }
}

public class Source {
    public static void main(String[] args) {
        /* Example usage */
    }
}
```
