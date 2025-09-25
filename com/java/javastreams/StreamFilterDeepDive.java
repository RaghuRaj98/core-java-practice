package com.java.javastreams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    // possibly more complex fields like budget, head, subteams...
    public Department(String name, String floor) { this.name = name; this.floor = floor; }
    public String getName() { return name; }
    public String getFloor() { return floor; }
    @Override
    public String toString() { return name + " (floor " + floor + ")"; }

    // equals/hashCode to support distinct/grouping by department object
    @Override
    public boolean equals(Object o) {
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

public class StreamFilterDeepDive {
    public static void main(String[] args) {
        // --- build sample complex data ---
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
            new Employee(106, "Grace", hr, 39000, null, Arrays.asList(hrRole), null), // null address
            new Employee(107, "Heidi", it, 120000, new Address("Bengaluru", "KA"), Arrays.asList(lead), mgrAlice),
            new Employee(108, "Ivan", finance, 50000, new Address("Delhi", "DL"), Arrays.asList(analyst), null)
        );

        System.out.println("\n=== 1) Simple numeric filter: salary > 60k ===");
        // Theory: simple stateless predicate on a primitive field
        employees.stream()
            .filter(e -> e.getSalary() > 60000)
            .forEach(System.out::println);

        System.out.println("\n=== My attempt) Simple string sort ===");
        String[] names = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi", "Ivan","1"};

        Stream.of(names).sorted().forEach(System.out::println);

        System.out.println("\n=== 3) Sort employees by name ===");
        employees.stream()
                .sorted(Comparator.comparing(Employee::getId).reversed())
                .forEach(System.out::println);

        System.out.println("\n=== 6) Multi-level sort: dept -> salary desc -> name ===");
        employees.stream()
                .sorted(Comparator.comparing(Employee::getDept, Comparator.comparing(Department::getName))
                        .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed())
                        .thenComparing(Employee::getName))
                .forEach(System.out::println);
        System.out.println("\n=== 2) Nested-property filter: dept name is IT ===");
        // Theory: filter uses nested object property; beware NPE if dept could be null
        employees.stream()
            .filter(e -> e.getDept() != null && "IT".equals(e.getDept().getName()))
            .forEach(System.out::println);

        System.out.println("\n=== My attempt) Nested-property filter: dept name is IT ===");
        employees.stream().filter(e -> e.getDept()!=null && e.getDept().getName().equals("IT")).forEach(System.out::println);

        System.out.println("\n=== 3) Null-safe filter: address may be null -> employees in Bengaluru ===");
        // Theory: check nested possibly-null fields in predicate to avoid NPE
        employees.stream()
            .filter(e -> {
                Address a = e.getAddress();
                return a != null && "Bengaluru".equals(a.getCity());
            })
            .forEach(System.out::println);

        employees.stream()
                .filter(e -> e.getAddress()!= null && "Bengaluru".equals(e.getAddress().getCity()))
                .forEach(System.out::println);

        System.out.println("\n=== 4) Predicate composition: HR and salary > 40k (using Predicate.and) ===");
        Predicate<Employee> isHR = e -> e.getDept() != null && "HR".equals(e.getDept().getName());
        Predicate<Employee> highPaid = e -> e.getSalary() > 40000;
        employees.stream()
            .filter(isHR.and(highPaid))
            .forEach(System.out::println);

        System.out.println("\n=== 5) Predicate OR and negate: (Finance OR IT) AND NOT manager ===");
        Predicate<Employee> isFinanceOrIT = e -> {
            Department d = e.getDept();
            return d != null && ("Finance".equals(d.getName()) || "IT".equals(d.getName()));
        };
        // manager is top-level field; we want employees who do not have a manager set (manager==null)
        employees.stream()
            .filter(isFinanceOrIT.and(Predicate.isEqual(null).negate().negate())) // trick: show negate usage below instead use lambda
            .filter(isFinanceOrIT) // keep it explicit for readability (above line is a contrived demonstration)
            .filter(e -> e.getManager() == null)
            .forEach(System.out::println);

        System.out.println("\n=== 6) Filter + map + distinct: unique departments of employees with salary>50k ===");
        // Theory: mapping before distinct changes what is considered distinct (we used Department equals/hashCode)
        employees.stream()
            .filter(e -> e.getSalary() > 50000)
            .map(Employee::getDept)
            .filter(Objects::nonNull)
            .distinct()
            .forEach(System.out::println);

        System.out.println("\n=== 7) Trace evaluation with peek: show before/after filter (lazy evaluation) ===");
        employees.stream()
            .peek(e -> System.out.println("seen: " + e.getName()))
            .filter(e -> {
                System.out.println("filtering: " + e.getName());
                return e.getSalary() > 50000;
            })
            .peek(e -> System.out.println("passed filter: " + e.getName()))
            .map(Employee::getName)
            .forEach(n -> System.out.println("final: " + n));

        System.out.println("\n=== 8) Filter + groupingBy: departments which have more than 1 employee ===");
        Map<Department, Long> countByDept = employees.stream()
            .filter(e -> e.getDept() != null)
            .collect(Collectors.groupingBy(Employee::getDept, Collectors.counting()));

        countByDept.entrySet().stream()
            .filter(entry -> entry.getValue() > 1)
            .forEach(entry -> System.out.println(entry.getKey() + " -> " + entry.getValue()));

        System.out.println("\n=== 9) findFirst after filter (short-circuit) - first IT employee with salary>100k ===");
        Optional<Employee> firstHighIt = employees.stream()
            .filter(e -> e.getDept() != null && "IT".equals(e.getDept().getName()))
            .filter(e -> e.getSalary() > 100000)
            .findFirst();

        System.out.println(firstHighIt.map(e -> "Found: " + e).orElse("No matching employee"));

        System.out.println("\n=== 10) Parallel stream caveat: don't use shared mutable state inside filter/map ===");
        // We'll compute count of Bengaluru employees using parallel stream safely (stateless predicate)
        long cnt = employees.parallelStream()
            .filter(e -> e.getAddress() != null && "Bengaluru".equals(e.getAddress().getCity()))
            .count();
        System.out.println("Bengaluru employees (parallel count): " + cnt);

        System.out.println("\n=== 11) Real-world complex predicate: employees who are developers living in Bengaluru and senior (>60k) ===");
        employees.stream()
            .filter(e -> e.getAddress() != null && "Bengaluru".equals(e.getAddress().getCity()))
            .filter(e -> e.getRoles().stream().anyMatch(r -> "Developer".equals(r.getName())))
            .filter(e -> e.getSalary() > 60000)
            .forEach(System.out::println);

        employees.sort(
                Comparator.comparing(Employee::getDept, Comparator.nullsLast(Comparator.comparing(Department::getName)))
                        .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed())
                        .thenComparing(Employee::getName)
        );


        employees.sort(Comparator.comparing(Employee::getId).reversed());

    }
}
