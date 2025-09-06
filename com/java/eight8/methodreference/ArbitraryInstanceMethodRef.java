package com.java.eight8.methodreference;

import java.util.*;

public class ArbitraryInstanceMethodRef {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Spring", "Java", "AWS");

        // Lambda:
        names.forEach(s -> System.out.println(s));

        names.forEach(System.out::println); // Method reference
    }
}
