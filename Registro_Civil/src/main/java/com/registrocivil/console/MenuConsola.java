package com.registrocivil.console;

import com.registrocivil.logica.*;
import java.io.*; 
import java.util.ArrayList; 

public class MenuConsola extends MenuBase{
    // Constructor que recibe el sistema central, mediante el constructor de la clase padre MenuBase. 
    public MenuConsola(GestionSistema sistema){
        super(sistema, new BufferedReader(new InputStreamReader(System.in)));
    }
    
    /* Apartado que nos hacer escoger que actividad queremos realizar, si gestionar ciudadanos o las regiones, de no escoger ninguna
    se puede salir del programa utilizando el numero 3.
    */
    @Override
    public void iniciar(){
        boolean salir = false; 
        while(!salir){
            System.out.println("=== SISTEMA REGISTRO CIVIL ===");
            System.out.println("1. Gestionar Regiones");
            System.out.println("2. Gestionar Ciudadanos"); 
            System.out.println("3. Salir (Cerrar Programa)"); 
            System.out.println("Seleccione una opción: ");
            
            try {
                String opcion = lector.readLine(); 
                switch (opcion){
                    case "1":
                        SubMenuRegiones menuRegiones = new SubMenuRegiones(sistema, lector);
                        menuRegiones.iniciar(); 
                        break; 
                    case "2":
                        SubMenuCiudadanos menuCiudadanos = new SubMenuCiudadanos(sistema, lector); 
                        menuCiudadanos.iniciar(); 
                        break;
                    case "3": 
                        System.out.println(" === SALIENDO. CERRANDO SISTEMA === "); 
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción Invalida. Intente Nuevamente"); 
                }
            } catch (IOException e){
                System.out.println("Error de lectura: "+ e.getMessage());
            } catch (Exception e){
                System.out.println("Ocurrió un error en el submenu: " + e.getMessage());
            } 
        }
    }
    
    
    
}
