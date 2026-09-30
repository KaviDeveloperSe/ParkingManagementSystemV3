package dao;

import database.database_connection;
import model.parking_session_model;
import model.parking_space_model;
import model.vehicle_model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class park_vehicle_dao {
        public List<vehicle_model> getAvailableVehicles() {
                List<vehicle_model> vehicles = new ArrayList<>();
                String sql = "SELECT " + "v.vehicle_id, " + "v.registration_number, " + "v.vehicle_type, " + "v.brand, " + "v.model, " + "v.customer_id, " + "c.name AS customer_name, " + "v.created_at " + "FROM vehicles v " + "INNER JOIN customers c " + "ON v.customer_id = c.customer_id " + "WHERE NOT EXISTS (" + "SELECT 1 " + "FROM parking_sessions ps " + "WHERE ps.vehicle_id = v.vehicle_id " + "AND ps.status = 'ACTIVE'" + ") " + "ORDER BY v.registration_number ASC";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet result = statement.executeQuery();

                        while (result.next()) {
                                vehicle_model vehicle = new vehicle_model(result.getInt("vehicle_id"), result.getString("registration_number"), result.getString("vehicle_type"), result.getString("brand"), result.getString("model"), result.getInt("customer_id"), result.getString("customer_name"), result.getTimestamp("created_at"));
                                vehicles.add(vehicle);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return vehicles;
        }

        public List<parking_space_model> getAvailableSpacesByType(String vehicleType) {
                List<parking_space_model> spaces = new ArrayList<>();
                String sql = "SELECT " + "space_id, " + "space_number, " + "space_type, " + "status " + "FROM parking_spaces " + "WHERE status = 'AVAILABLE' " + "AND space_type = ? " + "ORDER BY space_number ASC";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, vehicleType);
                        ResultSet result = statement.executeQuery();

                        while (result.next()) {
                                parking_space_model space = new parking_space_model(result.getInt("space_id"), result.getString("space_number"), result.getString("space_type"), result.getString("status"));
                                spaces.add(space);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return spaces;
        }

        public boolean parkVehicle(
                parking_session_model session) {

                Connection connection = null;

                try {

                connection = database_connection.getConnection();
                connection.setAutoCommit(false);
                String vehicleCheckSql = "SELECT session_id " + "FROM parking_sessions " + "WHERE vehicle_id = ? " + "AND status = 'ACTIVE' " + "LIMIT 1";

                PreparedStatement vehicleCheck = connection.prepareStatement(vehicleCheckSql);
                vehicleCheck.setInt(1, session.getVehicle_id());
                ResultSet vehicleResult = vehicleCheck.executeQuery();

                if (vehicleResult.next()) {
                        connection.rollback();
                        return false;
                }

                String occupySql = "UPDATE parking_spaces " + "SET status = 'OCCUPIED' " + "WHERE space_id = ? " + "AND status = 'AVAILABLE'";
                PreparedStatement occupyStatement = connection.prepareStatement(occupySql);
                occupyStatement.setInt(1, session.getSpace_id());
                int updated = occupyStatement.executeUpdate();

                if (updated != 1) {
                        connection.rollback();
                        return false;
                }

                String insertSql = "INSERT INTO parking_sessions " + "(vehicle_id, " + "space_id, " + "entry_time, " + "hourly_rate, " + "status) " + "VALUES (?, ?, ?, ?, 'ACTIVE')";
                PreparedStatement insertStatement = connection.prepareStatement(insertSql);
                insertStatement.setInt(1, session.getVehicle_id());
                insertStatement.setInt(2, session.getSpace_id());
                insertStatement.setTimestamp(3, session.getEntry_time());
                insertStatement.setBigDecimal(4, session.getHourly_rate());
                int inserted = insertStatement.executeUpdate();

                if (inserted != 1) {
                        connection.rollback();
                        return false;
                }

                connection.commit();

                return true;

                } catch (SQLException e) {
                        if (connection != null) {
                                try {
                                        connection.rollback();
                                } catch (SQLException rollbackError) {
                                        rollbackError.printStackTrace();
                                }
                        }

                        e.printStackTrace();

                        return false;
                } finally {
                        if (connection != null) {
                                try {
                                        connection.setAutoCommit(true);
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }
}