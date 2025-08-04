package com.java.design.patterns.singleton;

public class BankingApp {
    public static void main(String[] args) {
        BankLogger logger1 = BankLogger.getInstance();
        logger1.log("Deposit of ₹5000 completed");

        BankLogger logger2 = BankLogger.getInstance();
        logger2.log("Loan approval for customer ID 12345");

        System.out.println(logger1 == logger2);  // Output: true
    }
}
