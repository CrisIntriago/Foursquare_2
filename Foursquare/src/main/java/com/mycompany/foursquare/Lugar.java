/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.foursquare;

import static com.mycompany.foursquare.App.conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author Administrador
 */
public class Lugar {

    private int id_Sitio;
    private int id_SitioM;
    private String nombre;
    private int id_tipo;
    private double precio_minimo;
    private double precio_maximo;
    private String disponibilidad;
    private int id_Ubicacion;

    public Lugar(int id_Sitio, String nombre, int tipo, double precio_minimo, double precio_maximo, String disponibilidad, int id_Ubicacion) {
        this.id_Sitio = id_Sitio;
        this.nombre = nombre;
        this.id_tipo = tipo;
        this.precio_minimo = precio_minimo;
        this.precio_maximo = precio_maximo;
        this.disponibilidad = disponibilidad;
        this.id_Ubicacion = id_Ubicacion;
    }

    public static LinkedList<Lugar> getLugares() {
        LinkedList<Lugar> lugares = new LinkedList<>();
        Statement st = null;
        ResultSet rs = null;
        if (conexion != null) {
            try {

                st = conexion.createStatement();
                rs = st.executeQuery("SELECT * FROM Sitio");

                while (rs.next()) {

                    int id_Sitio = rs.getInt("id_Sitio");
                    String nombre = rs.getString("nombre");
                    int tipo = rs.getInt("id_Tipo");
                    double precio_minimo = rs.getDouble("precio_min");
                    double precio_maximo = rs.getDouble("precio_max");
                    String disponibilidad = rs.getString("disponibilidad");
                    int id_Ubicacion = rs.getInt("id_Ubicacion");

                    lugares.add(new Lugar(id_Sitio, nombre, tipo, precio_minimo, precio_maximo, disponibilidad, id_Ubicacion));
                }

            } catch (Exception e) {
                System.out.println("Error: " + e);
            } finally {
                try {
                    rs.close();
                    st.close();
                } catch (Exception e) {
                }
            }
        }
        return lugares;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public int getId_Sitio() {
        return id_Sitio;
    }

    public void setId_Sitio(int id_Sitio) {
        this.id_Sitio = id_Sitio;
    }

    public int getId_SitioM() {
        return id_SitioM;
    }

    public void setId_SitioM(int id_SitioM) {
        this.id_SitioM = id_SitioM;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public double getPrecio_minimo() {
        return precio_minimo;
    }

    public void setPrecio_minimo(double precio_minimo) {
        this.precio_minimo = precio_minimo;
    }

    public double getPrecio_maximo() {
        return precio_maximo;
    }

    public void setPrecio_maximo(double precio_maximo) {
        this.precio_maximo = precio_maximo;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getId_Ubicacion() {
        return id_Ubicacion;
    }

    public void setId_Ubicacion(int id_Ubicacion) {
        this.id_Ubicacion = id_Ubicacion;
    }

    public String[] obtenerDetallesLugar() {
        String buscar = String.valueOf(this.id_Sitio);

        String[] retornable = new String[5];

        Statement st = null;
        ResultSet rs = null;
        if (conexion != null) {
            try {

                st = conexion.createStatement();
                rs = st.executeQuery(
                        "SELECT pais_nom,ciudad_nom,parroquia_nom,codigoPostal,calleTransversal\n"
                        + "FROM Ubicacion u\n"
                        + "JOIN (Parroquia p, Ciudad c, Pais pa)\n"
                        + "ON (p.id_Parroquia=u.id_Parroquia and pa.id_Pais=c.id_Pais and c.id_Ciudad=p.id_Ciudad and c.id_Pais=pa.id_Pais)\n"
                        + "where id_Ubicacion=" + this.id_Sitio + ";");

                rs.next();

                retornable[0] = rs.getString("pais_nom");
                retornable[1] = rs.getString("ciudad_nom");
                retornable[2] = rs.getString("parroquia_nom");
                retornable[3] = rs.getString("codigoPostal");
                retornable[4] = rs.getString("calleTransversal");

            } catch (Exception e) {
                System.out.println("Error: " + e);
            } finally {
                try {
                    rs.close();
                    st.close();
                } catch (Exception e) {
                }
            }
        }

        return retornable;

    }

    public static void editarLugar(int id_Sitio, String nuevoNombre) {
        String sql = "UPDATE Sitio SET nombre = ? WHERE id_Sitio = ?";

        Statement st = null;
        ResultSet rs = null;
        System.out.println("whattt");
        if (conexion != null) {
            try {
                PreparedStatement preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setString(1, nuevoNombre);
                preparedStatement.setString(2, String.valueOf(id_Sitio));

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Se modifico esa fila");
                } else {
                    System.out.println("No se modifico nada");
                }
     
            } catch (Exception e) {
                System.out.println("Error: " + e);
            } finally {
                try {
                    rs.close();
                    st.close();
                } catch (Exception e) {
                }
            }
        }
    }
;
    
    public static void eliminarLugar(int id_Sitio) {
        String sql = "Delete FROM Sitio WHERE id_Sitio = ?";

        Statement st = null;
        ResultSet rs = null;
        System.out.println("whattt");
        if (conexion != null) {
            try {
                PreparedStatement preparedStatement = conexion.prepareStatement(sql);
                preparedStatement.setString(1, String.valueOf(id_Sitio));
                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Se elimino esa fila");
                } else {
                    System.out.println("No se modifico nada");
                }
     
            } catch (Exception e) {
                System.out.println("Error: " + e);
            } finally {
                try {
                    rs.close();
                    st.close();
                } catch (Exception e) {
                }
            }
        }
    }
;
}
