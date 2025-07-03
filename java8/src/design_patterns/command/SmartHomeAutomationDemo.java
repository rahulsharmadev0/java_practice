package design_patterns.command;

public class SmartHomeAutomationDemo {
    public static void main(String[] args) {
        SmartLight smartLight = new SmartLight();

        RemoteControl remote = new RemoteControl(new LightOnCommand(smartLight));

        remote.pressButton();

        remote.setCommand(new LightOffCommand(smartLight));

        remote.pressButton();
    }
}


interface Command {
    void execute();
}

class RemoteControl {
    Command command;
    public RemoteControl(Command initalCommand) {
        setCommand(initalCommand);
    }

   void setCommand(Command command){
        this.command = command;
    }
    public void pressButton() {
        command.execute();
    }
}

// Real Light Device
class SmartLight{
   public  void turnOn(){
        System.out.println("SmartLight is on");
    }
    public  void turnOff(){
       System.out.println("Smart light is off");
    }
}

record LightOnCommand(SmartLight light) implements Command {
    @Override
    public void execute() {
        light.turnOn();
    }
}
record LightOffCommand(SmartLight light) implements Command {
    @Override
    public void execute() {
        light.turnOff();
    }
}