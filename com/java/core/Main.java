package com.java.core;

class BankAccount {
    public static void interestRate() {
        System.out.println("Base interest rate: 3%");
    }
}

class SavingsAccount extends BankAccount {
    // This is not overriding — it's hiding the method
    public static void interestRate() {
        System.out.println("Savings account interest rate: 4%");
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount account = new SavingsAccount();
        account.interestRate(); // Calls BankAccount's method (compile-time binding)

        SavingsAccount sa = new SavingsAccount();
        sa.interestRate(); // Calls SavingsAccount's method
    }
}
