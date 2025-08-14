package com.java.design.patterns.builder;

import com.java.abstraction.BankApp;

public class BuilderPatternDemo {
    public static void main(String[] args) {
        // Building a custom loan application
        LoanApplication loan = new LoanApplication.Builder("Ravi Kumar", "Home Loan")
                .coApplicant(true)
                .insurance(true)
                .collateral("Property Papers")
                .build();

        loan.printDetails();
    }
}
