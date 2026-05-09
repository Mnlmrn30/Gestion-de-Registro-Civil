package com.registrocivil.console;

import com.registrocivil.logica.*; 
import java.io.*; 

// Superclase de los menus en consola. 
public abstract class MenuBase {
    protected GestionSistema sistema; 
    protected BufferedReader lector; 
    
    public MenuBase(GestionSistema sistema, BufferedReader lector){
        this.sistema = sistema; 
        this.lector = lector; 
    }
    
    public abstract void iniciar() throws Exception;
}
