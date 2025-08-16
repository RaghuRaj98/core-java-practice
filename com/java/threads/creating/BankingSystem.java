package com.java.threads.creating;

// SMS Notification task
class SMSNotification implements Runnable {
    public void run() {
        System.out.println("📩 Sending SMS notification...");
        try {
            Thread.sleep(1000); // simulate delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("✅ SMS sent successfully!");
    }
}

// Transaction Log task
class TransactionLog implements Runnable {
    public void run() {
        System.out.println("📝 Updating transaction log...");
        try {
            Thread.sleep(1500); // simulate delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("✅ Transaction log updated!");
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Thread smsThread = new Thread(new SMSNotification());
        Thread logThread = new Thread(new TransactionLog());

        smsThread.start();
        logThread.start();
    }
}
