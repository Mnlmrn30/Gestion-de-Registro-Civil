package com.registrocivil.console;

import com.registrocivil.logica.*; 
import java.io.*;
// HANS PAZ BONILLA -- REGISTRO NACIMIENTO Y VINCULO MATRIMONIO

public class GestorConsolaCiudadano1 {
    private GestionSistema sistema; 
    private BufferedReader lector; 
    
    public GestorConsolaCiudadano1(GestionSistema sistema, BufferedReader lector){
        this.sistema = sistema; 
        this.lector = lector; 
    }
    
    public void inscribirNacimiento(){
        try{
            System.out.println("=== 2. INSCRIBIR NACIMIENTO ===");
            System.out.print("Ingrese la Región: ");
            String region = lector.readLine();
            System.out.print("Ingrese RUT del recién nacido (Ej: 24.123.456-7): ");
            String rut = lector.readLine();
            System.out.print("Ingrese Primer Nombre: ");
            String pNombre = lector.readLine();
            System.out.print("Ingrese Segundo Nombre (Deje en blanco si no tiene): ");
            String sNombre = lector.readLine();
            System.out.print("Ingrese Primer Apellido: ");
            String pApellido = lector.readLine();
            System.out.print("Ingrese Segundo Apellido: ");
            String sApellido = lector.readLine();
            System.out.print("Ingrese Sexo (Masculino/Femenino): ");
            String sexo = lector.readLine();
    
            System.out.println("Día de Nacimiento: ");
            int dia = Integer.parseInt(lector.readLine()); 
            System.out.println("Mes de Nacimiento: ");
            int mes = Integer.parseInt(lector.readLine());
            System.out.println("Año de Nacimiento: ");
            int año = Integer.parseInt(lector.readLine());
        
            boolean exito = sistema.registrarPersona(region, rut, pNombre, sNombre, pApellido, sApellido, sexo, dia, mes, año); 
            if(exito){
                System.out.println("-> ¡Nacimiento registrado exitosamente en " + region + "!"); 
            }
            else{
                System.out.println("-> Error: No se pudo registrar. Verifique los datos ingresados"); 
            }
        }catch(NumberFormatException e){
            System.out.println("-> Error: Debe ingresar números válidos para la fecha.");
        }catch(Exception e){
            System.out.println("-> Ocurrió un error en la lectura de datos: " + e.getMessage());
        }
    }

    
}
