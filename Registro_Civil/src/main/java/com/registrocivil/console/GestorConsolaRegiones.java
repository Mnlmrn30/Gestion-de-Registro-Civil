/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registrocivil.console;

import com.registrocivil.logica.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;

/*
Las funciones necesarias para que subMenuRegiones funcione correctamente, cada metodo corresponde a un case
para que a la hora de ingresar el numero nos arroje el metodo correspondiente.
*/
public class GestorConsolaRegiones {
    
    private GestionSistema sistema;
    private HashMap<String, Region> regiones;
    private BufferedReader lector; 

    public GestorConsolaRegiones(GestionSistema sistema, BufferedReader lector) {
        this.sistema = sistema; 
        this.regiones = sistema.getRegiones();
        this.lector = lector; 
    }
    
    public void mostrarInformacionRegiones(){
        System.out.println("\n INFORMACION TODAS LAS REGIONES");
        
        if(regiones.isEmpty()){
            System.out.println("ERROR");
            return;
        }
        
        for (Region region : regiones.values()){
            System.out.println("Region: " + region.getNombre());
            System.out.println("Poblacion Actual: " + region.getNumeroHabitantes() + " Habitantes.");            
        }
    }
    
    public void verListadoCiudadanosPorRegion(BufferedReader lector){
        try{
            System.out.println("\n LISTADO DETALLADO DE CIUDADANOS ");
            System.out.println("Ingrese el nombre de la region a consultar: ");
            
            String nombreRegion = seleccionarRegion(); 
            Region regionEncontrada = regiones.get(nombreRegion);
            
            if(regionEncontrada != null){
                System.out.println("\nRegistro de ciudadanos en " + regionEncontrada.getNombre() + ":");
                System.out.println("-----------------------------------------------------------------");
                
                boolean hayCiudadanos = false;
                
                for(Persona p : regionEncontrada.getCiudadanos()){
                    hayCiudadanos = true;
                    System.out.println(p.toString()); 
                }
                
                if(!hayCiudadanos){
                    System.out.println("No hay ciudadanos registrados en esta región.");
                }
            } else {
                System.out.println("Error: No se encontró la región en el sistema.");}
        } catch (Exception e){
            System.out.println("Error al leer la entrada: " + e.getMessage());
        }
    }
    
    public void verListadoMatrimonio(BufferedReader lector){
    try {
        System.out.println("\n LISTADO DE MATRIMONIOS POR REGION ");
        String nombreRegion = seleccionarRegion(); 
        
        Region regionEncontrada = regiones.get(nombreRegion);
        
        if(regionEncontrada != null){
            System.out.println("\n=== REGISTRO CIVIL: " + regionEncontrada.getNombre().toUpperCase() + " ===");
            
            List<String> actas = regionEncontrada.getActasMatrimonio();
            
            if(actas.isEmpty()){
                System.out.println("No se han celebrado matrimonios en esta jurisdicción.");
            } else {
                System.out.println("Total de matrimonios celebrados: " + regionEncontrada.getContadorMatrimonios());
                System.out.println("------------------------------------------------");
                for(String acta : actas){
                    System.out.println(" [LIBRO DE ACTAS]: " + acta);
                }
                System.out.println("------------------------------------------------");
            }
        } else {
            System.out.println("Error: Región no válida.");
        }
    } catch (Exception e){
        System.out.println("Error al mostrar el listado: " + e.getMessage());
    }
}
    
    public void verEstadisticasGenerales(){
        System.out.println("\n ==== ESTADISTICAS GENERALES TOTAL ==== ");
        
        int totalNacional = 0; 
        int totalFallecidosNacional = 0; 
        int totalVivosNacional = 0; 
        
        int totalHombres = 0; 
        int totalMujeres = 0;
        int otros = 0;
        
        for(Region r : regiones.values()){
            int totalRegion = r.getCiudadanos().size();
            int fallecidosRegion = sistema.obtenerFallecidosPorRegion(r.getNombre());
            int vivosRegion = sistema.obtenerVivosPorRegion(r.getNombre()); 
            
            totalNacional += totalRegion; 
            totalFallecidosNacional += fallecidosRegion;
            totalVivosNacional += vivosRegion; 
            
            for(Persona p : r.getCiudadanos()){
                String sexo = p.getSexo().toLowerCase();
                if(sexo.equals("masculino") || sexo.equals("M") || sexo.equals("MASCULINO")){
                    totalHombres++;
                }
                else if(sexo.equals("femenino") || sexo.equals("F") || sexo.equals("FEMENINO")){
                    totalMujeres++; 
                } else{
                    otros++; 
                }
            }
            if(totalRegion > 0){
                System.out.println("\n REGIÓN: " + r.getNombre().toUpperCase());
                System.out.println("        Total Histórico Inscritos: " + totalRegion);
                System.out.println("        Ciudadanos Vivos: " + vivosRegion);
                System.out.println("        Defunciones Registradas: " + fallecidosRegion);
            }
        }
        
        System.out.println(" === RESUMEN NIVEL NACIONAL === ");
        System.out.println("Poblacion Total: " + totalNacional);
        System.out.println("Total Vivos Actuales: " + totalVivosNacional);
        System.out.println("Total Fallecidos: " + totalFallecidosNacional);
        System.out.println("-----------------------------------------------");
        System.out.println("Total Hombres: " + totalHombres);
        System.out.println("Total Mujeres " + totalMujeres);
        System.out.println("Total Otros " + otros);
        System.out.println(" ==============================================");
    }
    
    private String seleccionarRegion() {
        String[] regiones = {
            "Arica y Parinacota", "Tarapaca", "Antofagasta", "Atacama",
            "Coquimbo", "Valparaiso", "Región Metropolitana", "O'Higgins",
            "Maule", "Ñuble", "Biobio", "La Araucania", "Los Rios",
            "Los Lagos", "Aysen", "Magallanes"
        };

        while (true) {
            try {
                System.out.println("\n--- SELECCIONE LA REGIÓN ---");
                for (int i = 0; i < regiones.length; i++) {              
                    System.out.println((i + 1) + ". " + regiones[i]); 
                }
                System.out.println("Ingrese el número de la región (1-16): ");
                int opcion = Integer.parseInt(lector.readLine());

                if (opcion >= 1 && opcion <= regiones.length) {
                    return regiones[opcion - 1]; 
                } else {
                    System.out.println("Error: Ingrese un número válido.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número, no letras.");
            }
            catch (Exception e){
                System.out.println("Error de lectura en la consola.");
            }
        }
    }
    
}
