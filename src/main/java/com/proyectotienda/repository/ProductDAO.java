package com.proyectotienda.repository;
//Class to build the Product in the Database
import com.proyectotienda.model.Product;
import java.sql.*;

// TODO : List products, modify product, remove product(Admin class?)
public class ProductDAO{
    private final Connection conn;

    public ProductDAO(Connection conn){
        this.conn = conn;
    }

    public boolean addProduct(Product product){
        String sql = "INSERT INTO PRODUCTS (name, type, price) VALUES (?, ?, ?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductType());
            stmt.setFloat(3, product.getProductPrice());

            int rowsInserted = stmt.executeUpdate();

            if(rowsInserted > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    product.setProductId(rs.getInt(1));
                }
                return true;
            }
        }catch(SQLException e){
            System.out.println("Error inserting product: "+ e.getMessage());
        }
        return false;
    }
}