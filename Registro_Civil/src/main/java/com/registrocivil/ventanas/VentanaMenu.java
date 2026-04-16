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
        panelPrincipal.setBackground(new Color(240, 245, 255));
 
        // Titulo
        JLabel titulo = new JLabel("SISTEMA DE REGISTRO CIVIL", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(30, 60, 120));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
 
        JLabel subtitulo = new JLabel("República de Chile", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 13));
        subtitulo.setForeground(new Color(100, 100, 100));
 
        JPanel panelTitulo = new JPanel(new GridLayout(2, 1));
        panelTitulo.setBackground(new Color(240, 245, 255));
        panelTitulo.add(titulo);
        panelTitulo.add(subtitulo);
 
        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 15));
        panelBotones.setBackground(new Color(240, 245, 255));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
 
        JButton btnRegiones = crearBoton("🗺  Gestionar Regiones", new Color(46, 117, 182));
        JButton btnCiudadanos = crearBoton("👤  Gestionar Ciudadanos", new Color(68, 140, 68));
 
        btnRegiones.addActionListener(e -> {
            new VentanaRegiones(sistema).setVisible(true);
        });
 
        btnCiudadanos.addActionListener(e -> {
            new VentanaCiudadanos(sistema).setVisible(true);
        });
 
        panelBotones.add(btnRegiones);
        panelBotones.add(btnCiudadanos);
 
        // Footer
        JLabel footer = new JLabel("Seleccione una opción para continuar", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 11));
        footer.setForeground(Color.GRAY);
 
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);
 
        add(panelPrincipal);
    }
 
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(0, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
