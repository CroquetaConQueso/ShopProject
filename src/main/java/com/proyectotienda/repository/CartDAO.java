package com.proyectotienda.repository;

import java.sql.*;

import com.proyectotienda.model.Cart;
import com.proyectotienda.model.Product;
import com.proyectotienda.model.User;

public class CartDAO {
    private final Connection conn;

    public CartDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addCart(Cart userCart, User s, Product p) {
        String sql = "INSERT INTO cart_items (user_id,product_id, quantity) VALUES ( ?, ? , ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getUserId());
            stmt.setInt(2, p.getProductId());
            stmt.setInt(3, userCart.getProducts().get(p));

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("The cart has been successfully loaded into the database");
                return true;
            } else {
                System.out.println("The cart wasn't loaded into the database");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("There was an error with the database while you tried to add a cart: " + e.getMessage());
            return false;
        }
    }

    public boolean dropCart(User user) {
        String sql = "DELETE FROM cart_items where user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("The cart has been successfully deleted from the database");
                return true;
            } else {
                System.out.println("The user cart wasn't found");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error while trying to delete the user cart from the database" + e.getMessage());
            return false;
        }
    }
}