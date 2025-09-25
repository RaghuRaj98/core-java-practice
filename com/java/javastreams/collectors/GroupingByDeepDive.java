package com.java.javastreams.collectors;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import static java.util.stream.Collectors.*;

/**
 * GroupingBy deep dive — 20 examples.
 * Java 8+ runnable.
 */
public class GroupingByDeepDive {

    // domain classes
    static class Department {
        private final String name;
        Department(String name) { this.name = name; }
        public String getName() { return name; }
        @Override public String toString() { return name; }
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Department)) return false;
            return Objects.equals(name, ((Department)o).name);
        }
        @Override public int hashCode() { return Objects.hash(name); }
    }

    static class Role {
        private final String name;
        Role(String name) { this.name = name; }
        public String getName() { return name; }
        @Override public String toString() { return name; }
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Role)) return false;
            return Objects.equals(name, ((Role)o).name);
        }
        @Override public int hashCode() { return Objects.hash(name); }
    }

    static class Employee {
        private final int id;
        private final String name;
        private final Department dept;
        private final double salary;
        private final List<Role> roles;

        Employee(int id, String name, Department dept, double salary, List<Role> roles) {
            this.id = id; this.name = name; this.dept = dept; this.salary = salary;
            this.roles = roles == null ? Collections.emptyList() : roles;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public Department getDept() { return dept; }
        public double getSalary() { return salary; }
        public List<Role> getRoles() { return roles; }

        @Override public String toString() {
            return id + ":" + name + "{" + (dept==null ? "NO_DEPT" : dept.getName()) + ",$" + salary + ",roles=" + roles + "}";
        }
    }

    // sample data
    static List<Employee> sampleEmployees() {
        Department it = new Department("IT");
        Department hr = new Department("HR");
        Department fin = new Department("Finance");
        Role dev = new Role("Developer");
        Role lead = new Role("Lead");
        Role hrRole = new Role("HR");
        Role analyst = new Role("Analyst");

        return Arrays.asList(
            new Employee(1, "Alice", it, 100_000, Arrays.asList(lead, dev)),
            new Employee(2, "Bob", hr, 55_000, Arrays.asList(hrRole)),
            new Employee(3, "Charlie", fin, 70_000, Arrays.asList(analyst)),
            new Employee(4, "David", it, 120_000, Arrays.asList(dev)),
            new Employee(5, "Eve", fin, 80_000, Arrays.asList(analyst)),
            new Employee(6, "Frank", it, 60_000, Arrays.asList(dev)),
            new Employee(7, "Grace", hr, 39_000, Arrays.asList(hrRole)),
            new Employee(8, "Heidi", it, 120_000, Arrays.asList(lead)),
            new Employee(9, "Ivan", fin, 50_000, Arrays.asList(analyst)),
            new Employee(10, "Judy", null, 45_000, Arrays.asList()) // no dept
        );
    }

    public static void main(String[] args) {
        List<Employee> emps = sampleEmployees();
        System.out.println("Employees:");
        emps.forEach(System.out::println);
        System.out.println("\n--- Running 20 groupingBy examples ---\n");

        ex1_groupByDeptList(emps);
        ex2_groupByDeptCount(emps);
        ex3_groupByDeptSumSalary(emps);
        ex4_groupByDeptAvgSalary(emps);
        ex5_groupByDeptMaxSalaryEmployee(emps);
        ex6_groupByDeptMinSalaryEmployee(emps);
        ex7_groupByDeptNamesJoined(emps);
        ex8_nestedGroupingByDeptSalaryBracket(emps);
        ex9_groupByRoleListOfEmployees_flattened(emps);
        ex10_groupingBy_with_partitioningDownstream(emps);
        ex11_groupingBy_toMapDownstream(emps);
        ex12_groupingBy_with_mappingToSet(emps);
        ex13_groupingBy_concurrent(emps);
        ex14_groupingBy_orderedMap_LinkedHashMap(emps);
        ex15_groupingBy_usingReducing_sumSalary(emps);
        ex16_groupingBy_collectingAndThen_topPaidPerDept(emps);
        ex17_groupingBy_count_then_percentagePerDept(emps);
        ex18_groupingBy_with_customKeyTransformer(emps);
        ex19_groupingBy_keyByPredicateBuckets(emps);
        ex20_groupBy_when_classifier_mayBeNull_handleNullKeys(emps);
    }

    // 1) Simple grouping: dept -> List<Employee>
    static void ex1_groupByDeptList(List<Employee> emps) {
        System.out.println("=== 1) groupBy dept -> List<Employee> ===");
        Map<Department, List<Employee>> byDept =
            emps.stream()
                .filter(e -> e.getDept() != null)          // optional: drop null dept
                .collect(groupingBy(Employee::getDept));
        byDept.forEach((d, list) -> System.out.println(d + " -> " + list));
        System.out.println();
    }

    // 2) grouping with counting: dept -> count
    static void ex2_groupByDeptCount(List<Employee> emps) {
        System.out.println("=== 2) groupBy dept -> count (Collectors.counting) ===");
        Map<Department, Long> counts = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept, counting()));
        counts.forEach((k,v) -> System.out.println(k + " -> " + v));
        System.out.println();
    }

    // 3) grouping with summingDouble: dept -> sum(salary)
    static void ex3_groupByDeptSumSalary(List<Employee> emps) {
        System.out.println("=== 3) groupBy dept -> sum of salary ===");
        Map<Department, Double> sumByDept = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept,
                    summingDouble(Employee::getSalary)));
        sumByDept.forEach((k,v) -> System.out.println(k + " -> $" + v));
        System.out.println();
    }

    // 4) grouping with averagingDouble: dept -> avg salary
    static void ex4_groupByDeptAvgSalary(List<Employee> emps) {
        System.out.println("=== 4) groupBy dept -> average salary ===");
        Map<Department, Double> avg = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept, averagingDouble(Employee::getSalary)));
        avg.forEach((k,v) -> System.out.println(k + " -> avg $" + v));
        System.out.println();
    }

    // 5) grouping -> maxBy to get Employee with max salary per dept
    static void ex5_groupByDeptMaxSalaryEmployee(List<Employee> emps) {
        System.out.println("=== 5) groupBy dept -> max salary employee (Optional) ===");
        Map<Department, Optional<Employee>> topPerDept = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept,
                    maxBy(Comparator.comparingDouble(Employee::getSalary))));
        topPerDept.forEach((d, opt) -> System.out.println(d + " -> " + opt));
        System.out.println();
    }

    // 6) grouping -> minBy then collectingAndThen(Optional::get) safely with filter
    static void ex6_groupByDeptMinSalaryEmployee(List<Employee> emps) {
        System.out.println("=== 6) groupBy dept -> min salary employee (unwrap Optional) ===");
        Map<Department, Optional<Employee>> minPerDept = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept,
                    minBy(Comparator.comparingDouble(Employee::getSalary))));
        minPerDept.forEach((d, e) -> {System.out.println(d + " -> " + e);});
        System.out.println();
    }

    // 7) mapping downstream: dept -> joined names
    static void ex7_groupByDeptNamesJoined(List<Employee> emps) {
        System.out.println("=== 7) groupBy dept -> joined names (mapping + joining) ===");
        Map<Department, String> joined = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept,
                mapping(Employee::getName, joining(", ", "[", "]"))));
        joined.forEach((k,v) -> System.out.println(k + " -> " + v));
        System.out.println();
    }

    // 8) Nested grouping: dept -> salary bracket -> list of employees
    static void ex8_nestedGroupingByDeptSalaryBracket(List<Employee> emps) {
        System.out.println("=== 8) nested grouping: dept -> salary bracket -> List<Employee> ===");
        Function<Employee, String> bracket = e -> {
            double s = e.getSalary();
            if (s >= 100_000) return ">=100k";
            if (s >= 70_000) return "70k-99k";
            if (s >= 50_000) return "50k-69k";
            return "<50k";
        };
        Map<Department, Map<String, List<Employee>>> nested = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept, groupingBy(bracket)));
        nested.forEach((d, map) -> {
            System.out.println(d);
            map.forEach((br, list) -> System.out.println("  " + br + " -> " + list));
        });
        System.out.println();
    }

    // 9) Flatten roles and group by role -> list of employee names (role-centric)
    static void ex9_groupByRoleListOfEmployees_flattened(List<Employee> emps) {
        System.out.println("=== 9) flatten roles and group by role -> list of employee names ===");
        Map<Role, List<String>> byRole = emps.stream()
            .flatMap(e -> e.getRoles().stream().map(r -> new AbstractMap.SimpleEntry<>(r, e.getName())))
            .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toList())));
        byRole.forEach((role, names) -> System.out.println(role + " -> " + names));
        System.out.println();

        for(Map.Entry<Role, List<String>> entry : byRole.entrySet()) {
            Role role = entry.getKey();
            List<String> names = entry.getValue();
            System.out.println(role + " -> " + names);
        }
        for(Role r: byRole.keySet()) {
            List<String> names = byRole.get(r);
            System.out.println(r + " -> " + names);
        }

        Collection<List<String>> values = byRole.values();
        for(List<String> d:byRole.values()){

        }
    }

    // 10) groupingBy with partitioningBy as downstream (dept -> {true->highPaid, false->others})
    static void ex10_groupingBy_with_partitioningDownstream(List<Employee> emps) {
        System.out.println("=== 10) groupBy dept -> partition by salary > 75k ===");
        Map<Department, Map<Boolean, List<Employee>>> res = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept,
                partitioningBy(e -> e.getSalary() > 75_000)));
        res.forEach((d, map) -> System.out.println(d + " -> " + map));
        System.out.println();
    }

    // 11) groupingBy with toMap as downstream (dept -> id->name map)
    static void ex11_groupingBy_toMapDownstream(List<Employee> emps) {
        System.out.println("=== 11) groupBy dept -> toMap(id->name) (careful with duplicate keys) ===");
        Map<Department, Map<Integer, String>> map = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept,
                toMap(Employee::getId, Employee::getName)));
        map.forEach((d, m) -> System.out.println(d + " -> " + m));
        System.out.println();
    }

    // 12) groupingBy with mapping to Set (unique names per dept)
    static void ex12_groupingBy_with_mappingToSet(List<Employee> emps) {
        System.out.println("=== 12) groupBy dept -> unique names (Set) ===");
        Map<Department, Set<String>> namesSet = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept,
                mapping(Employee::getName, toSet())));
        namesSet.forEach((d, s) -> System.out.println(d + " -> " + s));
        System.out.println();
    }

    // 13) groupingByConcurrent for parallel collection
    static void ex13_groupingBy_concurrent(List<Employee> emps) {
        System.out.println("=== 13) groupingByConcurrent dept -> count (parallel) ===");
        Map<Department, Long> counts = emps.parallelStream()
            .filter(e -> e.getDept() != null)
            .collect(groupingByConcurrent(Employee::getDept, counting()));
        counts.forEach((k,v) -> System.out.println(k + " -> " + v));
        System.out.println();
    }

    // 14) groupingBy with LinkedHashMap supplier to preserve insertion order of keys
    static void ex14_groupingBy_orderedMap_LinkedHashMap(List<Employee> emps) {
        System.out.println("=== 14) groupingBy with LinkedHashMap to preserve key order ===");
        Map<Department, List<Employee>> ordered = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept, LinkedHashMap::new, toList()));
        ordered.forEach((k,v) -> System.out.println(k + " -> " + v));
        System.out.println();
    }

    // 15) groupingBy with reducing downstream to sum salary (alternate to summingDouble)
    static void ex15_groupingBy_usingReducing_sumSalary(List<Employee> emps) {
        System.out.println("=== 15) groupingBy dept -> sum salary using reducing ===");
        Map<Department, Double> sums = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept,
                reducing(0.0, Employee::getSalary, Double::sum)));
        sums.forEach((k,v) -> System.out.println(k + " -> $" + v));
        System.out.println();
    }

    // 16) groupingBy + collectingAndThen to get top-paid employee per dept (unwrap Optional)
    static void ex16_groupingBy_collectingAndThen_topPaidPerDept(List<Employee> emps) {
        System.out.println("=== 16) groupingBy dept -> top-paid Employee (collectingAndThen) ===");
        Map<Department, Employee> top = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept,
                collectingAndThen(
                    maxBy(Comparator.comparingDouble(Employee::getSalary)),
                    opt -> opt.orElse(null)
                )));
        top.forEach((k,v) -> System.out.println(k + " -> " + v));
        System.out.println();
    }

    // 17) groupingBy counts then compute percentage per dept (post processing)
    static void ex17_groupingBy_count_then_percentagePerDept(List<Employee> emps) {
        System.out.println("=== 17) counts per dept -> percentage (post-process) ===");
        Map<Department, Long> counts = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(Employee::getDept, counting()));
        long total = counts.values().stream().mapToLong(Long::longValue).sum();
        Map<Department, Double> pct = counts.entrySet().stream()
            .collect(toMap(Map.Entry::getKey, e -> (e.getValue() * 100.0) / total));
        pct.forEach((k,v) -> System.out.println(k + " -> " + String.format("%.2f%%", v)));
        System.out.println();
    }

    // 18) groupingBy with transformed key (e.g., uppercase dept name as key)
    static void ex18_groupingBy_with_customKeyTransformer(List<Employee> emps) {
        System.out.println("=== 18) groupBy transformed key (deptNameUpperCase) -> list of names ===");
        Map<String, List<String>> byKey = emps.stream()
            .filter(e -> e.getDept() != null)
            .collect(groupingBy(e -> e.getDept().getName().toUpperCase(),
                mapping(Employee::getName, toList())));
        byKey.forEach((k,v) -> System.out.println(k + " -> " + v));
        System.out.println();
    }

    // 19) grouping into buckets by predicate (multi-bucket example)
    static void ex19_groupingBy_keyByPredicateBuckets(List<Employee> emps) {
        System.out.println("=== 19) custom bucket keys via classifier (seniority buckets) ===");
        Function<Employee,String> seniority = e -> {
            if (e.getSalary() >= 100_000) return "Senior";
            if (e.getSalary() >= 70_000) return "Mid";
            if (e.getSalary() >= 50_000) return "Junior";
            return "Entry";
        };
        Map<String, List<Employee>> buckets = emps.stream().collect(groupingBy(seniority));
        buckets.forEach((k,v) -> System.out.println(k + " -> " + v));
        System.out.println();
    }

    // 20) handle null keys (group employees by dept name but put nulls under "NO_DEPT")
    static void ex20_groupBy_when_classifier_mayBeNull_handleNullKeys(List<Employee> emps) {
        System.out.println("=== 20) handle null classifier (map null dept -> 'NO_DEPT') ===");
        Map<String, List<Employee>> safe = emps.stream()
            .collect(groupingBy(e -> {
                Department d = e.getDept();
                return d == null ? "NO_DEPT" : d.getName();
            }));
        safe.forEach((k,v) -> System.out.println(k + " -> " + v));
        System.out.println();
    }
}
