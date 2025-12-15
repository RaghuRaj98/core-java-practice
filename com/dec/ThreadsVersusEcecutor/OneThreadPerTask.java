package com.dec.ThreadsVersusEcecutor;

public class OneThreadPerTask {
    public static void main(String[] args) {

        for (int i = 1; i <= 100; i++) {
            int num = i; // effectively final for lambda
            Thread t = new Thread(() -> {
                System.out.println("Task " + num + " is running in thread " + Thread.currentThread().getName());
            });
            t.start();
        }
    }
}
