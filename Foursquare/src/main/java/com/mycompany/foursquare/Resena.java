package com.mycompany.foursquare;

import java.util.LinkedList;
import java.util.List;
import static com.mycompany.foursquare.ConexionMySQL.getConector;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

public class Resena {
    private int id_Resena;
    private String descripcion;
    private int id_Sitio;
    private int id_Usuario;

    public Resena(int id_Resena, String descripcion, int id_Sitio, int id_Usuario) {
        this.id_Resena = id_Resena;
        this.descripcion = descripcion;
        this.id_Sitio = id_Sitio;
        this.id_Usuario = id_Usuario;
    }

    public int getId_Resena() {
        return id_Resena;
    }

    public void setId_Resena(int id_Resena) {
        this.id_Resena = id_Resena;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion.length() > 1000) {
            this.descripcion = descripcion.substring(0, 1000);
        } else {
            this.descripcion = descripcion;
        }
    }

    public int getId_Sitio() {
        return id_Sitio;
    }

    public void setId_Sitio(int id_Sitio) {
        this.id_Sitio = id_Sitio;
    }

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public List<Resena> getResenas(int id_Sitio) {
        List<Resena> resenas = new LinkedList<>();
        Connection conexion = getConector("localhost", "3306", "foursquare", "root", "root");
        Statement st = null;
        ResultSet rs = null;
        if (conexion != null) {
            try {
                st = conexion.createStatement();
                rs = st.executeQuery("SELECT * FROM Resena WHERE id_Sitio = " + id_Sitio);
                while (rs.next()) {
                    int id_Resena = rs.getInt("id_Resena");
                    String descripcion = rs.getString("descripcion");
                    int id_Usuario = rs.getInt("id_Usuario");
                    Resena resena = new Resena(id_Resena, descripcion, id_Sitio, id_Usuario);
                    resenas.add(resena);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            } finally {
                try {
                    rs.close();
                    st.close();
                    conexion.close();
                } catch (Exception e) {
                }
            }
        }
        return resenas;
    }

    public void addResena(String descripcion, int id_Sitio, int id_Usuario) {
        Connection conexion = getConector("localhost", "3306", "foursquare", "root", "root");
        Statement st = null;
        if (conexion != null) {
            try {
                st = conexion.createStatement();
                st.executeUpdate("INSERT INTO Resena (descripcion, id_Sitio, id_Usuario) VALUES ('" +
                        descripcion + "', " + id_Sitio + ", " + id_Usuario + ")");
            } catch (Exception e) {
                System.out.println("Error: " + e);
            } finally {
                try {
                    st.close();
                    conexion.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public void deleteResena(int id_Resena) {
        Connection conexion = getConector("localhost", "3306", "foursquare", "root", "root");
        Statement st = null;
        if (conexion != null) {
            try {
                st = conexion.createStatement();
                st.executeUpdate("DELETE FROM Resena WHERE id_Resena = " + id_Resena);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            } finally {
                try {
                    st.close();
                    conexion.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public void updateResena(int id_Resena, String descripcion) {
        Connection conexion = getConector("localhost", "3306", "foursquare", "root", "root");
        Statement st = null;
        if (conexion != null) {
            try {
                st = conexion.createStatement();
                st.executeUpdate("UPDATE FROM Resena " +
                        "SET descripcion = '" + descripcion + "' " +
                        "WHERE id_Resena = " + id_Resena);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            } finally {
                try {
                    st.close();
                    conexion.close();
                } catch (Exception e) {
                }
            }
        }
    }

}