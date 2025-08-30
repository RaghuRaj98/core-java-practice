package com.java.threads;

class FetchAccountDetails extends Thread {
    public void run() {
        try {
            System.out.println("Fetching account details...");
            Thread.sleep(2000); // simulate DB call
            System.out.println("Account details fetched ✅");
        } catch (InterruptedException e) {}
    }
}

class PaymentProcessing extends Thread {
    public void run() {
        try {
            System.out.println("Processing payment...");
            Thread.sleep(3000); // simulate external API call
            System.out.println("Payment successful ✅");
        } catch (InterruptedException e) {}
    }
}

class NotificationService extends Thread {
    public void run() {
        try {
            System.out.println("Sending notification...");
            Thread.sleep(1000); // simulate SMS/Email
            System.out.println("Notification sent 📩");
        } catch (InterruptedException e) {}
    }
}

public class BankingJoinExample {
    public static void main(String[] args) throws InterruptedException {
        FetchAccountDetails fetch = new FetchAccountDetails();
        PaymentProcessing payment = new PaymentProcessing();
        NotificationService notify = new NotificationService();

        // Start account fetch & payment in parallel
        fetch.start();
        payment.start();

        // Ensure both are completed before sending notification
        fetch.join();
        payment.join();

        // Now send notification
        notify.start();
    }
}
