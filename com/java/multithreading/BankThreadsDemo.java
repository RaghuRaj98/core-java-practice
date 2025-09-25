package com.java.multithreading;

import java.util.*;
import java.util.concurrent.*;

/**
 * BankThreadsDemo.java
 *
 * Demonstrates Thread methods in a banking scenario:
 * - start(): spawn teller threads
 * - run(): direct call (executes on current thread, no new thread)
 * - sleep(ms): teller simulates service time
 * - join(): supervisor waits for tellers to finish
 * - yield(): teller gives up CPU briefly during long operations (hint)
 * - interrupt(): supervisor interrupts a teller (simulated outage)
 * - isAlive(): check teller thread status
 *
 * Run: java BankThreadsDemo
 */
public class BankThreadsDemo {

    // Simple customer record
    static class Customer {
        final int id;
        final int serviceTimeMs; // how long teller should serve this customer
        Customer(int id, int serviceTimeMs) { this.id = id; this.serviceTimeMs = serviceTimeMs; }
        @Override public String toString() { return "C#" + id + "(t=" + serviceTimeMs + "ms)"; }
    }

    // Shared customer queue (blocking)
    static class CustomerQueue {
        private final BlockingQueue<Customer> q = new LinkedBlockingQueue<>();
        void add(Customer c) {
            q.offer(c);
        }
        Customer poll(long timeoutMs) throws InterruptedException {
            return q.poll(timeoutMs, TimeUnit.MILLISECONDS);
        }
    }

    // Teller thread: repeatedly polls customer queue and serves customers.
    static class Teller extends Thread {
        private final CustomerQueue queue;
        private final int tellerId;
        private volatile boolean running = true;

        Teller(int tellerId, CustomerQueue queue) {
            super("Teller-" + tellerId);
            this.tellerId = tellerId;
            this.queue = queue;
        }

        // Graceful stop requested by setting running=false or interrupt()
        public void shutdown() { running = false; this.interrupt(); }

        @Override
        public void run() {
            log("STARTED");
            try {
                while (running) {
                    // wait up to 1s for a customer, otherwise check running flag
                    Customer c = queue.poll(1000);
                    if (c == null) {
                        // no customer now — loop again (and show isAlive usage by supervisor)
                        // simulate cooperative yield if busy loop occurs
                        Thread.yield(); // hint to scheduler
                        continue;
                    }
                    log("Picked " + c + " — serving...");
                    try {
                        // simulate service time; sleep is interruptible
                        int step = Math.min(200, c.serviceTimeMs);
                        int remaining = c.serviceTimeMs;
                        while (remaining > 0) {
                            Thread.sleep(step);
                            remaining -= step;
                            // occasionally yield during long service to be polite to scheduler
                            if (remaining > 0 && remaining % 300 == 0) Thread.yield();
                        }
                        log("Completed " + c);
                    } catch (InterruptedException ie) {
                        // interrupted while serving -> simulate aborting the service
                        log("Interrupted during service of " + c + " -> aborting that customer");
                        Thread.currentThread().interrupt(); // restore interrupt flag
                        // break or continue based on policy; here we break to stop teller
                        break;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                log("TERMINATING");
            }
        }

        private void log(String msg) {
            System.out.printf("[%s] %s%n", getName(), msg);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== BankThreadsDemo: demonstrate Thread methods in a bank tellers scenario ===\n");

        CustomerQueue queue = new CustomerQueue();

        // populate some customers (variable service times)
        for (int i = 1; i <= 12; i++) {
            int svc = 200 + (i % 5) * 150; // 200,350,500,650,800 ...
            queue.add(new Customer(i, svc));
        }

        // Create two teller threads and start them
        Teller t1 = new Teller(1, queue);
        Teller t2 = new Teller(2, queue);

        System.out.println("-- Demonstrate start() -> new threads are created --");
        t1.start(); // new OS-level thread (or JVM thread)
        t2.start();

        // Demonstrate calling run() directly (executes on main thread)
        System.out.println("\n-- Demonstrate run() called directly (no new thread) --");
        Teller inlineTeller = new Teller(99, queue);
        System.out.println("Calling inlineTeller.run() — this will run on MAIN thread, not create a new thread");
        inlineTeller.run(); // runs in main thread synchronously; not recommended for concurrency demos

        // Show isAlive() while tellers are processing
        Thread.sleep(300);
        System.out.println("\n-- isAlive() checks --");
        System.out.println("t1.isAlive() = " + t1.isAlive());
        System.out.println("t2.isAlive() = " + t2.isAlive());

        // Wait a bit and then interrupt one teller to simulate an outage
        Thread.sleep(1000);
        System.out.println("\n-- Simulate outage: interrupt t2 --");
        System.out.println("Main interrupts Teller-2 to simulate a sudden failure");
        t2.interrupt(); // signal interrupt (will cause sleep to throw InterruptedException if sleeping)

        // Meanwhile main thread can wait for tellers to finish via join()
        System.out.println("\n-- Main waits (join) for t1 and t2 to terminate --");
        // Wait up to a reasonable time; join() blocks current thread until target finishes
        t1.join(5000); // wait up to 5s
        System.out.println("After join(5s) t1.isAlive() = " + t1.isAlive());

        // If still alive after join, request shutdown and interrupt
        if (t1.isAlive()) {
            System.out.println("t1 still alive — request graceful shutdown and interrupt");
            t1.shutdown();
            t1.join(); // wait until it terminates
        }

        // Ensure t2 terminated (it may have been interrupted earlier)
        if (t2.isAlive()) {
            System.out.println("t2 still alive — request graceful shutdown and interrupt");
            t2.shutdown();
            t2.join();
        }

        System.out.println("\n-- All tellers terminated. Main exits --");
    }
}
