package com.java.design.patterns.singleton;

public class BankingApp {
    public static void main(String[] args) {
        BankLogger logger1 = BankLogger.getInstance();
        logger1.log("Deposit of ₹5000 completed");

        ThreadSafeLogger logger2 = ThreadSafeLogger.getInstance();
        logger2.log("Loan approval for customer ID 12345");

        //System.out.println(logger1 == logger2);  // Output: true
    }
}
