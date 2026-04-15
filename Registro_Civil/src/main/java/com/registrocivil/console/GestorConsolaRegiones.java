/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registrocivil.console;

import com.registrocivil.logica.*;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;


public class GestorConsolaRegiones {
    
    private GestionSistema sistema;
    private HashMap<String, Region> regiones;

    public GestorConsolaRegiones(GestionSistema sistema, BufferedReader lector) {
        this.sistema = sistema; 
        this.regiones = sistema.getRegiones();
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
            String nombreRegion = lector.readLine();
            
            Region regionEncontrada = null;
            
            for(Region r : regiones.values()){
                if(r.getNombre().equalsIgnoreCase(nombreRegion)){
                    regionEncontrada = r;
                    break;
                }
            }
            
            if(regionEncontrada != null){
                System.out.println("Registro de ciudadanos en " + regionEncontrada.getNombre() + ":");
                System.out.println("-----------------------------------------------------------------");
                System.out.printf("%-15s | %-30s | %-10s%n", "RUT", "NOMBRE COMPLETO", "SEXO");
                
                boolean hayCiudadanos = false;
                
                for(Persona p : regionEncontrada.getCiudadanos()){
                    hayCiudadanos = true;
                    String nombreCompleto = p.getPrimerNombre() + " " + p.getPrimerApellido();
                    
                    if(p.getSegundoApellido() != null && !p.getSegundoApellido().isEmpty()){
                        nombreCompleto += " " + p.getSegundoApellido();
                    }
                    
                    System.out.printf("%-15s | %-30s | %-10s%n", p.getRut(), nombreCompleto, p.getSexo());
                }
                
                if(!hayCiudadanos){
                    System.out.println("No hay ciudadanos registrados en esta region.");
                }
            } else {
                System.out.println("ERROR: No se encontro la region " + nombreRegion + ".");
            }
        } catch (Exception e){
            System.out.println("Error al leer la entrada: " + e.getMessage());
        }
    }
    
    public void verListadoMatrimonio(BufferedReader lector){
        try {
            System.out.println("\n LISTADO DE MATRIMONIOS POR REGION ");
            System.out.println("Ingrese el nombre de la region a consultar: ");
            String nombreRegion = lector.readLine();
            
            Region regionEncontrada = null;
            for(Region r : regiones.values()){
                if(r.getNombre().equalsIgnoreCase(nombreRegion)){
                    regionEncontrada = r;
                    break;
                }
            }
            
            if(regionEncontrada != null){
                System.out.println("Matrimonios registrados en " + regionEncontrada.getNombre() + ":");
                boolean hayMatrimonios = false;
                
                HashSet<String> rutsProcesados = new HashSet<>();
                
                for(Persona p : regionEncontrada.getCiudadanos()){
                    Persona conyuge = p.getConyuge();
                    
                    if(conyuge != null && !rutsProcesados.contains(p.getRut())){
                        hayMatrimonios = true;
                        String nombreP1 = p.getPrimerNombre() + " " + p.getPrimerApellido();
                        String nombreP2 = conyuge.getPrimerNombre() + " " + conyuge.getPrimerApellido();
                        
                        System.out.println("- " + nombreP1 + "(RUT: " + p.getRut() + ") esta casado/a con " + nombreP2 + " (RUT: " + conyuge.getRut() + ")");
                        rutsProcesados.add(p.getRut());
                        rutsProcesados.add(conyuge.getRut());
                    }
                }
                
                if(!hayMatrimonios){
                    System.out.println("No hay matrimonios activos registrados en esta region");
                }
            } else {
                System.out.println("ERROR: No se encontro la region " + nombreRegion + ".");
            }
        } catch (Exception e){
            System.out.println("Error al leer la entrada: " + e.getMessage());
        }
    }
    
    public void verEstadisticasGenerales(){
        System.out.println("\n ESTADISTICAS GENERALES TOTAL");
        
        int poblacionTotal = 0;
        int totalHombres = 0;
        int totalMujeres = 0;
        int otros = 0;
        
        for (Region r : regiones.values()){
            for(Persona p : r.getCiudadanos()){
                poblacionTotal++;
                
                if(p.getSexo().equalsIgnoreCase("Masculino")){
                    totalHombres++;
                } else if (p.getSexo().equalsIgnoreCase("Femenino")){
                    totalMujeres++;
                } else {
                    otros++;
                }
            }        
        }
        System.out.println("Poblacion Total: " + poblacionTotal);
        System.out.println("Total Hombres: " + totalHombres);
        System.out.println("Total Mujeres " + totalMujeres);
        System.out.println("Total Otros " + otros);
    }
    
}
