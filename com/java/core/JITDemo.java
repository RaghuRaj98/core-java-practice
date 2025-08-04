package com.java.core;

public class JITDemo {
    
    // Method that simulates some heavy calculation
    public static long heavyCalculation(int n) {
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += Math.sqrt(i); // Some CPU work
        }
        return sum;
    }

    public static void main(String[] args) {
        int iterations = 10; // Number of test runs
        int n = 1_000_000;   // Number of loop calculations

        for (int i = 1; i <= iterations; i++) {
            long start = System.nanoTime();
            long result = heavyCalculation(n);
            long end = System.nanoTime();
            
            System.out.println("Run " + i + " result: " + result + 
                               " | Time: " + (end - start) / 1_000_000 + " ms");
        }
    }
}
