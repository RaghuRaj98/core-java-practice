package com.java.functionalinterface;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConsumerListExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 20, 30);

        Consumer<Integer> showSquare = n -> System.out.println("Square: " + (n * n));

        numbers.forEach(showSquare);

        BiConsumer<String, Integer> printKeyValue = (k, v) ->
                System.out.println("Key: " + k + ", Value: " + v);

        printKeyValue.accept("Age", 30); // Key: Age, Value: 30
    }
}
