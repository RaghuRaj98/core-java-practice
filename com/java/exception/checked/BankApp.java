package com.java.exception.checked;

import java.io.*;
import java.sql.*;

class BankService {

    // 1. Reading KYC Document (IOException)
    public void readKycDocument(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println("KYC Data: " + line);
        }
        br.close();
    }

    // 2. Fetch Account Balance (SQLException)
    public void fetchAccountBalance(int accountId) throws SQLException {
        // Simulating DB error (No DB connection)
        Connection connection = null; // Assume this is null to simulate no connection
        if (connection == null) {
            // This will throw SQLException if connection is null
            throw new SQLException("No connection to the database");
        }
        throw new SQLException("Unable to connect to banking database");
    }

    // 3. Load JDBC Driver (ClassNotFoundException)
    public void loadDatabaseDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }
}

public class BankApp {
    public static void main(String[] args) {
        BankService bankService = new BankService();

        // Checked Exception: IOException
        try {
            bankService.readKycDocument("kyc.txt");
        } catch (IOException e) {
            System.out.println("IOException caught: " + e.getMessage());
        }

        // Checked Exception: SQLException
        try {
            bankService.fetchAccountBalance(101);
        } catch (SQLException e) {
            System.out.println("SQLException caught: " + e.getMessage());
        }

        // Checked Exception: ClassNotFoundException
        try {
            bankService.loadDatabaseDriver();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException caught: " + e.getMessage());
        }
    }
}
