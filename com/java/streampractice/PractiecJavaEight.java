package com.java.streampractice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PractiecJavaEight {
    public static void main(String[] args) {
        System.out.println("Practice Java 8 Features");
        List<Employee> employees = Employee.sampleEmployees();

        System.out.println("Print all employee names.");
        employees.stream().forEach(s -> System.out.println(s));// Print names of employees earning more than 600

        System.out.println("Print all unique departments.");
        employees.stream().map(e -> e.getDepartment()).collect(Collectors.toSet()).forEach(System.out::println);

        System.out.println("Print all unique departments. ");
        employees.stream().map(e -> e.getDepartment()).distinct().forEach(System.out::println);

        System.out.println("Find total number of employees.");
        long count = employees.stream().count();
        System.out.println("Total number of employees: " + count);

        System.out.println("Count number of employees in “Engineering”.;");
        long co = employees.stream().filter(e -> e.getDepartment().equals(("Engineering"))).count();
        System.out.println("Number of employees in Engineering: " + co);

        System.out.println("Find all employees older than 30.");
        List<Employee> emp30 = employees.stream().filter(e -> e.getAge() > 30).collect(Collectors.toList());
        emp30.forEach(System.out::println);

        System.out.println("Find all female employees");
        employees.stream().filter(e -> e.getGender().equals("F")).forEach(System.out::println);

        System.out.println("Find all Male employees");
        employees.stream().filter(e -> e.getGender().equals("M")).forEach(System.out::println);

        System.out.println("All employee name in Uppercase");
        Stream<String> upper = employees.stream().map(e -> e.getName().toUpperCase());
        upper.forEach(System.out::println);

        System.out.println("Sort employees by Name");
        employees.stream().sorted(Comparator.comparing(Employee::getName)).forEach(System.out::println);

        System.out.println("Sort employees by Age");
        employees.stream().sorted(Comparator.comparing(e -> e.getAge())).forEach(System.out::println);

        System.out.println("Sort employees by Age Ascending");
        employees.stream().sorted(Comparator.comparing(Employee::getAge).reversed()).forEach(System.out::println);

        OptionalDouble avg = employees.stream().mapToDouble(Employee::getSalary).average();

        if (avg.isPresent()) {
            System.out.println("Average salary of employees: " + avg.getAsDouble());
        }

        Map<String, List<Employee>> depMap = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, HashMap::new, Collectors.toList()));

        for (Map.Entry<String, List<Employee>> entry : depMap.entrySet()) {
            System.out.println("Department: " + entry.getKey());
            long num = entry.getValue().stream().count();
            System.out.println("Number of Employees: " + num);
        }


        System.out.println("Find number of employees in each department.");
        Map<String, Long> empCountByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        empCountByDept.forEach((dept, count1) -> System.out.println(dept + ": " + count1));


        employees.stream().mapToDouble(Employee::getAge).average().ifPresent(avgAge ->
                System.out.println("Average age of employees: " + avgAge)
        );

        double sumofSalary = employees.stream().filter(e -> e.getDepartment().equals("Engineering")).mapToDouble(Employee::getSalary).sum();

        System.out.println("Total salary of employees in Engineering department: " + sumofSalary);

        Optional<Employee> maxSalEmployee = employees.stream().max(Comparator.comparing(Employee::getSalary));

        if (maxSalEmployee.isPresent()) {
            System.out.println("Employee with highest salary: " + maxSalEmployee.get());
        }

        Optional<Employee> minSalEmployee = employees.stream().min(Comparator.comparing(Employee::getSalary));

        if (minSalEmployee.isPresent()) {
            System.out.println("Employee with lowest salary: " + minSalEmployee.get());
        }

        employees.stream().sorted(Comparator.comparing(Employee::getSalary)).skip(1).limit(1).forEach(e ->
                System.out.println("Second lowest salary employee: " + e)
        );


        employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).skip(1).limit(1).forEach(e ->
                System.out.println("Second Highest salary employee: " + e)
        );

        employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).limit(1).forEach(e ->
                System.out.println("Highest salary salary employee: " + e)
        );

        System.out.println("Top 3 highest paid employees:");
        employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).limit(3).forEach(System.out::println);

        System.out.println("All unique genders of employees:");
        employees.stream().map(Employee::getGender).collect(Collectors.toSet()).forEach(System.out::println);


        String p = employees.stream().map(Employee::getName).collect(Collectors.joining(","));

        System.out.println("All employee names concatenated: " + p);

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment)).forEach((dep, elist) -> {
            System.out.println("Department: " + dep);
            elist.forEach(System.out::println);
        });

        Map<String, Long> countEmp = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        for (Map.Entry<String, Long> entry : countEmp.entrySet()) {
            System.out.println("Department: " + entry.getKey() + ", Number of Employees: " + entry.getValue());
        }

        System.out.println("Find department with maximum employees.");
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(entry -> System.out.println("Department with maximum employees: " + entry.getKey() + " (" + entry.getValue() + " employees)"));

        employees.stream().max(Comparator.comparing(Employee::getAge)).ifPresent(e ->
                System.out.println("Oldest employee: " + e)
        );

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(entry -> System.out.println("Department with highest average salary: " + entry.getKey() + " (Average Salary: " + entry.getValue() + ")"));


        employees.stream().collect(Collectors.partitioningBy(e -> e.getGender().equals("F"))).forEach((gender, list) -> {
            System.out.println("Gender:");
            if (gender) {
                System.out.println("Female Employees:");
            } else {
                System.out.println("Male Employees:");
            }
            list.forEach(System.out::println);
        });


        //Group employees by department, then by gender.
        Map<String, Map<String, List<Employee>>> groupedData = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.groupingBy(Employee::getGender)));


        // Print the result
        groupedData.forEach((dept, genderMap) -> {
            System.out.println("Department: " + dept);
            genderMap.forEach((gender, empList) -> {
                System.out.println("  " + gender + " -> " + empList);
            });
        });

        for (Map.Entry<String, Map<String, List<Employee>>> entry : groupedData.entrySet()) {
            System.out.println("Department: " + entry.getKey());
            Map<String, List<Employee>> genderMap = entry.getValue();
            for (Map.Entry<String, List<Employee>> genderEntry : genderMap.entrySet()) {
                System.out.println(" Gender: " + genderEntry.getKey());
                List<Employee> empList = genderEntry.getValue();
                empList.forEach(e -> System.out.println("   " + e));
            }
        }


        //Group employees by department, then get average salary per gender.
        Map<String, Map<String, Double>> data = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getSalary))));

        for (Map.Entry<String, Map<String, Double>> entry : data.entrySet()) {
            System.out.println("Department " + entry.getKey());
            for (Map.Entry<String, Double> genderAvg : entry.getValue().entrySet()) {
                System.out.println("gender: " + genderAvg.getKey() + ", Average Salary: " + genderAvg.getValue());
            }
        }

        //Find highest paid employee in each department.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.maxBy(Comparator.comparing(Employee::getSalary))))
                .forEach((dep, emp) -> System.out.println("Department: " + dep + ", Highest Paid Employee: " + emp.get()));


        //List all employee names grouped by department.
        employees.stream().collect(Collectors.groupingBy((Employee::getDepartment),
                        Collectors.mapping(Employee::getName, Collectors.toList())))
                .forEach((dep, names) -> System.out.println("Department: " + dep + ", Employee Names: " + names));

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.maxBy(Comparator.comparing(Employee::getAge))))
                .forEach((dep, emp) -> System.out.println("Department: " + dep + ", Oldest Employee: " + emp.get()));

        Map<String, Double> deptotalsal = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingDouble(Employee::getSalary)));

        String depname = "";
        double maxsal = 0.0;
        for (Map.Entry<String, Double> entry : deptotalsal.entrySet()) {
            if (entry.getValue() > maxsal) {
                maxsal = entry.getValue();
                depname = entry.getKey();
            }
        }
        System.out.println("Department with highest total salary: " + depname + " (Total Salary: " + maxsal + ")");


        //List all employee names grouped by department.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.mapping(Employee::getName, Collectors.toList())))
                .forEach((dep, names) -> System.out.println("Department: " + dep + ", Employee Names: " + names));


        //Get name of the oldest employee in each department.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.maxBy(Comparator.comparing(Employee::getAge))))
                .forEach((dep, emp) -> System.out.println("Department: " + dep + ", Oldest Employee: " + emp.get()));

        //Find employee count in each department sorted descending.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> System.out.println("Department: " + entry.getKey() + ", Employee Count: " + entry.getValue()));


        //Find department with maximum total salary.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingDouble(Employee::getSalary)))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(entry -> System.out.println("Department with maximum total salary: " + entry.getKey() + " (Total Salary: " + entry.getValue() + ")"));

        //Find youngest employee in each department.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.minBy(Comparator.comparing(Employee::getAge))))
                .forEach((dep, emp) -> System.out.println("Department: " + dep + ", Youngest Employee: " + emp.get()));

        //Get flattened list of all department names (distinct).
        employees.stream().map(Employee::getDepartment).distinct().forEach(System.out::println);

        //Create a Map<String, Double> of department -> totalSalary.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingDouble(Employee::getSalary)))
                .forEach((dep, totalSal) -> System.out.println("Department: " + dep + ", Total Salary: " + totalSal));

        //Create a Map<String, Employee> of department -> highestPaidEmployee.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, TreeMap::new, Collectors.maxBy(Comparator.comparing(Employee::getSalary))))
                .forEach((dep, emp) -> System.out.println("Department: " + dep + ", Highest Paid Employee: " + emp.get()));

        //Create a list of employee names sorted by department, then by salary descending.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment))
                .forEach((dep, empList) -> {
                    System.out.println("Department: " + dep);
                    empList.stream().sorted(Comparator.comparing(Employee::getSalary).reversed())
                            .map(Employee::getName)
                            .forEach(name -> System.out.println(" Employee Name: " + name));
                });
        //Find if any employee earns more than ₹1,00,000.
        boolean anyMatch = employees.stream().anyMatch(e -> e.getSalary() > 100000);
        if (anyMatch) {
            System.out.println("There is at least one employee earning more than ₹1,00,000.");
        } else {
            System.out.println("No employee earns more than ₹1,00,000.");
        }

        boolean allEmpGreatThanTwenty = employees.stream().allMatch(e -> e.getAge() > 20);

        if (allEmpGreatThanTwenty) {
            System.out.println("All employees are older than 20.");
        } else {
            System.out.println("Not all employees are older than 20.");
        }

        boolean noLawyer = employees.stream().noneMatch(e -> e.getDepartment().equals("Legal"));

        if(noLawyer) {
            System.out.println("There are no employees in the Legal department.");
        } else {
            System.out.println("There are employees in the Legal department.");
        }

        boolean anyEngineer
                 =employees.stream().anyMatch(e->e.getDepartment().equals("Engineering"));

        if(anyEngineer) {
            System.out.println("There is at least one employee in the Engineering department.");
        }
        else {
            System.out.println("There are no employees in the Engineering department.");
        }

        employees.stream().sorted(Comparator.comparing(Employee::getName).reversed()).limit(1).forEach(e->
                System.out.println("Employee with the smallest name lexicographically: "+e)
        );

        double emptotalSal = employees.stream().mapToDouble(Employee::getSalary).sum();

        System.out.println("Total salary of all employees: " + emptotalSal);


        //Print all employee names.
        employees.stream().map(Employee::getName).forEach(System.out::println);
        employees.stream().forEach(e -> System.out.println(e.getName()));

    }
}
