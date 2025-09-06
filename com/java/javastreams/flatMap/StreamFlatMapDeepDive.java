package com.java.javastreams.flatMap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Demonstrates flatMap usage with complex Employee/Department/Role classes.
 * Java 8+ runnable.
 */
class Address {
    private final String city;
    private final String state;
    public Address(String city, String state) { this.city = city; this.state = state; }
    public String getCity() { return city; }
    public String getState() { return state; }
    @Override public String toString() { return city + ", " + state; }
}

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
}

class Employee {
    private final int id;
    private final String name;
    private final Department dept;
    private final double salary;
    private final Address address;
    private final List<Role> roles;
    private final List<Employee> reports; // direct reports (could be empty)

    public Employee(int id, String name, Department dept, double salary,
                    Address address, List<Role> roles, List<Employee> reports) {
        this.id = id; this.name = name; this.dept = dept; this.salary = salary;
        this.address = address; this.roles = roles == null ? Collections.emptyList() : roles;
        this.reports = reports == null ? Collections.emptyList() : reports;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Department getDept() { return dept; }
    public double getSalary() { return salary; }
    public Address getAddress() { return address; }
    public List<Role> getRoles() { return roles; }
    public List<Employee> getReports() { return reports; }

    @Override
    public String toString() {
        return id + ":" + name + " {" + dept + ", $" + salary + ", roles=" + roles + "}";
    }
}

public class StreamFlatMapDeepDive {
    public static void main(String[] args) {
        Department it = new Department("IT");
        Department hr = new Department("HR");
        Department finance = new Department("Finance");

        Role dev = new Role("Developer");
        Role lead = new Role("Lead");
        Role hrRole = new Role("HR");
        Role analyst = new Role("Analyst");

        // Small org: manager with reports (hierarchy) to show flattening nested lists
        Employee empA = new Employee(201, "Ann", it, 120000, new Address("Bengaluru", "KA"), Arrays.asList(lead, dev), null);
        Employee empB = new Employee(202, "Ben", it, 80000, new Address("Bengaluru", "KA"), Arrays.asList(dev), null);
        Employee empC = new Employee(203, "Cara", hr, 60000, new Address("Pune", "MH"), Arrays.asList(hrRole), null);
        Employee empD = new Employee(204, "Dan", finance, 70000, new Address("Mumbai", "MH"), Arrays.asList(analyst), null);
        Employee empE = new Employee(205, "Eli", it, 90000, new Address("Bengaluru", "KA"), Arrays.asList(dev), null);

        // Manager has direct reports
        Employee manager = new Employee(300, "ManagerM", it, 200000, new Address("Bengaluru", "KA"),
                Arrays.asList(lead), Arrays.asList(empA, empB, empC));

        // CEO with reports including manager and others -> nested lists
        Employee ceo = new Employee(400, "CEO", it, 500000, new Address("Bengaluru", "KA"),
                Arrays.asList(lead), Arrays.asList(manager, empD, empE));

        List<Employee> employees = Arrays.asList(ceo, manager, empA, empB, empC, empD, empE);

        System.out.println("=== 1) map() vs flatMap() : get List<List<Role>> vs flattened List<Role> ===");
        // map => Stream<List<Role>>
        employees.stream()
            .map(Employee::getRoles)
            .limit(3)
            .forEach(list -> System.out.println("roles list: " + list));

        System.out.println("-- using flatMap to flatten roles into single stream --");
        employees.stream()
            .flatMap(e -> e.getRoles().stream())            // Stream<Role>
            .map(Role::getName)
            .distinct()
            .forEach(System.out::println);

        System.out.println("\n=== 2) Flatten nested employee hierarchy to a stream of all employees (BFS-like) ===");
        // naive flatten of one-level reports: flatten manager -> direct reports
        employees.stream()
            .flatMap(e -> e.getReports().stream())
            .forEach(e -> System.out.println("direct report: " + e.getName()));

        System.out.println("\n=== 3) Fully flatten hierarchy recursively (using flatMap + helper) ===");
        // recursive helper to get a stream of employee + all reports (depth-first)
        Stream<Employee> all = employees.stream().flatMap(StreamFlatMapDeepDive::employeeAndAllReports);
        all.forEach(e -> System.out.println("flattened: " + e.getName()));

        System.out.println("\n=== 4) Extract all cities where employees live (flatten optional/address null-safety) ===");
        employees.stream()
            .map(Employee::getAddress)            // Stream<Address>
            .filter(Objects::nonNull)
            .map(Address::getCity)
            .distinct()
            .forEach(System.out::println);

        System.out.println("\n=== 5) flatMap with Optional (to avoid nulls elegantly) ===");
        // Suppose some employees may have null address; we can use Optional to stream 0/1 element
        employees.stream()
            .flatMap(e -> Optional.ofNullable(e.getAddress()).map(a -> Stream.of(a.getCity())).orElseGet(Stream::empty))
            .distinct()
            .forEach(System.out::println);

        System.out.println("\n=== 5) flatMap with Optional (to avoid nulls elegantly) ===");
        // Suppose some employees may have null address; we can use Optional to stream 0/1 element
        employees.stream()
                .flatMap(e -> Optional.ofNullable(e.getAddress()).map(a -> Stream.of(a.getCity())).orElseGet(Stream::empty))
                .distinct()
                .forEach(System.out::println);

        System.out.println("\n=== 6) flatMap to pair employees with roles (EmployeeName -> RoleName) ===");
        employees.stream()
            .flatMap(e -> e.getRoles().stream().map(r -> e.getName() + "->" + r.getName()))
            .forEach(System.out::println);

        System.out.println("\n=== 7) flatMap to stream of primitive values (ids) via mapToInt ===");
        int sum = employees.stream()
            .flatMapToInt(e -> java.util.stream.IntStream.of(e.getId()))
            .sum();
        System.out.println("sum ids: " + sum);

        System.out.println("\n=== 8) flatMap with splitting and filtering duplicates ===");
        // roles could be duplicated across employees -> flatten, map, distinct
        List<String> uniqueRoles = employees.stream()
            .flatMap(e -> e.getRoles().stream())
            .map(Role::getName)
            .distinct()
            .collect(Collectors.toList());
        System.out.println("unique roles: " + uniqueRoles);

        System.out.println("\n=== 9) Using flatMap to build cross-product (cartesian) example ===");
        // Suppose we want all possible pairs of (employee, project) where each employee can have projects
        Map<String, List<String>> projects = new HashMap<>();
        projects.put("Ann", Arrays.asList("P1", "P2"));
        projects.put("Ben", Arrays.asList("P2"));
        // cross product: (e, project) for employees with projects
        employees.stream()
            .flatMap(e -> Optional.ofNullable(projects.get(e.getName()))
                                  .map(list -> list.stream().map(p -> e.getName() + ":" + p))
                                  .orElseGet(Stream::empty))
            .forEach(System.out::println);

        System.out.println("\n=== 10) Parallel flatMap caution: ensure inner streams are independent ===");
        List<String> parallelFlatten = employees.parallelStream()
            .flatMap(e -> e.getRoles().stream().map(Role::getName))
            .distinct()
            .collect(Collectors.toList());
        System.out.println("parallel flattened roles: " + parallelFlatten);
    }

    // helper: returns stream of employee and recursively all reports (depth-first)
    private static Stream<Employee> employeeAndAllReports(Employee e) {
        return Stream.concat(
            Stream.of(e),
            e.getReports().stream().flatMap(StreamFlatMapDeepDive::employeeAndAllReports)
        );
    }
}
