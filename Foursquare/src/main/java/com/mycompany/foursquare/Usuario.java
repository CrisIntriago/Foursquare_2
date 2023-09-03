package com.mycompany.foursquare;

import java.sql.ResultSet;
import java.sql.Statement;

import static com.mycompany.foursquare.App.conexion;

/**
 * Clase para almacenar la información PRINCIPAL del usuario
 */
public class Usuario {
    private String nombre;
    private String apellido;
    private int id_Usuario;

    public Usuario(int id_Usuario, String nombre, String apellido) {
        this.id_Usuario = id_Usuario;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getId_Usuario() {
        return id_Usuario;
    }

    public static Usuario getUsuario(int id_Usuario) {
        Statement st = null;
        ResultSet rs = null;
        Usuario usuario = null;
        if (conexion != null) {
            try {
                st = conexion.createStatement();
                rs = st.executeQuery(
                        "SELECT nombre, apellido FROM usuario WHERE id_Usuario = " + id_Usuario);
                while (rs.next()) {
                    String nombre = rs.getString("nombre") == null ? "" : rs.getString("nombre");
                    String apellido = rs.getString("apellido") == null ? "" : rs.getString("apellido");
                    usuario = new Usuario(id_Usuario, nombre, apellido);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    rs.close();
                    st.close();
                } catch (Exception e) {
                    System.out.println("Error en intento de cierre: " + e);
                }
            }
        }
        return usuario;
    }
}
