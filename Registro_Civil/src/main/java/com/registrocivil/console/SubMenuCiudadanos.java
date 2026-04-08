
package com.registrocivil.console;

import com.registrocivil.logica.*; 
import java.io.*; 

public class SubMenuCiudadanos extends MenuBase{
    
    public SubMenuCiudadanos(GestionSistema sistema, BufferedReader lector){
        super(sistema, lector); 
    }
    
    @Override
    public void iniciar() throws Exception{
        boolean volver = false; 
        while(!volver){
            System.out.println("==== GESTIÓN DE CIUDADANOS ==== "); 
            System.out.println("1. Inscribir Ciudadano"); 
            System.out.println("2. Mostrar Ciudadanos por Region"); 
            System.out.println("3. Editar Registro Ciudadano"); 
            System.out.println("4. Eliminar Registro Ciudadano"); 
            System.out.println("5. Buscar ciudadano");
            System.out.println("6. Volver Menu Principal");
            System.out.println("Seleccione Opción: "); 
            
            String op = lector.readLine(); 
            switch(op){
                case "1":
                    System.out.println("=== PROCESO === ");
                    break; 
                case "2":
                    System.out.println("=== PROCESO === ");
                    break; 
                case "3": 
                    System.out.println("=== PROCESO === ");
                    break;
                case "4":
                    System.out.println("=== PROCESO === ");
                    break; 
                case "5":
                    System.out.println("=== PROCESO === ");
                    break; 
                case "6":
                    volver = true; 
                    break; 
                default:
                    System.out.println("Opción Invalida");
            }
        }
    }
}
