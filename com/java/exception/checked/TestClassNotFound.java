package com.java.exception.checked;

public class TestClassNotFound {
    public static void main(String[] args) throws ClassNotFoundException {

        try {
            // Trying to load a class that does not exist
            Class.forName("com.example.DoesNotExist");
        } catch (ClassNotFoundException e) {
            System.out.println("Caught Exception: " + e);
        }
    }
}
