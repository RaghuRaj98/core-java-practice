package com.java.collection.list.vector;


public class BankingApp {
    public static void main(String[] args) throws InterruptedException {
        TransactionStore store = new TransactionStore();

        // Thread 1: Deposits
        Thread depositThread = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                store.addTransaction(new Transaction("D" + i, "DEPOSIT", 1000 * i));
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            }
        });

        // Thread 2: Withdrawals
        Thread withdrawThread = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                store.addTransaction(new Transaction("W" + i, "WITHDRAW", 500 * i));
                try { Thread.sleep(120); } catch (InterruptedException ignored) {}
            }
        });

        // Thread 3: Reporting (Reads)
        Thread reportThread = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    Thread.sleep(300);
                    System.out.println("\n[Audit Report] Current Txn Count: " + store.size());
                    store.printAllTransactions();
                }
            } catch (InterruptedException ignored) {}
        });

        // Start threads
        depositThread.start();
        withdrawThread.start();
        reportThread.start();

        depositThread.join();
        withdrawThread.join();
        reportThread.join();

        System.out.println("\n🎉 Final Transaction Count: " + store.size());
    }
}
