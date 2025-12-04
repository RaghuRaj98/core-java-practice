package com.java.multithreading;

import java.util.concurrent.*;

public class FixedThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " running in " + Thread.currentThread().getName());
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                System.out.println("Task " + taskId + " finished");
            });
        }

        executor.shutdown(); // stops accepting new tasks
    }
}
