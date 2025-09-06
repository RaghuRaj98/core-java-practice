package com.java.eight8.methodreference;

import java.util.*;
import java.util.stream.*;

public class MethodRefStream {
    public static void main(String[] args) {
        List<String> tech = Arrays.asList("Spring", "Docker", "AWS");

        // Using Lambda
        tech.stream().map(s -> s.toUpperCase()).forEach(s -> System.out.println(s));

        // Using Method Reference
        tech.stream().map(String::toUpperCase).forEach(System.out::println);
    }
}
