
package com.registrocivil.console;

import com.registrocivil.logica.*; 
import java.io.*; 

public class SubMenuCiudadanos extends MenuBase{
    
    public SubMenuCiudadanos(GestionSistema sistema, BufferedReader lector){
        super(sistema, lector); 
    }
    /*
    Menu para el gestion de ciudadanos, aqui se desarolla todo lo importante del codigo, donde se registran las personas,
    se editan, se elimina, se vinculan, entre otras funcionalidades.
    */
    @Override
    public void iniciar() throws Exception{
        boolean volver = false; 
        while(!volver){
            System.out.println("==== GESTIÓN DE CIUDADANOS ==== "); 
            System.out.println("1. Inscribir Ciudadano"); //Registro General 
            System.out.println("2. Inscribir Nacimiento"); //Registro nacimiento 
            System.out.println("3. Inscribir Matrimonio"); //Registro Matrimonio
            System.out.println("4. Registrar Defuncion"); //Registro Defuncion 
            System.out.println("5. Buscar ciudadano"); //Buscar Ciudadano
            System.out.println("6. Emitir Certificados"); //Emitir Certificados 
            System.out.println("7. Editar Registro de Ciudadano"); // Editará el registro de un ciudadano en caso de error de escritura o falta de datos. 
            System.out.println("8. Eliminar Registro"); // Eliminar registro en caso de error administrativo. 
            System.out.println("9. Volver Menú Principal");
            System.out.println("Seleccione Opción: "); 
            
            String op = lector.readLine(); 
            GestorConsolaCiudadano gestor = new GestorConsolaCiudadano(sistema, lector);
            
            switch(op){
                case "1":
                    gestor.registroGeneral();
                    break; 
                case "2":
                    gestor.inscribirNacimiento();
                    break; 
                case "3": 
                    gestor.inscribirMatrimonio();
                    break;
                case "4":
                    gestor.RegistrarDefuncion();
                    break; 
                case "5":
                    gestor.buscarCiudadano();
                    break; 
                case "6":
                    gestor.emitirCertificado(); 
                    break; 
                case "7":
                    gestor.editarCiudadano(); 
                    break; 
                case "8": 
                    gestor.eliminarCiudadano();
                    break;
                case "9": 
                    volver = true; 
                    break;
                default:
                    System.out.println("Opción Invalida");
            }
        }
    }
}
