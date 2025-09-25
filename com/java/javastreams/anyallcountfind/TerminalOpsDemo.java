package com.java.javastreams.anyallcountfind;

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

public class TerminalOpsDemo {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee(1, "Alice", 100000),
            new Employee(2, "Bob", 55000),
            new Employee(3, "Charlie", 70000),
            new Employee(4, "David", 120000),
            new Employee(5, "Eve", 80000)
        );

        System.out.println("=== count() ===");
        long count = employees.stream().count();
        System.out.println("Count = " + count);

        System.out.println("\n=== min() ===");
        employees.stream()
            .min(Comparator.comparingDouble(Employee::getSalary))
            .ifPresent(e -> System.out.println("Min salary: " + e));

        System.out.println("\n=== max() ===");
        employees.stream()
            .max(Comparator.comparingDouble(Employee::getSalary))
            .ifPresent(e -> System.out.println("Max salary: " + e));

        System.out.println("\n=== anyMatch() ===");
        boolean anyRich = employees.stream().anyMatch(e -> e.getSalary() > 100000);
        System.out.println("Any salary > 100k? " + anyRich);

        System.out.println("\n=== allMatch() ===");
        boolean allAbove50k = employees.stream().allMatch(e -> e.getSalary() > 50000);
        System.out.println("All salary > 50k? " + allAbove50k);

        System.out.println("\n=== noneMatch() ===");
        boolean noneBelow40k = employees.stream().noneMatch(e -> e.getSalary() < 40000);
        System.out.println("No salary < 40k? " + noneBelow40k);

        System.out.println("\n=== findFirst() ===");
        employees.stream().findFirst().ifPresent(e -> System.out.println("First = " + e));

        System.out.println("\n=== findAny() (sequential) ===");
        employees.stream().findAny().ifPresent(e -> System.out.println("Find any = " + e));

        System.out.println("\n=== findAny() (parallel) ===");
        employees.parallelStream().findAny().ifPresent(e -> System.out.println("Find any (parallel) = " + e));
    }
}
