// File: com.bank.loan.LoanAccount.java
package com.java.access.different;

import com.java.abstraction.BankAppCopyDefaultMethod;
import com.java.access.base.BankAccount;

public class LoanAccount extends BankAccount {
    protected String accountHolderName;   // Can be accessed everywhere
    public double balance;          // Accessible in subclasses
    String accountType;                // Default access
    private String pin;
    public LoanAccount(String name, double initialBalance, String pin) {
        super(name, initialBalance, pin); // Call to superclass constructor
        this.pin = pin; // Private field, accessible within this class
        this.accountHolderName = name; // Protected field, accessible in subclass
        this.balance = initialBalance; // Public field, accessible in subclass
    }

    public void loanRepayment(double amount) {
        withdraw(amount); // ✅ Can access protected method from superclass
        //accountType = "Loan"; // Not accessible (default access in different package)
        //pin = "1234"; // Private - not accessible
        System.out.println("Loan repayment of " + amount + " made by " + balance);
        System .out.println("Account Holder: " + pin);
    }

    public static void main(String[] args) {
        LoanAccount loanAccount = new LoanAccount("John Doe", 5000, "1234");
        loanAccount.loanRepayment(1000);
        BankAppCopyDefaultMethod bankApp = new BankAppCopyDefaultMethod();
        bankApp.name = "Banking Customer"; // Accessible as it's public
        //System.out.println("Banking Customer Name: " + bankApp.accountNumber); // Accessible as it's public
        //loanAccount.withdraw(500); // Not accessible (protected method)
        //System.out.println(loanAccount.accountType); // Not accessible (default access)
        //System.out.println(loanAccount.pin); // Not accessible (private field)
    }
}
