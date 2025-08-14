package com.java.immutability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Student {
    private final String name;
    private final int rollNo;
    private final List<String> subjects; // mutable type

    public Student(String name, int rollNo, List<String> subjects) {
        this.name = name;
        this.rollNo = rollNo;
        // Store a defensive copy of the list
        this.subjects = new ArrayList<>(subjects);
    }

    public String getName() {
        return name;
    }

    public int getRollNo() {
        return rollNo;
    }

    // Option 1: Return an unmodifiable view
    public List<String> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }

     //Option 2 (stricter): Return a copy instead
     public List<String> getSubjects2() {
         return new ArrayList<>(subjects);
     }

    @Override
    public String toString() {
        return name + " (" + rollNo + ") " + subjects;
    }
}
