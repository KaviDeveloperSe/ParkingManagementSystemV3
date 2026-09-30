package dao;

import database.database_connection;
import model.vehicle_model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class vehicle_dao {
        public boolean addVehicle(vehicle_model vehicle) {
                String sql = "INSERT INTO vehicles " + "(registration_number, vehicle_type, brand, model, customer_id) " + "VALUES (?, ?, ?, ?, ?)";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, vehicle.getRegistration_number());
                        statement.setString(2, vehicle.getVehicle_type());
                        statement.setString(3, vehicle.getBrand());
                        statement.setString(4, vehicle.getModel());
                        statement.setInt(5, vehicle.getCustomer_id());

                        return statement.executeUpdate() > 0;
                } catch (SQLException e) {
                        return false;
                }
        }

        public List<vehicle_model> getAllVehicles() {
                List<vehicle_model> vehicles = new ArrayList<>();
                String sql = "SELECT " + "v.vehicle_id, " + "v.registration_number, " + "v.vehicle_type, " + "v.brand, " + "v.model, " + "v.customer_id, " + "c.name AS customer_name, " + "v.created_at " + "FROM vehicles v " + "INNER JOIN customers c " + "ON v.customer_id = c.customer_id " + "ORDER BY v.vehicle_id DESC";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet resultSet = statement.executeQuery();

                        while (resultSet.next()) {
                                vehicles.add(mapVehicle(resultSet));
                        }
                } catch (SQLException e) { }

                return vehicles;
        }

        public vehicle_model getVehicleById(int vehicleId) {
                String sql = "SELECT " + "v.vehicle_id, " + "v.registration_number, " + "v.vehicle_type, " + "v.brand, " + "v.model, " + "v.customer_id, " + "c.name AS customer_name, " + "v.created_at " + "FROM vehicles v " + "INNER JOIN customers c " + "ON v.customer_id = c.customer_id " + "WHERE v.vehicle_id = ?";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);

                        statement.setInt(1, vehicleId);

                        try {
                                ResultSet resultSet = statement.executeQuery();

                                if (resultSet.next()) {
                                        return mapVehicle(resultSet);
                                }
                        } catch (SQLException e) { }
                } catch (SQLException e) { }

                return null;
        }

        public boolean updateVehicle(vehicle_model vehicle) {
                String sql = "UPDATE vehicles SET " + "registration_number = ?, " + "vehicle_type = ?, " + "brand = ?, " + "model = ?, " + "customer_id = ? " + "WHERE vehicle_id = ?";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);

                        statement.setString(1, vehicle.getRegistration_number());
                        statement.setString(2, vehicle.getVehicle_type());
                        statement.setString(3, vehicle.getBrand());
                        statement.setString(4, vehicle.getModel());
                        statement.setInt(5, vehicle.getCustomer_id());
                        statement.setInt(6, vehicle.getVehicle_id());

                        return statement.executeUpdate() > 0;
                } catch (SQLException e) {
                        return false;
                }
        }

        public boolean deleteVehicle(int vehicleId) {
                Connection connection = null;
                try {
                        connection = database_connection.getConnection();
                        if (connection == null) return false;
                        connection.setAutoCommit(false);
                        try (PreparedStatement history = connection.prepareStatement(
                                "DELETE FROM parking_sessions WHERE vehicle_id = ? AND status <> 'ACTIVE'")) {
                                history.setInt(1, vehicleId);
                                history.executeUpdate();
                        }
                        int deleted;
                        try (PreparedStatement vehicle = connection.prepareStatement(
                                "DELETE FROM vehicles WHERE vehicle_id = ?")) {
                                vehicle.setInt(1, vehicleId);
                                deleted = vehicle.executeUpdate();
                        }
                        if (deleted != 1) {
                                connection.rollback();
                                return false;
                        }
                        connection.commit();
                        return true;
                } catch (SQLException e) {
                        if (connection != null) try { connection.rollback(); } catch (SQLException ignored) { }
                        return false;
                } finally {
                        if (connection != null) try { connection.close(); } catch (SQLException ignored) { }
                }
        }

        public boolean vehicleHasActiveParking(int vehicleId) {
                String sql = "SELECT 1 FROM parking_sessions WHERE vehicle_id = ? AND status = 'ACTIVE' LIMIT 1";
                try (Connection connection = database_connection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setInt(1, vehicleId);
                        try (ResultSet result = statement.executeQuery()) {
                                return result.next();
                        }
                } catch (SQLException | NullPointerException e) {
                        return true;
                }
        }

        public List<vehicle_model> searchVehicles(String keyword) {
                List<vehicle_model> vehicles = new ArrayList<>();
                String sql = "SELECT " + "v.vehicle_id, " + "v.registration_number, " + "v.vehicle_type, " + "v.brand, " + "v.model, " + "v.customer_id, " + "c.name AS customer_name, " + "v.created_at " + "FROM vehicles v " + "INNER JOIN customers c " + "ON v.customer_id = c.customer_id " + "WHERE v.registration_number LIKE ? " + "OR v.vehicle_type LIKE ? " + "OR v.brand LIKE ? " + "OR v.model LIKE ? " + "OR c.name LIKE ? " + "ORDER BY v.vehicle_id DESC";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        String searchValue = "%" + keyword + "%";
                        statement.setString(1, searchValue);
                        statement.setString(2, searchValue);
                        statement.setString(3, searchValue);
                        statement.setString(4, searchValue);
                        statement.setString(5, searchValue);

                        try {
                                ResultSet resultSet = statement.executeQuery();

                                while (resultSet.next()) {
                                        vehicles.add(mapVehicle(resultSet));
                                }
                        } catch (SQLException e) { }

                } catch (SQLException e) { }

                return vehicles;
        }

        public boolean registrationExists(String registration, Integer excludeVehicleId) {
                String sql;

                if (excludeVehicleId == null) {
                        sql = "SELECT vehicle_id " + "FROM vehicles " + "WHERE registration_number = ? " + "LIMIT 1";
                } else {
                        sql = "SELECT vehicle_id " + "FROM vehicles " + "WHERE registration_number = ? " + "AND vehicle_id <> ? " + "LIMIT 1";
                }

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);

                        statement.setString(1, registration);

                        if (excludeVehicleId != null) {
                                statement.setInt(2, excludeVehicleId);
                        }

                        try (ResultSet resultSet = statement.executeQuery()) {
                                return resultSet.next();
                        }
                } catch (SQLException e) {
                        return false;
                }
        }

        public boolean vehicleHasParkingRecords(int vehicleId) {
                String sql = "SELECT COUNT(*) AS total " + "FROM parking_sessions " + "WHERE vehicle_id = ?";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setInt(1, vehicleId);

                        try (ResultSet resultSet = statement.executeQuery()) {
                                if (resultSet.next()) {
                                        return resultSet.getInt("total") > 0;
                                }
                        }
                } catch (SQLException e) { }

                return false;
        }

        public int getVehicleCount() {
                String sql = "SELECT COUNT(*) AS total " + "FROM vehicles";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                                return resultSet.getInt("total");
                        }
                } catch (SQLException e) { }

                return 0;
        }

        private vehicle_model mapVehicle(ResultSet resultSet) throws SQLException {
                return new vehicle_model(resultSet.getInt("vehicle_id"), resultSet.getString("registration_number"), resultSet.getString("vehicle_type"), resultSet.getString("brand"), resultSet.getString("model"), resultSet.getInt("customer_id"), resultSet.getString("customer_name"), resultSet.getTimestamp("created_at"));
        }
}
