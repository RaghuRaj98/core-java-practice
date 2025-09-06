package com.java.functionalinterface;

import java.awt.*;
import java.util.function.Predicate;

public class PredicateExample {
    public static void main(String[] args) {
        Predicate<Integer> isEven= n -> n % 2 == 0;

        System.out.println("Is 4 even? " + isEven.test(4)); // true
        System.out.println("Is 5 even? " + isEven.test(5)); //


        Predicate<String> isNotEmpty = str -> str != null && !str.isEmpty();

        System.out.println(isNotEmpty.test("Java")); // true
        System.out.println(isNotEmpty.test(""));     // false



    }
}
