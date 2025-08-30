package com.java.collection.list.arraylist;

public class TestMyArrayList {
    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();

        // Add elements more than default capacity
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }

        // Print elements
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }

        System.out.println("\nRemoved: " + list.remove(5));

        // Print after removal
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
    }
}
