package com.java.threads.threadpool;

import java.util.concurrent.*;

class FraudCheckTask implements Runnable {
    private String ruleName;
    private String transactionId;

    public FraudCheckTask(String transactionId, String ruleName) {
        this.transactionId = transactionId;
        this.ruleName = ruleName;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + 
            " running " + ruleName + " for Txn: " + transactionId);
        try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(Thread.currentThread().getName() + 
            " finished " + ruleName + " for Txn: " + transactionId);
    }
}

public class FraudDetectionExample {
    public static void main(String[] args) {
        ExecutorService fraudExecutor = Executors.newFixedThreadPool(3);

        String txnId = "TXN1001";

        fraudExecutor.execute(new FraudCheckTaskExample(txnId, "Geo-Location Rule"));
        fraudExecutor.execute(new FraudCheckTaskExample(txnId, "High-Amount Rule"));
        fraudExecutor.execute(new FraudCheckTaskExample(txnId, "Blacklisted Account Rule"));
        fraudExecutor.execute(new FraudCheckTaskExample(txnId, "Velocity Rule"));

        fraudExecutor.shutdown();
    }
}
