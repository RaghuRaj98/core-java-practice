package com.java.eight8.functionalInterface;

@FunctionalInterface
interface Calculator {
    int operate(int a, int b);
}

public class FunctionalInterfaceExample {
    public static void main(String[] args) {
        Calculator add = (a, b) -> a + b;
        Calculator multiply = (a, b) -> a * b;

        System.out.println(add.operate(5, 3));      // 8
        System.out.println(multiply.operate(5, 3)); // 15
    }
}
