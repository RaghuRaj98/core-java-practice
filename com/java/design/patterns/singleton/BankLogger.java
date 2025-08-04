package com.java.design.patterns.singleton;

// Singleton Logger Class
public class BankLogger {

    // Static instance
    private static BankLogger instance;

    // Private constructor
    private BankLogger() {
        System.out.println("Bank Logger initialized...");
    }

    // Public method to get the instance
    public static BankLogger getInstance() {
        if (instance == null) {
            instance = new BankLogger();
        }
        return instance;
    }

    // Logging method
    public void log(String message) {
        System.out.println("[BANK LOG] " + message);
    }
}
