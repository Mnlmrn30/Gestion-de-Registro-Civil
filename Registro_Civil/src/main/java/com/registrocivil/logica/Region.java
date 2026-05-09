package com.registrocivil.logica;
import java.util.ArrayList; 
import java.util.List;

public class Region {
    private NombreRegion nombre;
    private List<Persona> ciudadanos; 
    private int matrimonios = 0;
    private List<String> actasMatrimonio = new ArrayList<>();
    
    public Region(NombreRegion nombre){
        this.nombre = nombre; 
        this.ciudadanos = new ArrayList<>(); // Se inicializa una lista de ciudadanos vacia. 
    }
    
    // Getters y Setters. 
    public NombreRegion getNombre(){
        return nombre; 
    }
    public void setNombre(NombreRegion nombre){
        this.nombre = nombre;
    }
    
    public List<Persona> getCiudadanos(){
        return java.util.Collections.unmodifiableList(ciudadanos); 
    }

    public void agregarCiudadano(Persona p){
        this.ciudadanos.add(p); 
    }
    public void eliminarCiudadano(Persona p){
        this.ciudadanos.remove(p); 
    }
    
    public int getNumeroHabitantes(){
        return ciudadanos.size(); 
    }
    @Override
    public String toString(){
        return "Region: " + this.nombre.getNombreVisible() + " | Total Ciudadanos Inscritos: " + this.ciudadanos.size(); 
    }
    
    public void incrementarMatrimonios() {
        this.matrimonios++;
    }
    public int getContadorMatrimonios() {
        return matrimonios;
    }
    
    public void registrarActaMatrimonio(String nombresPareja) {
        this.actasMatrimonio.add(nombresPareja);
    }

    public List<String> getActasMatrimonio() {
        return actasMatrimonio;
    }
}
