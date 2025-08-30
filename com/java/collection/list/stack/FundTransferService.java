package com.java.collection.list.stack;

import java.util.Stack;

class Operation {
    String description;

    public Operation(String description) {
        this.description = description;
    }

    public void execute() {
        System.out.println("Executing: " + description);
    }

    public void rollback() {
        System.out.println("Rollback: " + description);
    }
}

public class FundTransferService {
    private Stack<Operation> stack = new Stack<>();

    public void transferFunds() {
        try {
            Operation debit = new Operation("Debit Sender Account");
            debit.execute();
            stack.push(debit);

            Operation credit = new Operation("Credit Receiver Account");
            credit.execute();
            stack.push(credit);

            // Simulate failure
            if (true) throw new RuntimeException("System error while crediting!");

            Operation audit = new Operation("Log Audit Entry");
            audit.execute();
            stack.push(audit);

        } catch (Exception e) {
            System.out.println("❌ Error occurred: " + e.getMessage());
            rollbackOperations();
        }
    }

    private void rollbackOperations() {
        while (!stack.isEmpty()) {
            Operation op = stack.pop();
            op.rollback();
        }
    }

    public static void main(String[] args) {
        new FundTransferService().transferFunds();
    }
}
