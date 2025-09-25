package com.java.multithreading;

public class ThreadDemo3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> 
            System.out.println("Lambda running: " + Thread.currentThread().getName()));
        t1.start();
    }
}
