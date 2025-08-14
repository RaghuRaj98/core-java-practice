// File: com.bank.account.BankAccount.java
package com.java.access.base;

public class BankAccount {
    protected String accountHolderName;   // Can be accessed everywhere
    public double balance;          // Accessible in subclasses
    String accountType;                // Default access
    public String pin;                 // Only inside BankAccount

    public BankAccount(String name, double initialBalance, String pin) {
        this.accountHolderName = name;
        this.balance = initialBalance;
        this.pin = pin;
    }

    public void deposit(double amount) { // Public API
        balance += amount;
    }

    protected void withdraw(double amount) { // Protected for subclasses
        balance -= amount;
    }

    private boolean authenticate(String inputPin) { // Private method
        return this.pin.equals(inputPin);
    }
}
