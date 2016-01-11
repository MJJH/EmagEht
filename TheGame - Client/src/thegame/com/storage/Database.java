/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author robin
 */
public class Database {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://athena01.fhict.local:3306/dbi322250?zeroDateTimeBehavior=convertToNull";

    //  Database credentials
    static final String USER = "dbi322250";
    static final String PASS = "lZoCxvXKps";

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

    public void openConnection() throws ClassNotFoundException
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
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection Successful");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Connection Failed!");
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException, ClassNotFoundException{
        //Execute a query
        Statement statement;
        ResultSet resultSet = null;
        try{
            openConnection();
            System.out.println("Creating statement...");
            statement = conn.createStatement();
            //String sql = "SELECT id, first, last, age FROM Employees";
            resultSet = statement.executeQuery(sql);
        }
        catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return resultSet;
        /*
        //Extract data from result set
        while(rs.next()){
        //Retrieve by column name
        int id  = rs.getInt("id");
        int age = rs.getInt("age");
        String first = rs.getString("first");
        String last = rs.getString("last");
        //Display values
        System.out.print("ID: " + id);
        System.out.print(", Age: " + age);
        System.out.print(", First: " + first);
        System.out.println(", Last: " + last);
        }
        */
    }
}
