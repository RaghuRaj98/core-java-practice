package com.java.exception.finall;

class BankingTransaction {
    public static void main(String[] args) {
        String dbConnection = null;

        try {
            // Step 1: Connect to database
            dbConnection = "Connected to Banking DB";
            System.out.println(dbConnection);

            // Step 2: Process transaction
            int balance = 5000;
            int withdrawalAmount = 6000; // More than balance → will cause error

            if (withdrawalAmount > balance) {
                throw new Exception("Insufficient Balance!");
            }

            balance -= withdrawalAmount;
            System.out.println("Transaction successful! Remaining balance: " + balance);
        }
        catch (Exception e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
        finally {
            // Step 3: Always close the connection
            System.out.println("Closing database connection...");
            dbConnection = null;
        }

        System.out.println("End of banking process.");
    }
}
