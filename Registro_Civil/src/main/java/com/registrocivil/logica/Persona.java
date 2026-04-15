package com.registrocivil.logica;

public class Persona {
    private String rut;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String sexo; 
    private int dia;
    private int mes; 
    private int añoNac; 
    private String estadoCivil; 
    private Persona conyuge; 
    private Persona padre; 
    private Persona madre; 
    private String estadoVital; 
    
    public Persona(String rut, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String sexo, int dia, int mes, int añoNac) {
        this.rut = rut;
        this.primerNombre = primerNombre;
        this.segundoNombre = (segundoNombre == null || segundoNombre.trim().isEmpty()) ? "" : segundoNombre.trim();
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.dia = dia; 
        this.mes = mes; 
        this.añoNac = añoNac; 
        this.sexo = sexo; 
        this.estadoVital = "Vivo"; 
       
        estadoCivil = "Soltero/a"; 
        conyuge = null; 
        
        padre = null; 
        madre = null;
    }
    
    public String getRut() { return rut; }
    public void setRut(String rut){ this.rut = rut;}
  
    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre){this.primerNombre = primerNombre;}
    
    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre){this.segundoNombre = segundoNombre;}
    
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido){this.primerApellido = primerApellido;} 
    
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido){this.segundoApellido = segundoApellido;}
    
    public String getSexo() { return sexo; }
    public void setSexo(String sexo){this.sexo = sexo;}
    
    public int getDiaNacimiento() { return dia; }
    public void setDiaNacimiento(int dia){this.dia = dia;}
   
    public int getMesNacimiento() { return mes; }
    public void setMesNacimiento(int mes){this.mes = mes;}
    
    public int getAñoNacimiento() { return añoNac; }
    public void setAñoNacimiento(int añoNac){this.añoNac = añoNac;}
    
    public String getEstadoCivil(){ return estadoCivil;}
    public void setEstadoCivil(String estadoCivil){ this.estadoCivil = estadoCivil;} 
    
    public Persona getConyuge(){ return conyuge;}
    public void setConyuge(Persona p){this.conyuge = p;} 
    
    public Persona getPadre(){ return padre;}
    public void setPadre(Persona p){this.padre = p;} 
    
    public Persona getMadre(){ return madre;}
    public void setMadre(Persona p){this.madre = p;} 
    
    public String getEstadoVital(){ return estadoVital;}
    public void setEstadoVital(String est){this.estadoVital = est;} 
    
    
}