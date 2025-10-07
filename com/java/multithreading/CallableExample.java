package com.java.multithreading;

import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) {
        // Create a thread pool with a single thread
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Create a Callable task
        Callable<Integer> task = () -> {
            System.out.println(Thread.currentThread().getName() + " is calculating...");
            Thread.sleep(2000); // simulate delay
            return 10 * 10; // return result
        };

        // Submit the task to executor
        Future<Integer> future = executor.submit(task);

        try {
            System.out.println("Doing something else while task is running...");
            
            // Get the result (this will block until task completes)
            Integer result = future.get();
            System.out.println("Result from Callable: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
