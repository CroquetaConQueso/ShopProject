package com.proyectotienda.repository;

//Class to build the User in the DataBase
import com.proyectotienda.model.User;

import java.sql.*;
//ResultSet is the class that holds all the data returned from 

// TODO : Display all users from the DB, remove User, modify User
public class UserDAO {
    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO users (user_name, user_pass, user_funds) VALUES (?, ?, ?)";
        // If values need to be returned from the database
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

    public boolean checkUser(String userToFind) {
        // After using a ? you have to use a stmt.setXXX to asign a value
        String sql = "SELECT COUNT(*) FROM users WHERE user_name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userToFind);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    System.out.println("The user with the name " + userToFind + " has been found in the database");
                    return true;
                }
            }

            System.out.println("The user with the name " + userToFind + " was not able to be found in the database ");
            return false;

        } catch (SQLException e) {
            System.out.println("Error while trying to search for an user: " + e.getMessage());
            return false;
        }
    }

    public void modifyUser(String nName, String nPass, float nFunds, String oldName) {
        // The "?" establish the order of the parameters, the first ? establishes that
        // parameterIndex one from stmt.setString will use that value
        String sql = "UPDATE users SET user_name = ?, user_pass = ? , user_funds = ? WHERE user_name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nName);
            stmt.setString(2, nPass);
            stmt.setFloat(3, nFunds);
            stmt.setString(4, oldName);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The user has been updated");
            } else {
                System.out.println("The user was not able to be updated");
            }

        } catch (SQLException e) {
            System.out.println("Error trying to update the user: " + e.getMessage());
        }
    }

    public void dropUser(String userName) {
        String sql = "DELETE FROM users where user_name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The user with the name " + userName + " was deleted");
            } else {
                System.out.println("The user with the name " + userName + " was not found");
            }
        } catch (SQLException e) {
            System.out.println("Error with the database while trying to connect to delete an user " + e.getMessage());
        }
    }

    public User logUserDao(String userName) {
        String sql = "SELECT id , user_name, user_pass, user_funds FROM users where user_name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                User s = User.builder().userId(rs.getInt(1)).userName(rs.getString(2)).userPass(rs.getString(3)).userFunds(rs.getFloat(4)).build();
                return s;
            }else{
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error trying to access the database to log as an user" + e.getMessage());
            return null;
        }
    }
}