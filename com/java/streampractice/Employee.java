package com.java.streampractice;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Employee {
    private int id;
    private String name;
    private int age;
    private String department;
    private double salary;
    private String gender;

    public Employee(int id, String name, int age, String department, double salary, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
        this.gender = gender;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Sample data for exercises
    public static List<Employee> sampleEmployees() {
        return Arrays.asList(
                new Employee(1, "Alice", 30, "Engineering", 90000, "F"),
                new Employee(2, "Bob", 45, "HR", 65000, "M"),
                new Employee(3, "Carol", 100, "Engineering", 70000, "F"),
                new Employee(4, "David", 40, "Sales", 55000, "M"),
                new Employee(5, "Eve", 35, "Engineering", 98000, "F"),
                new Employee(6, "Frank", 28, "Sales", 48000, "M"),
                new Employee(7, "Grace", 50, "Management", 120000, "F"),
                new Employee(8, "Heidi", 32, "HR", 68000, "F"),
                new Employee(9, "Ivan", 29, "Engineering", 72000, "M"),
                new Employee(10, "Judy", 26, "Engineering", 71000, "F"),
                new Employee(11, "Ken", 31, "Sales", 53000, "M"),
                new Employee(12, "Laura", 38, "Management", 110000, "F")
        );
    }
}

