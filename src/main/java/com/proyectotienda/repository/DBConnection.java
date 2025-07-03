package com.proyectotienda.repository;
//Class to connect to the database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String CONFIG_FILE = "/db.properties";

    public static Connection getConnection() throws SQLException{
        try{
            Properties props = new Properties();
            props.load(DBConnection.class.getResourceAsStream(CONFIG_FILE));
            
            String url = props.getProperty("db.url");    
            String user = props.getProperty("db.user");    
            String password = props.getProperty("db.password");  
            
            return DriverManager.getConnection(url, user, password);
            
            }catch(Exception e){
                throw new SQLException("Connection failed: "+e.getMessage());
            }
        }
    }
