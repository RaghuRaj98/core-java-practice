package com.java.threads.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ATMTask implements Runnable {
    private String atmId;
    private String operation;

    public ATMTask(String atmId, String operation) {
        this.atmId = atmId;
        this.operation = operation;
    }

    @Override
    public void run() {
        System.out.println("ATM " + atmId + " performing " + operation + " using " 
                           + Thread.currentThread().getName());
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("ATM " + atmId + " completed " + operation);
    }
}

public class ATMProcessingExample {
    public static void main(String[] args) {
        ExecutorService atmExecutor = Executors.newFixedThreadPool(4);

        atmExecutor.execute(new ATMTaskExample("ATM-101", "Cash Withdrawal"));
        atmExecutor.execute(new ATMTaskExample("ATM-102", "Balance Enquiry"));
        atmExecutor.execute(new ATMTaskExample("ATM-103", "PIN Change"));
        atmExecutor.execute(new ATMTaskExample("ATM-104", "Mini Statement"));
        atmExecutor.execute(new ATMTaskExample("ATM-105", "Cash Deposit"));

        atmExecutor.shutdown();
    }
}
