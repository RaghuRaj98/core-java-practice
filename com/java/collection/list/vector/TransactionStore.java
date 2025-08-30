package com.java.collection.list.vector;

import java.util.Vector;

public class TransactionStore {
    private final Vector<Transaction> transactions = new Vector<>();

    public void addTransaction(Transaction txn) {
        transactions.add(txn); // thread-safe
    }

    public void printAllTransactions() {
        System.out.println("==== Transaction History ====");
        for (Transaction txn : transactions) {
            System.out.println(txn);
        }
    }

    public int size() {
        return transactions.size();
    }
}
