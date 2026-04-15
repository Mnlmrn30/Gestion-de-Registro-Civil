package com.registrocivil.logica; 

import java.util.HashMap;
import java.util.ArrayList; 
import java.util.Random; 
import java.sql.*;

public class GestionSistema{
    private HashMap<String, Region> regiones; 
    private static final String URL_BD = "jdbc:sqlite:registrocivil.db";
    
    public GestionSistema(){
        regiones = new HashMap<>(); 
        inicializarRegiones(); 
        crearTabla();
    }
    
    private void inicializarRegiones(){
        String[] nombresRegiones = {"Arica y Parinacota", "Tarapaca", "Antofagasta", "Atacama", "Coquimbo", 
            "Valparaiso", "Región Metropolitana", "O'Higgins", 
            "Maule", "Ñuble", "Biobio", "La Araucania", "Los Rios", "Los Lagos", 
            "Aysen", "Magallanes"}; 
        
        for (String nombre: nombresRegiones){
            regiones.put(nombre, new Region(nombre)); 
        }
    }
    
    public HashMap<String, Region> getRegiones(){
        return regiones; 
    }
    
    private String generarRutAleatorio(){
        Random rnd = new Random(); 
        int numero = 25000000 + rnd.nextInt(5000000);
        String[] dv = {"1","2","3","4","5","6","7","8","9","K"}; 
        String digito = dv[rnd.nextInt(11)]; 
        return String.format("%,d", numero).replace(",",".") + "-" + digito;
    }
    
    public boolean registrarPersona(String nombreRegion, String rut, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
                                  String sexo, int diaNac, int mesNac, int añoNac){
        if(regiones.containsKey(nombreRegion)){
            Persona nuevaPersona = new Persona(rut, primerNombre, segundoNombre, primerApellido, segundoApellido, sexo, diaNac, mesNac, añoNac); 
            regiones.get(nombreRegion).getCiudadanos().add(nuevaPersona);
            return true; 
        }
        return false; 
    }
    
    public String registrarNacimiento(String nombreRegion, String pNombre, String sNombre, String pApellido, String sApellido, String sexo, int d, int m, int a, 
            String rutPadre, String rutMadre){
        if(!regiones.containsKey(nombreRegion)){
            return null; 
        }
        String nuevoRut = generarRutAleatorio(); 
        Persona bebe = new Persona(nuevoRut, pNombre,sNombre,pApellido,sApellido, sexo, d, m, a); 
        Persona padre = busquedaGlobalPersona(rutPadre);
        Persona madre = busquedaGlobalPersona(rutMadre); 
        
        bebe.setPadre(padre);
        bebe.setMadre(madre);
        regiones.get(nombreRegion).getCiudadanos().add(bebe); 
        return nuevoRut; 
        
    }
    
    public Persona busquedaGlobalPersona(String rut){
        for(Region r : regiones.values()){
            for(Persona p: r.getCiudadanos()){
                if(p.getRut().equals(rut)){
                    return p; 
                }
            }
        }
        return null; 
    }
    
    public String obtenerRegionDePersona(String rut){
        for(Region r: regiones.values()){
            for(Persona p: r.getCiudadanos()){
                if(p.getRut().equals(rut)){
                    return r.getNombre(); 
                }
            }
        }
        return "Desconocida"; 
    }
    
    
    public boolean editarPersona(String rut, String nuevoPrimerNombre, String nuevoSegundoNombre, String nuevoPrimerApellido, String nuevoSegundoApellido, String nuevoSexo, int nuevoDia, int nuevoMes, int nuevoAñoNac) {
        Persona personaAEditar = busquedaGlobalPersona(rut);
        
        if(personaAEditar != null){
            personaAEditar.setPrimerNombre(nuevoPrimerNombre);
            personaAEditar.setSegundoNombre(nuevoSegundoNombre);
            personaAEditar.setPrimerApellido(nuevoPrimerApellido);
            personaAEditar.setSegundoApellido(nuevoSegundoApellido);
            personaAEditar.setSexo(nuevoSexo);
            personaAEditar.setDiaNacimiento(nuevoDia);
            personaAEditar.setMesNacimiento(nuevoMes);
            personaAEditar.setAñoNacimiento(nuevoAñoNac);
            return true;
        }
        return false;
    }
    
    public boolean eliminarPersona(String nombreRegion, String rut) {
        Persona personaAEliminar = buscarPersona(nombreRegion, rut);
        if(personaAEliminar != null){
            regiones.get(nombreRegion).getCiudadanos().remove(personaAEliminar);
            return true;
        }
        return false;
    }
    
    // Primera busqueda de persona por Rut (localmente en persona) 
    public Persona buscarPersona(String nombreRegion, String rut) {
        if(regiones.containsKey(nombreRegion)){
            for(Persona p : regiones.get(nombreRegion).getCiudadanos()){
                if(p.getRut().equals(rut)){
                    return p;
                }
            }
        }
        return null; 
    }
    // Segunda busqueda de persona por Nombre y Apellidos 
    public Persona buscarPersona(String nombreRegion, String primerNombre, String primerApellido) {
        if(regiones.containsKey(nombreRegion)){
            for(Persona p : regiones.get(nombreRegion).getCiudadanos()){
                if(p.getPrimerNombre().equalsIgnoreCase(primerNombre) && p.getPrimerApellido().equalsIgnoreCase(primerApellido)){
                    return p;
                }
            }
        }
        return null;
    }
    
    public boolean registrarMatrimonio(String rut1, String rut2){
        Persona p1 = busquedaGlobalPersona(rut1); 
        Persona p2 = busquedaGlobalPersona(rut2);
        
        if(p1 == null || p2==null){
            return false; 
        }
        if(p1.getRut().equals(p2.getRut())){
            return false;
        }
        if (!p1.getEstadoCivil().equals("Soltero/a") && !p1.getEstadoCivil().equals("Viudo/a")) {
        return false;
        }
        if (!p2.getEstadoCivil().equals("Soltero/a") && !p2.getEstadoCivil().equals("Viudo/a")) {
            return false;
        }
        
        p1.setEstadoCivil("Casado/a");
        p2.setEstadoCivil("Casado/a");
        
        p1.setConyuge(p2);
        p2.setConyuge(p1);
        
        return true; 
    }
    
    private void crearTabla(){
        String sql = "CREATE TABLE IF NOT EXISTS Persona (\n" +
                     " rut TEXT PRIMARY KEY,\n" +
                     " region TEXT NOT NULL,\n" +
                     " primer_nombre TEXT,\n" +
                     " segundo_nombre TEXT,\n" +
                     " primer_apellido TEXT,\n" +
                     " segundo_apellido TEXT,\n" +
                     " sexo TEXT,\n" +
                     " dia INTEGER,\n" +
                     " mes INTEGER,\n" +
                     " anio INTEGER,\n" +
                     " estado_civil TEXT,\n" +
                     " estado_vital TEXT\n" +
                     ");";
        try(Connection conn = DriverManager.getConnection(URL_BD);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }
   
    
    
    public void cargarDatosDesdeBD(){
        String sql = "SELECT * FROM Persona";
        try(Connection conn = DriverManager.getConnection(URL_BD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                String region = rs.getString("region");
                String rut = rs.getString("rut");
                String pNombre = rs.getString("primer_nombre");
                String sNombre = rs.getString("segundo_nombre");
                String pApellido = rs.getString("primer_apellido");
                String sApellido = rs.getString("segundo_apellido");
                String sexo = rs.getString("sexo");
                int dia = rs.getInt("dia");
                int mes = rs.getInt("mes");
                int anio = rs.getInt("anio");
                this.registrarPersona(region, rut, pNombre, sNombre, pApellido, sApellido, sexo, dia, mes, anio);
                
                Persona p = buscarPersona(region, rut);
                if(p!=null){
                    p.setEstadoCivil(rs.getString("estado_civil"));
                    String estVital = rs.getString("estado_vital"); 
                    if(estVital != null){
                        p.setEstadoVital(estVital);
                    }
                }
            }
            System.out.println("DATOS CARGADOS CORRECTAMENTE DESDE SQLITE");
        } catch (SQLException e){
            System.out.println("No se pudo cargar la BD");
        }
    }
    
    public void guardarDatosEnBD() {
    String deleteSql = "DELETE FROM Persona";
    String insertSql = "INSERT INTO Persona (region, rut, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, sexo, dia, mes, anio, estado_civil, estado_vital) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(URL_BD)) {
        Statement st = conn.createStatement();
        st.executeUpdate(deleteSql);

        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            for (Region r : regiones.values()) {
                for (Persona p : r.getCiudadanos()) {
                    pstmt.setString(1, r.getNombre());
                    pstmt.setString(2, p.getRut());
                    pstmt.setString(3, p.getPrimerNombre());
                    pstmt.setString(4, p.getSegundoNombre());
                    pstmt.setString(5, p.getPrimerApellido());
                    pstmt.setString(6, p.getSegundoApellido());
                    pstmt.setString(7, p.getSexo());
                    pstmt.setInt(8, p.getDiaNacimiento());
                    pstmt.setInt(9, p.getMesNacimiento());
                    pstmt.setInt(10, p.getAñoNacimiento());
                    pstmt.setString(11, p.getEstadoCivil());
                    
                    pstmt.setString(12, p.getEstadoVital());
                    
                    pstmt.addBatch(); 
                }
            }
            pstmt.executeBatch();
        }
        System.out.println("Base de datos actualizada con éxito antes de salir");
    } catch (SQLException e) {
        System.out.println("Error crítico al guardar: " + e.getMessage());
    }
  }
  
  public int obtenerFallecidosPorRegion(String nombreRegion){
      int contador = 0; 
      Region r = regiones.get(nombreRegion);
      if(r != null){
          for(Persona p : r.getCiudadanos()){
              if("Fallecido".equalsIgnoreCase(p.getEstadoVital())){
                  contador++; 
              }
          }
      }
      return contador; 
  }
 
  public int obtenerVivosPorRegion(String nombreRegion){
      Region r = regiones.get(nombreRegion); 
      if(r!=null){
          return r.getCiudadanos().size() - obtenerFallecidosPorRegion(nombreRegion);
      }
      return 0; 
  }
  
}