package dao;

import database.database_connection;
import model.parking_space_model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class parking_space_dao {
        public boolean addSpace(parking_space_model space) {
                String sql = "INSERT INTO parking_spaces " + "(space_number, space_type, status) " + "VALUES (?, ?, 'AVAILABLE')";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, space.getSpace_number());
                        statement.setString(2, space.getSpace_type());

                        return statement.executeUpdate() > 0;
                } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                }
        }

        public List<parking_space_model> getAllSpaces() {
                List<parking_space_model> spaces = new ArrayList<>();
                String sql = "SELECT space_id, " + "space_number, " + "space_type, " + "status " + "FROM parking_spaces " + "ORDER BY space_id ASC";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet result = statement.executeQuery();

                        while (result.next()) {
                                spaces.add(mapSpace(result));
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return spaces;
        }

        public parking_space_model getSpaceById(int spaceId) {
                String sql = "SELECT space_id, " + "space_number, " + "space_type, " + "status " + "FROM parking_spaces " + "WHERE space_id = ?";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setInt(1, spaceId);
                        ResultSet result = statement.executeQuery();

                        if (result.next()) {
                                return mapSpace(result);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return null;
        }

        public boolean updateSpace(parking_space_model space) {
                String sql = "UPDATE parking_spaces " + "SET space_number = ?, " + "space_type = ? " + "WHERE space_id = ?";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, space.getSpace_number());
                        statement.setString(2, space.getSpace_type());
                        statement.setInt(3, space.getSpace_id());

                        return statement.executeUpdate() > 0;
                } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                }
        }

        public boolean deleteSpace(int spaceId) {
                String sql = "DELETE FROM parking_spaces " + "WHERE space_id = ?";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setInt(1, spaceId);

                        return statement.executeUpdate() > 0;
                } catch (SQLIntegrityConstraintViolationException e) {
                        return false;
                } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                }
        }

        public List<parking_space_model> searchSpaces(String keyword) {
                List<parking_space_model> spaces = new ArrayList<>();
                String sql = "SELECT space_id, " + "space_number, " + "space_type, " + "status " + "FROM parking_spaces " + "WHERE space_number LIKE ? " + "OR space_type LIKE ? " + "OR status LIKE ? " + "ORDER BY space_id ASC";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        String search = "%" + keyword + "%";
                        statement.setString(1, search);
                        statement.setString(2, search);
                        statement.setString(3, search);
                        ResultSet result = statement.executeQuery();

                        while (result.next()) {
                                spaces.add(mapSpace(result));
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return spaces;
        }

        public boolean spaceNumberExists(String spaceNumber, Integer excludeSpaceId) {
                String sql;

                if (excludeSpaceId == null) {
                        sql = "SELECT space_id " + "FROM parking_spaces " + "WHERE UPPER(space_number) = UPPER(?)";
                } else {
                        sql = "SELECT space_id " + "FROM parking_spaces " + "WHERE UPPER(space_number) = UPPER(?) " + "AND space_id <> ?";
                }

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, spaceNumber);

                        if (excludeSpaceId != null) {
                                statement.setInt(2, excludeSpaceId);
                        }

                        ResultSet result = statement.executeQuery();

                        return result.next();
                } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                }
        }

        public boolean spaceHasParkingRecords(int spaceId) {
                String sql = "SELECT session_id " + "FROM parking_sessions " + "WHERE space_id = ? " + "LIMIT 1";

                try {
                        Connection connection = database_connection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setInt(1, spaceId);
                        ResultSet result = statement.executeQuery();

                        return result.next();
                } catch (SQLException e) {
                        e.printStackTrace();
                        return true;
                }
        }

        private parking_space_model mapSpace(ResultSet result) throws SQLException {
                return new parking_space_model(result.getInt("space_id"), result.getString("space_number"), result.getString("space_type"), result.getString("status"));
        }
}