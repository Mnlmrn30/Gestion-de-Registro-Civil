package com.registrocivil.logica;

public class Persona {
    private String rut;
    private String nombre;
    private String apellido;
    private String estadoCivil;
    private Persona conyuge;
    private boolean vivo = true;
    
    public Persona(String rut, String nombre, String apellido){
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estadoCivil = "Soltero";
        this.conyuge = null;
    }
    @Override
    public String toString(){
        return "RUT : " + rut + " | Nombre: " + nombre + " " + apellido + " |Estado Civil: " + estadoCivil;
    }
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Persona getConyuge() {
        return conyuge;
    }

    public void setConyuge(Persona conyuge) {
        this.conyuge = conyuge;
    }
    
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}
