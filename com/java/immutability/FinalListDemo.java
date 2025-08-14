package com.java.immutability;

import java.util.ArrayList;
import java.util.List;

public class FinalListDemo {
    private final List<String> subjects = new ArrayList<>();

    public void test() {
        subjects.add("Math");    // ✅ allowed
        subjects.add("Science"); // ✅ allowed
        System.out.println(subjects);

        // subjects = new ArrayList<>(); // ❌ compilation error
    }

    public static void main(String[] args) {
        new FinalListDemo().test();
    }
}
