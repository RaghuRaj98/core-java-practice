package com.java.javastreams.collectors;

import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;

class Employee {
    private final int id;
    private final String name;
    private final String dept;
    private final double salary;

    public Employee(int id, String name, String dept, double salary) {
        this.id = id; this.name = name; this.dept = dept; this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDept() { return dept; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return id + ":" + name + " {" + dept + ", $" + salary + "}";
    }
}

public class CollectorsDemo {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee(1, "Alice", "IT", 100000),
            new Employee(2, "Bob", "HR", 55000),
            new Employee(3, "Charlie", "Finance", 70000),
            new Employee(4, "David", "IT", 120000),
            new Employee(5, "Eve", "Finance", 80000)
        );

        System.out.println("=== 1) toList ===");
        List<String> names = employees.stream()
            .map(Employee::getName)
            .collect(toList());
        System.out.println(names);

        System.out.println("\n=== 2) toSet ===");
        Set<String> depts = employees.stream()
            .map(Employee::getDept)
            .collect(toSet());
        System.out.println(depts);

        System.out.println("\n=== 3) toMap (id -> name) ===");
        Map<Integer, String> idToName = employees.stream()
            .collect(toMap(Employee::getId, Employee::getName));
        System.out.println(idToName);

        System.out.println("\n=== 4) joining ===");
        String joined = employees.stream()
            .map(Employee::getName)
            .collect(joining(", ", "{", "]"));
        System.out.println(joined);

        System.out.println("\n=== 5) counting ===");
        long count = employees.stream().collect(counting());
        System.out.println("Count = " + count);

        System.out.println("\n=== 6) averagingDouble ===");
        double avgSalary = employees.stream().collect(averagingDouble(Employee::getSalary));
        System.out.println("Average salary = " + avgSalary);

        System.out.println("\n=== 7) summarizingDouble ===");
        DoubleSummaryStatistics stats = employees.stream()
            .collect(summarizingDouble(Employee::getSalary));
        System.out.println(stats);

        System.out.println("\n=== 8) groupingBy dept ===");
        Map<String, List<Employee>> byDept = employees.stream()
            .collect(groupingBy(Employee::getDept));
        System.out.println(byDept);

        System.out.println("\n=== 9) partitioningBy salary > 80k ===");
        Map<Boolean, List<Employee>> partitioned = employees.stream()
            .collect(partitioningBy(e -> e.getSalary() > 80000));
        System.out.println(partitioned);

        System.out.println("\n=== 10) mapping (dept -> names) ===");
        Map<String, List<String>> deptToNames = employees.stream()
            .collect(groupingBy(
                Employee::getDept,
                mapping(Employee::getName, toList())
            ));
        System.out.println(deptToNames);
    }
}
