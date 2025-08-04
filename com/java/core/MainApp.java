package com.java.core;

// Public class must match file name
public class MainApp {
    public static void main(String[] args) {
        Helper helper = new Helper();
        helper.sayHello();

        Person person = new Person("Raghavendra", 27);
        person.displayInfo();
    }
}

// Another non-public class
class Helper {
    void sayHello() {
        System.out.println("Hello from Helper class!");
    }
}

// Another non-public class
class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}
