package dao;

import database.database_connection;
import model.vehicle_exit_model;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class vehicle_exit_dao {
        public List<vehicle_exit_model> getActiveParkingSessions() {
                List<vehicle_exit_model> sessions = new ArrayList<>();
                String sql = "SELECT " + "ps.session_id, " + "ps.vehicle_id, " + "ps.space_id, " + "ps.entry_time, " + "ps.hourly_rate, " + "v.registration_number, " + "v.vehicle_type, " + "c.name AS customer_name, " + "sp.space_number " + "FROM parking_sessions ps " + "INNER JOIN vehicles v " + "ON ps.vehicle_id = v.vehicle_id " + "INNER JOIN customers c " + "ON v.customer_id = c.customer_id " + "INNER JOIN parking_spaces sp " + "ON ps.space_id = sp.space_id " + "WHERE ps.status = 'ACTIVE' " + "ORDER BY ps.entry_time ASC";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet result = statement.executeQuery();

                        while (result.next()) {
                                sessions.add(mapSession(result));
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return sessions;
        }

        public vehicle_exit_model getActiveSessionById(int sessionId) {
                String sql = "SELECT " + "ps.session_id, " + "ps.vehicle_id, " + "ps.space_id, " + "ps.entry_time, " + "ps.hourly_rate, " + "v.registration_number, " + "v.vehicle_type, " + "c.name AS customer_name, " + "sp.space_number " + "FROM parking_sessions ps " + "INNER JOIN vehicles v " + "ON ps.vehicle_id = v.vehicle_id " + "INNER JOIN customers c " + "ON v.customer_id = c.customer_id " + "INNER JOIN parking_spaces sp " + "ON ps.space_id = sp.space_id " + "WHERE ps.session_id = ? " + "AND ps.status = 'ACTIVE'";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setInt(1, sessionId);
                        ResultSet result = statement.executeQuery();

                        if (result.next()) {
                                return mapSession(result);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return null;
        }

        public boolean completeVehicleExit(int sessionId, int spaceId, Timestamp exitTime, BigDecimal parkingFee) {
                Connection connection = null;

                try {
                        connection = database_connection.getConnection();
                        if (connection == null) {
                                return false;
                        }

                        connection.setAutoCommit(false);

                        // Lock and verify the related space before changing the session.
                        String spaceCheckSql = "SELECT space_id FROM parking_spaces WHERE space_id = ? FOR UPDATE";
                        try (PreparedStatement spaceCheck = connection.prepareStatement(spaceCheckSql)) {
                                spaceCheck.setInt(1, spaceId);
                                try (ResultSet result = spaceCheck.executeQuery()) {
                                        if (!result.next()) {
                                                connection.rollback();
                                                return false;
                                        }
                                }
                        }

                        String sessionSql = "UPDATE parking_sessions " + "SET exit_time = ?, " + "parking_fee = ?, " + "status = 'COMPLETED' " + "WHERE session_id = ? " + "AND space_id = ? " + "AND status = 'ACTIVE'";

                        int sessionUpdated;
                        try (PreparedStatement sessionStatement = connection.prepareStatement(sessionSql)) {
                                sessionStatement.setTimestamp(1, exitTime);
                                sessionStatement.setBigDecimal(2, parkingFee);
                                sessionStatement.setInt(3, sessionId);
                                sessionStatement.setInt(4, spaceId);
                                sessionUpdated = sessionStatement.executeUpdate();
                        }

                        if (sessionUpdated != 1) {
                                connection.rollback();
                                return false;
                        }

                        // Ensure the space is available. Do not fail if it was already
                        // available, because MySQL reports zero changed rows in that case.
                        String spaceSql = "UPDATE parking_spaces SET status = 'AVAILABLE' WHERE space_id = ?";
                        try (PreparedStatement spaceStatement = connection.prepareStatement(spaceSql)) {
                                spaceStatement.setInt(1, spaceId);
                                spaceStatement.executeUpdate();
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
                                        connection.close();
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }

        private vehicle_exit_model mapSession(ResultSet result) throws SQLException {
                return new vehicle_exit_model(result.getInt("session_id"), result.getInt("vehicle_id"), result.getInt("space_id"), result.getString("registration_number"), result.getString("vehicle_type"), result.getString("customer_name"), result.getString("space_number"), result.getTimestamp("entry_time"), result.getBigDecimal("hourly_rate"));
        }
}
