package com.java.exception.thro;

// Checked Exception
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Unchecked Exception
class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(String message) {
        super(message);
    }
}

public class BankService {

    public void processTransaction(String accountNumber, double amount) throws InsufficientFundsException {

        // Unchecked Exception example
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new InvalidAccountException("Account number is invalid");
        }

        // Checked Exception example
        if (amount > 5000) {
            throw new InsufficientFundsException("Insufficient balance for withdrawal of: " + amount);
        }

        System.out.println("Transaction successful for account: " + accountNumber);
    }

    public static void main(String[] args) {
        BankService bank = new BankService();

        try {
            // First will fail due to invalid account
            bank.processTransaction("", 3000);
        } 
        catch (InvalidAccountException e) {
            System.out.println("Runtime Exception caught: " + e.getMessage());
        }
        catch (InsufficientFundsException e) {
            System.out.println("Checked Exception caught: " + e.getMessage());
        }

        try {
            // This will fail due to insufficient funds
            bank.processTransaction("ACC12345", 6000);
        } 
        catch (InvalidAccountException e) {
            System.out.println("Runtime Exception caught: " + e.getMessage());
        }
        catch (InsufficientFundsException e) {
            System.out.println("Checked Exception caught: " + e.getMessage());
        }
    }
}
