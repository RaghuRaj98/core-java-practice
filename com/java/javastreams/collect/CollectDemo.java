package com.java.javastreams.collect;

import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;

class Department {
    private final String name;
    public Department(String name) { this.name = name; }
    public String getName() { return name; }
    @Override public String toString() { return name; }
}

class Employee {
    private final int id;
    private final String name;
    private final Department dept;
    private final double salary;

    public Employee(int id, String name, Department dept, double salary) {
        this.id = id; this.name = name; this.dept = dept; this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Department getDept() { return dept; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return id + ":" + name + " {" + dept + ", $" + salary + "}";
    }
}

public class CollectDemo {
    public static void main(String[] args) {
        Department it = new Department("IT");
        Department hr = new Department("HR");
        Department finance = new Department("Finance");

        List<Employee> employees = Arrays.asList(
            new Employee(1, "Alice", it, 100000),
            new Employee(2, "Bob", hr, 55000),
            new Employee(3, "Charlie", finance, 70000),
            new Employee(4, "David", it, 120000),
            new Employee(5, "Eve", finance, 80000)
        );

        System.out.println("=== 1) Collect into List ===");
        List<String> names = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.toList());
        System.out.println(names);

        System.out.println("\n=== 2) Collect into Set ===");
        Set<String> depts = employees.stream()
            .map(e -> e.getDept().getName())
            .collect(toSet());
        System.out.println(depts);

        System.out.println("\n=== 3) Collect into Map (id->name) ===");
        Map<Integer, String> idNameMap = employees.stream()
            .collect(toMap(Employee::getId, Employee::getName));
        System.out.println(idNameMap);

        System.out.println("\n=== 4) Joining names into a single String ===");
        String joined = employees.stream()
            .map(Employee::getName)
            .collect(joining(", ", "[", "]"));
        System.out.println(joined);

        System.out.println("\n=== 5) Counting employees in stream ===");
        long count = employees.stream().collect(counting());
        System.out.println("Count = " + count);

        System.out.println("\n=== 6) Average salary ===");
        double avg = employees.stream().collect(averagingDouble(Employee::getSalary));
        System.out.println("Average = " + avg);

        System.out.println("\n=== 7) Summarizing salary statistics ===");
        DoubleSummaryStatistics stats = employees.stream()
            .collect(summarizingDouble(Employee::getSalary));
        System.out.println(stats);

        System.out.println("\n=== 8) Grouping employees by department ===");
        Map<String, List<Employee>> byDept = employees.stream()
            .collect(groupingBy(e -> e.getDept().getName()));
        System.out.println(byDept);

        System.out.println("\n=== 9) Partitioning employees by salary > 80k ===");
        Map<Boolean, List<Employee>> partitioned = employees.stream()
            .collect(partitioningBy(e -> e.getSalary() > 80000));
        System.out.println(partitioned);

        System.out.println("\n=== 10) Mapping collector: Dept -> employee names ===");
        Map<String, List<String>> deptNames = employees.stream()
            .collect(groupingBy(
                e -> e.getDept().getName(),
                mapping(Employee::getName, toList())
            ));
        System.out.println(deptNames);
    }
}
