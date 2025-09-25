package com.java.javastreams.limitandskip;

import java.util.*;
import java.util.stream.*;

class Employee {
    private final int id;
    private final String name;
    private final double salary;

    public Employee(int id, String name, double salary) {
        this.id = id; this.name = name; this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return id + ":" + name + " $" + salary;
    }
}

public class LimitSkipDemo {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee(1, "Alice", 100000),
            new Employee(2, "Bob", 55000),
            new Employee(3, "Charlie", 70000),
            new Employee(4, "David", 120000),
            new Employee(5, "Eve", 80000),
            new Employee(6, "Frank", 60000)
        );

        System.out.println("=== 1) limit(3): First 3 employees ===");
        employees.stream()
            .limit(3)
            .forEach(System.out::println);

        System.out.println("\n=== 2) skip(2): Skip first 2 employees ===");
        employees.stream()
            .skip(2)
            .forEach(System.out::println);

        System.out.println("\n=== 3) Pagination: page 2, size 2 (skip(2).limit(2)) ===");
        employees.stream()
            .skip(2)
            .limit(2)
            .forEach(System.out::println);

        System.out.println("\n=== 4) Top 2 highest salaries ===");
        employees.stream()
            .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
            .limit(2)
            .forEach(System.out::println);

        System.out.println("\n=== 5) Skip while limiting (skip(1).limit(3)) ===");
        employees.stream()
            .skip(1)
            .limit(3)
            .forEach(System.out::println);
    }
}
