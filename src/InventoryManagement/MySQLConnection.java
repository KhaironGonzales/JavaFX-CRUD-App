package InventoryManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/javafx_crud";
    private static final String USER = "root";
    private static final String PASSWORD = "Password123!";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
