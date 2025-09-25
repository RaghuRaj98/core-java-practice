package com.java.multithreading;

import java.util.concurrent.*;

public class ThreadDemo4 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> System.out.println("Task running in pool"));
        executor.shutdown();
    }
}
