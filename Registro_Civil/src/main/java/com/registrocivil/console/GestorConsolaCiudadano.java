/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registrocivil.console;

// uniremos todo aqui luego de cada uno terminar su parte

import com.registrocivil.logica.GestionSistema;
import com.registrocivil.logica.Persona;
import java.io.BufferedReader;
import java.io.IOException;



public class GestorConsolaCiudadano {
    private GestionSistema sistema; 
    private BufferedReader lector;
    
    public GestorConsolaCiudadano(GestionSistema sistema, BufferedReader lector){
        this.sistema = sistema; 
        this.lector = lector; 
    }

    GestorConsolaCiudadano() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void registroGeneral(){
        try{
            System.out.println("\n 1. INSCRIBIR CIUDADANO (REGISTRO GENERAL)");
            System.out.println("Ingrese la Región: ");
            String region = lector.readLine();
            System.out.println("Ingrese RUT (ej: 12.345.678-9): ");
            String rut = lector.readLine();
            System.out.println("Ingrese Primer Nombre: ");
            String primerNombre = lector.readLine();
            System.out.println("Ingrese Segundo Nombre: ");
            String segundoNombre = lector.readLine();
            System.out.println("Ingrese Primer Apellido: ");
            String primerApellido = lector.readLine();
            System.out.println("Ingrese Segundo Apellido: ");
            String segundoApellido = lector.readLine();
            System.out.println("Ingrese Sexo (Masculino/Femenino): ");
            String sexo = lector.readLine();
            System.out.println("Ingrese Día de Nacimiento: ");
            int dia = Integer.parseInt(lector.readLine());
            System.out.println("Ingrese Mes de Nacimiento: ");
            int mes = Integer.parseInt(lector.readLine());
            System.out.println("Ingrese Año de Nacimientos: ");
            int anyo = Integer.parseInt(lector.readLine());
            
            boolean exito = sistema.registrarPersona(region, rut, primerNombre, segundoNombre, primerApellido, segundoApellido, sexo, dia, mes, anyo);
            
            if (exito) {
                System.out.println("Ciudadano registrado con éxito en la región de " + region + "!");
            } else {
                System.out.println("Error: No se pudo registrar.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, ingrese números válidos para la fecha de nacimiento.");
        } catch (IOException e) {
            System.out.println("Error al ingresar los datos: " + e.getMessage());
        }
    }
    
    public void inscribirNacimiento(){
        try{
            System.out.println("=== 2. INSCRIBIR NACIMIENTO (RECIÉN NACIDO) ===");
            System.out.println("Ingrese la Región de nacimiento: ");
            String region = lector.readLine();
            
            System.out.println("Ingrese Primer Nombre del recién nacido: ");
            String pNombre = lector.readLine();
            System.out.println("Ingrese Segundo Nombre (Deje en blanco si no tiene): ");
            String sNombre = lector.readLine();
            System.out.println("Ingrese Primer Apellido: ");
            String pApellido = lector.readLine();
            System.out.println("Ingrese Segundo Apellido: ");
            String sApellido = lector.readLine();
            System.out.println("Ingrese Sexo (Masculino/Femenino): ");
            String sexo = lector.readLine();
    
            System.out.println("Día de Nacimiento: ");
            int dia = Integer.parseInt(lector.readLine()); 
            System.out.println("Mes de Nacimiento: ");
            int mes = Integer.parseInt(lector.readLine());
            System.out.println("Año de Nacimiento: ");
            int año = Integer.parseInt(lector.readLine());
            
            System.out.println("\n--- Datos de los Progenitores ---");
            System.out.println("Ingrese RUT del Padre (Deje en blanco si no aplica): ");
            String rutPadre = lector.readLine();
            System.out.println("Ingrese RUT de la Madre (Deje en blanco si no aplica): ");
            String rutMadre = lector.readLine();
        
            String rutGenerado = sistema.registrarNacimiento(region, pNombre, sNombre, pApellido, sApellido, sexo, dia, mes, año, rutPadre, rutMadre); 
            
            if(rutGenerado != null){
                System.out.println("Nacimiento registrado exitosamente"); 
                System.out.println("-> *** RUT ASIGNADO AL RECIÉN NACIDO: " + rutGenerado + " ***");
                
            }
            else{
                System.out.println("-> Error: No se pudo registrar. Verifique los datos ingresados"); 
            }
        }catch(NumberFormatException e){
            System.out.println("-> Error: Debe ingresar números válidos para la fecha.");
        }catch(IOException e){
            System.out.println("-> Ocurrió un error en la lectura de datos: " + e.getMessage());
        }
    }
    
    /*
    AQUI PONER METODO DE MATRIMONIO
    
    */
    
    public void RegistrarDefuncion(){
        try {
            System.out.println("\n--- 4. REGISTRAR DEFUNCIÓN ---");
            System.out.println("Ingrese la Región en la que está inscrito el ciudadano: ");
            String region = lector.readLine();
            
            System.out.println("Ingrese el RUT del fallecido: ");
            String rut = lector.readLine();

            Persona fallecido = sistema.buscarPersona(region, rut);
            
            if (fallecido != null){
                Persona conyuge = fallecido.getConyuge();
                if(conyuge != null){
                    conyuge.setEstadoCivil("Viudo/a");
                    conyuge.setConyuge(null);
                    System.out.println("Notificacion: El estado civil de " + conyuge.getPrimerNombre() + " " + conyuge.getPrimerApellido() + " ha sido actualizado a viudo/a");
                }
                
                boolean exito = sistema.eliminarPersona(region, rut);
                if (exito){
                    System.out.println("Defuncion registrada correctamente");                    
                } else {
                    System.out.println("Error al intentar remover al ciudadano");
                }
            } else {
                System.out.println("Error: No se encontro ningun ciudadano con el RUT " + rut + " en la region de " + region + ".");
            }
        } catch (IOException e){
            System.out.println("Error al registrar la defuncion: " + e.getMessage());
        }
    }
    
    /*
    AQUI PONER METODO DE BUSCAR CIUDADANO
    
    */
    
    /*
    AQUI PONER METODO DE EMITIR CERTIFICADOS
    
    */
}
