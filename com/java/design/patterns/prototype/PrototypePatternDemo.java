package com.java.design.patterns.prototype;

public class PrototypePatternDemo {
    public static void main(String[] args) {
        // Create original object
        KYC originalKYC = new KYC("Ravi Kumar", "Bangalore", "Aadhar-XXXX");

        // Clone the object
        KYC clonedKYC = originalKYC.clone();

        // Show details
        System.out.println("Original KYC:");
        originalKYC.showDetails();
        System.out.println("Original hashCode: " + originalKYC.hashCode());

        System.out.println("\nCloned KYC:");
        clonedKYC.showDetails();
        System.out.println("Cloned hashCode: " + clonedKYC.hashCode());

        // Compare references
        System.out.println("\nAre both references same? " + (originalKYC == clonedKYC));  // false
    }
}
