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

    public static void main(String[] args) {
        Remote aRemote = new AdvancedRemote();
        Remote bRemote = new BasicRemote();
        Device tvDevice = new TVDevice(aRemote);

        System.out.println(tvDevice); // print address
    }

}

abstract class Device {
    private final Remote remote;

    Device(final Remote remote) {
        this.remote = remote;
    }

    public Remote getRemote() {
        return remote;
    }
}

class TVDevice extends Device {
    TVDevice(final Remote remote) {
        super(remote);
    }
}

class RadioDevice extends Device {
    RadioDevice(final Remote remote) {
        super(remote);
    }
}

interface Remote {
    void turnOn();

    void turnOff();
}

class BasicRemote implements Remote {

    public void volumeUp() {
    }

    public void volumeDown() {
    }

    @Override
    public void turnOn() {
        throw new UnsupportedOperationException("Unimplemented method 'turnOn'");
    }

    @Override
    public void turnOff() {

        throw new UnsupportedOperationException("Unimplemented method 'turnOff'");
    }

}


class AdvancedRemote implements Remote  {

    public void mute(){

    }

    public void volumeUp() {
    }

    public void volumeDown() {
    }

    @Override
    public void turnOn() {
        throw new UnsupportedOperationException("Unimplemented method 'turnOn'");
    }

    @Override
    public void turnOff() {

        throw new UnsupportedOperationException("Unimplemented method 'turnOff'");
    }

}
