package com.java.eight8.methodreference;

import java.util.function.Consumer;

public class InstanceMethodRef {
    public void display(String msg) {
        System.out.println(msg.toUpperCase());
    }

    public static void main(String[] args) {
        InstanceMethodRef obj = new InstanceMethodRef();

        Consumer<String> consumer = obj::display;
        consumer.accept("hello java"); // HELLO JAVA
    }
}
