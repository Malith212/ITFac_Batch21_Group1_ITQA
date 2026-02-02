package com.itqa.assignment.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = ConfigReader.getProperty("db.url");
    private static final String USER = ConfigReader.getProperty("db.username");
    private static final String PASSWORD = ConfigReader.getProperty("db.password");

    static {
        try {
            // Explicitly load the modern MySQL CJ driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found. Ensure mysql-connector-j is in pom.xml", e);
        }
    }

    public static void executeUpdate(String sql) {
        // The try-with-resources block ensures both Connection and Statement
        // are closed automatically, even if an exception occurs.
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            throw new RuntimeException("Failed to execute SQL: " + sql, e);
        }
    }
}