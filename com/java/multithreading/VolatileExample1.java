package com.java.multithreading;

public class VolatileExample1 {
    //  private static boolean running = true; // ❌ not volatile
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            while (running) {
                // do some work
            }
            System.out.println("Worker stopped.");
        });

        worker.start();

        Thread.sleep(2000);
        System.out.println("Main thread stopping worker...");
        running = false;  // change may not be visible to worker!
    }
}
