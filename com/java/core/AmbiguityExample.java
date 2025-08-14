package com.java.core;

public class AmbiguityExample  {

    void test(int a, int b) {
        System.out.println("Method with int, int");
    }

    void test1(int a) {
        System.out.println("Method with int");
    }

    void test1(double a) {
        System.out.println("Method with int, long");
    }

    void test(long a, long b) {
        System.out.println("Method with double, double");
    }

    public static void main(String[] args) {
        AmbiguityExample obj = new AmbiguityExample();

        // This line causes ambiguity
        obj.test(1000, 10);
        obj.test1(1);
    }
}
