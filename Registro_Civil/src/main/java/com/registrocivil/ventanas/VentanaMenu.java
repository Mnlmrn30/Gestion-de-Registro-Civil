package com.registrocivil.ventanas;

import com.registrocivil.logica.GestionSistema;
import javax.swing.*;
import java.awt.*;

public class VentanaMenu extends JFrame {

    // ===== PALETA DE COLORES UNIFICADA =====
    public static final Color COLOR_PRIMARIO     = new Color(31, 56, 100);   // azul oscuro - headers
    public static final Color COLOR_BOTON        = new Color(46, 90, 160);   // azul medio  - botones de accion
    public static final Color COLOR_BOTON_VOLVER = new Color(90, 110, 145);  // azul grisaceo - boton volver
    public static final Color COLOR_FONDO        = new Color(235, 240, 250); // fondo general
    public static final Color COLOR_TEXTO_HEADER = Color.WHITE;
    public static final Color COLOR_TEXTO_SUB    = new Color(220, 230, 245); // blanco suave - subtitulo en header oscuro

    private GestionSistema sistema;

    public VentanaMenu(GestionSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Registro Civil - Chile");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(480, 340);
        setLocationRelativeTo(null);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    VentanaMenu.this,
                    "Desea salir? Los datos seran guardados.",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    sistema.guardarDatosEnBD();
                    System.exit(0);
                }
            }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(COLOR_FONDO);

        // Header azul
        panelPrincipal.add(crearHeader("SISTEMA DE REGISTRO CIVIL", "Republica de Chile"), BorderLayout.NORTH);

        // Botones centrales
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 14));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(25, 50, 15, 50));

        JButton btnRegiones   = crearBoton("Gestionar Regiones");
        JButton btnCiudadanos = crearBoton("Gestionar Ciudadanos");

        btnRegiones.addActionListener(e -> {
            new VentanaRegiones(sistema, VentanaMenu.this).setVisible(true);
            setVisible(false);
        });
        btnCiudadanos.addActionListener(e -> {
            new VentanaCiudadanos(sistema, VentanaMenu.this).setVisible(true);
            setVisible(false);
        });

        panelBotones.add(btnRegiones);
        panelBotones.add(btnCiudadanos);

        JLabel footer = new JLabel("Seleccione una opcion para continuar", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 11));
        footer.setForeground(Color.DARK_GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    // ============================================================
    // METODOS ESTATICOS COMPARTIDOS - usados por todas las ventanas
    // ============================================================

    /** Boton de accion principal (azul medio) */
    public static JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(COLOR_BOTON);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 46));
        return btn;
    }

    /** Boton de volver / cancelar (azul grisaceo) */
    public static JButton crearBotonVolver(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(COLOR_BOTON_VOLVER);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(220, 38));
        return btn;
    }

    /** Header azul con titulo y subtitulo opcional */
    public static JPanel crearHeader(String titulo, String subtitulo) {
        JPanel header = new JPanel(new GridLayout(subtitulo != null ? 2 : 1, 1));
        header.setBackground(COLOR_PRIMARIO);
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(COLOR_TEXTO_HEADER);
        header.add(lblTitulo);
        if (subtitulo != null) {
            JLabel lblSub = new JLabel(subtitulo, SwingConstants.CENTER);
            lblSub.setFont(new Font("Arial", Font.PLAIN, 12));
            lblSub.setForeground(COLOR_TEXTO_SUB);
            header.add(lblSub);
        }
        return header;
    }

    public static JPanel crearHeader(String titulo) {
        return crearHeader(titulo, null);
    }

    public static JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.BLACK);
        return lbl;
    }
}
