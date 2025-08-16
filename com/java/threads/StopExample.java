package com.java.threads;

class Sensor extends Thread {
    public void run() {
        while (true) {
            System.out.println("🔍 Monitoring sensor data...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Sensor thread interrupted.");
            }
        }
    }
}

public class StopExample {
    public static void main(String[] args) throws InterruptedException {
        Sensor sensor = new Sensor();
        sensor.start();

        Thread.sleep(4000);
        System.out.println("❌ Stopping sensor monitoring...");
        sensor.stop();   // ⚠️ Deprecated, may leave shared data inconsistent
    }
}
