package com.java.functionalinterface;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class DateSupplier {
    public static void main(String[] args) {
        Supplier<LocalDateTime> nowSupplier = LocalDateTime::now;

        System.out.println("Current time: " + nowSupplier.get());
    }
}
