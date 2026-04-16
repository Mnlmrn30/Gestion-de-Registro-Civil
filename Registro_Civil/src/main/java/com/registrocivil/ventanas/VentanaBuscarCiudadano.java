package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

public class VentanaBuscarCiudadano extends JFrame {

    private GestionSistema sistema;
    private JFrame ventanaAnterior;
    private JTextField txtRut;
    private JTextArea areaResultado;

    public VentanaBuscarCiudadano(GestionSistema sistema, JFrame ventanaAnterior) {
        this.sistema = sistema;
        this.ventanaAnterior = ventanaAnterior;
        initComponents();
    }

    private void initComponents() {
        setTitle("Buscar Ciudadano");
        setSize(500, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { volver(); }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 0));
        panelPrincipal.setBackground(VentanaMenu.COLOR_FONDO);
        panelPrincipal.add(VentanaMenu.crearHeader("BUSCAR CIUDADANO", "Busqueda global por RUT"), BorderLayout.NORTH);

        // Barra de busqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 210, 230)));
        txtRut = new JTextField(18);
        JButton btnBuscar = VentanaMenu.crearBoton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(90, 32));
        panelBusqueda.add(new JLabel("RUT (ej: 12345678-9):"));
        panelBusqueda.add(txtRut);
        panelBusqueda.add(btnBuscar);

        // Area de resultado
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaResultado.setBackground(Color.WHITE);
        areaResultado.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultado"));

        btnBuscar.addActionListener(e -> buscar());
        txtRut.addActionListener(e -> buscar());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        footer.setBackground(VentanaMenu.COLOR_FONDO);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 12, 5, 12));
        JButton btnVolver = VentanaMenu.crearBotonVolver("Volver a Gestion de Ciudadanos");
        btnVolver.addActionListener(e -> volver());
        footer.add(btnVolver);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(panelBusqueda, BorderLayout.NORTH);
        panelCentro.add(scroll, BorderLayout.CENTER);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void volver() {
        if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
        dispose();
    }

    private void buscar() {
        String rut = txtRut.getText().trim();
        if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            areaResultado.setText("Error: Formato de RUT incorrecto.\nEjemplo: 12345678-9");
            return;
        }
        Persona p = sistema.busquedaGlobalPersona(rut);
        String region = sistema.obtenerRegionDePersona(rut);
        if (p != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("--- RESULTADO ---\n");
            sb.append(String.format("%-20s: %s\n", "RUT", p.getRut()));
            sb.append(String.format("%-20s: %s %s %s %s\n", "Nombre completo",
                p.getPrimerNombre(), p.getSegundoNombre(), p.getPrimerApellido(), p.getSegundoApellido()));
            sb.append(String.format("%-20s: %s\n", "Sexo", p.getSexo()));
            sb.append(String.format("%-20s: %d/%d/%d\n", "Fecha nacimiento",
                p.getDiaNacimiento(), p.getMesNacimiento(), p.getAñoNacimiento()));
            sb.append(String.format("%-20s: %s\n", "Region", region));
            sb.append(String.format("%-20s: %s\n", "Estado vital", p.getEstadoVital()));
            if (!"Fallecido".equalsIgnoreCase(p.getEstadoVital()))
                sb.append(String.format("%-20s: %s\n", "Estado civil", p.getEstadoCivil()));
            if (p.getConyuge() != null)
                sb.append(String.format("%-20s: %s %s (RUT: %s)\n", "Conyuge",
                    p.getConyuge().getPrimerNombre(), p.getConyuge().getPrimerApellido(), p.getConyuge().getRut()));
            if (p.getPadre() != null)
                sb.append(String.format("%-20s: %s %s\n", "Padre",
                    p.getPadre().getPrimerNombre(), p.getPadre().getPrimerApellido()));
            if (p.getMadre() != null)
                sb.append(String.format("%-20s: %s %s\n", "Madre",
                    p.getMadre().getPrimerNombre(), p.getMadre().getPrimerApellido()));
            areaResultado.setText(sb.toString());
        } else {
            areaResultado.setText("No se encontro ningun ciudadano con RUT: " + rut);
        }
    }
}