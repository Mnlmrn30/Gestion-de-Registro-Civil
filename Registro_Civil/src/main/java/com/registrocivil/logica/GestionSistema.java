package com.registrocivil.logica;

import java.util.ArrayList;
import java.util.HashMap;

public class GestionSistema {
    private HashMap <String, ArrayList<Persona>> regiones;
    
    public GestionSistema(){
        this.regiones = new HashMap<>();
        String[] listaRegiones = {"Arica y Parinacota", "Tarapaca", "Antofagasta",
            "Atacama", "Coquimbo", "Valparaiso", "Metropolitana de Santiago",
            "Bernardo O'Higgins", "Maule", "Ñuble","Biobio", "La Araucania", 
            "Los Rios", "Los Lagos", "Aysen","Magallanes"};
        
        for (String r : listaRegiones){
            regiones.put(r, new ArrayList<Persona>());
        }
        cargarDatosPrueba();
    }
    
    private void cargarDatosPrueba(){
        Persona p1 = new Persona("21.943.128-7", "Manuel", "Moreno");
        Persona p2 = new Persona("22.023.557-2", "Hans", "Paz");
        registrarNacimiento("Antofagasta", p1);
        registrarNacimiento("Coquimbo", p2);
    }
    public void registrarNacimiento(String nombreRegion, Persona p){
        if(regiones.containsKey(nombreRegion)){
            regiones.get(nombreRegion).add(p);
        }
    }
    
    public Persona buscarPorPersonaEnRegion(String nombreRegion, String rut){
        if(regiones.containsKey(nombreRegion)){
            ArrayList<Persona> lista = regiones.get(nombreRegion);
            for(Persona p : lista){
                if(p.getRut().equals(rut)){
                    return p;
                }
            }
        }
        return null;
    }
    
    // Metodo para mostrar personas en lista por región. 
    public ArrayList<Persona> listarPersonasRegion(String nombreRegion){
        if(regiones.containsKey(nombreRegion)){
            return regiones.get(nombreRegion); // retornamos la lista de personas por región. 
        }
        return new ArrayList<>(); //si no existe la region retornamos una lista vacía. 
    }
    
    // Metodo para eliminar personas buscandola por el rut. 
    public boolean eliminarPersona(String nombreRegion, String rut){
        Persona personaAEliminar = buscarPorPersonaEnRegion(nombreRegion, rut); 
        if(personaAEliminar != null){
            regiones.get(nombreRegion).remove(personaAEliminar);
            return true;
        }
        return false; 
    }
    
    // Metodo que cambia los datos de un objeto persona. 
    public boolean editarPersona(String nombreRegion, String rut, String nuevoNombre, String nuevoApellido){
        Persona personaAEditar = buscarPorPersonaEnRegion(nombreRegion, rut); 
        if(personaAEditar != null){
            personaAEditar.setNombre(nuevoNombre); 
            personaAEditar.setApellido(nuevoApellido);
            return true; 
        }
        return false; 
    }
    public String[] getNombreRegiones(){
        return regiones.keySet().toArray(new String[0]);
    }
}
