package com.java.threads;

// External service for authentication
class AuthenticationService {
    public boolean authenticate(String cardNumber, String pin) throws InterruptedException {
        System.out.println("🔐 Authenticating card: " + cardNumber);
        Thread.sleep(200000); // Simulating server delay
        if ("1234".equals(pin)) {
            System.out.println("✅ Authentication successful for card: " + cardNumber);
            return true;
        }
        System.out.println("❌ Authentication failed for card: " + cardNumber);
        return false;
    }
}

// External service for fraud detection
class FraudCheckService {
    public boolean checkFraud(String cardNumber, int amount) throws InterruptedException {
        System.out.println("🔍 Running fraud checks for card: " + cardNumber + " | Amount: ₹" + amount);
        Thread.sleep(1500); // Simulating fraud check delay
        if (amount > 100000) {
            System.out.println("🚨 Fraud detected for transaction: ₹" + amount);
            return false;
        }
        System.out.println("✅ No fraud detected.");
        return true;
    }
}

// External service for core banking system
class CoreBankingService {
    private int balance = 50000; // Default balance for simplicity

    public synchronized boolean debit(String cardNumber, int amount) throws InterruptedException {
        System.out.println("🏦 Connecting to Core Banking System...");
        Thread.sleep(3000); // Simulating bank server delay

        if (amount <= balance) {
            balance -= amount;
            System.out.println("💸 Transaction successful! Debited: ₹" + amount + " | Remaining balance: ₹" + balance);
            return true;
        } else {
            System.out.println("❌ Insufficient balance for transaction: ₹" + amount);
            return false;
        }
    }
}

// Thread representing ATM transaction
class ATMTransactionSimulate extends Thread {
    private String customerName;
    private String cardNumber;
    private String pin;
    private int amount;

    private AuthenticationService authService;
    private FraudCheckService fraudService;
    private CoreBankingService coreBanking;

    public ATMTransactionSimulate(String customerName, String cardNumber, String pin, int amount,
                          AuthenticationService authService, FraudCheckService fraudService,
                          CoreBankingService coreBanking) {
        this.customerName = customerName;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.amount = amount;
        this.authService = authService;
        this.fraudService = fraudService;
        this.coreBanking = coreBanking;
    }

    @Override
    public void run() {
        try {
            System.out.println("\n👤 Customer: " + customerName + " started transaction...");

            // Step 1: Authenticate
            if (!authService.authenticate(cardNumber, pin)) {
                System.out.println("❌ Transaction failed due to authentication error for " + customerName);
                return;
            }

            // Step 2: Fraud check
            if (!fraudService.checkFraud(cardNumber, amount)) {
                System.out.println("❌ Transaction blocked due to fraud detection for " + customerName);
                return;
            }

            // Step 3: Debit from core banking system
            if (coreBanking.debit(cardNumber, amount)) {
                System.out.println("✅ Transaction completed successfully for " + customerName + ". Dispensing cash...");
                Thread.sleep(2000); // Simulate cash dispensing
                System.out.println("💵 Cash dispensed to " + customerName + ": ₹" + amount);
            } else {
                System.out.println("❌ Transaction failed due to insufficient funds for " + customerName);
            }

        } catch (InterruptedException e) {
            System.out.println("⚠ Transaction interrupted for " + customerName);
        }
    }
}

// Main class
public class BankingSimulation {
    public static void main(String[] args) {
        AuthenticationService authService = new AuthenticationService();
        FraudCheckService fraudService = new FraudCheckService();
        CoreBankingService coreBanking = new CoreBankingService();

        // Two customers trying to withdraw at the same time
        ATMTransactionSimulate t1 = new ATMTransactionSimulate("Raghav", "1111-2222-3333", "1234", 20000,
                authService, fraudService, coreBanking);

        ATMTransactionSimulate t2 = new ATMTransactionSimulate("Raj", "4444-5555-6666", "1234", 40000,
                authService, fraudService, coreBanking);

        t1.start();
        t2.start();
    }
}
