package com.dec.comparable;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Employee e1 = new Employee();
        e1.id = 101;
        e1.name = "Sandeep";

        Employee e2 = new Employee();
        e2.id = 102;
        e2.name = "Ankit";

        int result = e1.compareTo(e2);
        if(result == 0) {
            System.out.println("Both employees are equal");
        } else if(result < 0) {
            System.out.println(e1.name + " comes before " + e2.name);
        } else {
            System.out.println(e1.name + " comes after " + e2.name);
        }

        Comparator<Employee> nameComparator = Comparator.comparing(ea -> ea.name);
        Comparator<Employee> idComparator = Comparator.comparingInt(ea -> ea.id);
        int nameResult = nameComparator.compare(e1, e2);
        if(nameResult == 0) {
            System.out.println("Both employees have same name");
        } else if(nameResult < 0) {
            System.out.println(e1.name + " comes before " + e2.name + " in alphabetical order");
        } else {
            System.out.println(e1.name + " comes after " + e2.name + " in alphabetical order");
        }
    }
}
