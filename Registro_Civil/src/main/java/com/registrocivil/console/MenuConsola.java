package com.registrocivil.console;

import com.registrocivil.logica.GestionSistema; 
import com.registrocivil.logica.Persona; 
import java.io.*; 

public class MenuConsola {
    private GestionSistema sistema; 
    private BufferedReader lector; 
    
    // Constructor que recibe el sistema central. 
    public MenuConsola(GestionSistema sistema){
        this.sistema = sistema; 
        this.lector = new BufferedReader(new InputStreamReader(System.in)); 
    }
    // Método principal
    public void iniciar(){
        boolean salir = false; 
        while(!salir){
            System.out.println("\n===== SISTEMA DE REGISTRO CIVIL =====");
            System.out.println("1. Registrar Nacimiento ");
            System.out.println("2. Mostrar registros por región"); 
            System.out.println("3. Buscar ciudadano por RUT"); 
            System.out.println("4. Registrar Defunción");
            System.out.println("5. Salir");
            
            try {
                String opcion = lector.readLine(); 
                switch (opcion){
                    case "1":
                        // registrarNacimiento(); 
                        System.out.println(" === EN PROCESO === "); 
                        break; 
                    case "2":
                        // mostrarRegistrosRegion(); 
                        System.out.println(" === EN PROCESO === "); 
                        break;
                    case "3": 
                        // busquedaRut(); 
                        System.out.println(" === EN PROCESO === "); 
                        break;
                    case "4":
                        // registrarDefuncion(); 
                        System.out.println(" === EN PROCESO === "); 
                        break; 
                    case "5":
                        salir = true; 
                        System.out.println("=== SALIENDO DEL MODO CONSOLA ===");
                        break; 
                    default:
                        System.out.println("Opción Invalida. Intente Nuevamente"); 
                }
            } catch (IOException e){
                System.out.println("Error de lectura: "+ e.getMessage());
            } 
        }
        
        
    }
    
    
    
}
