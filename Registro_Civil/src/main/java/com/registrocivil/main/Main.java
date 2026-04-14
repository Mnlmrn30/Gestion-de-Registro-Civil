package com.registrocivil.main;

import com.registrocivil.logica.GestionSistema;
import com.registrocivil.console.*;
import java.io.*;

public class Main {
    public static void main(String[] arr) throws IOException {
        
        GestionSistema sistemaTotal = new GestionSistema();
        
        
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("--- CARGANDO DATOS INICIALES ---"); 
        sistemaTotal.cargarDatosDesdeBD();
        
        System.out.println("===============================");
        System.out.println("   SISTEMA DE REGISTRO CIVIL   ");
        System.out.println("===============================");
        System.out.println("Seleccione el modo de inicio:"); 
        System.out.println("1. Consola (Texto)"); 
        System.out.println("2. Ventana (Interfaz Gráfica)"); 
        System.out.print("Ingrese una opción (1 o 2): ");
        
        try {
            String opcion = lector.readLine();
            
            if(opcion.equals("1")) {
                System.out.println("\nIniciando modo consola...");
                MenuConsola consola = new MenuConsola(sistemaTotal); 
                consola.iniciar();
                System.out.println("\n[SISTEMA] GUARDANDO DATOS EN EL SISTEMA. ");
                sistemaTotal.guardarDatosEnBD();
            }
            else if (opcion.equals("2")) {
                System.out.println("\nIniciando modo ventana...");
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        // new VentanaMenu(sistemaTotal).setVisible(true);
                    }
                });
            }
            else {
                System.out.println("Opción no válida. Cerrando sistema."); 
            }
        } catch(IOException e){
            System.out.println("Error al leer la entrada del usuario: " + e.getMessage());
        }
    }
}
