package com.registrocivil.console;

import com.registrocivil.logica.*;
import java.io.*; 

public class SubMenuRegiones extends MenuBase{
    public SubMenuRegiones(GestionSistema sistema, BufferedReader lector){
        super(sistema, lector); 
    }
    /*
    Menu Consola para las estadisticas de la region, basicamente este apartado es unicamente para mostrar
    estadisticas y cantidades.
    */
    @Override
    public void iniciar() throws Exception{
        boolean volver = false; 
        while(!volver){
            System.out.println("=== ESTADISTICAS POR REGION === ");
            System.out.println("1. Mostrar todas las regiones y su informacion ");
            System.out.println("2. Consultar listado de Ciudadanos por region ");
            System.out.println("3. Ver listado de matrimonios de una region ");
            System.out.println("4. Ver estadisticas generales "); 
            System.out.println("5. Volver Menú Principal ");
            System.out.println("Selecione opción: ");
            
            String op = lector.readLine();
            GestorConsolaRegiones gestor = new GestorConsolaRegiones(sistema, lector);
            
            switch(op){
                case "1":
                    gestor.mostrarInformacionRegiones();
                    break; 
                case "2":
                    gestor.verListadoCiudadanosPorRegion(lector);
                    break; 
                case "3":
                    gestor.verListadoMatrimonio(lector);
                    break; 
                case "4":
                    gestor.verEstadisticasGenerales();
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
