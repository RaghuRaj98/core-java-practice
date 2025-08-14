package com.java.core;

interface PaymentGateway {
    default void transactionReport() {
        System.out.println("PaymentGateway: Generating basic payment report...");
    }
}

interface FraudDetection {
    default void transactionReport() {
        System.out.println("FraudDetection: Generating fraud check report...");
    }
}

class BankTransaction implements PaymentGateway, FraudDetection {
    @Override
    public void transactionReport() {
        // Explicitly choosing which default method to use
        PaymentGateway.super.transactionReport(); // Call PaymentGateway's version
        FraudDetection.super.transactionReport(); // Call FraudDetection's version
        System.out.println("BankTransaction: Merged report with payment + fraud details.");
    }
}

public class BankingApp {
    public static void main(String[] args) {
        BankTransaction bt = new BankTransaction();
        bt.transactionReport();
    }
}
