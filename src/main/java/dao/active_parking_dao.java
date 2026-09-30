package dao;

import database.database_connection;
import model.active_parking_model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class active_parking_dao {
        public List<active_parking_model> get_all_active_parking() {
                List<active_parking_model> active_parking_list = new ArrayList<>();

                String sql = "SELECT " + "ps.session_id, " + "v.registration_number, " + "c.name AS customer_name, " + "v.vehicle_type, " + "p.space_number, " + "ps.entry_time " + "FROM parking_sessions ps " + "INNER JOIN vehicles v " + "ON ps.vehicle_id = v.vehicle_id " + "INNER JOIN customers c " + "ON v.customer_id = c.customer_id " + "INNER JOIN parking_spaces p " + "ON ps.space_id = p.space_id " + "WHERE ps.status = 'ACTIVE' " + "ORDER BY ps.entry_time DESC";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet result = statement.executeQuery();

                        while (result.next()) {
                                active_parking_model active_parking = new active_parking_model(result.getInt("session_id"), result.getString("registration_number"), result.getString("customer_name"), result.getString("vehicle_type"), result.getString("space_number"), result.getTimestamp("entry_time").toLocalDateTime());
                                active_parking_list.add(active_parking);
                        }

                        result.close();
                        statement.close();
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return active_parking_list;
        }

        public List<active_parking_model> search_active_parking(String keyword) {
                List<active_parking_model> active_parking_list = new ArrayList<>();

                String sql = "SELECT " + "ps.session_id, " + "v.registration_number, " + "c.name AS customer_name, " + "v.vehicle_type, " + "p.space_number, " + "ps.entry_time " + "FROM parking_sessions ps " + "INNER JOIN vehicles v " + "ON ps.vehicle_id = v.vehicle_id " + "INNER JOIN customers c " + "ON v.customer_id = c.customer_id " + "INNER JOIN parking_spaces p " + "ON ps.space_id = p.space_id " + "WHERE ps.status = 'ACTIVE' " + "AND (" + "v.registration_number LIKE ? " + "OR c.name LIKE ? " + "OR v.vehicle_type LIKE ? " + "OR p.space_number LIKE ?" + ") " + "ORDER BY ps.entry_time DESC";

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
                                active_parking_model active_parking = new active_parking_model(result.getInt("session_id"), result.getString("registration_number"), result.getString("customer_name"), result.getString("vehicle_type"), result.getString("space_number"), result.getTimestamp("entry_time").toLocalDateTime());
                                active_parking_list.add(active_parking);
                        }

                        result.close();
                        statement.close();
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return active_parking_list;
        }
}