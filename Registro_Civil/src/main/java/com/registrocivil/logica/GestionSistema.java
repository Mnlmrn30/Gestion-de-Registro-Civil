package com.registrocivil.logica; 

import java.util.HashMap;
import java.util.ArrayList; 
import java.util.Random; 

public class GestionSistema{
    private HashMap<String, Region> regiones; 
    public GestionSistema(){
        regiones = new HashMap<>(); 
        inicializarRegiones(); 
        cargarDatosPrueba();
    }
    
    private void inicializarRegiones(){
        String[] nombresRegiones = {"Arica y Parinacota", "Tarapaca", "Antofagasta", "Atacama", "Coquimbo", 
            "Valparaiso", "Metropolitana de Santiago", "Bernardo O'Higgins", 
            "Maule", "Ñuble", "Biobio", "La Araucania", "Los Rios", "Los Lagos", 
            "Aysen", "Magallanes"}; 
        
        for (String nombre: nombresRegiones){
            regiones.put(nombre, new Region(nombre)); 
        }
    }
    
    private void cargarDatosPrueba(){
        registrarPersona("Antofagasta", "21.943.128-7", "Manuel", "Sebastian", "Moreno","Galleguillos", "Masculino", 30,9,2005);
        registrarPersona("Coquimbo","22.023.557-2", "Hans", "Paulo", "Paz", "Bonilla", "Masculino", 17, 1, 2006); 
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
    
    
    public boolean editarPersona(String nombreRegion, String rut, String nuevoPrimerNombre, String nuevoSegundoNombre, String nuevoPrimerApellido, String nuevoSegundoApellido, String nuevoSexo, int nuevoDia, int nuevoMes, int nuevoAñoNac) {
        Persona personaAEditar = buscarPersona(nombreRegion, rut);
        
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
    
    
}