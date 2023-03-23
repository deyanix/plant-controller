package com.plantcontroller.serwer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306";
        String user = "";
        String password = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver is loaded successfully");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection is successful with"+" "+con);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}