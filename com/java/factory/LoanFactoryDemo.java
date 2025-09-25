package com.java.factory;


interface Loan {
    double getInterestRate();
}
class HomeLoan implements Loan {
    @Override
    public double getInterestRate() {
        return 6.5;
    }
}

class PersonalLoan implements Loan{
    @Override
    public double getInterestRate() {
        return 12.5;
    }
}

class LoanFactory {
    public static Loan getLoan(String loanType) {
        if ("Home".equals(loanType)) {
            return new HomeLoan();
        }
        else if ("Personal".equals(loanType)) {
            return new PersonalLoan();
        }
        throw new IllegalArgumentException("Unknown loan type: " + loanType);
    }
}

public class LoanFactoryDemo {
    public static void main(String[] args) {

        Loan homeLoan = LoanFactory.getLoan("Home");
        System.out.println("Home Loan Interest Rate: " + homeLoan.getInterestRate() + "%");

        Loan personalLoan = LoanFactory.getLoan("Personal");
        System.out.println("Personal Loan Interest Rate: " + personalLoan.getInterestRate() + "%");
    }
}

