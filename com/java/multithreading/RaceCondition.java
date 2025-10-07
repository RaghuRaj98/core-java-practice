package com.java.multithreading;

class Counters {
    int count = 0;

    // public void increment() { count++; }
    public synchronized void increment() {
        count++;
    }

    private int count1 = 0;

    public synchronized void increment1() {
        count1++;
    }

    public synchronized int get() {
        return count1;
    }

    // synchronized block (preferred for finer control)
    public void add(int value) {
        synchronized (this) {
            count1 += value;
        }
    }

    // static synchronized -> locks the Class object (global across instances)
    public static synchronized void staticMethod() {
        System.out.println("Static synchronized method called by " + Thread.currentThread().getName());
        // Add logic here that needs to be synchronized at the class level
    }
}

public class RaceCondition {
    public static void main(String[] args) throws InterruptedException {
        Counters c = new Counters();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) c.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) c.increment();
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) c.increment1();
        });
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) c.add(1);
        });
        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) Counters.staticMethod();
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

        System.out.println("Count = " + c.count);
        System.out.println("Count1 = " + c.get());
    }
}
