package com.java.functionalinterface;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionStreamExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Spring", "Boot", "AWS");

        Function<String, Integer> lengthFunc = String::length;

        List<Integer> lengths = names.stream()
                                     .map(lengthFunc) // Function used here
                                     .collect(Collectors.toList());

        System.out.println(lengths); // [6, 4, 3]

        BiFunction<Integer, Integer, Integer> sum = (a, b) -> a + b;
        System.out.println(sum.apply(10, 20)); // 30
    }
}
