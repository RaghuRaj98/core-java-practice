package com.java.functionalinterface;

import java.util.function.BiFunction;

public class BiFunctionExample {
    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;

        System.out.println(add.apply(10, 20)); // 30

        BiFunction<String, Integer, String> repeat = (str, count) -> str.repeat(count);

        System.out.println(repeat.apply("Hi ", 3));
    }
}
