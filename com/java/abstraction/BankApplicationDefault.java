package com.java.abstraction;

interface BankingService2 {
    void deposit(double amount);
    void withdraw(double amount);

    // Default method with body
    default void printMiniStatement() {
        System.out.println("Printing mini statement (default format)...");
    }
}

class SavingsAccount2 implements BankingService2 {
    @Override
    public void deposit(double amount) {
        System.out.println("Depositing " + amount);
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("Withdrawing " + amount);
    }
}

public class BankApplicationDefault {
    public static void main(String[] args) {
        BankAppCopyDefaultMethod bankApp = new BankAppCopyDefaultMethod();
        bankApp.name = "Banking Customer"; // Accessible as it's public
        System.out.println("Banking Customer Name: " + bankApp.accountNumber); //
        BankingService2 acc = new SavingsAccount2();
        acc.deposit(1000);
        acc.printMiniStatement(); // Uses default implementation
    }
}
