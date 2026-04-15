
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

            String region = seleccionarRegion();
            System.out.println("Ingrese RUT (ej: 12345678-9): ");
            String rut = lector.readLine();
            validarFormatoRut(rut); 
            if(sistema.busquedaGlobalPersona(rut) != null){
                System.out.println("Error. Ya existe una persona registrada con ese rut");
                return; 
            }
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
            validarFecha(dia, mes, anyo);
            
            boolean exito = sistema.registrarPersona(region, rut, primerNombre, segundoNombre, primerApellido, segundoApellido, sexo, dia, mes, anyo);
            
            if (exito) {
                System.out.println("Ciudadano registrado con éxito en la región de " + region + "!");
            } else {
                System.out.println("Error: No se pudo registrar.");
            }
        } catch (com.registrocivil.logica.RutInvalidoException e){
            System.out.println("Error de validación: " + e.getMessage());
        } catch (com.registrocivil.logica.FechaInvalidaException e){
            System.out.println("Error de validación: "+ e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, ingrese números válidos para la fecha de nacimiento.");
        } catch (IOException e) {
            System.out.println("Error al ingresar los datos: " + e.getMessage());
        }
    }
    
    public void inscribirNacimiento(){
        try{
            System.out.println("=== 2. INSCRIBIR NACIMIENTO (RECIÉN NACIDO) ===");
            String region = seleccionarRegion();
            
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
            int anio = Integer.parseInt(lector.readLine());
            validarFecha(dia, mes, anio); 
            
            System.out.println("\n--- Datos de los Progenitores ---");
            System.out.println("Ingrese RUT del Padre (Deje en blanco si no aplica): ");
            String rutPadre = lector.readLine();
            validarFormatoRut(rutPadre); 
            System.out.println("Ingrese RUT de la Madre (Deje en blanco si no aplica): ");
            String rutMadre = lector.readLine();
            validarFormatoRut(rutMadre);
                    
            String rutGenerado = sistema.registrarNacimiento(region, pNombre, sNombre, pApellido, sApellido, sexo, dia, mes, anio, rutPadre, rutMadre); 
            
            if(rutGenerado != null){
                System.out.println("Nacimiento registrado exitosamente"); 
                System.out.println("*** RUT ASIGNADO AL RECIÉN NACIDO: " + rutGenerado + " ***");
                
            }
            else{
                System.out.println("Error: No se pudo registrar. Verifique los datos ingresados"); 
            }
        }catch(com.registrocivil.logica.RutInvalidoException e){
            System.out.println("Error de validación: " + e.getMessage());
        }catch(com.registrocivil.logica.FechaInvalidaException e){
            System.out.println("Error de validación: "+ e.getMessage());
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
            validarFormatoRut(rut1); 
            System.out.println("Ingrese RUT del segundo contrayente: ");
            String rut2 = lector.readLine();
            validarFormatoRut(rut2);
            
            boolean exito = sistema.registrarMatrimonio(rut1, rut2); 
            if(exito){
                System.out.println("\n¡ÉXITO! El matrimonio se ha registrado en el sistema.");
                System.out.println("*** El estado civil de ambos ciudadanos ha cambiado a 'Casado/a'. *** ");
        } else {
                System.out.println("\nERROR: No se pudo registrar el matrimonio.");
                System.out.println("Posibles causas: Uno de los RUT no existe, ingresó el mismo RUT dos veces, o uno de los ciudadanos ya está casado.");
            }
        }catch(com.registrocivil.logica.RutInvalidoException e){
            System.out.println("Error de validación: " + e.getMessage());
        }catch(Exception e){
            System.out.println("Error inesperado al procesar el matrimonio: "+ e.getMessage());
        }
    }
    
    public void RegistrarDefuncion(){
        try {
            System.out.println("\n--- 4. REGISTRAR DEFUNCIÓN ---");
            System.out.println("Ingrese el RUT del fallecido: ");
            String rut = lector.readLine();
            validarFormatoRut(rut);
            Persona fallecido = sistema.busquedaGlobalPersona(rut);
            if(fallecido == null) {
                System.out.println("Error: No se encontró ningún ciudadano con el RUT ingresado: " + rut);
                return; 
            }
            
            if("Fallecido".equalsIgnoreCase(fallecido.getEstadoVital())){
                System.out.println("Aviso: Este ciudadano ya se encuentra registrado como fallecido"); 
                return; 
            }
            
            fallecido.setEstadoVital("Fallecido");
            
            Persona conyuge = fallecido.getConyuge();
            if(conyuge != null){
                conyuge.setEstadoCivil("Viudo/a");
                conyuge.setConyuge(null);
                System.out.println("Notificacion: El estado civil de " + conyuge.getPrimerNombre() + " " + conyuge.getPrimerApellido() + " ha sido actualizado a viudo/a");
            }
            System.out.println("Defuncion registrada correctamente");                    
        } catch(com.registrocivil.logica.RutInvalidoException e){
            System.out.println("Error de validación: " + e.getMessage());
        }catch (IOException e){
            System.out.println("Error al registrar la defuncion: " + e.getMessage());
        }
    }
    
    public void buscarCiudadano(){
        try{
            System.out.println("\n=== 5. BUSCAR CIUDADANO (BÚSQUEDA GLOBAL) ===");
            System.out.println("Ingrese el RUT a buscar (ej: 12345678-9): ");
            String rut = lector.readLine();
            validarFormatoRut(rut);
            
            Persona personaEncontrada = sistema.busquedaGlobalPersona(rut);
            String regionRegistro = sistema.obtenerRegionDePersona(rut);
            
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
                System.out.println("Región de Registro: " + regionRegistro); 
                if(!personaEncontrada.getEstadoVital().equals("Fallecido")){
                    System.out.println("Estado Civil: " + personaEncontrada.getEstadoCivil());
                }
                System.out.println("Estado Vital: " + personaEncontrada.getEstadoVital());
     
                if(personaEncontrada.getConyuge() != null){
                System.out.println("Cónyuge: " + personaEncontrada.getConyuge().getPrimerNombre() + " " + 
                                       personaEncontrada.getConyuge().getPrimerApellido() + 
                                       " (RUT: " + personaEncontrada.getConyuge().getRut() + ")");
                }
            System.out.println("------------------------------------");            
            }else{
                System.out.println("Error: No se ha encontrado a ningún ciudadano con el RUT "+ rut+ " a nivel nacional."); 
            }    
        } catch(com.registrocivil.logica.RutInvalidoException e){
            System.out.println("Error de validación: "+ e.getMessage());
        } 
        catch (IOException e){
            System.out.println("Ocurrió un error en la lectura de los datos: " + e.getMessage()); 
        }
    }
    
    public void emitirCertificado(){
        try{
            System.out.println("\n=== 6. EMITIR CERTIFICADOS ===");
            System.out.println("Ingrese el RUT del ciudadano para emitir el documento: ");
            String rut = lector.readLine();
            validarFormatoRut(rut); 
            Persona persona = sistema.busquedaGlobalPersona(rut); 
            String regionRegistrado = sistema.obtenerRegionDePersona(rut);
            
            if(persona != null){
                System.out.println("Seleccione el certificado que desea emitir:");
                System.out.println("1. Certificado de Nacimiento / Antecedentes Básicos");
                System.out.println("2. Certificado de Matrimonio");
                System.out.println("Opción: ");
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
                    System.out.println("REGIÓN : "+ regionRegistrado);
                
                    if(persona.getPadre() != null){
                        System.out.println("\n---------------------------------------------------------");
                        System.out.println("RUT DEL PADRE           : " + persona.getPadre().getRut());
                        System.out.println("NOMBRES DEL PADRE         : " + persona.getPadre().getPrimerNombre() + " " + persona.getPadre().getSegundoNombre());
                        System.out.println("APELLIDOS DEL PADRE       : " + persona.getPadre().getPrimerApellido() + " " + persona.getPadre().getSegundoApellido());
                    }
                    if(persona.getMadre() != null){
                        System.out.println("\n---------------------------------------------------------");
                        System.out.println("RUT DE LA MADRE            : " + persona.getMadre().getRut());
                        System.out.println("NOMBRES DE LA MADRE         : " + persona.getMadre().getPrimerNombre() + " " + persona.getMadre().getSegundoNombre());
                        System.out.println("APELLIDOS DE LA MADRE       : " + persona.getMadre().getPrimerApellido() + " " + persona.getMadre().getSegundoApellido()); 
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
        }catch(com.registrocivil.logica.RutInvalidoException e){
            System.out.println("Error de validación: " + e.getMessage());
        }
        catch (IOException e){
            System.out.println("Error al procesar la solicitud: " + e.getMessage());
        }
    }
    
    public void editarCiudadano() throws Exception{
        try{
            System.out.println("\n--- EDITAR CIUDADANO ---");
            System.out.println("Ingrese el RUT del ciudadano a editar: ");
            String rut = lector.readLine();
            validarFormatoRut(rut);
            Persona p = sistema.busquedaGlobalPersona(rut); 
            if(p == null) {
                System.out.println("Error: No se encontró ningún ciudadano con el RUT ingresado."); 
                return; 
            }
            System.out.println("Ciudadano encontrado: " + p.getPrimerNombre() + " " + p.getPrimerApellido());
            System.out.println("(Presione 'Enter' sin escribir nada si NO desea modificar un campo)");
        
            System.out.println("Nuevo Primer Nombre [" + p.getPrimerNombre() + "]: ");
            String pNombre = lector.readLine();
            if (pNombre.trim().isEmpty()) pNombre = p.getPrimerNombre();

            System.out.println("Nuevo Segundo Nombre [" + p.getSegundoNombre() + "]: ");
            String sNombre = lector.readLine();
            if (sNombre.trim().isEmpty()) sNombre = p.getSegundoNombre();

            System.out.println("Nuevo Primer Apellido [" + p.getPrimerApellido() + "]: ");
            String pApellido = lector.readLine();
            if (pApellido.trim().isEmpty()) pApellido = p.getPrimerApellido();

            System.out.println("Nuevo Segundo Apellido [" + p.getSegundoApellido() + "]: ");
            String sApellido = lector.readLine();
            if (sApellido.trim().isEmpty()) sApellido = p.getSegundoApellido();

            System.out.println("Nuevo Sexo [" + p.getSexo() + "]: ");
            String sexo = lector.readLine();
            if (sexo.trim().isEmpty()) sexo = p.getSexo();

            System.out.println("Nuevo Día Nac. [" + p.getDiaNacimiento() + "]: ");
            String diaStr = lector.readLine();
            int dia = diaStr.trim().isEmpty() ? p.getDiaNacimiento() : Integer.parseInt(diaStr);

            System.out.println("Nuevo Mes Nac. [" + p.getMesNacimiento() + "]: ");
            String mesStr = lector.readLine();
            int mes = mesStr.trim().isEmpty() ? p.getMesNacimiento() : Integer.parseInt(mesStr);

            System.out.println("Nuevo Año Nac. [" + p.getAñoNacimiento() + "]: ");
            String anioStr = lector.readLine();
            int anio = anioStr.trim().isEmpty() ? p.getAñoNacimiento() : Integer.parseInt(anioStr);
        
            validarFecha(dia, mes, anio);
        
            sistema.editarPersona(rut, pNombre, sNombre, pApellido, sApellido, sexo, dia, mes, anio); 

            System.out.println("\n-- ASIGNACIÓN DE PADRES --");
            System.out.println("RUT del Padre (deje en blanco para no cambiar): ");
            String rutPadre = lector.readLine();
            // Preguntará si se desea agregar a la persona padres, para que personas ingresadas por registro general puedan vincularse a otro objeto Persona 
            // padre o madre. 
            // Se revisa si el padre existe en los datos para poder asignarselo a la persona en edición (hijo) 
            if (!rutPadre.trim().isEmpty()){
                validarFormatoRut(rutPadre);
                Persona padre = sistema.busquedaGlobalPersona(rutPadre);
                if (padre != null) {
                    p.setPadre(padre);
                    System.out.println("Padre asignado: " + padre.getPrimerNombre());
                } else {
                    System.out.println("No se encontró padre con ese RUT en el sistema.");
                }
            }
            // Se realizo lo mismo que lo anterior solo que en este caso se pregunta por la madre. 
            System.out.println("RUT de la Madre (deje en blanco para no cambiar): ");
            String rutMadre = lector.readLine();
            if (!rutMadre.trim().isEmpty()) {
                validarFormatoRut(rutMadre);
                Persona madre = sistema.busquedaGlobalPersona(rutMadre);
                if (madre != null) {
                    p.setMadre(madre);
                    System.out.println("Madre asignada: " + madre.getPrimerNombre());
                } else {
                    System.out.println("No se encontró madre con ese RUT en el sistema.");
                }
            }

            System.out.println("\n¡Ciudadano actualizado con éxito!");
        } catch(com.registrocivil.logica.RutInvalidoException e){
            System.out.println("Error de validación: "+ e.getMessage());
        } catch(com.registrocivil.logica.FechaInvalidaException e){
            System.out.println("Error de validación: "+ e.getMessage());
        } catch(NumberFormatException e){
            System.out.println("Error: Debes ingresar un número válido para la fechas"); 
        } catch(Exception e){
            System.out.println("Error inesperado" + e.getMessage());
        }
    }
    
    public void eliminarCiudadano(){
        try {
            System.out.println("\n--- 8. ELIMINAR REGISTRO ---");
            System.out.println("Ingrese el RUT del ciudadano a eliminar: ");
            String rut = lector.readLine();
            validarFormatoRut(rut);
            
            Persona p = sistema.busquedaGlobalPersona(rut);

            if (p == null) {
                System.out.println("Error: No se encontró ningún ciudadano con el RUT " + rut);
                return;
            }

    
            System.out.println("Ciudadano encontrado: " + p.getPrimerNombre() + " " + p.getPrimerApellido() + " | RUT: " + p.getRut());
            System.out.println("¡ADVERTENCIA! Esta acción borrará el registro del sistema.");
            System.out.println("¿Está seguro que desea eliminar a este ciudadano? (S/N): ");
            String confirmacion = lector.readLine();

            if (confirmacion.equalsIgnoreCase("S")) {
                
                // buscamos la region del ciudadano a eliminar de forma automatica. 
                String nombreRegion = "";
               
                for (com.registrocivil.logica.Region r : sistema.getRegiones().values()) {
                    if (r.getCiudadanos().contains(p)) {
                        nombreRegion = r.getNombre();
                        break;
                    }
                }
                if (!nombreRegion.isEmpty()) {
                    boolean exito = sistema.eliminarPersona(nombreRegion, rut);
                    if (exito) {
                        System.out.println("Registro eliminado exitosamente del sistema.");
                    } else {
                        System.out.println("Ocurrió un error al intentar eliminar el registro.");
                    }
                }
                
            } else {
                System.out.println("Operación cancelada.");
            }

        } catch(com.registrocivil.logica.RutInvalidoException e){
            System.out.println("Error de validación: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error al intentar eliminar: " + e.getMessage());
        }
    }
    
    
    private void validarFecha(int dia, int mes, int anio) throws com.registrocivil.logica.FechaInvalidaException{
        if(anio < 0|| anio > 2026){
            throw new com.registrocivil.logica.FechaInvalidaException("El año debe estar entre 0 y 2026,"); 
        }
        if(mes < 1 || mes > 12){
            throw new com.registrocivil.logica.FechaInvalidaException("El mes debe estar entre 1 y 12.");
        }
        int diasMaximos = 31; 
        if(dia == 4 || dia == 6 || dia == 9 || dia == 11) diasMaximos = 30; 
        if(dia == 2) diasMaximos = 29; 
        if(dia < 1 || dia > diasMaximos){
            throw new com.registrocivil.logica.FechaInvalidaException("El día " + dia + "no es válido para el mes "); 
        }
    }
    
    private void validarFormatoRut(String rut) throws com.registrocivil.logica.RutInvalidoException{
        if(!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")){
            throw new com.registrocivil.logica.RutInvalidoException("Formato de RUT incorrecto. Debe ser sin puntos y con guión (ej. 12345678-9)");
        }
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
            } catch (IOException e) {
                System.out.println("Error de lectura en la consola.");
            }
        }
    }
    
    
}
