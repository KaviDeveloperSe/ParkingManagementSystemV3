package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database_connection {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "parking_management_system";
    private static final String USER = "root";
    private static final String PASSWORD = "root321@";

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=false" + "&allowPublicKeyRetrieval=true" + "&serverTimezone=Asia/Colombo";

    public static Connection getConnection() {
        try {
            System.out.println("Database Are Connected.");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database Are Not Connected.");
            return null;
        }
    }
}