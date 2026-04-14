
package com.registrocivil.console;

import com.registrocivil.logica.GestionSistema;
import com.registrocivil.logica.Persona;
import java.io.BufferedReader;
// MANUEL MORENO GALLEGUILLOS --- REGISTRO GENERAL Y REGISTRAR DEFUNCION

public class GestorConsolaCiudadano2 {
    
    public void registroGeneral(GestionSistema sistema, BufferedReader lector){
        try{
            System.out.println("\n 1. INSCRIBIR CIUDADANO (REGISTRO GENERAL)");
            System.out.print("Ingrese la Región (ej: Coquimbo): ");
            String region = lector.readLine();
            System.out.print("Ingrese RUT (ej: 12.345.678-9): ");
            String rut = lector.readLine();
            System.out.print("Ingrese Primer Nombre: ");
            String primerNombre = lector.readLine();
            System.out.print("Ingrese Segundo Nombre: ");
            String segundoNombre = lector.readLine();
            System.out.print("Ingrese Primer Apellido: ");
            String primerApellido = lector.readLine();
            System.out.print("Ingrese Segundo Apellido: ");
            String segundoApellido = lector.readLine();
            System.out.print("Ingrese Sexo (Masculino/Femenino/Otro): ");
            String sexo = lector.readLine();
            System.out.print("Ingrese Día de Nacimiento (número): ");
            int dia = Integer.parseInt(lector.readLine());
            System.out.print("Ingrese Mes de Nacimiento (número): ");
            int mes = Integer.parseInt(lector.readLine());
            System.out.print("Ingrese Año de Nacimiento (número): ");
            int anyo = Integer.parseInt(lector.readLine());
            
            boolean exito = sistema.registrarPersona(region, rut, primerNombre, segundoNombre, primerApellido, segundoApellido, sexo, dia, mes, anyo);
            
            if (exito) {
                System.out.println("Ciudadano registrado con éxito en la región de " + region + "!");
            } else {
                System.out.println("Error: No se pudo registrar.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, ingrese números válidos para la fecha de nacimiento.");
        } catch (Exception e) {
            System.out.println("Error al ingresar los datos: " + e.getMessage());
        }
    }
    
    public void RegistrarDefuncion(GestionSistema sistema, BufferedReader lector){
        try {
            System.out.println("\n--- 4. REGISTRAR DEFUNCIÓN ---");
            System.out.print("Ingrese la Región en la que está inscrito el ciudadano: ");
            String region = lector.readLine();
            
            System.out.print("Ingrese el RUT del fallecido: ");
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
        } catch (Exception e){
            System.out.println("Error al registrar la defuncion: " + e.getMessage());
        }
    }
}
