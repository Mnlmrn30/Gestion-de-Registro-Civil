package com.registrocivil.logica;


public class Validador {

    
    public static void validarFormatoRut(String rut) throws RutInvalidoException {
        if (rut == null || rut.trim().isEmpty()) return; // opcional: se permite vacío
        if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            throw new RutInvalidoException(
                "Formato de RUT incorrecto. Debe ser sin puntos y con guion (ej. 12345678-9)."
            );
        }
    }

    public static void validarFormatoRutObligatorio(String rut) throws RutInvalidoException {
        if (rut == null || rut.trim().isEmpty()) {
            throw new RutInvalidoException("El RUT es obligatorio. No puede estar en blanco.");
        }
        validarFormatoRut(rut);
    }

    public static void validarFecha(int dia, int mes, int anio) throws FechaInvalidaException {
        if (anio < 1 || anio > 2026) {
            throw new FechaInvalidaException("El anio debe estar entre 1 y 2026.");
        }
        if (mes < 1 || mes > 12) {
            throw new FechaInvalidaException("El mes debe estar entre 1 y 12.");
        }
        int diasMaximos = 31;
        if (mes == 4 || mes == 6 || mes == 9 || mes == 11) diasMaximos = 30;
        if (mes == 2) diasMaximos = 29;
        if (dia < 1 || dia > diasMaximos) {
            throw new FechaInvalidaException(
                "El dia " + dia + " no es valido para el mes " + mes + "."
            );
        }
    }
}