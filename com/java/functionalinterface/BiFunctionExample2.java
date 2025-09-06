package com.java.functionalinterface;

import java.util.function.BiFunction;

class Employee {
    String name;
    int salary;

    Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }
}

public class BiFunctionExample2 {
    public static void main(String[] args) {
        BiFunction<String, Integer, Employee> empCreator = (name, salary) -> new Employee(name, salary);

        Employee e = empCreator.apply("Raghav", 50000);
        System.out.println(e.name + " earns " + e.salary);
    }
}
