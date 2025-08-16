package com.java.threads;

class DeliveryTask implements Runnable {
    public void run() {
        try {
            System.out.println("📦 Order received, preparing... State: " + Thread.currentThread().getState());

            // Thread goes into TIMED_WAITING
            Thread.sleep(1000);
            System.out.println("⏳ Waiting for restaurant to prepare food. State: " + Thread.currentThread().getState());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("✅ Food delivered! State: " + Thread.currentThread().getState());
    }
}

public class ThreadLifeCycleDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new DeliveryTask());

        System.out.println("➡️ State after creating thread: " + t1.getState()); // NEW

        t1.start();
        System.out.println("➡️ State after start(): " + t1.getState()); // RUNNABLE

        // Main thread waits for t1 to complete
        t1.join();
        System.out.println("➡️ Final state after completion: " + t1.getState()); // TERMINATED
    }
}
