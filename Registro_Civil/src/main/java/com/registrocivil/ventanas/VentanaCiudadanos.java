package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

public class VentanaCiudadanos extends JFrame {

    static final String[] NOMBRES_REGIONES = {
        "Arica y Parinacota", "Tarapaca", "Antofagasta", "Atacama",
        "Coquimbo", "Valparaiso", "Region Metropolitana", "O'Higgins",
        "Maule", "Nuble", "Biobio", "La Araucania", "Los Rios",
        "Los Lagos", "Aysen", "Magallanes"
    };

    private GestionSistema sistema;
    private JFrame ventanaAnterior;

    public VentanaCiudadanos(GestionSistema sistema, JFrame ventanaAnterior) {
        this.sistema = sistema;
        this.ventanaAnterior = ventanaAnterior;
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestion de Ciudadanos");
        setSize(460, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                volver();
            }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 0));
        panelPrincipal.setBackground(VentanaMenu.COLOR_FONDO);

        // Header
        panelPrincipal.add(VentanaMenu.crearHeader("GESTION DE CIUDADANOS"), BorderLayout.NORTH);

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(8, 1, 0, 10));
        panelBotones.setBackground(VentanaMenu.COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(18, 40, 10, 40));

        String[] etiquetas = {
            "1. Inscribir Ciudadano",
            "2. Inscribir Nacimiento",
            "3. Inscribir Matrimonio",
            "4. Registrar Defuncion",
            "5. Buscar Ciudadano",
            "6. Emitir Certificados",
            "7. Editar Registro de Ciudadano",
            "8. Eliminar Registro"
        };

        for (String etiqueta : etiquetas) {
            panelBotones.add(VentanaMenu.crearBoton(etiqueta));
        }

        // Asignar acciones a cada boton
        Component[] bots = panelBotones.getComponents();
        ((JButton) bots[0]).addActionListener(e -> { new VentanaInscribirCiudadano(sistema, VentanaCiudadanos.this).setVisible(true); setVisible(false); });
        ((JButton) bots[1]).addActionListener(e -> { new VentanaInscribirNacimiento(sistema, VentanaCiudadanos.this).setVisible(true); setVisible(false); });
        ((JButton) bots[2]).addActionListener(e -> { new VentanaMatrimonio(sistema, VentanaCiudadanos.this).setVisible(true); setVisible(false); });
        ((JButton) bots[3]).addActionListener(e -> { new VentanaDefuncion(sistema, VentanaCiudadanos.this).setVisible(true); setVisible(false); });
        ((JButton) bots[4]).addActionListener(e -> { new VentanaBuscarCiudadano(sistema, VentanaCiudadanos.this).setVisible(true); setVisible(false); });
        ((JButton) bots[5]).addActionListener(e -> { new VentanaCertificados(sistema, VentanaCiudadanos.this).setVisible(true); setVisible(false); });
        ((JButton) bots[6]).addActionListener(e -> { new VentanaEditarCiudadano(sistema, VentanaCiudadanos.this).setVisible(true); setVisible(false); });
        ((JButton) bots[7]).addActionListener(e -> { new VentanaEliminarCiudadano(sistema, VentanaCiudadanos.this).setVisible(true); setVisible(false); });

        // Footer con boton Volver
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.setBackground(VentanaMenu.COLOR_FONDO);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 40, 10, 40));
        JButton btnVolver = VentanaMenu.crearBotonVolver("Volver al Menu Principal");
        btnVolver.addActionListener(e -> volver());
        footer.add(btnVolver);

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void volver() {
        if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
        dispose();
    }
}

