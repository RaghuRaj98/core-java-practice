package com.dec.multithreading;

public class BankAccount {

    private double balance;

    public BankAccount(double openingBalance) {
        this.balance = openingBalance;
    }

    // Thread-safe now
    public synchronized void deposit(double amount) {
        double current = balance;
        sleep(100);
        balance = current + amount;
    }

    // Thread-safe now
    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            double current = balance;
            sleep(100);
            balance = current - amount;
        } else {
            System.out.println("Insufficient funds for withdrawal: " + amount);
        }
    }

    public synchronized double getBalance() {
        return balance;
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(1000);

        Thread loanThread = new Thread(() -> {
            System.out.println("Loan approved: +500");
            account.deposit(500);
        });

        Thread withdrawThread = new Thread(() -> {
            System.out.println("Trying to withdraw: 700");
            account.withdraw(700);
        });

        loanThread.start();
        withdrawThread.start();

        loanThread.join();
        withdrawThread.join();

        System.out.println("Final balance (WITH sync): " + account.getBalance());
    }
}
