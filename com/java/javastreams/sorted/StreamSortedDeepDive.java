package com.java.javastreams.sorted;

import java.util.*;
import java.util.stream.Collectors;

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

public class StreamSortedDeepDive {
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

        System.out.println("=== 1) Natural sorting on primitive/strings ===");
        List<String> langs = Arrays.asList("Java", "Python", "Go", "C++", "Rust");
        langs.stream().sorted().forEach(System.out::println); // natural order

        System.out.println("\n=== 2) Reverse order sorting ===");
        langs.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        System.out.println("\n=== 3) Sort employees by name (natural order of String) ===");
        employees.stream()
            .sorted(Comparator.comparing(Employee::getName))
            .forEach(System.out::println);

        System.out.println("\n=== 4) Sort employees by salary (ascending) ===");
        employees.stream()
            .sorted(Comparator.comparingDouble(Employee::getSalary))
            .forEach(System.out::println);

        System.out.println("\n=== 5) Sort employees by salary (descending) ===");
        employees.stream()
            .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
            .forEach(System.out::println);

        System.out.println("\n=== 6) Multi-level sorting: dept -> salary desc -> name ===");
        employees.stream()
            .sorted(Comparator.comparing((Employee e) -> e.getDept().getName())
                    .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed())
                    .thenComparing(Employee::getName))
            .forEach(System.out::println);

        System.out.println("\n=== 7) Sort + distinct: sorted dept names without duplicates ===");
        employees.stream()
            .map(e -> e.getDept().getName())
            .distinct()
            .sorted()
            .forEach(System.out::println);

        System.out.println("\n=== 8) Limit + sorted: top 3 highest salaries ===");
        employees.stream()
            .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
            .limit(3)
            .forEach(System.out::println);

        System.out.println("\n=== 9) Parallel stream with sorted (order guaranteed) ===");
        employees.parallelStream()
            .map(Employee::getName)
            .sorted()
            .forEachOrdered(System.out::println); // must use forEachOrdered to keep sorted order

        System.out.println("\n=== 10) Collect sorted into list ===");
        List<Employee> sortedList = employees.stream()
            .sorted(Comparator.comparing(Employee::getId))
            .collect(Collectors.toList());
        System.out.println(sortedList);
    }
}
