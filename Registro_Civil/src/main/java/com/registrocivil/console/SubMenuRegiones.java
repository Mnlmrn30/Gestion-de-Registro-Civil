package com.registrocivil.console;

import com.registrocivil.logica.*;
import java.io.*; 

public class SubMenuRegiones extends MenuBase{
    public SubMenuRegiones(GestionSistema sistema, BufferedReader lector){
        super(sistema, lector); 
    }
    
    @Override
    public void iniciar() throws Exception{
        boolean volver = false; 
        while(!volver){
            System.out.println("=== ESTADISTICAS POR REGION === ");
            System.out.println("1. Mostrar todas las regiones y su informarcion ");
            System.out.println("2. Consultar poblacion total de una region ");
            System.out.println("3. Ver listado de matrimonios de una region ");
            System.out.println("4. Ver estadisticas generales "); 
            System.out.println("5. Volver Menú Principal ");
            System.out.println("Selecione opción: ");
            
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
                    volver = true; 
                    break;
                default:
                    System.out.println("Opción Invalida.");
            }
            
        }
    }
}
