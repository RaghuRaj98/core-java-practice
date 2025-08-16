package com.java.exception.overridingexception;

import java.io.IOException;

// Superclass representing general banking service
class BankService {
    void processTransaction() throws IOException {
        System.out.println("Processing transaction in BankService...");
    }
}

// Subclass for Internet Banking service
class InternetBankingService extends BankService {
    // Allowed: Declaring the same exception
    @Override
    void processTransaction() throws IOException {
        System.out.println("Processing transaction via Internet Banking...");
    }
}

// Subclass for ATM Banking service
class ATMService extends BankService {
    // Allowed: Declaring a subclass exception
    @Override
    void processTransaction() throws java.io.FileNotFoundException {
        System.out.println("Processing transaction via ATM...");
    }
}

// Subclass for Mobile Banking service
class MobileBankingService extends BankService {
    // Allowed: No exception declared
    @Override
    void processTransaction() {
        System.out.println("Processing transaction via Mobile Banking...");
    }
}

public class BankingApp {
    public static void main(String[] args) {
        try {
            BankService service = new InternetBankingService();
            service.processTransaction();

            service = new ATMService();
            service.processTransaction();

            service = new MobileBankingService();
            service.processTransaction();

        } catch (IOException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
}
