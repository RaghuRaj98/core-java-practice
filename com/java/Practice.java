package com.java;

import com.java.streampractice.Employee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

public class Practice {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
       // names.stream().filter(n -> n.startsWith('R')).for(System.out::println);

        List<Integer> num = new ArrayList<>();
        num.add(5);
        num.add(6);
        num.add(6);
        num.add(3);
        num.add(3);
        num.add(4);

        num.stream().sorted().distinct().forEach(System.out::println);

        //
        // emp.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).skik(1).limit(1);

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> f = executor.submit(() -> 10);

        int xs = f.get();   // BLOCKING
        System.out.println(xs);



        CompletableFuture.supplyAsync(() -> 11)
                .thenAccept(x -> System.out.println(x));

    }
}
