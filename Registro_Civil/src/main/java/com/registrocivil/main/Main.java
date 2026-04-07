package com.registrocivil.main;

import com.registrocivil.logica.GestionSistema;
import com.registrocivil.window.VentanaMenu;
import java.io.*;

public class Main {
    public static void main(String[] arr)throws IOException{
        GestionSistema sistemaTotal = new GestionSistema();
        
        // Inicializar un lector para que el usuario escoga el modo, ya sea consola o ventana. 
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("SISTEMA DE REGISTRO CIVIL"); 
        System.out.println("Seleccione el modo de inicio:"); 
        System.out.println("1. Consola"); 
        System.out.println("2. Ventana"); 
        System.out.println("Ingrese una opción (1 o 2): ");
        
        try {
            String opcion = lector.readLine();
            if(opcion.equals("1")){
                System.out.println("Iniciando modo consola");
                // Se llamara una clase la cual inicializa el programa en modo consola. 
            }
            else if (opcion.equals("2")){
                VentanaMenu menu = new VentanaMenu(sistemaTotal); // Se inicia el sistema en modo ventana.
                menu.setVisible(true); 
            }
            else{
                System.out.println("Opción no válida. Cerrando sistema"); 
            }
        } catch(IOException e){
            System.out.println("Error al leer la entrada del usuario " + e.getMessage());
        }
    }
}
