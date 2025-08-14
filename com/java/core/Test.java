package com.java.core;

abstract class Vehicle {
    String brand;

    // Abstract class constructor
    Vehicle(String brand) {
        this.brand = brand;
        System.out.println("Vehicle constructor called");
    }
}

class Car extends Vehicle {
    int a =10;
    Car(String brand) {
        super(brand); // Calls abstract class constructor
        System.out.println("Car constructor called");
    }
}

public class Test {
    public static void main(String[] args) {
        Vehicle c = new Car("Hyundai");
        System.out.println("Brand: " + c.brand);
        System.out.println("Value of a: " + ((Car) c).a);
    }
}
