package com.java.multithreading;

class Counter {
    private static int count = 0;
    private static final Object lock = new Object();

    public void increment() {
        synchronized (lock) {
            count++; // not atomic!
        }
    }
    public void decrement() {
        count--;
    }
    public int getCount() { return count; }
}

public class RaceDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        };
        Runnable taskdec = () -> {
            for (int i = 0; i < 1000; i++) counter.decrement();
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(taskdec);
        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Final count = " + counter.getCount()); 
        // ❌ Often < 2000 due to race condition
    }
}
