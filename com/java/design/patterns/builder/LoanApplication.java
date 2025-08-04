package com.java.design.patterns.builder;

// LoanApplication class
public class LoanApplication {

    // Required fields
    private final String applicantName;
    private final String loanType;

    // Optional fields
    private final boolean hasCoApplicant;
    private final boolean includesInsurance;
    private final String collateralDetails;

    // Private constructor
    private LoanApplication(Builder builder) {
        this.applicantName = builder.applicantName;
        this.loanType = builder.loanType;
        this.hasCoApplicant = builder.hasCoApplicant;
        this.includesInsurance = builder.includesInsurance;
        this.collateralDetails = builder.collateralDetails;
    }

    // Static Builder Class
    public static class Builder {
        private final String applicantName;
        private final String loanType;
        private boolean hasCoApplicant;
        private boolean includesInsurance;
        private String collateralDetails;

        public Builder(String applicantName, String loanType) {
            this.applicantName = applicantName;
            this.loanType = loanType;
        }

        public Builder coApplicant(boolean val) {
            this.hasCoApplicant = val;
            return this;
        }

        public Builder insurance(boolean val) {
            this.includesInsurance = val;
            return this;
        }

        public Builder collateral(String val) {
            this.collateralDetails = val;
            return this;
        }

        public LoanApplication build() {
            return new LoanApplication(this);
        }
    }

    // Method to display loan details
    public void printDetails() {
        System.out.println("Loan Application for: " + applicantName);
        System.out.println("Loan Type: " + loanType);
        System.out.println("Co-applicant: " + hasCoApplicant);
        System.out.println("Insurance: " + includesInsurance);
        System.out.println("Collateral: " + (collateralDetails != null ? collateralDetails : "None"));
    }
}
