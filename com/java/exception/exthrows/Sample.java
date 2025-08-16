package com.java.exception.exthrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class LoanService {
    Connection connectToDB() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "user", "password");
    }
}

public class Sample {
    public static void main(String[] args) {
        LoanService service = new LoanService();
        try {
            Connection conn = service.connectToDB();
            System.out.println("Connected to Loan DB!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
}
