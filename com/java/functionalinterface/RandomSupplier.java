package com.java.functionalinterface;

import java.util.function.Supplier;
import java.util.Random;

public class RandomSupplier {
    public static void main(String[] args) {
        Supplier<Integer> randomSupplier = () -> new Random().nextInt(100);

        System.out.println(randomSupplier.get()); // e.g., 42
        System.out.println(randomSupplier.get()); // e.g., 87
    }
}
