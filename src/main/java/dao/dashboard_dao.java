package dao;

import database.database_connection;
import model.dashboard_stats_model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dashboard_dao {
    public dashboard_stats_model get_dashboard_stats() {
        String sql = "SELECT " + "(SELECT COUNT(*) FROM customers) AS total_customers, " + "(SELECT COUNT(*) FROM vehicles) AS total_vehicles, " + "(SELECT COUNT(*) FROM parking_spaces) AS total_spaces, " + "(SELECT COUNT(*) FROM parking_spaces WHERE status = 'AVAILABLE') AS available_spaces, " + "(SELECT COUNT(*) FROM parking_spaces WHERE status = 'OCCUPIED') AS occupied_spaces, " + "(SELECT COUNT(*) FROM parking_sessions WHERE status = 'ACTIVE') AS active_parking, " + "(SELECT COUNT(*) FROM parking_sessions WHERE status = 'COMPLETED' AND DATE(exit_time) = CURDATE()) AS completed_today, " + "(SELECT COALESCE(SUM(parking_fee), 0) FROM parking_sessions WHERE status = 'COMPLETED' AND DATE(exit_time) = CURDATE()) AS today_revenue";

        try {
            Connection ob_0 = database_connection.getConnection();
            PreparedStatement ob_1 = ob_0.prepareStatement(sql);
            ResultSet ob_2 = ob_1.executeQuery();

            if (ob_2.next()) {
                return new dashboard_stats_model(ob_2.getInt("total_customers"), ob_2.getInt("total_vehicles"), ob_2.getInt("total_spaces"), ob_2.getInt("available_spaces"), ob_2.getInt("occupied_spaces"), ob_2.getInt("active_parking"), ob_2.getInt("completed_today"), ob_2.getDouble("today_revenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}