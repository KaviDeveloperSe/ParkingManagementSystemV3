package dao;

import database.database_connection;
import model.customer_model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class customer_dao {
    public boolean addCustomer(customer_model customer) {
        String sql = "INSERT INTO customers (name, phone, email) " + "VALUES (?, ?, ?)";

        try {
                Connection connection = database_connection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, customer.get_customer_model_name());
                statement.setString(2, customer.get_customer_model_phone());
                statement.setString(3, customer.get_customer_model_email());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<customer_model> getAllCustomers() {
        List<customer_model> customers = new ArrayList<>();
        String sql = "SELECT customer_id, name, phone, email, created_at " + "FROM customers " + "ORDER BY customer_id DESC";

        try {
                Connection connection = database_connection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                        customers.add(mapCustomer(resultSet));
                }
        } catch (SQLException e) {
                return customers;
        }

        return customers;
    }

    public customer_model getCustomerById(int customerId) {
        String sql = "SELECT customer_id, name, phone, email, created_at " + "FROM customers " + "WHERE customer_id = ?";

        try {
                Connection connection = database_connection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setInt(1, customerId);

                try {
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                                return mapCustomer(resultSet);
                        }
                } catch (SQLException e) {
                        return null;
                }
        } catch (SQLException e) {
                return null;
        }

        return null;
    }

    public boolean updateCustomer(customer_model customer) {
        String sql = "UPDATE customers " + "SET name = ?, phone = ?, email = ? " + "WHERE customer_id = ?";

        try {
                Connection connection = database_connection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, customer.get_customer_model_name());
                statement.setString(2, customer.get_customer_model_phone());
                statement.setString(3, customer.get_customer_model_email());
                statement.setInt(4, customer.get_customer_model_customer_id());

                return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteCustomer(int customerId) {
        Connection connection = null;
        try {
            connection = database_connection.getConnection();
            if (connection == null) return false;
            connection.setAutoCommit(false);
            try (PreparedStatement history = connection.prepareStatement(
                    "DELETE ps FROM parking_sessions ps INNER JOIN vehicles v "
                    + "ON ps.vehicle_id = v.vehicle_id "
                    + "WHERE v.customer_id = ? AND ps.status <> 'ACTIVE'")) {
                history.setInt(1, customerId);
                history.executeUpdate();
            }
            try (PreparedStatement vehicles = connection.prepareStatement(
                    "DELETE FROM vehicles WHERE customer_id = ?")) {
                vehicles.setInt(1, customerId);
                vehicles.executeUpdate();
            }
            int deleted;
            try (PreparedStatement customer = connection.prepareStatement(
                    "DELETE FROM customers WHERE customer_id = ?")) {
                customer.setInt(1, customerId);
                deleted = customer.executeUpdate();
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

    public boolean customerHasActiveParking(int customerId) {
        String sql = "SELECT 1 FROM parking_sessions ps INNER JOIN vehicles v "
                + "ON ps.vehicle_id = v.vehicle_id "
                + "WHERE v.customer_id = ? AND ps.status = 'ACTIVE' LIMIT 1";
        try (Connection connection = database_connection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException | NullPointerException e) {
            return true;
        }
    }

    public List<customer_model> searchCustomers(String keyword) {
        List<customer_model> customers = new ArrayList<>();
        String sql = "SELECT customer_id, name, phone, email, created_at " + "FROM customers " + "WHERE name LIKE ? " + "OR phone LIKE ? " + "OR email LIKE ? " + "ORDER BY customer_id DESC";

        try {
                Connection connection = database_connection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);

                String searchValue = "%" + keyword + "%";

                statement.setString(1, searchValue);
                statement.setString(2, searchValue);
                statement.setString(3, searchValue);

                try {
                        ResultSet resultSet = statement.executeQuery();

                        while (resultSet.next()) {
                                customers.add(mapCustomer(resultSet));
                        }
                } catch (SQLException e) {
                        return customers;
                }
        } catch (SQLException e) {
                return customers;
        }

        return customers;
    }

    public boolean emailExists(String email, Integer excludeCustomerId) {
        String sql;

        if (excludeCustomerId == null) {
            sql = "SELECT customer_id " + "FROM customers " + "WHERE email = ? " + "LIMIT 1";
        } else {
            sql = "SELECT customer_id " + "FROM customers " + "WHERE email = ? " + "AND customer_id <> ? " + "LIMIT 1";
        }

        try {
                Connection connection = database_connection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, email);

                if (excludeCustomerId != null) {
                        statement.setInt(2, excludeCustomerId);
                }

                try {
                        ResultSet resultSet = statement.executeQuery();
                        return resultSet.next();
                } catch (SQLException e) {
                        return false;
                }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean customerHasVehicles(int customerId) {
        String sql = "SELECT COUNT(*) AS total " + "FROM vehicles " + "WHERE customer_id = ?";

        try {
                Connection connection = database_connection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setInt(1, customerId);

                try {
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                                return resultSet.getInt("total") > 0;
                        }
                } catch (SQLException e) {
                        return false;
                }
        } catch (SQLException e) {
                return false;
        }

        return false;
    }

    public int getCustomerCount() {
        String sql = "SELECT COUNT(*) AS total " + "FROM customers";

        try {
                Connection connection = database_connection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                        return resultSet.getInt("total");
                }
        } catch (SQLException e) {     
                return 0;
        }

        return 0;
    }

    private customer_model mapCustomer(ResultSet resultSet) throws SQLException {
        return new customer_model(resultSet.getInt("customer_id"), resultSet.getString("name"), resultSet.getString("phone"), resultSet.getString("email"), resultSet.getTimestamp("created_at"));
    }
}
