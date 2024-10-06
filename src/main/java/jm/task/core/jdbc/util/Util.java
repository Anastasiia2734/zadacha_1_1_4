package jm.task.core.jdbc.util;

import java.io.IOException;
import java.security.interfaces.DSAKey;
import java.sql.*;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://private.oxfraud.cc:3306/user_04102024";
    private static final String DB_LOGIN = "user_04102024";
    private static final String DB_PASSWORD = "04102024oO!";
    public static Statement statement;
    public static Connection connection;
public static Connection getConnection() {
    Connection connection1 = null;
    try {
        Class.forName(DB_DRIVER);
        connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
        System.out.println("Connection ok");
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        System.out.println("Connection error");
    }
    return connection;
    }
}