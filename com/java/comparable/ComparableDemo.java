package com.java.comparable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Employee implements Comparable<Employee> {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id; this.name = name; this.salary = salary;
    }

    @Override
    public int compareTo(Employee other) {
        // Natural order = ascending by name
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return id + ":" + name + " $" + salary;
    }
}

public class ComparableDemo {
    public static void main(String[] args) {
        List<Employee> list = Arrays.asList(
            new Employee(1, "Charlie", 70000),
            new Employee(2, "Alice", 100000),
            new Employee(3, "Bob", 90000)
        );
        Collections.sort(list, Comparator.comparing(Employee::getSalary));


        Collections.sort(list, Comparator.comparing(Employee::getId).reversed());// uses compareTo()
        list.forEach(System.out::println);
    }
}
