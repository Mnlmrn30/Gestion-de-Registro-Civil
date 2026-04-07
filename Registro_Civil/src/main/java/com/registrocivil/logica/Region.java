package com.registrocivil.logica;
import java.util.ArrayList; 

public class Region {
    private String nombre;
    private ArrayList<Persona> ciudadanos; 
    
    public Region(String nombre){
        this.nombre = nombre; 
        this.ciudadanos = new ArrayList<>(); // Se inicializa una lista de ciudadanos vacia. 
    }
    
    // Getters y Setters. 
    public String getNombre(){
        return nombre; 
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public ArrayList<Persona> getCiudadanos(){
        return ciudadanos; 
    }
    public void setCiudadanos(ArrayList<Persona> ciudadanos){
        this.ciudadanos = ciudadanos;
    }
    public int getNumeroHabitantes(){
        return ciudadanos.size(); 
    }
}
