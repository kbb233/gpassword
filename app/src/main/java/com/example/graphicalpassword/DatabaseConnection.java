package com.example.graphicalpassword;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnection {

    private static final String url = "jdbc:postgresql://localhost/GraphicalPassword";
    //private static final String user = "postgres";
    //private static final String password = "0111";

    public Connection connect() {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "0111");
        props.setProperty("ssl", "true");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, props);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("CONNOT CONNECT TO THE DATABASE");
        }

        return conn;
    }
}

