package com.java.functionalinterface;

import java.util.function.BiPredicate;

class Employees {
    String name;
    int age;

    Employees(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class BiPredicateObjExample {
    public static void main(String[] args) {
        BiPredicate<Employees, Integer> isOlderThan = (emp, ageLimit) -> emp.age > ageLimit;

        Employees ee = new Employees("Raghav", 30);

        System.out.println(isOlderThan.test(ee, 25)); // true
        System.out.println(isOlderThan.test(ee, 35)); // false
    }
}
