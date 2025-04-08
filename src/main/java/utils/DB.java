package main.java.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    public static Connection connect() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/db_s2_ETU003184?useSSL=false&serverTimezone=UTC";
        Properties properties = new Properties();
        properties.setProperty("user", "ETU003184");
        properties.setProperty("password", "EQXaqgRw");

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(URL, properties);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }
}