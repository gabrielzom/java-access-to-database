package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String url = "jdbc:mysql://localhost:3306/JAVA_ACCESS";
    private static final String user = "root";
    private static final String password = "R00t@@2022";

    public static Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.printf("[LOG] Database driver's found.\n");
        } catch (ClassNotFoundException e) {
            System.out.printf("[ERROR] Database driver's not found. Message:\n%s .\n", e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("[LOG] Connected to database.\n");
            return connection;
        } catch (SQLException e) {
            System.out.printf("[ERROR] Can't connect to database. Message:\n%s .\n", e.getMessage());
            return null;
        }
    }
}
