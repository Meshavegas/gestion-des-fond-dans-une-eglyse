package com.nely.gesfond.dbHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    static Connection connection ;
    private static String url = "jdbc:mysql://localhost:3306/mesfonds";
    private static String USERNAME = "root";
    private static String PASSWORD = "Morelle #2";
    public static Connection getConect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url,USERNAME,PASSWORD);
        return connection;
    }
}
