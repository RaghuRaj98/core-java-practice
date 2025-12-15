package com.java.streampractice;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PracticeJavaEightThree {
    public static void main(String[] args) {
        System.out.println("Practice Java 8 Features");
        List<Employee> employees = Employee.sampleEmployees();

        //Print all employee names
        employees.stream().map(Employee::getName).collect(Collectors.toList()).forEach(
                System.out::println
        );

        System.out.println(employees.stream().count());

        employees.stream().filter(e -> e.getDepartment().equals("IT"))
                .count();

        employees.stream().map(e -> e.getName().toUpperCase()).collect(Collectors.toList())
                .forEach(System.out::println);

        employees.stream().sorted(Comparator.comparing(Employee::getName).reversed())
                .collect(Collectors.toList())
                .forEach(e -> System.out.println(e.getName()));

        employees.stream().filter(e -> e.getDepartment().equals("HR"))
                .collect(Collectors.toList())
                .forEach(e -> System.out.println(e.getName()));

        employees.stream().map(Employee::getAge).collect(Collectors.toList())
                .forEach(System.out::println);

        employees.stream().filter(e -> isStartwith(e.getName())).map(Employee::getName)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println(employees.stream().mapToDouble(Employee::getSalary).sum());


        employees.stream().mapToDouble(Employee::getSalary).average();

        employees.stream().max(Comparator.comparing(Employee::getSalary));

        employees.stream().min(Comparator.comparing(Employee::getAge));

        double ans = employees.stream().mapToDouble(Employee::getSalary).reduce(0.0, (a, b) -> a + b);


        System.out.println("Total Salary: " + ans);

        employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).skip(1).findFirst()
                .ifPresent(e -> System.out.println("2nd Highest Salary: " + e.getSalary()));

        employees.stream().min(Comparator.comparing(Employee::getSalary))
                .ifPresent(e -> System.out.println("Lowest Salary: " + e.getSalary()));


        String names = employees.stream().map(Employee::getName).collect(Collectors.joining(", "));

        employees.stream().collect(Collectors.toMap(Employee::getId, Employee::getName));

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment)).forEach(
                (dept, empList) -> {
                    System.out.println("Department: " + dept);
                    empList.forEach(e -> System.out.println(" - " + e.getName()));
                }
        );

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment)).entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().size()))
                .ifPresent(e -> System.out.println("Department with most employees: " + e.getKey() + " (" + e.getValue().size() + " employees)"));

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting())).forEach((dept,count)->{
            System.out.println("Department: " + dept + ", Employee Count: " + count);
        });

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.averagingDouble(Employee::getSalary))).forEach((dept,avgSalary)->{
            System.out.println("Department: " + dept + ", Average Salary: " + avgSalary);
        });

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting())).entrySet().stream().filter(e-> e.getValue()>2).forEach(e->{
            System.out.println("Department with more than 2 employees: " + e.getKey() + " (" + e.getValue() + " employees)");
        });

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.summingDouble(Employee::getSalary))).forEach((dept,totalSalary)->{
            System.out.println("Department: " + dept + ", Total Salary: " + totalSalary);
        });

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting())).entrySet()
                .stream().max(Comparator.comparingLong(e-> e.getValue()))
                .ifPresent(e->
            System.out.println("Department with highest number of employees: " + e.getKey() + " (" + e.getValue() + " employees)")
        );


        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.averagingDouble(Employee::getSalary)))
                .entrySet().stream().max(Comparator.comparingDouble(e-> e.getValue()))
                .ifPresent(e->
            System.out.println("Department with highest average salary: " + e.getKey() + " (Average Salary: " + e.getValue() + ")")
        );

        //Get flattened list of all department names (distinct).
        employees.stream().map(Employee::getDepartment).distinct().collect(Collectors.toList())
                .forEach(System.out::println);

        employees.stream().collect(Collectors.partitioningBy(e-> e.getAge()>30)).forEach((isAbove30, empList)->{
            System.out.println(isAbove30 ? "Employees above 30:" : "Employees 30 or below:");
            empList.forEach(e-> System.out.println(" - " + e.getName()));
        });
        //Group employees by department, then get average salary per gender.
        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.groupingBy(Employee::getGender,
                        Collectors.averagingDouble(Employee::getSalary)))).forEach((dept, genderAvgSalaryMap)->{
            System.out.println("Department: " + dept);
            genderAvgSalaryMap.forEach((gender, avgSalary)->{
                System.out.println("    Gender: " + gender + ", Average Salary: " + avgSalary);
            });
        });
    }

    private static boolean isStartwith(String name) {
        char c = name.charAt(0);
        return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }


}
