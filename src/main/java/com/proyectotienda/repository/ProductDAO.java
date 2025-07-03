package com.proyectotienda.repository;

//Class to build the Product in the Database
import com.proyectotienda.model.Product;
import java.sql.*;

// TODO : List products, modify product, remove product(Admin class?)
public class ProductDAO {
    private final Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO PRODUCTS (name, type, price) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductType());
            stmt.setFloat(3, product.getProductPrice());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    product.setProductId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error inserting product: " + e.getMessage());
        }
        return false;
    }

    public boolean checkProduct(String productToFind) {
        String sql = "SELECT COUNT(*) FROM products where name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productToFind);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    System.out.println("The product with the name " + productToFind + " was found");
                    return true;
                }
            }

            System.out.println("The product with the name " + productToFind + " was not able to be found");
            return false;
        } catch (SQLException e) {
            System.out.println("Error trying to search for the product: " + e.getMessage());
            return false;
        }
    }

    public boolean modifyProduct(String nProductName, String nProductType, float nProductPrice, String oldProductName) {
        String sql = "UPDATE productos SET name = ?, type = ? , price = ? where name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nProductName);
            stmt.setString(2, nProductType);
            stmt.setFloat(3, nProductPrice);
            stmt.setString(4, oldProductName);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The product with the name " + oldProductName + " was updated into " + nProductName);
                return true;
            } else {
                System.out.println("The product was not able to be found");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error trying to load the database to update a product: " + e.getMessage());
            return false;
        }
    }

    public boolean dropProduct(String productName) {
        String sql = "DELETE from productos WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productName);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The product with the name " + productName + " has been deleted");
                return true;
            } else {
                System.out.println("The product with the name " + productName + " wasn't deleted");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error with the connection as we tried to delete a product "+e.getMessage());
            return false;
        }
    }

}