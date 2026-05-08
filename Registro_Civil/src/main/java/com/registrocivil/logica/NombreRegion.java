package com.registrocivil.logica;

public enum NombreRegion {
    ARICA_Y_PARINACOTA("Arica y Parinacota"),
    TARAPACA("Tarapaca"),
    ANTOFAGASTA("Antofagasta"),
    ATACAMA("Atacama"),
    COQUIMBO("Coquimbo"),
    VALPARAISO("Valparaiso"),
    METROPOLITANA("Metropolitana"),
    OHIGGINS("O'Higgins"),
    MAULE("Maule"),
    NUBLE("Nuble"),
    BIOBIO("Biobio"),
    LA_ARAUCANIA("La Araucania"),
    LOS_RIOS("Los Rios"),
    LOS_LAGOS("Los Lagos"),
    AYSEN("Aysen"),
    MAGALLANES("Magallanes");

    private final String nombreVisible;

    NombreRegion(String nombreVisible) {
        this.nombreVisible = nombreVisible;
    }

    public String getNombreVisible() {
        return nombreVisible;
    }
    public static NombreRegion buscarPorNombre(String texto) {
        for (NombreRegion r : NombreRegion.values()) {
            if (r.getNombreVisible().equalsIgnoreCase(texto)) {
                return r;
            }
        }
        return null; 
    }
}