# Coding Challenge: Smart Home Energy Manager

### **Problem Statement**

You are building a backend module for a Smart Home system. You need to model a `SmartDevice` and create a `HomeAutomationHub` class that processes a list of devices to analyze energy consumption using the **Stream API**.

### **Class Specifications**

#### **1. Enum: `DeviceType**`

- Define an enum named `DeviceType` with the constants: `LIGHT`, `THERMOSTAT`, `CAMERA`, `APPLIANCE`.

#### **2. Class: `SmartDevice**`

- **Data Members (Private):**
- `String id`
- `DeviceType type`
- `double powerWatts` (The power consumption of the device)
- `boolean isOn` (Current status of the device)

- **Constructor:**
- `SmartDevice(String id, DeviceType type, double powerWatts, boolean isOn)`: Initialize all fields.

- **Methods:**
- Public getters for all data members.
- `toString()` is provided in the stub.

#### **3. Class: `HomeAutomationHub**`

Implement the following methods using **Java Streams**:

- **`List<SmartDevice> filterHighPowerDevices(List<SmartDevice> devices, double threshold)`**
- **Logic:** Filter the list to return only devices that are currently **turned ON** (`isOn == true`) AND consume power **greater than** the specified `threshold`.
- **Return:** A `List` of the matching devices.

- **`Optional<SmartDevice> findMostConsumingByType(List<SmartDevice> devices, DeviceType targetType)`**
- **Logic:** Search through the devices of the specific `targetType` (regardless of whether they are ON or OFF) and find the one with the maximum `powerWatts`.
- **Return:** An `Optional` containing the device, or an empty `Optional` if no devices of that type exist.

---

### **Sample Execution**

**Input:**

```java
List<SmartDevice> devices = new ArrayList<>();
devices.add(new SmartDevice("LivingRoomLight", DeviceType.LIGHT, 15.0, true));
devices.add(new SmartDevice("KitchenFridge", DeviceType.APPLIANCE, 150.0, true));
devices.add(new SmartDevice("BedroomHeater", DeviceType.THERMOSTAT, 2000.0, true));
devices.add(new SmartDevice("PorchCam", DeviceType.CAMERA, 10.0, false));
devices.add(new SmartDevice("Oven", DeviceType.APPLIANCE, 2500.0, false));

HomeAutomationHub hub = new HomeAutomationHub();

// Filter devices that are ON and use > 100 watts
hub.filterHighPowerDevices(devices, 100.0);

// Find the appliance with the highest power rating
hub.findMostConsumingByType(devices, DeviceType.APPLIANCE);

```

**Output:**

```text
[KitchenFridge, BedroomHeater]
Optional[Oven]

```

---

### **Starter Code Stub**

```java
import java.util.*;
import java.util.stream.Collectors;

enum DeviceType {
    LIGHT, THERMOSTAT, CAMERA, APPLIANCE
}

class SmartDevice {
    // Define private data members

    // Define public constructor

    // Define public getters

    @Override
    public String toString() {
        return id; // Simplified for output matching
    }
}

class HomeAutomationHub {

    public List<SmartDevice> filterHighPowerDevices(List<SmartDevice> devices, double threshold) {
        // TODO: Use Stream API to filter active devices > threshold
        return null;
    }

    public Optional<SmartDevice> findMostConsumingByType(List<SmartDevice> devices, DeviceType targetType) {
        // TODO: Use Stream API to find max power device of specific type
        return null;
    }
}

public class Source {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        /* You can verify your implementation using the Sample Input logic */
    }
}

```
