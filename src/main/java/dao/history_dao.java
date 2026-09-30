package dao;

import database.database_connection;
import model.history_model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class history_dao {
        public List<history_model> get_all_history() {
                List<history_model> history_list = new ArrayList<>();
                String sql = "SELECT " + "ps.session_id, " + "v.registration_number, " + "c.name AS customer_name, " + "v.vehicle_type, " + "p.space_number, " + "ps.entry_time, " + "ps.exit_time, " + "ps.hourly_rate, " + "ps.parking_fee " + "FROM parking_sessions ps " + "INNER JOIN vehicles v " + "ON ps.vehicle_id = v.vehicle_id " + "INNER JOIN customers c " + "ON v.customer_id = c.customer_id " + "INNER JOIN parking_spaces p " + "ON ps.space_id = p.space_id " + "WHERE ps.status = 'COMPLETED' " + "ORDER BY ps.exit_time DESC";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet result = statement.executeQuery();

                        while (result.next()) {
                                history_model history = new history_model(result.getInt("session_id"), result.getString("registration_number"), result.getString("customer_name"), result.getString("vehicle_type"), result.getString("space_number"), result.getTimestamp("entry_time").toLocalDateTime(), result.getTimestamp("exit_time").toLocalDateTime(), result.getBigDecimal("hourly_rate"), result.getBigDecimal("parking_fee"));
                                history_list.add(history);
                        }

                        result.close();
                        statement.close();
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return history_list;
        }

        public List<history_model> search_history(String keyword) {
                List<history_model> history_list = new ArrayList<>();
                String sql = "SELECT " + "ps.session_id, " + "v.registration_number, " + "c.name AS customer_name, " + "v.vehicle_type, " + "p.space_number, " + "ps.entry_time, " + "ps.exit_time, " + "ps.hourly_rate, " + "ps.parking_fee " + "FROM parking_sessions ps " + "INNER JOIN vehicles v " + "ON ps.vehicle_id = v.vehicle_id " + "INNER JOIN customers c " + "ON v.customer_id = c.customer_id " + "INNER JOIN parking_spaces p " + "ON ps.space_id = p.space_id " + "WHERE ps.status = 'COMPLETED' " + "AND (" + "v.registration_number LIKE ? " + "OR c.name LIKE ? " + "OR v.vehicle_type LIKE ? " + "OR p.space_number LIKE ?" + ") " + "ORDER BY ps.exit_time DESC";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        String search_keyword = "%" + keyword + "%";

                        statement.setString(1, search_keyword);
                        statement.setString(2, search_keyword);
                        statement.setString(3, search_keyword);
                        statement.setString(4, search_keyword);

                        ResultSet result = statement.executeQuery();

                        while (result.next()) {
                                history_model history = new history_model(result.getInt("session_id"), result.getString("registration_number"), result.getString("customer_name"), result.getString("vehicle_type"), result.getString("space_number"), result.getTimestamp("entry_time").toLocalDateTime(), result.getTimestamp("exit_time").toLocalDateTime(), result.getBigDecimal("hourly_rate"), result.getBigDecimal("parking_fee"));
                                history_list.add(history);
                        }

                        result.close();
                        statement.close();
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return history_list;
        }

        public int get_history_count() {
                String sql = "SELECT COUNT(*) AS total " + "FROM parking_sessions " + "WHERE status = 'COMPLETED'";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet result = statement.executeQuery();

                        if (result.next()) {
                                int count = result.getInt("total");

                                result.close();
                                statement.close();

                                return count;
                        }

                        result.close();
                        statement.close();
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return 0;
        }
}