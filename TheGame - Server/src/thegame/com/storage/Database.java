/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.com.Menu.Account;

/**
 *
 * @author robin
 */
public class Database {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://athena01.fhict.local:3306/dbi322250?zeroDateTimeBehavior=convertToNull";
    static final String DB_URL = "jdbc:mysql://84.24.141.120:3306/thegame";

    //  Database credentials
    //static final String USER = "dbi322250";
    //static final String PASS = "lZoCxvXKps";
    static final String USER = "thegame";
    static final String PASS = "@thegame1";

    private static Database database;

    public static Database getDatabase()
    {
        if (database == null)
        {
            database = new Database();
        }
        return database;
    }

    Connection conn = null;

    public void openConnection()
    {
        try
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e)
            {
                System.out.println("Where is your MySQL JDBC Driver?");
            }
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection Successful");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Connection Failed!");
        }
    }

    public ResultSet executeUnsafeQuery(String sql) throws SQLException
    {
        //Execute a query
        Statement statement;
        ResultSet resultSet = null;
        try
        {
            if (conn == null || conn.isClosed())
            {
                openConnection();
            }
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return resultSet;
    }

    public void closeConnection()
    {
        try
        {
            if (conn != null || !conn.isClosed())
            {
                conn.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
