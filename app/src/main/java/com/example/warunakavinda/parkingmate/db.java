/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.warunakavinda.parkingmate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Waruna
 */
public class
        db {
    public static Connection myconn() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection c= DriverManager.getConnection("jdbc:mysql://192.168.23.1:3306/parkingmate?zeroDateTimeBehavior=convertToNull","root","admin"); //removed Localhost, replaced 72.9.147.83\MSSQL2008 and instead of payroll replaced pay
        return c;
    }
    
public static void con_close(Connection dbConn) {
try {
dbConn.close();
} catch (SQLException sQLException) {
System.out.println(sQLException + "-----------DB connection closing failure");
}
    
}
}
