package design_patterns.bridge;
/*
Remote Control & Device
🧩 Problem:
You are designing a remote control system for multiple devices.
You have:
Devices: TV, Radio
Remotes: BasicRemote, AdvancedRemote (has mute)
Each remote can control any device, and devices may vary in features.
🔧 Task:
    - Use Bridge Pattern to separate remote abstraction from device implementation.
    - Remote should control any device (TV, Radio, etc.)
    - Devices may add new capabilities later, without breaking remotes
🧠 Concepts:
    - Hierarchy splitting
    - Composition over inheritance
    - Extendability for future devices/remotes
*/
public class RemoteControlDemo {
}
