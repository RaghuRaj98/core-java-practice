package com.java.facade;

// ----- Subsystems -----
class CreditCheck {
    public boolean hasGoodCredit(String customer) {
        System.out.println("Checking credit for " + customer);
        return true; // mock
    }
}

class AccountCheck {
    public boolean hasSufficientBalance(String customer) {
        System.out.println("Checking account balance for " + customer);
        return true; // mock
    }
}

class EmploymentCheck {
    public boolean isEmployed(String customer) {
        System.out.println("Verifying employment for " + customer);
        return true; // mock
    }
}

// ----- Facade -----
class LoanFacade {
    private CreditCheck credit;
    private AccountCheck account;
    private EmploymentCheck employment;

    public LoanFacade() {
        this.credit = new CreditCheck();
        this.account = new AccountCheck();
        this.employment = new EmploymentCheck();
    }

    public boolean applyLoan(String customer) {
        System.out.println("Processing loan application for " + customer);

        if (credit.hasGoodCredit(customer) &&
            account.hasSufficientBalance(customer) &&
            employment.isEmployed(customer)) {
            System.out.println("Loan Approved for " + customer);
            return true;
        } else {
            System.out.println("Loan Denied for " + customer);
            return false;
        }
    }
}
