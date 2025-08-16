package com.java.threads;

public class ActiveCountExample {
    public static void main(String[] args) {
        System.out.println("👥 Active threads: " + Thread.activeCount());
        System.out.println("Current thread group: " + Thread.currentThread().getThreadGroup().getName());
    }
}
