package com.java.eight8.methodreference;

import java.util.function.Function;

public class StaticMethodRef {
    public static int square(int n) {
        return n * n;
    }

    public static void main(String[] args) {
        Function<Integer, Integer> func = StaticMethodRef::square;
        System.out.println(func.apply(5)); // 25
    }
}
