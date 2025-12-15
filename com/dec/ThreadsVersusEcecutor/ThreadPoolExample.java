package com.dec.ThreadsVersusEcecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 100; i++) {
            int num = i;
            executor.submit(() -> {
                System.out.println("Thread: " + Thread.currentThread().getName() + " -> " + num);
            });
        }

        executor.shutdown();
    }
}
