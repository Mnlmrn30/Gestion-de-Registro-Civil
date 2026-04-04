package com.registrocivil.main;

import com.registrocivil.logica.GestionSistema;
import com.registrocivil.window.VentanaMenu;

public class Main {
    public static void main(String[] arr){
        GestionSistema sistemaTotal = new GestionSistema();
        
        VentanaMenu menu = new VentanaMenu(sistemaTotal);
        menu.setVisible(true);
    }
}
