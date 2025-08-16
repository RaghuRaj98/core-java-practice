package com.java.exception.exthrows;

import java.io.FileWriter;
import java.io.IOException;

class BankingLogger {
    void logTransaction(String data) throws IOException {
        FileWriter writer = new FileWriter("transactions.txt", true);
        writer.write(data + "\n");
        writer.close();
    }
}

public class Main {
    public static void main(String[] args) {
        BankingLogger logger = new BankingLogger();
        try {
            logger.logTransaction("Transfer: $500 to Account XYZ");
            System.out.println("Transaction logged successfully!");
        } catch (IOException e) {
            System.out.println("Failed to log transaction: " + e.getMessage());
        }
    }
}
