package com.java.design.patterns.singleton;

public class ThreadSafeLogger {

    private static ThreadSafeLogger instance;

    private ThreadSafeLogger() {}

    public static synchronized ThreadSafeLogger getInstance() {
        if (instance == null) {
            instance = new ThreadSafeLogger();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[THREAD-SAFE LOG] " + message);
    }
}
