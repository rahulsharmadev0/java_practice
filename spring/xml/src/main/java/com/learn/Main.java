package com.learn;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args){
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml")) {
            VehicleInterface vehicle = context.getBean("vehicle", VehicleInterface.class);
            System.out.println(vehicle.getMaxSpeed());
            System.out.println(vehicle.getName());
            vehicle.getHorn().play();
        } catch (BeansException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}