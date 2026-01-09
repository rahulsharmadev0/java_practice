package tasks;
import java.util.*;

public class Task3 {
    public static void main(String[] args) {
        Source.main(args);
    }

}

enum DeviceType {
    LIGHT, THERMOSTAT, CAMERA, APPLIANCE
}

class SmartDevice {

    private String id;
    private DeviceType type;
    private double powerWatts;
    private boolean isOn;

    public SmartDevice(String id, DeviceType type, double powerWatts, boolean isOn) {
        this.id = id;
        this.type = type;
        this.powerWatts = powerWatts;
        this.isOn = isOn;
    }

    String getId() {
        return id;
    }

    DeviceType getType() {
        return type;
    }

    double getPowerWatts() {
        return powerWatts;
    }

    boolean getIsOn() {
        return isOn;
    }

    @Override
    public String toString() {
        return id; // Simplified for output matching
    }
}

class HomeAutomationHub {

    public List<SmartDevice> filterHighPowerDevices(List<SmartDevice> devices, double threshold) {
        // Use Stream API to filter active devices > threshold
        return devices.stream().filter(device -> device.getPowerWatts() > threshold).toList();
    }

    public Optional<SmartDevice> findMostConsumingByType(List<SmartDevice> devices, DeviceType targetType) {
        // Use Stream API to find max power device of specific type
        return devices.stream().filter(d -> d.getType().equals(targetType))
                .max(Comparator.comparingDouble(SmartDevice::getPowerWatts));
    }
}

class Source {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        /* You can verify your implementation using the Sample Input logic */

        List<SmartDevice> devices = new ArrayList<>();
        devices.add(new SmartDevice("LivingRoomLight", DeviceType.LIGHT, 15.0, true));
        devices.add(new SmartDevice("KitchenFridge", DeviceType.APPLIANCE, 150.0, true));
        devices.add(new SmartDevice("BedroomHeater", DeviceType.THERMOSTAT, 2000.0, true));
        devices.add(new SmartDevice("PorchCam", DeviceType.CAMERA, 10.0, false));
        devices.add(new SmartDevice("Oven", DeviceType.APPLIANCE, 2500.0, false));

        HomeAutomationHub hub = new HomeAutomationHub();

        // Filter devices that are ON and use > 100 watts
        System.out.println(hub.filterHighPowerDevices(devices, 100.0));

        // Find the appliance with the highest power rating
        System.out.println(hub.findMostConsumingByType(devices, DeviceType.APPLIANCE));

    }
}