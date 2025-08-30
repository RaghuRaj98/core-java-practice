package com.java.threads.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Fraud detection task
class FraudCheckTaskExample implements Runnable {
    private String transactionId;
    private String ruleName;

    public FraudCheckTaskExample(String transactionId, String ruleName) {
        this.transactionId = transactionId;
        this.ruleName = ruleName;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() +
                " running fraud rule: " + ruleName + " for Txn: " + transactionId);
        try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(Thread.currentThread().getName() +
                " finished fraud rule: " + ruleName + " for Txn: " + transactionId);
    }
}

// ATM service task
class ATMTaskExample implements Runnable {
    private String atmId;
    private String operation;

    public ATMTaskExample(String atmId, String operation) {
        this.atmId = atmId;
        this.operation = operation;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() +
                " performing " + operation + " on ATM " + atmId);
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(Thread.currentThread().getName() +
                " completed " + operation + " on ATM " + atmId);
    }
}

public class BankingUnifiedExecutorExample {
    public static void main(String[] args) {
        // One common pool of 4 threads
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Fraud detection tasks
        executor.execute(new FraudCheckTaskExample("TXN1001", "Geo-Location Rule"));
        executor.execute(new FraudCheckTaskExample("TXN1001", "High Amount Rule"));

        // ATM service tasks
        executor.execute(new ATMTaskExample("ATM-101", "Cash Withdrawal"));
        executor.execute(new ATMTaskExample("ATM-102", "Balance Enquiry"));

        // More tasks mixed
        executor.execute(new FraudCheckTaskExample("TXN1002", "Velocity Rule"));
        executor.execute(new ATMTaskExample("ATM-103", "PIN Change"));

        executor.shutdown();
    }
}
