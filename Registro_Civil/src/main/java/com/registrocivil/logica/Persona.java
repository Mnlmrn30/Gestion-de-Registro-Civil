package com.registrocivil.logica;

public class Persona {
    private String rut;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;

    public Persona(String rut, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido) {
        this.rut = rut;
        this.primerNombre = primerNombre;
        this.segundoNombre = (segundoNombre == null || segundoNombre.trim().isEmpty()) ? "" : segundoNombre.trim();
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
    }

    public Persona(String rut, String primerNombre, String primerApellido) {
        this(rut, primerNombre, "", primerApellido, ""); 
    }

    
    public String getRut() { return rut; }
    public String getPrimerNombre() { return primerNombre; }
    public String getSegundoNombre() { return segundoNombre; }
    public String getPrimerApellido() { return primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
}