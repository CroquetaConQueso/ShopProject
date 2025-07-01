package com.proyectotienda.repository;
//Class to build the user
import com.proyectotienda.model.User;
import java.sql.*;

public class UserDAO {
    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO users (user_name, user_pass, user_funds) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getUserPass());
            stmt.setFloat(3, user.getUserFunds());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setUserId(rs.getInt(1)); // Asigna la ID generada
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
        }
        return false;
    }
}