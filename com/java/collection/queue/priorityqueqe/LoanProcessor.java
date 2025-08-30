package com.java.collection.queue.priorityqueqe;

import java.util.Comparator;
import java.util.PriorityQueue;

class LoanRequest {
    private final int customerId;
    private final String loanType;
    private final double amount;
    private final int priority; // Higher = more important

    public LoanRequest(int customerId, String loanType, double amount, int priority) {
        this.customerId = customerId;
        this.loanType = loanType;
        this.amount = amount;
        this.priority = priority;
    }

    public int getPriority() { return priority; }

    public void process() {
        System.out.println("Processing Loan for Customer #" + customerId +
                " | Type: " + loanType +
                " | Amount: " + amount +
                " | Priority: " + priority);
    }
}

public class LoanProcessor {
    public static void main(String[] args) {
        PriorityQueue<LoanRequest> loanQueue = new PriorityQueue<>(
                Comparator.comparingInt(LoanRequest::getPriority).reversed() // Higher priority first
        );

        loanQueue.offer(new LoanRequest(101, "Home Loan", 5000000, 5));
        loanQueue.offer(new LoanRequest(102, "Car Loan", 800000, 2));
        loanQueue.offer(new LoanRequest(103, "Business Loan", 10000000, 8));
        loanQueue.offer(new LoanRequest(104, "Personal Loan", 300000, 3));

        while (!loanQueue.isEmpty()) {
            loanQueue.poll().process();
        }
    }
}
