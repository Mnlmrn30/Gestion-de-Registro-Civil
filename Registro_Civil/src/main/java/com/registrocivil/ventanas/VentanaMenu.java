package com.registrocivil.ventanas;

import com.registrocivil.logica.GestionSistema;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del Sistema de Registro Civil.
 * Muestra el menu inicial con acceso a Regiones y Ciudadanos.
 */
public class VentanaMenu extends JFrame {

    private GestionSistema sistema;

    
    static final Color COLOR_PRIMARIO   = new Color(46, 117, 182);
    static final Color COLOR_FONDO      = new Color(240, 245, 255);
    static final Color COLOR_TITULO     = new Color(30, 60, 120);

    public VentanaMenu(GestionSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Registro Civil - Chile");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        // Al cerrar la ventana se guardan los datos en la BD
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    VentanaMenu.this,
                    "¿Desea salir? Los datos serán guardados.",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    sistema.guardarDatosEnBD();
                    System.exit(0);
                }
            }
        });

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panelPrincipal.setBackground(COLOR_FONDO);

       
        JLabel titulo = new JLabel("SISTEMA DE REGISTRO CIVIL", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(COLOR_TITULO);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel subtitulo = new JLabel("República de Chile", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 13));
        subtitulo.setForeground(new Color(100, 100, 100));

        JPanel panelTitulo = new JPanel(new GridLayout(2, 1));
        panelTitulo.setBackground(COLOR_FONDO);
        panelTitulo.add(titulo);
        panelTitulo.add(subtitulo);

        
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 15));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        JButton btnRegiones    = crearBoton("Gestionar Regiones");
        JButton btnCiudadanos  = crearBoton("Gestionar Ciudadanos");

        btnRegiones.addActionListener(e -> {
            new VentanaRegiones(sistema).setVisible(true);
        });

        btnCiudadanos.addActionListener(e -> {
            new VentanaCiudadanos(sistema).setVisible(true);
        });

        panelBotones.add(btnRegiones);
        panelBotones.add(btnCiudadanos);

       
        JLabel footer = new JLabel("Seleccione una opción para continuar", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 11));
        footer.setForeground(Color.GRAY);

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    static JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(COLOR_PRIMARIO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(0, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    static JButton crearBotonVolver(JFrame ventana) {
        JButton btn = new JButton("← Volver");
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(new Color(100, 100, 100));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> ventana.dispose());
        return btn;
    }
}

