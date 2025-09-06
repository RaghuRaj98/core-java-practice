package com.java.eight8.methodreference;

import java.util.function.Supplier;

class Employee {
    Employee() {
        System.out.println("Employee object created");
    }
}

public class ConstructorRef {
    public static void main(String[] args) {
        Supplier<Employee> empSupplier = Employee::new; // Lambda expression
        empSupplier.get(); // Employee object created
    }
}
