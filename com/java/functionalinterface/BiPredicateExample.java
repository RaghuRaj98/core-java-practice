package com.java.functionalinterface;

import java.util.function.BiPredicate;

public class BiPredicateExample {
    public static void main(String[] args) {
        BiPredicate<Integer, Integer> isSumEven = (a, b) -> (a + b) % 2 == 0;

        System.out.println(isSumEven.test(10, 20)); // true
        System.out.println(isSumEven.test(10, 25)); // false
    }
}
