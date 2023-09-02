/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.foursquare;

/**
 *
 * @author Administrador
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionMySQL {
    
    public static Connection getConector(String servidor, String port, String bd, String user, String pswd) {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String URL = "jdbc:mysql://" + servidor + ":" + port + "/" + bd;
            conexion = DriverManager.getConnection(URL, user, pswd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

}
