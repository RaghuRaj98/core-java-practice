package com.dec.lamdas;

public class ShowTimeImplementation implements ShowTime {
    @Override
    public void show() {
        System.out.println("Showing time from ShowTimeImplementation = " + java.time.LocalTime.now());
    }
}
