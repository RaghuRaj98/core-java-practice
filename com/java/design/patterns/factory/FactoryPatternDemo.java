package com.java.design.patterns.factory;

// Step 1: Product interface
interface Account {
    void accountType();
}

// Step 2: Concrete Products
class SavingsAccount implements Account {
    public void accountType() {
        System.out.println("Savings Account Created");
    }
}

class CurrentAccount implements Account {
    public void accountType() {
        System.out.println("Current Account Created");
    }
}

// Step 3: Factory class
class AccountFactory {
    public static Account getAccount(String type) {
        if (type.equalsIgnoreCase("savings")) {
            return new SavingsAccount();
        } else if (type.equalsIgnoreCase("current")) {
            return new CurrentAccount();
        }
        throw new IllegalArgumentException("Invalid account type");
    }
}

// Step 4: Main class
public class FactoryPatternDemo {
    public static void main(String[] args) {
        Account acc1 = AccountFactory.getAccount("savings");
        acc1.accountType();  // Output: Savings Account Created

        Account acc2 = AccountFactory.getAccount("current");
        acc2.accountType();  // Output: Current Account Created
    }
}
