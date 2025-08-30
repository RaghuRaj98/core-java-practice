package com.java.threads.threadgroup;

class BankTask extends Thread {
    public BankTask(ThreadGroup group, String name) {
        super(group, name);
    }

    @Override
    public void run() {
        System.out.println(getThreadGroup().getName() + " -> " + getName() + " started");
        try { Thread.sleep(2000); } catch (InterruptedException e) { }
        System.out.println(getThreadGroup().getName() + " -> " + getName() + " finished");
    }
}

public class ThreadGroupExample {
    public static void main(String[] args) {
        // Create two thread groups
        ThreadGroup atmGroup = new ThreadGroup("ATMServicesGroup");
        ThreadGroup fraudGroup = new ThreadGroup("FraudDetectionGroup");

        // ATM service threads
        BankTask t1 = new BankTask(atmGroup, "Cash Withdrawal");
        BankTask t2 = new BankTask(atmGroup, "Balance Enquiry");

        // Fraud detection threads
        BankTask f1 = new BankTask(fraudGroup, "Geo Location Rule");
        BankTask f2 = new BankTask(fraudGroup, "High Amount Rule");

        t1.start();
        t2.start();
        f1.start();
        f2.start();

        // Print details of groups
        atmGroup.list();
        fraudGroup.list();

        // Example: Interrupt all fraud detection tasks together
        fraudGroup.interrupt();
    }
}
