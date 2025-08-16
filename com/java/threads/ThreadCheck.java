package com.java.threads;

public class ThreadCheck {
    public static void main(String[] args) {
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        Thread[] threads = new Thread[tg.activeCount()];
        tg.enumerate(threads);

        System.out.println("👥 Active threads: " + Thread.activeCount());
        for (Thread t : threads) {
            if (t != null) {
                System.out.println("➡️ Thread name: " + t.getName() + ", Daemon: " + t.isDaemon());
            }
        }
    }
}
