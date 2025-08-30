package com.java.garbagecollection;

class LoanApplication {
    private String customerName;
    private double amount;

    public LoanApplication(String customerName, double amount) {
        this.customerName = customerName;
        this.amount = amount;
    }

    //@Override
//    //protected void finalize() {
//        System.out.println("Cleaning up LoanApplication for: " + customerName);
//    }
}

public class BankingGCExample {
    public static void main(String[] args) {
        LoanApplication loan1 = new LoanApplication("Ravi", 500000);
        LoanApplication loan2 = new LoanApplication("Sneha", 200000);

        // After processing loan requests
        loan1 = null; // eligible for GC
        loan2 = null; // eligible for GC

        // Request JVM to run Garbage Collector
        System.gc();

        System.out.println("Banking transactions completed...");
    }
}
