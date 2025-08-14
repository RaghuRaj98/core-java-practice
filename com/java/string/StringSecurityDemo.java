package com.java.string;

public class StringSecurityDemo {
    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://secureDB";
        connectToDB(dbUrl);

        // Attempt to change the string just makes a new String
        String s = dbUrl + "jdbc:mysql://maliciousDB";
        dbUrl.replace("jdbc:mysql://secureDB", "jdbc:mysql://maliciousDB");

        // Original passed value is still safe
        System.out.println("Changed dbUrl reference: " + dbUrl);
        connectToDB(dbUrl);
        System.out.println("Original dbUrl reference remains unchanged.");
        connectToDB("jdbc:mysql://secureDB");
    }

    public static void connectToDB(String url) {
        System.out.println("Connecting to: " + url);
    }
}
