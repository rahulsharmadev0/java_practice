package design_patterns.factory_method;

public class VehicleDemo {

    public static void main(String[] args) {
        Vehicle car = VehicleFactory.createVehicle("car");
        car.drive(); // Output: Driving a car

        Vehicle bike = VehicleFactory.createVehicle("bike");
        bike.drive(); // Output: Driving a bike

        Vehicle truck = VehicleFactory.createVehicle("truck");
        truck.drive(); // Output: Driving a truck
    }
}


/*
 * Vehicle Creator
 * --------------------------
 *      Design a VehicleFactory using the Factory Method pattern.
 *      Define a Vehicle interface with drive()
 *      Create Car, Bike, and Truck classes
 *      Each factory subclass should return a different vehicle
 *      Client code should not use new keyword directly
 * */

interface Vehicle {
    void drive();
}

class Car implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Driving a car");
    }
}

class Bike implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Driving a bike");
    }
}

class Truck implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Driving a truck");
    }
}

class VehicleFactory {

    static Vehicle createVehicle(String type) {
        return switch (type) {
            case "car" -> new Car();
            case "bike" -> new Bike();
            case "truck" -> new Truck();
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}

