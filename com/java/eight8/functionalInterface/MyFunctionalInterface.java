package com.java.eight8.functionalInterface;

@FunctionalInterface
public interface MyFunctionalInterface {
    void doSomething();  // single abstract method

    // Allowed
    default void log(String msg) {
        System.out.println("Log: " + msg);
    }

    static void printHello() {
        System.out.println("Hello");
    }
}
