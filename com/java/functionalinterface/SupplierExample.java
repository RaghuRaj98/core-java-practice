package com.java.functionalinterface;

import java.util.function.Supplier;

public class SupplierExample {
    public static void main(String[] args) {
        Supplier<String> supplier = () -> "Hello from Supplier";

        System.out.println(supplier.get()); // Hello from Supplier
    }
}
