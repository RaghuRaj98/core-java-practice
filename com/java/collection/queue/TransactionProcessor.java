package com.java.collection.queue;

import java.util.LinkedList;
import java.util.Queue;

class Transaction {
    private final int id;
    private final String type;
    private final double amount;

    public Transaction(int id, String type, double amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public void process() {
        System.out.println("Processing Transaction #" + id + " | Type: " + type + " | Amount: " + amount);
    }
}

public class TransactionProcessor {
    private Queue<Transaction> queue = new LinkedList<>();

    public void addTransaction(Transaction tx) {
        queue.offer(tx); // add to queue
        System.out.println("Added Transaction #" + tx);
    }

    public void processTransactions() {
        while (!queue.isEmpty()) {
            Transaction tx = queue.poll();
            tx.process();
        }
    }

    public static void main(String[] args) {
        TransactionProcessor processor = new TransactionProcessor();

        processor.addTransaction(new Transaction(101, "Deposit", 5000));
        processor.addTransaction(new Transaction(102, "Withdraw", 2000));
        processor.addTransaction(new Transaction(103, "Loan Payment", 10000));

        processor.processTransactions();
    }
}
