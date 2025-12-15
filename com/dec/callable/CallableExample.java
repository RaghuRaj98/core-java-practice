package com.dec.callable;

import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) throws Exception {

        // ExecutorService with a pool of 5 threads
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Callable task
        Callable<Integer> task = () -> {
            System.out.println(Thread.currentThread().getName() + " calculating sum...");
            int sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += i;
            }
            return sum; // Callable returns result
        };

        // Submit and receive Future
        Future<Integer> future = executor.submit(task);

        // Do something else (non-blocking)
        System.out.println("Main thread is free to do other work...");

        // BLOCKING call — wait for result
        Integer result = future.get();
        System.out.println("Result: " + result);

        executor.shutdown();
    }
}
