package com.java.functionalinterface;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateWithStream {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Spring", "Boot", "AWS", "Microservices");

        Predicate<String> startsWithS = str -> str.startsWith("S") && str.length() < 3;
        Predicate<Integer> greaterThan10 = n -> n > 10;
        Predicate<Integer> even = n -> n % 2 == 0;

        List<String> result = names.stream()
                                   .filter(startsWithS)
                                   .collect(Collectors.toList());

        System.out.println(result); // [Spring]

        Predicate<String> isJava = Predicate.isEqual("Java");
        System.out.println(isJava.test("Java"));  // true
        System.out.println(isJava.test("Spring")); // false

    }
}
