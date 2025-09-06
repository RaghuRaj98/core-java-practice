package com.java.javastreams.maps;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private final String floor;
    public Department(String name, String floor) { this.name = name; this.floor = floor; }
    public String getName() { return name; }
    public String getFloor() { return floor; }
    @Override public String toString() { return name + " (floor " + floor + ")"; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department d = (Department) o;
        return Objects.equals(name, d.name) && Objects.equals(floor, d.floor);
    }
    @Override public int hashCode() { return Objects.hash(name, floor); }
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
    private final Employee manager; // may be null

    public Employee(int id, String name, Department dept, double salary,
                    Address address, List<Role> roles, Employee manager) {
        this.id = id; this.name = name; this.dept = dept; this.salary = salary;
        this.address = address; this.roles = roles == null ? Collections.emptyList() : roles;
        this.manager = manager;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Department getDept() { return dept; }
    public double getSalary() { return salary; }
    public Address getAddress() { return address; }
    public List<Role> getRoles() { return roles; }
    public Employee getManager() { return manager; }

    @Override
    public String toString() {
        return id + ":" + name + " {" + dept + ", $" + salary + ", " + address + ", roles=" + roles + "}";
    }
}

public class StreamMapDeepDive {
    public static void main(String[] args) {
        Department it = new Department("IT", "5");
        Department hr = new Department("HR", "2");
        Department finance = new Department("Finance", "3");

        Role dev = new Role("Developer");
        Role lead = new Role("Lead");
        Role hrRole = new Role("HR");
        Role analyst = new Role("Analyst");

        Employee mgrAlice = new Employee(100, "Alice", it, 130000,
                new Address("Bengaluru", "KA"), Arrays.asList(lead, dev), null);

        List<Employee> employees = Arrays.asList(
            mgrAlice,
            new Employee(101, "Bob", hr, 55000, new Address("Pune", "MH"), Arrays.asList(hrRole), null),
            new Employee(102, "Charlie", finance, 70000, new Address("Mumbai", "MH"), Arrays.asList(analyst), mgrAlice),
            new Employee(103, "David", it, 45000, new Address("Bengaluru", "KA"), Arrays.asList(dev), mgrAlice),
            new Employee(104, "Eve", finance, 80000, new Address("Chennai", "TN"), Arrays.asList(analyst), null),
            new Employee(105, "Frank", it, 60000, new Address("Bengaluru", "KA"), Arrays.asList(dev), mgrAlice),
            new Employee(106, "Grace", hr, 39000, null, Arrays.asList(hrRole), null),
            new Employee(107, "Heidi", it, 120000, new Address("Bengaluru", "KA"), Arrays.asList(lead), mgrAlice),
            new Employee(108, "Ivan", finance, 50000, new Address("Delhi", "DL"), Arrays.asList(analyst), null)
        );

        System.out.println("\n=== 1) map: Employee -> name (simple mapping) ===");
        employees.stream()
            .map(Employee::getName)
            .forEach(System.out::println);

        System.out.println("\n=== 2) map to DTO: Employee -> Map (id,name,dept) ===");
        List<Map<String, Object>> dto = employees.stream()
            .map(e -> {
                Map<String, Object> m = new HashMap<>();
                m.put("id", e.getId());
                m.put("name", e.getName());
                m.put("dept", e.getDept() != null ? e.getDept().getName() : null);
                return m;
            })
            .collect(Collectors.toList());
        dto.forEach(System.out::println);

        System.out.println("\n=== 3) map + filter: names of employees with salary>60k ===");
        employees.stream()
            .filter(e -> e.getSalary() > 60000)
            .map(Employee::getName)
            .forEach(System.out::println);

        System.out.println("\n=== 4) map vs flatMap: list of roles (map returns Stream<List<Role>>) ===");
        // map produces a Stream<List<Role>>
        employees.stream()
            .map(Employee::getRoles)
            .limit(3)
            .forEach(list -> System.out.println("roles list: " + list));

        System.out.println("\n=== 5) flatMap: flatten roles to Stream<Role> and distinct role names ===");
        employees.stream()
            .flatMap(e -> e.getRoles().stream())
            .map(Role::getName)
            .distinct()
            .forEach(System.out::println);



        System.out.println("\n=== 6) map to primitive stream (IntStream) and compute sum/range ===");
        int sumIds = employees.stream()
            .mapToInt(Employee::getId) // IntStream avoids boxing
            .sum();
        System.out.println("Sum of IDs: " + sumIds);

        System.out.println("\n=== 7) mapping collector: grouping departments -> list of employee names ===");
        Map<Department, List<String>> namesByDept = employees.stream()
            .filter(e -> e.getDept() != null)
            .collect(Collectors.groupingBy(
                Employee::getDept,
                Collectors.mapping(Employee::getName, Collectors.toList())
            ));
        namesByDept.forEach((d, names) -> System.out.println(d + " -> " + names));

        System.out.println("\n=== 8) map with Optional: safe navigation to manager name ===");
        List<String> managerNames = employees.stream()
            .map(e -> Optional.ofNullable(e.getManager()).map(Employee::getName).orElse("No-Manager"))
            .collect(Collectors.toList());
        managerNames.forEach(System.out::println);

        System.out.println("\n=== 9) map with side-effect (don't do this) ===");
        List<String> collector = new ArrayList<>();
        employees.stream()
            .map(Employee::getName)
            .forEach(name -> {
                // side-effect on external collection — unsafe in parallel
                collector.add(name);
            });
        System.out.println("Collected (unsafe): " + collector);

        System.out.println("\n=== 10) parallel map caution: pure functions only ===");
        List<String> parallelNames = employees.parallelStream()
            .map(Employee::getName) // pure mapping
            .collect(Collectors.toList());
        System.out.println("Parallel names: " + parallelNames);

        System.out.println("\n=== 11) transform to different object: Employee -> Summary class ===");
        List<EmployeeSummary> summaries = employees.stream()
            .map(e -> new EmployeeSummary(e.getId(), e.getName(), e.getDept() == null ? null : e.getDept().getName(), e.getSalary()))
            .collect(Collectors.toList());
        summaries.forEach(System.out::println);

        System.out.println("\n=== 12) mapping + reducing: longest employee name ===");
        Optional<String> longest = employees.stream()
            .map(Employee::getName)
            .reduce((a, b) -> a.length() >= b.length() ? a : b);
        System.out.println("Longest name: " + longest.orElse("none"));

        System.out.println("\n=== 13) map chaining: complex transformation example ===");
        employees.stream()
            .map(e -> e.getDept() == null ? "NO_DEPT" : e.getDept().getName().toUpperCase())
            .filter(Objects::nonNull)
            .distinct()
            .forEach(System.out::println);
    }

    // simple DTO/summary used in example
    static class EmployeeSummary {
        private final int id;
        private final String name;
        private final String dept;
        private final double salary;
        EmployeeSummary(int id, String name, String dept, double salary) {
            this.id = id; this.name = name; this.dept = dept; this.salary = salary;
        }
        @Override public String toString() {
            return "Sum{" + id + "," + name + "," + dept + ",$" + salary + "}";
        }
    }
}
