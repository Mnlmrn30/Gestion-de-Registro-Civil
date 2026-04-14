
package com.registrocivil.console;


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
        throw new UnsupportedOperationException("Not supported yet."); 
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
            System.out.println("Ingrese Año de Nacimiento: ");
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
                System.out.println("*** RUT ASIGNADO AL RECIÉN NACIDO: " + rutGenerado + " ***");
                
            }
            else{
                System.out.println("Error: No se pudo registrar. Verifique los datos ingresados"); 
            }
        }catch(NumberFormatException e){
            System.out.println("Error: Debe ingresar números válidos para la fecha.");
        }catch(IOException e){
            System.out.println("Ocurrió un error en la lectura de datos: " + e.getMessage());
        }
    }
    
    public void inscribirMatrimonio(){
        try{
            System.out.println("\n=== 3. INSCRIBIR MATRIMONIO ===");
            System.out.println("Nota: Ambos ciudadanos deben estar previamente registrados y ser solteros/viudos.");
        
            System.out.println("Ingrese RUT del primer contrayente: ");
            String rut1 = lector.readLine();
            System.out.println("Ingrese RUT del segundo contrayente: ");
            String rut2 = lector.readLine();
            
            boolean exito = sistema.registrarMatrimonio(rut1, rut2); 
            if(exito){
                System.out.println("\n¡ÉXITO! El matrimonio se ha registrado en el sistema.");
                System.out.println("*** El estado civil de ambos ciudadanos ha cambiado a 'Casado/a'. *** ");
        } else {
                System.out.println("\nERROR: No se pudo registrar el matrimonio.");
                System.out.println("Posibles causas: Uno de los RUT no existe, ingresó el mismo RUT dos veces, o uno de los ciudadanos ya está casado.");
            }
        }
        catch(Exception e){
            System.out.println("Error inesperado al procesar el matrimonio: "+ e.getMessage());
        }
    }
    
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
    
    public void buscarCiudadano(){
        try{
            System.out.println("\n=== 5. BUSCAR CIUDADANO (BÚSQUEDA GLOBAL) ===");
            System.out.println("Ingrese el RUT a buscar (ej: 12.345.678-9): ");
            String rut = lector.readLine();
            
            Persona personaEncontrada = sistema.busquedaGlobalPersona(rut); 
            
            if(personaEncontrada != null){
                System.out.println("\n--- RESULTADO DE LA BÚSQUEDA ---");
                System.out.println("RUT: " + personaEncontrada.getRut());
                System.out.println("Nombre Completo: " + personaEncontrada.getPrimerNombre() + " " + 
                                   personaEncontrada.getSegundoNombre() + " " + 
                                   personaEncontrada.getPrimerApellido() + " " + 
                                   personaEncontrada.getSegundoApellido());
                System.out.println("Sexo: " + personaEncontrada.getSexo());
                System.out.println("Fecha de Nacimiento: " + personaEncontrada.getDiaNacimiento() + "/" + 
                                   personaEncontrada.getMesNacimiento() + "/" + 
                                   personaEncontrada.getAñoNacimiento());
                System.out.println("Estado Civil: " + personaEncontrada.getEstadoCivil());
     
                if(personaEncontrada.getConyuge() != null){
                System.out.println("Cónyuge: " + personaEncontrada.getConyuge().getPrimerNombre() + " " + 
                                       personaEncontrada.getConyuge().getPrimerApellido() + 
                                       " (RUT: " + personaEncontrada.getConyuge().getRut() + ")");
                }
            System.out.println("------------------------------------");            
            }else{
                System.out.println("Error: No se ha encontrado a ningún ciudadano con el RUT "+ rut+ "a nivel nacional."); 
            }    
        } catch (IOException e){
            System.out.println("Ocurrió un error en la lectura de los datos: " + e.getMessage()); 
        }
    }
    
    public void emitirCertificado(){
        try{
            System.out.println("\n=== 6. EMITIR CERTIFICADOS ===");
            System.out.println("Ingrese el RUT del ciudadano para emitir el documento: ");
            String rut = lector.readLine();
            Persona persona = sistema.busquedaGlobalPersona(rut); 
            
            if(persona != null){
                System.out.println("Seleccione el certificado que desea emitir:");
                System.out.println("1. Certificado de Nacimiento / Antecedentes Básicos");
                System.out.println("2. Certificado de Matrimonio");
                System.out.print("Opción: ");
                String opcion = lector.readLine();
            
            
                System.out.println("\n=======================================================");
                System.out.println("=================REPÚBLICA DE CHILE===================");
                System.out.println("=====SERVICIO DE REGISTRO CIVIL E IDENTIFICACIÓN=====");
                System.out.println("=======================================================");
            
                if(opcion.equals("1")){
                    System.out.println("               CERTIFICADO DE NACIMIENTO");
                    System.out.println("-------------------------------------------------------");
                    System.out.println("RUT             : " + persona.getRut());
                    System.out.println("NOMBRES         : " + persona.getPrimerNombre() + " " + persona.getSegundoNombre());
                    System.out.println("APELLIDOS       : " + persona.getPrimerApellido() + " " + persona.getSegundoApellido());
                    System.out.println("FECHA NACIMIENTO: " + persona.getDiaNacimiento() + "/" + persona.getMesNacimiento() + "/" + persona.getAñoNacimiento());
                    System.out.println("SEXO            : " + persona.getSexo());
                
                    if(persona.getPadre() != null){
                        System.out.println("\n---------------------------------------------------------");
                        System.out.println("RUT             : " + persona.getPadre().getRut());
                        System.out.println("NOMBRES         : " + persona.getPadre().getPrimerNombre() + " " + persona.getPadre().getSegundoNombre());
                        System.out.println("APELLIDOS       : " + persona.getPadre().getPrimerApellido() + " " + persona.getPadre().getSegundoApellido());
                    }
                    if(persona.getMadre() != null){
                        System.out.println("\n---------------------------------------------------------");
                        System.out.println("RUT             : " + persona.getMadre().getRut());
                        System.out.println("NOMBRES         : " + persona.getMadre().getPrimerNombre() + " " + persona.getMadre().getSegundoNombre());
                        System.out.println("APELLIDOS       : " + persona.getMadre().getPrimerApellido() + " " + persona.getMadre().getSegundoApellido()); 
                    }
                
                } else if(opcion.equals("2")){
                    System.out.println("               CERTIFICADO DE MATRIMONIO               ");
                    System.out.println("-------------------------------------------------------");
                    if (persona.getEstadoCivil().equals("Casado/a") && persona.getConyuge() != null) {
                        System.out.println("RUT CONTRAYENTE 1   : " + persona.getRut());
                        System.out.println("NOMBRE CONTRAYENTE 1: " + persona.getPrimerNombre() + " " + persona.getPrimerApellido());
                        System.out.println("\nRUT CONTRAYENTE 2   : " + persona.getConyuge().getRut());
                        System.out.println("NOMBRE CONTRAYENTE 2: " + persona.getConyuge().getPrimerNombre() + " " + persona.getConyuge().getPrimerApellido());
                        System.out.println("\nESTADO DEL VÍNCULO  : VIGENTE");
                    } else {
                        System.out.println("\n *** El ciudadano consultado no registra un matrimonio vigente. *** ");
                    }
                } else{
                    System.out.println("Opción inválida. Transacción anulada.");
                }
                System.out.println("======================================================="); 
            } else{
                System.out.println("Error: No se encontró al ciudadano. No es posible emitir certificado"); 
            }
        }catch (IOException e){
            System.out.println("Error al procesar la solicitud: " + e.getMessage());
        }
    }
    
    
}
