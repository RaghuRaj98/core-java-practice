package com.java.threads.priority;

class FraudDetectionTask extends Thread {
    public void run() {
        System.out.println("🔍 Fraud detection running...");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("✅ Fraud detection complete.");
    }
}

class PaymentProcessingTask extends Thread {
    public void run() {
        System.out.println("💳 Payment processing started...");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        System.out.println("✅ Payment processed successfully.");
    }
}

class NotificationTask extends Thread {
    public void run() {
        System.out.println("📩 Sending notifications...");
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        System.out.println("✅ Notifications sent.");
    }
}

public class BankingPriorityExample {
    public static void main(String[] args) {
        FraudDetectionTask fraudTask = new FraudDetectionTask();
        PaymentProcessingTask paymentTask = new PaymentProcessingTask();
        NotificationTask notificationTask = new NotificationTask();

        // Assigning priorities
        fraudTask.setPriority(Thread.MAX_PRIORITY);       // 10
        paymentTask.setPriority(Thread.NORM_PRIORITY);    // 5
        notificationTask.setPriority(Thread.MIN_PRIORITY);// 1

        // Start tasks
        fraudTask.start();
        paymentTask.start();
        notificationTask.start();

        System.out.println("👥 Active threads: " + Thread.activeCount());
    }
}
