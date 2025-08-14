package com.java.immutability;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> subList = new ArrayList<>();
        subList.add("Math");
        subList.add("Science");

        Student s = new Student("Alice", 101, subList);

        System.out.println("Original: " + s);

        // Try modifying original list after creation
        subList.add("History");
        System.out.println("After modifying input list: " + s);
        s.getSubjects2().add("Geography");
        // Try modifying through getter
        try {

            s.getSubjects().add("Geography");
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught exception: Cannot modify subjects");
        }

        System.out.println("Final: " + s);
    }
}
