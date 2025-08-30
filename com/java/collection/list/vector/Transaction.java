package com.java.collection.list.vector;

import java.time.LocalDateTime;

public class Transaction {
    private String txnId;
    private String type;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(String txnId, String type, double amount) {
        this.txnId = txnId;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return txnId + " | " + type + " | " + amount + " | " + timestamp;
    }
}
