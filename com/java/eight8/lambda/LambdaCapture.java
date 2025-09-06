package com.java.eight8.lambda;

import com.java.eight8.functionalInterface.MyFunctionalInterface;

public class LambdaCapture {
    public static void main(String[] args) {
        String prefix = "Hello"; // effectively final

        Runnable r = () -> System.out.println(prefix + " World");
        r.run();
        new Thread(r).start();

        MyFunctionalInterface noParameters = () -> System.out.println("No parameters");

        noParameters.doSomething();

    }
}
