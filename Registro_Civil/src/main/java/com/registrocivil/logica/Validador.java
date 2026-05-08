package com.registrocivil.logica;


public class Validador {

    /**
     * Valida que el RUT tenga el formato correcto (ej: 12345678-9 o 12345678-K).
     * Permite cadena vacía para campos opcionales (ej: RUT del padre/madre).
     *
     * @param rut El RUT a validar.
     * @throws RutInvalidoException si el formato del RUT es incorrecto.
     */
    public static void validarFormatoRut(String rut) throws RutInvalidoException {
        if (rut == null || rut.trim().isEmpty()) return; // opcional: se permite vacío
        if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            throw new RutInvalidoException(
                "Formato de RUT incorrecto. Debe ser sin puntos y con guion (ej. 12345678-9)."
            );
        }
    }

    /**
     * Valida que el RUT NO sea vacío y tenga formato correcto.
     * Para campos obligatorios.
     *
     * @param rut El RUT a validar.
     * @throws RutInvalidoException si el RUT está vacío o tiene formato incorrecto.
     */
    public static void validarFormatoRutObligatorio(String rut) throws RutInvalidoException {
        if (rut == null || rut.trim().isEmpty()) {
            throw new RutInvalidoException("El RUT es obligatorio. No puede estar en blanco.");
        }
        validarFormatoRut(rut);
    }

    /**
     * Valida que una fecha sea coherente (dia, mes y anio en rangos válidos).
     *
     * @param dia  Día de nacimiento.
     * @param mes  Mes de nacimiento.
     * @param anio Año de nacimiento.
     * @throws FechaInvalidaException si la fecha no es válida.
     */
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
