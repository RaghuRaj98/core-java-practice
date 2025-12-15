package com.dec.synchronization;

public class BankAccount {

    private double balance;

    public BankAccount(double openingBalance) {
        this.balance = openingBalance;
    }

    // NOT thread-safe
    public void deposit(double amount) {
        double current = balance;          // read
        sleep(100);                        // simulate delay
        balance = current + amount;        // write
    }

    // NOT thread-safe
    public void withdraw(double amount) {
        if (balance >= amount) {
            double current = balance;      // read
            sleep(100);                    // simulate delay
            balance = current - amount;    // write
        } else {
            System.out.println("Insufficient funds for withdrawal: " + amount);
        }
    }

    public double getBalance() {
        return balance;
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(1000);

        // Thread 1 → loan approval adds money
        Thread loanThread = new Thread(() -> {
            System.out.println("Loan approved: +500");
            account.deposit(500);
        });

        // Thread 2 → user trying to withdraw
        Thread withdrawThread = new Thread(() -> {
            System.out.println("Trying to withdraw: 700");
            account.withdraw(700);
        });

        loanThread.start();
        withdrawThread.start();

        loanThread.join();
        withdrawThread.join();

        System.out.println("Final balance (NO sync): " + account.getBalance());
    }
}
