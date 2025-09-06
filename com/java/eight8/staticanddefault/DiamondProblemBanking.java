package com.java.eight8.staticanddefault;

interface UPIService {
    default void makePayment() {
        System.out.println("Payment done via UPI Service");
    }
}

interface CardService {
    default void makePayment() {
        System.out.println("Payment done via Card Service");
    }
}

class BankPayment implements UPIService, CardService {

    @Override
    public void makePayment() {
        UPIService.super.makePayment();
        CardService.super.makePayment();
        System.out.println("Payment processed by BankPayment");
    }
}

public class DiamondProblemBanking {
    public static void main(String[] args) {
        BankPayment payment = new BankPayment();
        payment.makePayment();
    }
}
