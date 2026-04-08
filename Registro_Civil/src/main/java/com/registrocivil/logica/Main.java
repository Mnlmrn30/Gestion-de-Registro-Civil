package com.registrocivil.logica;

public class Main {
    
    public static void main(String[] args) {
        
        System.out.println("Iniciando el sistema de Registro Civil...");
        
        GestionSistema sistema = new GestionSistema();
        
        Persona p1 = new Persona("11.111.111-1", "Manuel", "Perez");
        Persona p2 = new Persona("22.222.222-2", "Fernanda", "Gonzalez");
        
        p2.setEstadoCivil("Casada");
        
        System.out.println("\nGuardando personas...");
        sistema.registrarNacimiento("Valparaiso", p1);
        sistema.registrarNacimiento("Valparaiso", p2);
        
        System.out.println("\nBuscando a Fernanda en Valparaíso...");
        Persona encontrada = sistema.buscarPorPersonaEnRegion("Valparaiso", "22.222.222-2");
        
        if (encontrada != null) {
            System.out.println("¡Éxito! Persona encontrada:");
            System.out.println(encontrada.toString());
        } else {
            System.out.println("No se encontró a la persona en la base de datos.");
        }
    }
}
