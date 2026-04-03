package com.registrocivil.main;

import com.registrocivil.logica.GestionSistema;
import com.registrocivil.window.VentanaPrincipal;

public class Main {
    public static void main(String[] arr){
        GestionSistema sistemaTotal = new GestionSistema();
        
        VentanaPrincipal vista = new VentanaPrincipal(sistemaTotal);
        vista.setVisible(true);
    }
}
