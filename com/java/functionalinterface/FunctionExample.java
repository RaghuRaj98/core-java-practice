package com.java.functionalinterface;

import java.awt.*;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public class FunctionExample {
    public static void main(String[] args) {
        Function<String, Integer> lengthFunc = str -> str.length();

        System.out.println(lengthFunc.apply("Java")); // 4
        System.out.println(lengthFunc.apply("Microservices")); // 13

        Function<Integer, Integer> square = n -> n * n;

        System.out.println(square.apply(5)); // 25

        Function<Integer, Integer> multiplyBy2 = n -> n * 2;
        Function<Integer, Integer> add3 = n -> n + 3;

// First multiply, then add
        System.out.println(multiplyBy2.andThen(add3).apply(5)); // (5*2)+3 = 13

// First add, then multiply
        System.out.println(multiplyBy2.compose(add3).apply(5)); // (5+3)*2 = 16


    }
}
