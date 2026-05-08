package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class VentanaRegiones extends JFrame {

    private static final String[] NOMBRES_REGIONES = {
        "Arica y Parinacota", "Tarapaca", "Antofagasta", "Atacama",
        "Coquimbo", "Valparaiso", "Metropolitana", "O'Higgins",
        "Maule", "Nuble", "Biobio", "La Araucania", "Los Rios",
        "Los Lagos", "Aysen", "Magallanes"
    };

    private GestionSistema sistema;
    private JFrame ventanaAnterior;
    private JTextArea areaResultado;

    public VentanaRegiones(GestionSistema sistema, JFrame ventanaAnterior) {
        this.sistema = sistema;
        this.ventanaAnterior = ventanaAnterior;
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestion de Regiones");
        setSize(760, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Al cerrar esta ventana vuelve al menu anterior
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                volver();
            }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 0));
        panelPrincipal.setBackground(VentanaMenu.COLOR_FONDO);

        // Header
        panelPrincipal.add(VentanaMenu.crearHeader("GESTION DE REGIONES"), BorderLayout.NORTH);

        // Panel central: botones izquierda + resultados derecha
        JPanel panelCentro = new JPanel(new BorderLayout(10, 0));
        panelCentro.setBackground(VentanaMenu.COLOR_FONDO);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Columna de botones
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 0, 10));
        panelBotones.setBackground(VentanaMenu.COLOR_FONDO);
        panelBotones.setPreferredSize(new Dimension(210, 0));

        JButton btnTodasRegiones    = VentanaMenu.crearBoton("Mostrar todas las regiones");
        JButton btnListadoCiudadanos = VentanaMenu.crearBoton("Ciudadanos por region");
        JButton btnMatrimonios       = VentanaMenu.crearBoton("Matrimonios por region");
        JButton btnEstadisticas      = VentanaMenu.crearBoton("Estadisticas generales");

        btnTodasRegiones.addActionListener(e    -> mostrarTodasRegiones());
        btnListadoCiudadanos.addActionListener(e -> mostrarCiudadanosPorRegion());
        btnMatrimonios.addActionListener(e       -> mostrarMatrimoniosPorRegion());
        btnEstadisticas.addActionListener(e      -> mostrarEstadisticasGenerales());

        panelBotones.add(btnTodasRegiones);
        panelBotones.add(btnListadoCiudadanos);
        panelBotones.add(btnMatrimonios);
        panelBotones.add(btnEstadisticas);

        // Area de resultados
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaResultado.setBackground(Color.WHITE);
        areaResultado.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));

        panelCentro.add(panelBotones, BorderLayout.WEST);
        panelCentro.add(scroll, BorderLayout.CENTER);

        // Footer con boton Volver
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.setBackground(VentanaMenu.COLOR_FONDO);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 12));
        JButton btnVolver = VentanaMenu.crearBotonVolver("Volver al Menu Principal");
        btnVolver.addActionListener(e -> volver());
        footer.add(btnVolver);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void volver() {
        if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
        dispose();
    }

    private void mostrarTodasRegiones() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== INFORMACION DE TODAS LAS REGIONES ===\n\n");
        HashMap<String, Region> regiones = sistema.getRegiones();
        for (String nombre : NOMBRES_REGIONES) {
            Region r = regiones.get(nombre);
            if (r != null) {
                sb.append("Region: ").append(r.getNombre()).append("\n");
                sb.append("  Inscritos:   ").append(r.getNumeroHabitantes()).append("\n");
                sb.append("  Vivos:       ").append(sistema.obtenerVivosPorRegion(r.getNombre())).append("\n");
                sb.append("  Fallecidos:  ").append(sistema.obtenerFallecidosPorRegion(r.getNombre())).append("\n");
                sb.append("------------------------------------------------\n");
            }
        }
        areaResultado.setText(sb.toString());
        areaResultado.setCaretPosition(0);
    }

    private void mostrarCiudadanosPorRegion() {
        String sel = (String) JOptionPane.showInputDialog(
            this, "Seleccione la region:", "Ciudadanos por Region",
            JOptionPane.PLAIN_MESSAGE, null, NOMBRES_REGIONES, NOMBRES_REGIONES[0]);
        if (sel == null) return;

        Region r = sistema.getRegiones().get(sel);
        StringBuilder sb = new StringBuilder();
        sb.append("=== CIUDADANOS EN ").append(sel.toUpperCase()).append(" ===\n\n");
        if (r == null || r.getCiudadanos().isEmpty()) {
            sb.append("No hay ciudadanos registrados en esta region.");
        } else {
            for (Persona p : r.getCiudadanos()) sb.append(p.toString()).append("\n");
        }
        areaResultado.setText(sb.toString());
        areaResultado.setCaretPosition(0);
    }

    private void mostrarMatrimoniosPorRegion() {
        String sel = (String) JOptionPane.showInputDialog(
            this, "Seleccione la region:", "Matrimonios por Region",
            JOptionPane.PLAIN_MESSAGE, null, NOMBRES_REGIONES, NOMBRES_REGIONES[0]);
    
        if (sel == null) return;

        int cantidad = sistema.obtenerCantidadMatrimoniosPorRegion(sel);
        java.util.List<String> parejas = sistema.obtenerActasMatrimonioPorRegion(sel);

        StringBuilder sb = new StringBuilder();
        sb.append("=== MATRIMONIOS EN ").append(sel.toUpperCase()).append(" ===\n\n");
        sb.append("Total de matrimonios registrados: ").append(cantidad).append("\n\n");

        if (cantidad == 0 || parejas.isEmpty()) {
            sb.append("No hay matrimonios registrados en esta region.");
        } else {
            sb.append("Parejas casadas:\n");
            for (String pareja : parejas) {
                sb.append("- ").append(pareja).append("\n");
            }
        }

        areaResultado.setText(sb.toString());
        areaResultado.setCaretPosition(0);
    }

    private void mostrarEstadisticasGenerales() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADISTICAS GENERALES NACIONALES ===\n\n");
        int totalNac = 0, totalVivos = 0, totalFall = 0, hombres = 0, mujeres = 0, otros = 0;

        for (String nombre : NOMBRES_REGIONES) {
            Region r = sistema.getRegiones().get(nombre);
            if (r != null && r.getNumeroHabitantes() > 0) {
                int v = sistema.obtenerVivosPorRegion(r.getNombre());
                int f = sistema.obtenerFallecidosPorRegion(r.getNombre());
                totalNac += r.getNumeroHabitantes(); totalVivos += v; totalFall += f;
                sb.append("REGION: ").append(r.getNombre().toUpperCase()).append("\n");
                sb.append("  Inscritos: ").append(r.getNumeroHabitantes())
                  .append(" | Vivos: ").append(v).append(" | Fallecidos: ").append(f).append("\n");
                for (Persona p : r.getCiudadanos()) {
                    String s = p.getSexo().toLowerCase();
                    if (s.equals("masculino")) hombres++;
                    else if (s.equals("femenino")) mujeres++;
                    else otros++;
                }
            }
        }
        sb.append("\n=== RESUMEN NACIONAL ===\n");
        sb.append("Poblacion total historica:  ").append(totalNac).append("\n");
        sb.append("Ciudadanos vivos:           ").append(totalVivos).append("\n");
        sb.append("Defunciones registradas:    ").append(totalFall).append("\n");
        sb.append("Total hombres:              ").append(hombres).append("\n");
        sb.append("Total mujeres:              ").append(mujeres).append("\n");
        if (otros > 0) sb.append("Otros:                      ").append(otros).append("\n");
        areaResultado.setText(sb.toString());
        areaResultado.setCaretPosition(0);
    }
}