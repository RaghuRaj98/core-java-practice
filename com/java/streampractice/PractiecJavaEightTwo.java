package com.java.streampractice;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PractiecJavaEightTwo {
    public static void main(String[] args) {
        System.out.println("Practice Java 8 Features");
        List<Employee> employees = Employee.sampleEmployees();

        //Print all employee names.
        employees.stream().map(Employee::getName).forEach(System.out::println);
        employees.stream().forEach(e -> System.out.println(e.getName()));


        //Print all unique departments.
        employees.stream().map(Employee::getDepartment).distinct().forEach(System.out::println);

        //Find total number of employees.
        long count = employees.stream().count();
        System.out.println("Total number of employees: " + count);

        //Find
        employees.stream().filter(e -> e.getDepartment().equals("Engineering"))
                .forEach(e -> System.out.println(e.getName()));

        //Count number of employees in “Engineering”.
        long engineersCount = employees.stream().filter(e -> e.getDepartment().equals("Engineering")).count();
        System.out.println("Number of employees in Engineering: " + engineersCount);


        //Find names of employees whose age is greater than 30 and salary is greater than 60000.
        System.out.println("Employees with age > 30 and salary > 60000:");
        employees.stream().filter(e ->e.getAge()>30 && e.getSalary()>60000)
                .forEach(e -> System.out.println(e.getName()));


        System.out.println("Female Employees:");
        employees.stream().filter(e->e.getGender().equals("F")).forEach(System.out::println);

        //Print names of all employees in uppercase.
        System.out.println("Employee names in uppercase:");
        employees.stream().map(e->e.getName().toUpperCase()).forEach(System.out::println);


        employees.stream().sorted(Comparator.comparing(Employee::getName).reversed()).forEach(System.out::println);

        //Print names of all employees sorted by their names.
        System.out.println("Employee names sorted:");
        employees.stream().sorted(Comparator.comparing(Employee::getName)).forEach(System.out::println);


        //Print employees sorted by age.
        System.out.println("Employees sorted by age:");
        employees.stream().sorted(Comparator.comparing(Employee::getAge)).forEach(System.out::println);

        //Find names of employees in “HR” department.
        System.out.println("Names of employees in HR department:");
        employees.stream().filter(e->e.getDepartment().equals("HR")).map(Employee::getName).forEach(System.out::println);

        //Print names and salaries of employees whose salary is greater than 70000.
        System.out.println("Employees with salary > 70000:");
        employees.stream().filter(e->e.getSalary()>70000).forEach(e-> System.out.println(e.getName() + ": " + e.getSalary()));

        List<Integer> listOfAge = employees.stream().map(Employee::getAge).collect(Collectors.toList());
        System.out.println("List of Ages: " + listOfAge);
        listOfAge.stream().forEach(System.out::println);

        System.out.println("Employees whose names start with 'A':");
        employees.stream().filter(e->e.getName().startsWith("A")).forEach(System.out::println);


        System.out.println("Employees whose names end with 'n':");
        employees.stream().filter(e->e.getName().endsWith("n")).forEach(System.out::println);

        System.out.println("Employees whose names contain 'ar':");
        employees.stream().filter(e->e.getName().contains("ar")).forEach(System.out::println);

        Predicate<Employee> statsWithA = e -> e.getName().startsWith("A");
        Predicate<Employee> statsWithE = e -> e.getName().startsWith("E");
        Predicate<Employee> statsWithI = e -> e.getName().startsWith("I");

        System.out.println("Employees whose names start with 'A', 'E' or 'I':");
        employees.stream().filter(e->e.getName().startsWith("A") || e.getName().startsWith("E") || e.getName().startsWith("I"))
                .forEach(System.out::println);

        //Find employees whose name starts with a vowel.
        System.out.println("Employees whose names start with a vowel:");
        employees.stream().filter(e->{
            char firstChar = Character.toUpperCase(e.getName().charAt(0));
            return firstChar == 'A' || firstChar == 'E' || firstChar == 'I' || firstChar == 'O' || firstChar == 'U';
        }).forEach(System.out::println);

        //Find employees who work in either “Sales” or “HR” department.
        System.out.println("Employees in Sales or HR department:");
        employees.stream().filter(e -> e.getDepartment().equals("Sales") || e.getDepartment().equals("HR"))
                .forEach(System.out::println);

        //Find names of employees who do not work in “Engineering” department.
        System.out.println("Employees not in Engineering department:");
        employees.stream().filter(e-> !e.getDepartment().equals("Engineering"))
                .forEach(System.out::println);

        //Find total salary of all employees.

        double totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();

        employees.stream().mapToDouble(Employee::getSalary).reduce(0.0, Double::sum);

        System.out.println("Total salary of all employees: " + totalSalary);

        //Find average salary of all employees.
        System.out.println("Average salary of all employees:");
        employees.stream().mapToDouble(Employee::getSalary).average()
                .ifPresent(avg -> System.out.println("Average salary of all employees: " + avg));

        employees.stream().mapToDouble(Employee::getSalary).max()
                .ifPresent(max -> System.out.println("Maximum salary of all employees: " + max));

        employees.stream().mapToDouble(Employee::getSalary).min()
                .ifPresent(min -> System.out.println("Minimum salary of all employees: " + min));

        //Find the second highest salary among all employees.
        System.out.println("Second highest salary among all employees:");
        employees.stream().map(Employee::getSalary).distinct().sorted(Comparator.reverseOrder())
                .skip(1).findFirst()
                .ifPresent(secondHighest -> System.out.println("Second highest salary: " + secondHighest));

        //Find number of employees in each department.
        System.out.println("Number of employees in each department:");
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .forEach((department, empCount) -> System.out.println(department + ": " + empCount));

        //Find average age of all employees.
        System.out.println("Average age of all employees");
        employees.stream().mapToDouble(Employee::getAge).average()
                .ifPresent(avgAge -> System.out.println("Average age of all employees: " + avgAge));

        double sum =  employees.stream().filter(e->e.getDepartment().equals("Engineering"))
                .mapToDouble(Employee::getSalary).sum();
        double sumEngSal = employees.stream().filter(e->e.getDepartment().equals("Engineering"))
                .mapToDouble(Employee::getSalary).reduce(0.000, Double::sum);

        System.out.println("Total salary of all employees in Engineering department: " + sum);

        System.out.println("Total salary of all employees in Engineering department using reduce: " + sumEngSal);

        System.out.println("Employee with highest salary:");
        employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).limit(1).forEach(System.out::println);

        //Find employee with highest salary.
        employees.stream().max(Comparator.comparing(Employee::getSalary))
                .ifPresent(emp -> System.out.println("Employee with highest salary: " + emp));

        //Find employee with lowest salary.
        employees.stream().min(Comparator.comparing(Employee::getSalary))
                .ifPresent(emp -> System.out.println("Employee with lowest salary: " + emp));



        employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).skip(1).limit(1)
                .forEach(emp -> System.out.println("Employee second highest salary: " + emp));

        employees.stream().sorted(Comparator.comparing(Employee::getSalary)).skip(1).limit(1)
                .forEach(emp -> System.out.println("Employee with second lowest salary: " + emp));


        employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).limit(3)
                .forEach(emp -> System.out.println("Top 3 highest paid employee " + emp));

        employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).forEach(System.out::println);

        employees.stream().map(Employee::getGender).distinct().forEach(System.out::println);

        //Print names of employees as comma-separated string.
        String namesCommaSeparated = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", "));
        System.out.println("Employee names (comma-separated): " + namesCommaSeparated);

        //Convert List<Employee> to Map<Integer, String> of id -> name.
        Map<Integer, String> idNameMap = employees.stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getName));
        System.out.println("ID to Name Map: " + idNameMap);


        //Grouping and Aggregation: Group employees by department and calculate average salary per department.

        //Group employees by department.
        Map<String, List<Employee>> empByDept =employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));

        for(Map.Entry<String, List<Employee>> emp:empByDept.entrySet()){
            String dept = emp.getKey();
            List<Employee> empList = emp.getValue();
            System.out.println("Department: " + dept);
            empList.stream().forEach(e-> System.out.println("\t" + e));
        }

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment))
                .forEach((dept, empList) -> {
                    System.out.println("Department: " + dept);
                    empList.stream().forEach(e -> System.out.println("\t" + e));
                });

        employees.stream().collect(Collectors.groupingBy(Employee::getGender)).forEach((dept, empList) -> {
            System.out.println("Gender: " + dept);
            empList.stream().forEach(e -> System.out.println("\t" + e));
        });

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            // Simulate a long-running computation
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 42;
        });


        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment)).forEach((dept, empList) -> {
            double avgSalary = empList.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
            System.out.println("Department: " + dept + ", Average Salary: " + avgSalary);
        });


        //Group employees by gender.
        employees.stream().collect(Collectors.groupingBy(Employee::getGender)).forEach((gender,emplist)->{
                System.out.println("Gender: " + gender);
                emplist.stream().forEach(e-> System.out.println("\t" + e));
        });

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting())
        ).forEach((department, empCount) -> System.out.println(department + ": " + empCount));

        //Group employees by department and count them.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting())
        ).forEach((department, empCount) -> System.out.println(department + ": " + empCount));

        employees.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting())
        ).forEach((gender, empCount) -> System.out.println(gender + ": " + empCount));


        //Group employees by department and calculate highest salary per department.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.maxBy(Comparator.comparing(Employee::getSalary)))
        ).forEach((department, emp) -> emp.ifPresent(e -> System.out.println(department + ": " + e)));


        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.minBy(Comparator.comparing(Employee::getSalary)))
        ).forEach((department, emp) -> emp.ifPresent(e -> System.out.println(department + ": " + e)));

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary))
        ).forEach((department, avgSalary) -> System.out.println(department + ": " + avgSalary));

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.averagingDouble(Employee::getAge))).forEach((department, avgAge) ->
                System.out.println(department + ": " + avgAge));

        //Find department with maximum employees.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(entry -> System.out.println("Department with maximum employees: " + entry.getKey() + " (" + entry.getValue() + ")"));

        Map<String,Long>  empdecount = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));


        int maxCount= 0;
        String empMax = null;
        for(Map.Entry<String, Long> entry : empdecount.entrySet()) {
            if(entry.getValue()>maxCount)
            {
                maxCount = empdecount.get(entry.getKey()).intValue();
                empMax = entry.getKey();
            }
        }
        System.out.println("Department with maximum employees: " + empMax + " (" + maxCount + ")");


        //Partition employees into male/female.
        Map<Boolean, List<Employee>> partitionedByGender = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getGender().equals("M")));
        System.out.println("Male Employees:");
        partitionedByGender.get(true).forEach(System.out::println);
        System.out.println("Female Employees:");
        partitionedByGender.get(false).forEach(System.out::println);


        employees.stream().collect(Collectors.partitioningBy(e->e.getSalary()>60000))
                .forEach((isHighSalary, empList) -> {
                    System.out.println(isHighSalary ? "Employees with salary > 60000:" : "Employees with salary <= 60000:");
                    empList.stream().forEach(e -> System.out.println("\t" + e));
                });

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.groupingBy(Employee::getGender)))
                .forEach((department, genderMap) -> {
                    System.out.println("Department: " + department);
                    genderMap.forEach((gender, empList) -> {
                        System.out.println("\tGender: " +   gender);
                        empList.stream().forEach(e -> System.out.println("\t\t" + e));
                    });
                });

        //Group employees by department, and find the highest-paid employee in each department.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.maxBy(Comparator.comparing(Employee::getSalary)))
        ).forEach((department, emp) -> emp.ifPresent(e -> System.out.println(department + ": " + e)));


        //List all employee names grouped by department.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.mapping(Employee::getName, Collectors.joining(", "))))
                .forEach((department, names) -> System.out.println(department + ": " + names));

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.maxBy(Comparator.comparing(Employee::getAge))))
                .forEach((department, emp) -> emp.ifPresent(e -> System.out.println(department + ": " + e)));


        employees.stream().map(Employee::getName).forEach(System.out::println);

        employees.stream().sorted(Comparator.comparing(Employee::getName)).forEach(System.out::println);

        employees.stream().filter(e->e.getDepartment().equals("HR")).map(Employee::getName).forEach(System.out::println);

        //Find number of employees in each department.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting())).forEach((dept,num)->{
            System.out.println("dept "+dept+" count "+ num);
        });


        employees.stream().max(Comparator.comparing(Employee::getSalary)).ifPresent(high ->{
            System.out.println(high);
        });

        employees.stream().mapToDouble(Employee::getSalary).max().ifPresent(max->
            System.out.println());

        employees.stream()
                .mapToDouble(Employee::getSalary)
                .max()
                .ifPresent(max -> System.out.println("The maximum salary is: " + max));

        employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).limit(3).forEach(System.out::println);

        employees.stream().map(Employee::getGender).distinct().forEach(System.out::println);

        String namesOf = employees.stream().map(Employee::getName).collect(Collectors.joining(","));
        System.out.println(namesOf);

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment)).forEach((dept,employeeList)->{
            System.out.println("Department = "+ dept);
            employeeList.stream().forEach(System.out::println);
        });

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting())).entrySet().stream()
                .max(Map.Entry.comparingByValue()).ifPresent(
                        entry -> System.out.println(entry.getKey())
                );

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.averagingDouble(Employee::getSalary))).entrySet().stream()
                .max(Map.Entry.comparingByValue()).ifPresent(
                        entry -> System.out.println(entry.getKey())
                );


    }
}
