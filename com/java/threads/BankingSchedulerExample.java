package com.java.threads;

class FundTransfer extends Thread {
    public FundTransfer() {
        setPriority(Thread.MAX_PRIORITY); // highest priority
    }
    public void run() {
        System.out.println("💰 Processing fund transfer...");
    }
}

class SMSNotification extends Thread {
    public SMSNotification() {
        setPriority(Thread.NORM_PRIORITY); // medium priority
    }
    public void run() {
        System.out.println("📩 Sending SMS notification...");
    }
}

class TransactionLog extends Thread {
    public TransactionLog() {
        setPriority(Thread.MIN_PRIORITY); // lowest priority
    }
    public void run() {
        System.out.println("📝 Updating transaction log...");
    }
}

public class BankingSchedulerExample {
    public static void main(String[] args) {
        FundTransfer t1 = new FundTransfer();
        SMSNotification t2 = new SMSNotification();
        TransactionLog t3 = new TransactionLog();

        t3.start(); // low priority
        t2.start(); // medium priority
        t1.start(); // high priority
    }
}
