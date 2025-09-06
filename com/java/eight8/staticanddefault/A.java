package com.java.eight8.staticanddefault;

interface A {
    default void show() { System.out.println("A's show"); }
}

interface B {
    default void show() { System.out.println("B's show"); }
}

class C implements A, B {
    @Override
    public void show() {
        A.super.show(); // Call A's default
        B.super.show(); // Call B's default
        System.out.println("C's show");
    }
}
