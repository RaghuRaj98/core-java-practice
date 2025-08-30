package com.java.streams;

import java.util.*;
import java.util.stream.*;

class Employee {
    private final int id;
    private final String name;
    private final String dept;
    private final int salary;
    private final boolean active;

    public Employee(int id, String name, String dept, int salary, boolean active) {
        this.id = id; this.name = name; this.dept = dept; this.salary = salary; this.active = active;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDept() { return dept; }
    public int getSalary() { return salary; }
    public boolean isActive() { return active; }
}

class Account {
    private final String iban;
    private final int employeeId;
    private final String type; // SAVINGS / CHECKING / BROKER
    private final double balance;

    public Account(String iban, int employeeId, String type, double balance) {
        this.iban = iban; this.employeeId = employeeId; this.type = type; this.balance = balance;
    }
    public String getIban() { return iban; }
    public int getEmployeeId() { return employeeId; }
    public String getType() { return type; }
    public double getBalance() { return balance; }

    @Override
    public String toString() {
        return "Account{" +
                "iban='" + iban + '\'' +
                ", employeeId=" + employeeId +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                '}';
    }
}

class Txn {
    private final String accountIban;
    private final String kind; // DEBIT / CREDIT
    private final double amount;
    private final long epochMillis;

    public Txn(String accountIban, String kind, double amount, long epochMillis) {
        this.accountIban = accountIban; this.kind = kind; this.amount = amount; this.epochMillis = epochMillis;
    }
    public String getAccountIban() { return accountIban; }
    public String getKind() { return kind; }
    public double getAmount() { return amount; }
    public long getEpochMillis() { return epochMillis; }
}

class Bank {
    private final List<Employee> employees;
    private final List<Account> accounts;
    private final List<Txn> txns;

    public Bank(List<Employee> employees, List<Account> accounts, List<Txn> txns) {
        this.employees = employees; this.accounts = accounts; this.txns = txns;
    }
    public List<Employee> getEmployees() { return employees; }
    public List<Account> getAccounts() { return accounts; }
    public List<Txn> getTxns() { return txns; }
}
class EmpView {
    private final String name;
    private final String dept;
    public EmpView(String name, String dept) {
        this.name = name; this.dept = dept;
    }
//    public String toString() {
//        return name + " (" + dept + ")";
//    }
    @Override
    public String toString() {
        return "EmpView{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}

public class StreamsBank {
    static Bank seed() {
        List<Employee> employees = Arrays.asList(
            new Employee(1,"Asha","ENG",120_000,true),
            new Employee(2,"Bala","ENG",105_000,true),
            new Employee(3,"Chirag","HR",90_000,false),
            new Employee(4,"Deepti","FIN",140_000,true),
            new Employee(5,"Eshan","ENG",120_000,true)
        );

        List<Account> accounts = Arrays.asList(
            new Account("IN01",1,"SAVINGS", 12500.50),
            new Account("IN02",1,"CHECKING", 2050.00),
            new Account("IN03",2,"SAVINGS",  8200.00),
            new Account("IN04",4,"CHECKING",50000.00),
            new Account("IN05",5,"SAVINGS", 28300.75),
            new Account("IN06",5,"BROKER", 120000.00),
                new Account("IN06",5,"BROKER", 120000.00)
        );

        long now = System.currentTimeMillis();
        List<Txn> txns = Arrays.asList(
            new Txn("IN01","DEBIT",  500.0, now-86_400_000L*3),
            new Txn("IN01","CREDIT", 700.0, now-86_400_000L*2),
            new Txn("IN02","DEBIT",  100.0, now-86_400_000L*3),
            new Txn("IN04","DEBIT", 5000.0, now-86_400_000L),
            new Txn("IN05","CREDIT",2500.0, now-86_400_000L*5),
            new Txn("IN06","CREDIT",9000.0, now-86_400_000L),
            new Txn("IN06","DEBIT", 2000.0, now-86_400_000L/2)
        );

        return new Bank(employees, accounts, txns);
    }

    public static void main(String[] args) {
        Bank bank = seed();

        // Put any snippet from the sections below here to run.
        List<Employee> eng120 = bank.getEmployees().stream()
                .peek(e -> System.out.println("Starting: " + e.getName()))
                .filter(e -> e.isActive())
                .peek(e -> System.out.println("Filter passed " + e.getName()))// condition 1: only active
                .filter(e -> "ENG".equals(e.getDept()))
                .peek(e -> System.out.println("Last filter: " + e.getName()))// condition 2: dept ENG
                .filter(e -> e.getSalary() >= 120_000)     // condition 3: salary ≥ 120k
                .collect(Collectors.toList());

// Expected: [Asha, Eshan]
        eng120.forEach(e -> System.out.println(e.getName()));

        List<EmpView> views = bank.getEmployees().stream()
                .map(e -> new EmpView(e.getName(), e.getDept()))   // Employee -> EmpView
                .collect(Collectors.toList());
        views.forEach(System.out::println);

        // IBANs of all active employees
        Set <String> ibans = bank.getEmployees().stream()
                .filter(e -> e.isActive())
                .peek(e -> System.out.println("Starting: " + e.getName()))
                .flatMap(e -> bank.getAccounts().stream()
                        .filter(a -> a.getEmployeeId() == e.getId()))
                .peek(e -> System.out.println("Starting: " + e.getIban()))// Employee -> Stream<Account>
                .map(a -> a.getIban())                             // Account -> IBAN
                .collect(Collectors.toSet());

        // Output: All IBANs belonging to active employees
        ibans.forEach(System.out::println);


        List<Employee> result = bank.getEmployees().stream()
                .peek(e -> System.out.println("Starting: " + e.getName()))
                .filter(e -> e.getSalary() > 100000)
                .peek(e -> System.out.println("Passed salary filter: " + e.getName()))
                .filter(Employee::isActive)
                .peek(e -> System.out.println("Still active: " + e.getName()))
                .collect(Collectors.toList());
        System.out.println("Final Result:");
        result.forEach(e -> System.out.println(e.getName()));

        List<String> names = Arrays.asList("Java", "Streams", "API");

        List<Integer> lengths = names.stream()
                .peek(s -> System.out.println("Processing: " + s))
                .filter(e -> e.length()>2)// side-effect
                .peek(s -> System.out.println("Processing: " + s))
                .map(s -> s.length())
                .peek(s -> System.out.println("Processing: " + s))// transform String -> Integer
                .collect(Collectors.toList());

        lengths.forEach(System.out::println);

        System.out.println("=== Termainal Operations ===");
        Set<String> depts = bank.getEmployees().stream()
                .map(Employee::getDept)
                .collect(Collectors.toSet());
        depts.forEach(System.out::println);


        Map<Integer, String> empMap = bank.getEmployees().stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getName));
        empMap.forEach((k,v) -> System.out.println(k + " -> " + v));



        List<String> namesee = List.of("Asha", "Bala", "Eshan");

        namesee.stream()
                .forEach(n -> System.out.println("Hello " + n));

        namesee.parallelStream()
                .forEachOrdered(System.out::println);

        bank.getEmployees().parallelStream()
                .forEach(e -> System.out.println(e.getName()));


        List<String> resultus = new ArrayList<>();
        bank.getEmployees().parallelStream()
                .forEach(e -> resultus.add(e.getName()));
        resultus.forEach(System.out::println);


        String[] empNames = bank.getEmployees().stream()
                .map(Employee::getName)
                .toArray(String[]::new);

        System.out.println(Arrays.toString(empNames));
       for(String n: empNames){
           System.out.println(n);
       }

       System.out.println("=== Reduce===");

        int totalSalary = bank.getEmployees().stream()
                .map(Employee::getSalary)
                .reduce(0, Integer::sum);   // identity = 0, accumulator = sum
        System.out.println("Total Salary: " + totalSalary);

        String allNames = bank.getEmployees().stream()
                .map(Employee::getName)
                .reduce("", (a, b) -> a + ", " + b);
        System.out.println(allNames);

        System.out.println("=== Min/Max/Average/Sum/Count ===");
        Optional<Employee> lowestPaid = bank.getEmployees().stream()
                .min(Comparator.comparingInt(Employee::getSalary));
        lowestPaid.ifPresent(e ->
                System.out.println("Lowest Paid: " + e.getName() + " - " + e.getSalary()));

        Optional<Employee> highestPaid = bank.getEmployees().stream()
                .max(Comparator.comparingInt(Employee::getSalary));
        highestPaid.ifPresent(e ->
                System.out.println("Highest Paid: " + e.getName() + " - " + e.getSalary()));



        long activeCount = bank.getEmployees().stream()
                .filter(Employee::isActive)
                .count();
        System.out.println("Active Employees: " + activeCount);


        double totalBalance = bank.getAccounts().stream()
                .mapToDouble(Account::getBalance)   // DoubleStream
                .sum();
        System.out.println("Total Balance: " + totalBalance);

        System.out.println("=== Match/Find ===");
        boolean anyEng = bank.getEmployees().stream()
                .anyMatch(e -> "ENG".equals(e.getDept()));   // true if any ENG employee

        boolean allActive = bank.getEmployees().stream()
                .allMatch(Employee::isActive);               // true if all active

        boolean noneHR = bank.getEmployees().stream()
                .noneMatch(e -> "HR".equals(e.getDept()));   // true if no HR employee
        System.out.println("Any ENG? " + anyEng);

        Employee first = bank.getEmployees().stream()
                .findFirst()
                .orElse(null);

        Employee any = bank.getEmployees().parallelStream()
                .findAny()
                .orElse(null);

    }
}
