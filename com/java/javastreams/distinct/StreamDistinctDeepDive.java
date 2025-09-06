package com.java.javastreams.distinct;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.*;

class Department {
    private final String name;
    public Department(String name) { this.name = name; }
    public String getName() { return name; }

    @Override public String toString() { return name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        return Objects.equals(name, ((Department) o).name);
    }
    @Override public int hashCode() { return Objects.hash(name); }
}

class Role {
    private final String name;
    public Role(String name) { this.name = name; }
    public String getName() { return name; }
    @Override public String toString() { return name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        return Objects.equals(name, ((Role) o).name);
    }
    @Override public int hashCode() { return Objects.hash(name); }
}

class Employee {
    private final int id;
    private final String name;
    private final Department dept;
    private final double salary;
    private final List<Role> roles;

    public Employee(int id, String name, Department dept, double salary, List<Role> roles) {
        this.id = id; this.name = name; this.dept = dept; this.salary = salary;
        this.roles = roles == null ? Collections.emptyList() : roles;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Department getDept() { return dept; }
    public double getSalary() { return salary; }
    public List<Role> getRoles() { return roles; }

    @Override public String toString() {
        return id + ":" + name + " {" + dept + ", $" + salary + ", roles=" + roles + "}";
    }

    // equality based on name+dept for demonstration
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee e = (Employee) o;
        return Objects.equals(name, e.name) &&
               Objects.equals(dept, e.dept);
    }
    @Override public int hashCode() { return Objects.hash(name, dept); }
}

public class StreamDistinctDeepDive {
    public static void main(String[] args) {
        Department it = new Department("IT");
        Department hr = new Department("HR");
        Department finance = new Department("Finance");

        Role dev = new Role("Developer");
        Role lead = new Role("Lead");

        List<Employee> employees = Arrays.asList(
            new Employee(1, "Alice", it, 100000, Arrays.asList(dev, lead)),
            new Employee(2, "Bob", hr, 55000, Arrays.asList(dev)),
            new Employee(3, "Charlie", finance, 70000, Arrays.asList(lead)),
            new Employee(4, "Alice", it, 120000, Arrays.asList(dev)), // duplicate (same name+dept)
            new Employee(5, "Eve", finance, 80000, Arrays.asList(lead)),
            new Employee(6, "Bob", hr, 60000, Arrays.asList(dev))     // duplicate (same name+dept)
        );

        System.out.println("=== 1) Simple distinct on primitive/strings ===");
        List<String> names = Arrays.asList("Java", "Java", "Python", "Go", "Python");
        names.stream().distinct().forEach(System.out::println);

        System.out.println("\n=== 2) distinct() on Employee objects (based on equals/hashCode) ===");
        employees.stream().distinct().forEach(System.out::println);

        System.out.println("\n=== 3) distinct() after map: get distinct department names ===");
        employees.stream()
            .map(e -> e.getDept().getName())
            .distinct()
            .forEach(System.out::println);

        System.out.println("\n=== 4) distinct() with flatMap: unique roles across employees ===");
        employees.stream()
            .flatMap(e -> e.getRoles().stream())
            .distinct()
            .forEach(System.out::println);

        System.out.println("\n=== 5) distinct() vs Collectors.toSet() ===");
        // distinct preserves encounter order (linked hash semantics), Set may not
        List<String> distinctList = names.stream().distinct().collect(Collectors.toList());
        Set<String> set = new HashSet<>(names);
        System.out.println("distinct list: " + distinctList);
        System.out.println("set (unordered): " + set);

        System.out.println("\n=== 6) distinct() combined with sorted() ===");
        names.stream()
            .distinct()
            .sorted()
            .forEach(System.out::println);
        String[] data  = {"banana", "1", "orange", "banana", "kiwi", "apple"};
        Arrays.asList(data).stream()
            .sorted()
            .forEach(System.out::println);
        Arrays.sort(data);
        System.out.println("---- after Arrays.sort() ----");
        System.out.println("Arrays.sort: " + Arrays.toString(data));

        System.out.println("\n=== 7) distinct() with custom mapping (simulate unique by dept) ===");
        employees.stream()
            .map(Employee::getDept)
            .distinct()
            .forEach(System.out::println);

        System.out.println("\n=== 8) distinct() on parallel stream ===");
        employees.parallelStream()
            .map(Employee::getName)
            .distinct()
            .forEachOrdered(System.out::println); // forEachOrdered preserves order
    }
}
