package com.java.functionalinterface;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class BiPredicateStream {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Spring", "Boot", "AWS", "AI");

        BiPredicate<String, Integer> longerThan = (str, length) -> str.length() > length;

        int minLength = 3;

        List<String> result = names.stream()
                                   .filter(name -> longerThan.test(name, minLength))
                                   .collect(Collectors.toList());

        System.out.println(result); // [Spring, Boot, AWS]
    }
}
