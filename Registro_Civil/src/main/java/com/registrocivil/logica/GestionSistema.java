package com.registrocivil.logica; 

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random; 
import java.sql.*;

public class GestionSistema{
    private HashMap<String, Region> regiones; 
    private static final String URL_BD = "jdbc:sqlite:registrocivil.db";
    
    public GestionSistema(){
        regiones = new HashMap<>(); 
        inicializarRegiones(); 
        crearTabla();
        cargarDatosDesdeBD();
        cargarDatosPrueba(); 
    }
    
    /*
    Creamos el mapa base del sistema que contiene las 16 regiones de chile en un HashMap, para que organizemos
    a los ciudadanos por zona
    */
    private void inicializarRegiones(){
        String[] nombresRegiones = {"Arica y Parinacota", "Tarapaca", "Antofagasta", "Atacama", "Coquimbo", 
            "Valparaiso", "Región Metropolitana", "O'Higgins", 
            "Maule", "Nuble", "Biobio", "La Araucania", "Los Rios", "Los Lagos", 
            "Aysen", "Magallanes"}; 
        
        for (String nombre: nombresRegiones){
            regiones.put(nombre, new Region(nombre)); 
        }
    }
    
    public HashMap<String, Region> getRegiones(){
        return regiones; 
    }
    
    /*
    Casos de prueba que se piden en la SIA-11.
    */
    public void cargarDatosPrueba(){
        int totalCiudadanos = 0;
        for (Region r : regiones.values()) {
            totalCiudadanos += r.getCiudadanos().size();
        }

        if (totalCiudadanos == 0) {          
            // --- REGION METROPOLITANA ---
            this.registrarPersona("Metropolitana", "15111222-3", "Andres", "Felipe", "Munoz", "Soto", "Masculino", 12, 5, 1985);
            this.registrarPersona("Metropolitana", "16222333-4", "Camila", "Ignacia", "Rojas", "Diaz", "Femenino", 24, 8, 1988);
            this.registrarPersona("Metropolitana", "17333444-5", "Matias", "Alejandro", "Perez", "Gonzalez", "Masculino", 3, 11, 1990);
            this.registrarPersona("Metropolitana", "18444555-6", "Valentina", "Paz", "Silva", "Martinez", "Femenino", 17, 2, 1992);
            this.registrarPersona("Metropolitana", "19555666-7", "Diego", "Esteban", "Contreras", "Sepulveda", "Masculino", 9, 7, 1995);
            this.registrarPersona("Metropolitana", "20666777-8", "Javiera", "Belen", "Morales", "Fuentes", "Femenino", 21, 1, 1998);
            this.registrarPersona("Metropolitana", "21777888-9", "Nicolas", "Eduardo", "Lopez", "Cabrera", "Masculino", 5, 6, 2001);
            this.registrarPersona("Metropolitana", "22888999-0", "Fernanda", "Andrea", "Gomez", "Rios", "Femenino", 30, 9, 2003);
            this.registrarPersona("Metropolitana", "12999000-1", "Carlos", "Alberto", "Herrera", "Vidal", "Masculino", 14, 12, 1975);
            this.registrarPersona("Metropolitana", "13123456-2", "Ana", "Maria", "Castro", "Poblete", "Femenino", 2, 4, 1978);

            // --- VALPARAISO ---
            this.registrarPersona("Valparaiso", "14234567-3", "Luis", "Patricio", "Tapia", "Guzman", "Masculino", 18, 10, 1982);
            this.registrarPersona("Valparaiso", "15345678-4", "Carolina", "Andrea", "Navarro", "Araya", "Femenino", 7, 3, 1986);
            this.registrarPersona("Valparaiso", "16456789-5", "Joaquin", "Alonso", "Cortes", "Vergara", "Masculino", 25, 11, 1991);
            this.registrarPersona("Valparaiso", "17567890-6", "Daniela", "Alejandra", "Orellana", "Carrasco", "Femenino", 11, 5, 1994);
            this.registrarPersona("Valparaiso", "18678901-7", "Sebastian", "Andres", "Moya", "Salazar", "Masculino", 29, 8, 1997);
            this.registrarPersona("Valparaiso", "19789012-8", "Martina", "Ignacia", "Bravo", "Valdes", "Femenino", 4, 2, 2000);
            this.registrarPersona("Valparaiso", "20890123-9", "Tomas", "Agustin", "Zuniga", "Pino", "Masculino", 16, 7, 2002);
            this.registrarPersona("Valparaiso", "11901234-K", "Hector", "Manuel", "Figueroa", "Olivares", "Masculino", 22, 12, 1970);

            // --- BIOBIO ---
            this.registrarPersona("Biobio", "12012345-0", "Jose", "Miguel", "Caceres", "Mendez", "Masculino", 8, 1, 1972);
            this.registrarPersona("Biobio", "13123456-1", "Rosa", "Elena", "Vega", "Gallardo", "Femenino", 19, 6, 1976);
            this.registrarPersona("Biobio", "14234567-2", "Francisco", "Javier", "Pinto", "Saavedra", "Masculino", 3, 9, 1980);
            this.registrarPersona("Biobio", "15345678-3", "Paulina", "Andrea", "Aguilar", "Donoso", "Femenino", 27, 4, 1984);
            this.registrarPersona("Biobio", "16456789-4", "Cristian", "Mauricio", "Salinas", "Bustos", "Masculino", 15, 11, 1989);
            this.registrarPersona("Biobio", "17567890-5", "Claudia", "Loreto", "Riquelme", "Parra", "Femenino", 1, 8, 1993);
            this.registrarPersona("Biobio", "18678901-6", "Felipe", "Ignacio", "Valenzuela", "Alvarez", "Masculino", 20, 3, 1996);

            // --- COQUIMBO ---
            this.registrarPersona("Coquimbo", "10111222-1", "Manuel", "Antonio", "Carvajal", "Rivera", "Masculino", 14, 2, 1965);
            this.registrarPersona("Coquimbo", "11222333-2", "Teresa", "Margarita", "Pizarro", "Escobar", "Femenino", 9, 7, 1968);
            this.registrarPersona("Coquimbo", "19789012-7", "Benjamin", "Alonso", "Cortes", "Miranda", "Masculino", 28, 5, 1999);
            this.registrarPersona("Coquimbo", "20890123-8", "Antonia", "Belen", "Aguirre", "Toro", "Femenino", 12, 10, 2004);
            this.registrarPersona("Coquimbo", "21901234-9", "Vicente", "Tomas", "Godoy", "Sanhueza", "Masculino", 6, 12, 2005);

            // --- ARAUCANIA ---
            this.registrarPersona("Araucania", "09888777-6", "Pedro", "Pablo", "Melinao", "Curaqueo", "Masculino", 25, 12, 1960);
            this.registrarPersona("Araucania", "10999888-7", "Carmen", "Gloria", "Huenchuman", "Catrileo", "Femenino", 17, 4, 1963);
            this.registrarPersona("Araucania", "18111222-8", "Rodrigo", "Alejandro", "Soto", "Jara", "Masculino", 2, 9, 1991);
            this.registrarPersona("Araucania", "19222333-9", "Macarena", "Paz", "Garrido", "Lagos", "Femenino", 11, 1, 1995);

            // --- ANTOFAGASTA ---
            this.registrarPersona("Antofagasta", "14555666-0", "Jorge", "Luis", "Pena", "Guzman", "Masculino", 8, 8, 1981);
            this.registrarPersona("Antofagasta", "15666777-1", "Natalia", "Andrea", "Maldonado", "Cornejo", "Femenino", 23, 11, 1985);
            this.registrarPersona("Antofagasta", "22333444-2", "Lucas", "Mateo", "Vargas", "Cardenas", "Masculino", 19, 6, 2006);
            this.registrarPersona("Antofagasta", "23444555-3", "Sofia", "Isidora", "Farias", "Navarrete", "Femenino", 5, 2, 2008);

            // --- ARICA Y PARINACOTA ---
            this.registrarPersona("Arica y Parinacota", "08777666-4", "Raul", "Ernesto", "Mamani", "Choque", "Masculino", 30, 1, 1955);
            this.registrarPersona("Arica y Parinacota", "09888999-5", "Silvia", "Ines", "Condori", "Vilca", "Femenino", 14, 5, 1958);


            String[] rutsFallecidos = {"12999000-1", "11901234-K", "12012345-0", "10111222-1", "09888777-6", "08777666-4"};
            for (String rutF : rutsFallecidos) {
                Persona pFallecida = this.busquedaGlobalPersona(rutF);
                if (pFallecida != null) {
                    pFallecida.setEstadoVital("Fallecido");
                }
            }
            
            //8 Parejas casadas
            this.registrarMatrimonio("15111222-3", "16222333-4", "Metropolitana"); 
            this.registrarMatrimonio("17333444-5", "18444555-6", "Metropolitana"); 
            this.registrarMatrimonio("13123456-2", "12999000-1", "Coquimbo"); 
            this.registrarMatrimonio("14234567-3", "15345678-4", "Valparaiso"); 
            this.registrarMatrimonio("13123456-1", "14234567-2", "Biobio"); 
            this.registrarMatrimonio("11222333-2", "10111222-1", "Maule"); 
            this.registrarMatrimonio("18111222-8", "19222333-9", "Araucania");
            this.registrarMatrimonio("14555666-0", "15666777-1", "Antofagasta"); 
        }
    }
     /*
    Se crea esta funcion para darles un nuevo Rut a los ingresados recien nacidos.
    */
    private String generarRutAleatorio(){
        Random rnd = new Random(); 
        int numero = 25000000 + rnd.nextInt(5000000);
        String[] dv = {"1","2","3","4","5","6","7","8","9","K"}; 
        String digito = dv[rnd.nextInt(11)]; 
        return String.format("%,d", numero).replace(",",".") + "-" + digito;
    }
     /*
    Toma todos los datos mas importantess de un ciudadano y crea un objeto Persona, ahi los guarda segun la region correspondiente 
    */
    public boolean registrarPersona(String nombreRegion, String rut, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
                                  String sexo, int diaNac, int mesNac, int añoNac){
        
        if (busquedaGlobalPersona(rut) != null) {
            return false; 
        }
        
        if(regiones.containsKey(nombreRegion)){
            Persona nuevaPersona = new Persona(rut, primerNombre, segundoNombre, primerApellido, segundoApellido, sexo, diaNac, mesNac, añoNac); 
            regiones.get(nombreRegion).getCiudadanos().add(nuevaPersona);
            return true; 
        }
        return false; 
    }
     /*
    registra a todos los recien nacidos, aqui se utiliza el rut aleatorizado.
    */
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
    /*
    Es el buscador Principal va recorriendo todas las regiones una por una hasta que encuentra al ciudadano.
    */
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
    
    public List<Persona> busquedaGlobalPersona(String primerNombre, String primerApellido){
        List<Persona> coincidencias = new ArrayList<>();
        for (Region r : regiones.values()) {
            for (Persona p : r.getCiudadanos()) {
                if (p.getPrimerNombre().equalsIgnoreCase(primerNombre) && 
                    p.getPrimerApellido().equalsIgnoreCase(primerApellido)) {
                    coincidencias.add(p);
                }
            }
        }
        return coincidencias;
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
    
    /*
    Edita personas en caso de que haya un error al ingresarlos, y ayuda tambien para inscribir a los padres de los
    agregados en general para sacar asi certificados en el que pide a ambos.
    */
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
    
    // Elimina persona con el rut.
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
    
    /*
    Pide el rut de ambas personas para poder registrar en el matrimonio y asi cambiar su estado civil, tambien registrar donde
    contraen matrimonios para soltar su informacion
    */
    public boolean registrarMatrimonio(String rut1, String rut2, String nombreRegion){
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
        
        if(regiones.containsKey(nombreRegion)){
            regiones.get(nombreRegion).incrementarMatrimonios(); 
            return true; 
        }
        return true; 
    }
    
    /*
    Encargada de la base de datos, donde aqui se ejecuta el codigo SQL.
    */
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
   
    /*
    Toma a los ciudadanos guardados en la base de datos, y los vuelve a llenar en lista de las regiones con los ciudadanos
    guardados.
    */
    
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
    
    /*
    Toma a todos los ciudadanos que se encuentran en la memoria del programa y los inserta en el archivo de la base de datos.
    */
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