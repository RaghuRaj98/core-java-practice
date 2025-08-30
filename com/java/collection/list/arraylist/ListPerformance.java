package com.java.collection.list.arraylist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListPerformance {
    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        // Insert at end
        long start = System.nanoTime();
        for(int i=0; i<1_000_00; i++) arrayList.add(i);
        long end = System.nanoTime();
        System.out.println("ArrayList Insert: " + (end-start));

        start = System.nanoTime();
        for(int i=0; i<1_000_00; i++) linkedList.add(i);
        end = System.nanoTime();
        System.out.println("LinkedList Insert: " + (end-start));
    }
}
