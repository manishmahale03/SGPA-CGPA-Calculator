package com.example.sgpacgpacalculator;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseConnection;

    public Connection getDatabaseConnection() {
        String databaseName = "calculator";
        String databaseUser = "root";
        String databasePassword = "2003Manish#";
        String databaseURL = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseConnection = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseConnection;
    }
}
