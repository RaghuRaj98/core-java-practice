package com.java.functionalinterface;

import java.util.function.Consumer;

public class ConsumerExample {
    public static void main(String[] args) {
        Consumer<String> printer = str -> System.out.println("Hello, " + str);

        printer.accept("Java");  // Hello, Java
        printer.accept("Spring Boot"); // Hello, Spring Boot
    }
}
