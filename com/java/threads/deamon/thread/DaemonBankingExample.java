package com.java.threads.deamon.thread;

class AuditLogService extends Thread {
    public void run() {
        while (true) {
            System.out.println("📝 Audit log: recording transaction activity...");
            try {
                Thread.sleep(1000); // background logging every 1 sec
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class BankingTransaction extends Thread {
    private String transactionName;

    BankingTransaction(String transactionName) {
        this.transactionName = transactionName;
    }

    public void run() {
        System.out.println("💳 Processing transaction: " + transactionName);
        try {
            Thread.sleep(2000); // simulate transaction time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("✅ Transaction complete: " + transactionName);
    }
}

public class DaemonBankingExample {
    public static void main(String[] args) {
        // Daemon thread for audit logging
        AuditLogService auditThread = new AuditLogService();
        auditThread.setDaemon(true); // must be called before start
        auditThread.start();

        // User threads (banking transactions)
        BankingTransaction t1 = new BankingTransaction("Money Transfer");
        BankingTransaction t2 = new BankingTransaction("Bill Payment");

        t1.start();
        t2.start();

        // JVM will stop auditThread once all transactions (user threads) complete
    }
}
