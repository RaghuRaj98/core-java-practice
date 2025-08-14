package com.java.abstraction;

// Interface declaration
interface BankingService {
    // Constants (public static final by default)
    double MIN_BALANCE = 1000.0;

    // Abstract methods (public abstract by default)
    void deposit(double amount);
    void withdraw(double amount);
    double checkBalance();
}

// Implementation for a Savings Account
class SavingsAccount implements BankingService {
    private double balance;

    SavingsAccount(double initialDeposit) {
        if (initialDeposit < MIN_BALANCE) {
            throw new IllegalArgumentException("Initial deposit must be at least " + MIN_BALANCE);
        }
        this.balance = initialDeposit;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount + ", New Balance: " + balance);
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount < MIN_BALANCE) {
            System.out.println("Withdrawal denied! Minimum balance requirement not met.");
        } else {
            balance -= amount;
            System.out.println("Withdrew: " + amount + ", New Balance: " + balance);
        }
    }

    @Override
    public double checkBalance() {
        return balance;
    }
}

// Main class
public class BankApp {
    public static void main(String[] args) {
        BankingService account = new SavingsAccount(5000);
        account.deposit(2000);
        account.withdraw(3000);
        System.out.println("Final Balance: " + account.checkBalance());
    }
}
