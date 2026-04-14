
package com.registrocivil.console;

import com.registrocivil.logica.GestionSistema;
import com.registrocivil.logica.Region;
import com.registrocivil.logica.Persona;
import java.util.HashMap; 
// MANUEL MORENO ---  Mostrar todas las regiones y su informarcion  ---  Ver estadisticas generales 
public class GestorConsolaRegiones2 {
    
    private GestionSistema sistema;
    HashMap<String, Region> regiones = sistema.getRegiones();
    public void mostrarInformacionRegiones(){
        System.out.println("\n INFORMACION TODAS LAS REGIONES");
        
        if(regiones.isEmpty()){
            System.out.println("ERROR");
        }
        
        for (Region region : regiones.values()){
            System.out.println("Region: " + region.getNombre());
            System.out.println("Poblacion Actual: " + region.getNumeroHabitantes() + " Habitantes.");            
        }
    }
    
    public void verEstadisticasGenerales(){
        System.out.println("\n ESTADISTICAS GENERALES TOTAL");
        
        int poblacionTotal = 0;
        int totalHombres = 0;
        int totalMuejres = 0;
        int otros = 0;
        
        for (Region r : regiones.values()){
            for(p.getSexo().equalsIgnoreCase("Masculino")){
                totalHombres
            }
        }
    }
}
