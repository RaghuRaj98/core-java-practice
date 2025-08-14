package com.java.abstraction;

interface ATMServiceStaic {
    static void serviceInfo() {
        System.out.println("ATM Service: Available 24/7");
    }
}

interface MobileBankingServiceStatic {
    static void serviceInfo() {
        System.out.println("Mobile Banking Service: Available 24/7");
    }
}

class BankingCustomerStatic implements ATMServiceStaic, MobileBankingServiceStatic {
    // No conflict — static methods are not inherited
}

public class StaticInheritance {
    public static void main(String[] args) {
        // Call explicitly with interface name
        ATMServiceStaic.serviceInfo();
        MobileBankingServiceStatic.serviceInfo();
    }
}
