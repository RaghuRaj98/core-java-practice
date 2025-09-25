package com.java.finalize;

class MyResource {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalize called: Cleaning up resources");
    }
}

public class TestFinalize {
    public static void main(String[] args) {
        MyResource r1 = new MyResource();
        MyResource r2 = new MyResource();

        r1 = null;
        r2 = null;

        System.gc();  // Request garbage collection

        System.out.println("End of main method");
    }
}
