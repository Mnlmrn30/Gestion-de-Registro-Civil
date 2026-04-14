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
            System.out.println("=== 2. INSCRIBIR NACIMIENTO (RECIÉN NACIDO) ===");
            System.out.print("Ingrese la Región de nacimiento: ");
            String region = lector.readLine();
            
            System.out.print("Ingrese Primer Nombre del recién nacido: ");
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
            
            System.out.println("\n--- Datos de los Progenitores ---");
            System.out.print("Ingrese RUT del Padre (Deje en blanco si no aplica): ");
            String rutPadre = lector.readLine();
            System.out.print("Ingrese RUT de la Madre (Deje en blanco si no aplica): ");
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
        }catch(Exception e){
            System.out.println("-> Ocurrió un error en la lectura de datos: " + e.getMessage());
        }
    }

    
}
