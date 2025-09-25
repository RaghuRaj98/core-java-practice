package com.java.multithreading;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ExecutorServiceQuickRev.java
 * - Demonstrates core ExecutorService usage in a concise runnable example.
 * Paste into IDE and run.
 */
public class ExecutorServiceQuickRev {

    static class SimpleTask implements Runnable {
        private final int id;
        SimpleTask(int id) { this.id = id; }
        @Override
        public void run() {
            String tname = Thread.currentThread().getName();
            System.out.println(tname + " START task " + id);
            try {
                // simulate variable work
                Thread.sleep(200 + (id % 5) * 100L);
                if (id % 7 == 0) throw new RuntimeException("simulated failure for " + id);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                System.out.println(tname + " INTERRUPTED task " + id);
            }
            System.out.println(tname + " END   task " + id);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== ExecutorService quick demo ===");

        // 1) simple fixed thread pool
        ExecutorService fixedPool = Executors.newFixedThreadPool(10);
        System.out.println("\n-- Submitting 6 tasks to fixedPool (4 threads) --");
        for (int i = 1; i <= 6; i++) fixedPool.submit(new SimpleTask(i));
        shutdownGracefully(fixedPool, 5, TimeUnit.SECONDS);

        // 2) custom ThreadPoolExecutor (bounded queue + custom rejection handler)
        System.out.println("\n-- Custom ThreadPoolExecutor with bounded queue & rejection handler --");
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(2);
        AtomicInteger threadCounter = new AtomicInteger(1);
        ThreadFactory tf = r -> new Thread(r, "custom-worker-" + threadCounter.getAndIncrement());

        RejectedExecutionHandler rejectHandler = (r, executor) -> {
            System.err.println("TASK REJECTED: queue full - handling (fallback persist/log).");
            // In prod: persist task to durable queue (DB/Kafka). Here we just log.
        };

        ThreadPoolExecutor customPool = new ThreadPoolExecutor(
            2,            // core
            4,            // max
            30, TimeUnit.SECONDS,
            queue,
            tf,
            rejectHandler
        );

        // Submit more tasks than capacity to trigger rejection behavior
        for (int i = 1; i <= 10; i++) {
            final int id = i;
            try {
                customPool.execute(() -> {
                    new SimpleTask(id).run();
                });
            } catch (RejectedExecutionException rex) {
                System.err.println("submit threw RejectedExecutionException for task " + id);
            }
        }

        // Print simple metrics periodically
        ScheduledExecutorService monitor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "pool-monitor"); t.setDaemon(true); return t;
        });
        monitor.scheduleAtFixedRate(() -> {
            System.out.println(String.format("CUSTOM POOL: active=%d poolSize=%d queue=%d completed=%d",
                customPool.getActiveCount(), customPool.getPoolSize(), customPool.getQueue().size(), customPool.getCompletedTaskCount()));
        }, 0, 1, TimeUnit.SECONDS);

        // Let tasks run a bit then shutdown
        Thread.sleep(3000);
        shutdownGracefully(customPool, 5, TimeUnit.SECONDS);
        monitor.shutdownNow();

        // 3) ScheduledExecutorService (delayed + periodic)
        System.out.println("\n-- ScheduledExecutorService demo --");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        scheduler.schedule(() -> System.out.println("Delayed task executed after 1s"), 1, TimeUnit.SECONDS);
        ScheduledFuture<?> periodic = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Periodic heartbeat at " + new Date());
        }, 0, 1, TimeUnit.SECONDS);

        Thread.sleep(3200);
        periodic.cancel(true);
        shutdownGracefully(scheduler, 3, TimeUnit.SECONDS);

        // 4) CompletableFuture with custom executor
        System.out.println("\n-- CompletableFuture with custom executor --");
        ExecutorService cfExecutor = Executors.newFixedThreadPool(2, tf);
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " supplyAsync running");
            return "RESULT";
        }, cfExecutor).thenApplyAsync(s -> {
            System.out.println(Thread.currentThread().getName() + " thenApplyAsync got " + s);
            return s + "-ok";
        }, cfExecutor);

        System.out.println("CompletableFuture result: " + cf.get(3, TimeUnit.SECONDS));
        shutdownGracefully(cfExecutor, 3, TimeUnit.SECONDS);

        System.out.println("\n=== Demo complete ===");
    }

    // Helper: graceful shutdown with timeout, then force shutdown
    static void shutdownGracefully(ExecutorService ex, long timeout, TimeUnit unit) {
        ex.shutdown(); // disable new tasks
        try {
            if (!ex.awaitTermination(timeout, unit)) {
                System.out.println("Forcing shutdown...");
                List<Runnable> dropped = ex.shutdownNow(); // returns tasks that never commenced
                System.out.println("Dropped tasks count: " + dropped.size());
            } else {
                System.out.println("Shutdown completed gracefully.");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            ex.shutdownNow();
        }
    }
}
