package com.learn;
public class Truck implements VehicleInterface{
    private final String name;
    private Horn horn;

    public Truck(String name){
        this.name = name;
    }

    @Override
    public int getMaxSpeed(){return 80;}

    @Override
    public String getName() { return name;}

    public void setHorn(Horn horn){
        this.horn = horn;
    }
    @Override
    public Horn getHorn() {
        return horn;

    }
}
