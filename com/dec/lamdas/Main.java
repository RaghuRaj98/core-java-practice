package com.dec.lamdas;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        ShowTime showTime = new ShowTime() {
            @Override
            public void show() {
                System.out.println("Showing time from anonymous class = " + LocalTime.now());
            }
        };
        showTime.show();

        ShowTime time = () -> {
            System.out.println("Showing time from lamda = " + LocalTime.now());
        };
        time.show();
    }
}
