package com.registrocivil.logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionSistema {

    private final String url = "jdbc:sqlite:registrocivil.db"; 

    public GestionSistema() {
        crearTablas();
        // Manu o Hanss Puedes descomentar la siguiente línea si quieres que se carguen 
        // los datos de prueba la primera vez que se crea la base de datos.
        cargarDatosPrueba(); 
    }

    private Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error de conexión a SQLite: " + e.getMessage());
        }
        return conn;
    }

    private void crearTablas() {
        String sql = "CREATE TABLE IF NOT EXISTS personas (\n"
                + " rut TEXT PRIMARY KEY,\n"
                + " nombre TEXT NOT NULL,\n"
                + " apellido TEXT NOT NULL,\n"
                + " estado_civil TEXT NOT NULL,\n"
                + " rut_conyuge TEXT,\n" 
                + " region TEXT NOT NULL\n"
                + ");";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }

    private void cargarDatosPrueba(){
        Persona p1 = new Persona("21.943.128-7", "Manuel", "Moreno");
        Persona p2 = new Persona("22.023.557-2", "Hans", "Paz");
        registrarNacimiento("Antofagasta", p1);
        registrarNacimiento("Coquimbo", p2);
    }

    public void registrarNacimiento(String nombreRegion, Persona p) {
        String sql = "INSERT INTO personas(rut, nombre, apellido, estado_civil, rut_conyuge, region) VALUES(?,?,?,?,?,?)";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, p.getRut());
            pstmt.setString(2, p.getNombre()); 
            pstmt.setString(3, p.getApellido()); 
            pstmt.setString(4, p.getEstadoCivil());
            
            if (p.getConyuge() != null) {
                pstmt.setString(5, p.getConyuge().getRut());
            } else {
                pstmt.setString(5, null);
            }
            
            pstmt.setString(6, nombreRegion);
            
            pstmt.executeUpdate();
            System.out.println("Registro guardado con éxito en la Base de Datos: " + p.getNombre());
            
        } catch (SQLException e) {
            System.out.println("Error al registrar (¿El RUT ya existe?): " + e.getMessage());
        }
    }

    public Persona buscarPorPersonaEnRegion(String nombreRegion, String rut) {
        String sql = "SELECT * FROM personas WHERE region = ? AND rut = ?";
        Persona personaEncontrada = null;

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombreRegion);
            pstmt.setString(2, rut);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                personaEncontrada = new Persona(
                        rs.getString("rut"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
                personaEncontrada.setEstadoCivil(rs.getString("estado_civil"));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar en la Base de Datos: " + e.getMessage());
        }
        
        return personaEncontrada;
    }

    public String[] getNombreRegiones() {
        return new String[]{
            "Arica y Parinacota", "Tarapaca", "Antofagasta", "Atacama", "Coquimbo", 
            "Valparaiso", "Metropolitana de Santiago", "Bernardo O'Higgins", 
            "Maule", "Ñuble", "Biobio", "La Araucania", "Los Rios", "Los Lagos", 
            "Aysen", "Magallanes"
        };
    }
}
