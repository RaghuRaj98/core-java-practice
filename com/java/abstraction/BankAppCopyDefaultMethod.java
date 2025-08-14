package com.java.abstraction;

interface ATMService {
    default void transactionSummary() {
        System.out.println("ATM Transaction Summary");
    }
}

interface MobileBankingService {
    default void transactionSummary() {
        System.out.println("Mobile Banking Transaction Summary");
    }
}

class BankingCustomer implements ATMService, MobileBankingService {
    @Override
    public void transactionSummary() {
        // Resolve ambiguity using interface name
        ATMService.super.transactionSummary();
        MobileBankingService.super.transactionSummary();
        System.out.println("Combined Transaction Summary");
    }
}

public class BankAppCopyDefaultMethod {
    public String name = "Banking Customer";
    String accountNumber = "123456789";

    public static void main(String[] args) {
        ATMService customer = new BankingCustomer();
        customer.transactionSummary();
    }
}
