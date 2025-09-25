package com.java.design.patterns.singleton;

public class ThreadSafeLogger {

    private static ThreadSafeLogger instance;

    private ThreadSafeLogger() {}

    public static  ThreadSafeLogger getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeLogger.class) {
               {
                    instance = new ThreadSafeLogger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[THREAD-SAFE LOG] " + message);
    }
}
