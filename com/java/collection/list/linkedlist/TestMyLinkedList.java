package com.java.collection.list.linkedlist;

public class TestMyLinkedList {
    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<>();

        list.add("Account Opening");
        list.add("Deposit");
        list.add("Withdrawal");
        list.add("Loan Processing");

        System.out.println("First: " + list.get(0));    // Account Opening
        System.out.println("Last: " + list.get(3));     // Loan Processing

        System.out.println("Removed: " + list.remove(1)); // Deposit

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
