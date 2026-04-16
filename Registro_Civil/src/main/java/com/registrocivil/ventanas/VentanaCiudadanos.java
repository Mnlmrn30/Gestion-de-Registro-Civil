package com.registrocivil.ventanas;
 
import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;
 
/**
 * Ventana de gestión de ciudadanos. Muestra todas las operaciones disponibles.
 */
public class VentanaCiudadanos extends JFrame {
 
    private GestionSistema sistema;
 
    private static final String[] NOMBRES_REGIONES = {
        "Arica y Parinacota", "Tarapaca", "Antofagasta", "Atacama",
        "Coquimbo", "Valparaiso", "Región Metropolitana", "O'Higgins",
        "Maule", "Ñuble", "Biobio", "La Araucania", "Los Rios",
        "Los Lagos", "Aysen", "Magallanes"
    };
 
    public VentanaCiudadanos(GestionSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }
 
    private void initComponents() {
        setTitle("Gestión de Ciudadanos");
        setSize(480, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setBackground(new Color(240, 245, 255));
 
        JLabel titulo = new JLabel("GESTIÓN DE CIUDADANOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 17));
        titulo.setForeground(new Color(30, 60, 120));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
 
        JPanel panelBotones = new JPanel(new GridLayout(8, 1, 5, 10));
        panelBotones.setBackground(new Color(240, 245, 255));
 
        JButton[] botones = {
            crearBoton("1. Inscribir Ciudadano",          new Color(46, 117, 182)),
            crearBoton("2. Inscribir Nacimiento",          new Color(46, 117, 182)),
            crearBoton("3. Inscribir Matrimonio",          new Color(46, 117, 182)),
            crearBoton("4. Registrar Defunción",           new Color(180, 70, 70)),
            crearBoton("5. Buscar Ciudadano",              new Color(46, 117, 182)),
            crearBoton("6. Emitir Certificados",           new Color(68, 140, 68)),
            crearBoton("7. Editar Registro de Ciudadano",  new Color(150, 100, 30)),
            crearBoton("8. Eliminar Registro",             new Color(180, 70, 70)),
        };
 
        botones[0].addActionListener(e -> new VentanaInscribirCiudadano(sistema, NOMBRES_REGIONES).setVisible(true));
        botones[1].addActionListener(e -> new VentanaInscribirNacimiento(sistema, NOMBRES_REGIONES).setVisible(true));
        botones[2].addActionListener(e -> new VentanaMatrimonio(sistema).setVisible(true));
        botones[3].addActionListener(e -> new VentanaDefuncion(sistema).setVisible(true));
        botones[4].addActionListener(e -> new VentanaBuscarCiudadano(sistema).setVisible(true));
        botones[5].addActionListener(e -> new VentanaCertificados(sistema).setVisible(true));
        botones[6].addActionListener(e -> new VentanaEditarCiudadano(sistema, NOMBRES_REGIONES).setVisible(true));
        botones[7].addActionListener(e -> new VentanaEliminarCiudadano(sistema).setVisible(true));
 
        for (JButton b : botones) panelBotones.add(b);
 
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);
        add(panel);
    }
 
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 45));
        return btn;
    }
}
