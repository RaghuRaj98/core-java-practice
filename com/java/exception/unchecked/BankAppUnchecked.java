package com.java.exception.unchecked;

class BankServiceUnchecked {

    // 1. NullPointerException - Trying to access customer name when it's null
    public void printCustomerName(String customerName) {
        System.out.println("Customer Name Length: " + customerName.length());
    }

    // 2. ArrayIndexOutOfBoundsException - Accessing invalid transaction index
    public void getTransactionByIndex(int index) {
        String[] transactions = {"Deposit ₹5000", "Withdraw ₹2000", "Deposit ₹3000"};
        System.out.println("Transaction: " + transactions[index]);
    }

    // 3. ArithmeticException - Divide by zero while calculating interest
    public void calculateInterest(int totalAmount, int months) {
        int interestPerMonth = totalAmount / months;
        System.out.println("Interest per month: " + interestPerMonth);
    }

    // 4. IllegalArgumentException - Invalid loan amount
    public void applyForLoan(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Loan amount must be greater than zero");
        }
        System.out.println("Loan approved for ₹" + amount);
    }
}

public class BankAppUnchecked {
    public static void main(String[] args) {
        BankServiceUnchecked bankService = new BankServiceUnchecked();
        // 1. NullPointerException
        try {
            bankService.printCustomerName(null);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught: " + e.getMessage());
        }

        // 2. ArrayIndexOutOfBoundsException
        try {
            bankService.getTransactionByIndex(5);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException caught: " + e.getMessage());
        }

        // 3. ArithmeticException
        try {
            bankService.calculateInterest(10000, 0);
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException caught: " + e.getMessage());
        }

        // 4. IllegalArgumentException
        try {
            bankService.applyForLoan(-5000);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException caught: " + e.getMessage());
        }
    }
}
