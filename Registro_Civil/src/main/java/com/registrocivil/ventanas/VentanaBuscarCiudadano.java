package com.registrocivil.ventanas;
 
import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;
 
/**
 * Ventana para buscar un ciudadano por RUT (búsqueda global).
 */
public class VentanaBuscarCiudadano extends JFrame {
 
    private GestionSistema sistema;
    private JTextField txtRut;
    private JTextArea areaResultado;
 
    public VentanaBuscarCiudadano(GestionSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }
 
    private void initComponents() {
        setTitle("Buscar Ciudadano");
        setSize(480, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setBackground(Color.WHITE);
 
        JLabel titulo = new JLabel("BUSCAR CIUDADANO (BÚSQUEDA GLOBAL)", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 13));
        titulo.setForeground(new Color(30, 60, 120));
 
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBusqueda.setBackground(Color.WHITE);
        txtRut = new JTextField(18);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(46, 117, 182));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        panelBusqueda.add(new JLabel("RUT (ej: 12345678-9):"));
        panelBusqueda.add(txtRut);
        panelBusqueda.add(btnBuscar);
 
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaResultado.setBackground(new Color(250, 252, 255));
        areaResultado.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultado"));
 
        btnBuscar.addActionListener(e -> buscar());
        txtRut.addActionListener(e -> buscar()); // también busca al presionar Enter
 
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelBusqueda, BorderLayout.CENTER);
        panel.add(scroll, BorderLayout.SOUTH);
 
        scroll.setPreferredSize(new Dimension(0, 220));
        add(panel);
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
            sb.append("RUT:              ").append(p.getRut()).append("\n");
            sb.append("Nombre completo:  ").append(p.getPrimerNombre()).append(" ")
              .append(p.getSegundoNombre()).append(" ")
              .append(p.getPrimerApellido()).append(" ")
              .append(p.getSegundoApellido()).append("\n");
            sb.append("Sexo:             ").append(p.getSexo()).append("\n");
            sb.append("Fecha nac.:       ").append(p.getDiaNacimiento()).append("/")
              .append(p.getMesNacimiento()).append("/").append(p.getAñoNacimiento()).append("\n");
            sb.append("Región:           ").append(region).append("\n");
            sb.append("Estado vital:     ").append(p.getEstadoVital()).append("\n");
            if (!"Fallecido".equalsIgnoreCase(p.getEstadoVital())) {
                sb.append("Estado civil:     ").append(p.getEstadoCivil()).append("\n");
            }
            if (p.getConyuge() != null) {
                sb.append("Cónyuge:          ").append(p.getConyuge().getPrimerNombre())
                  .append(" ").append(p.getConyuge().getPrimerApellido())
                  .append(" (RUT: ").append(p.getConyuge().getRut()).append(")\n");
            }
            if (p.getPadre() != null) {
                sb.append("Padre:            ").append(p.getPadre().getPrimerNombre())
                  .append(" ").append(p.getPadre().getPrimerApellido()).append("\n");
            }
            if (p.getMadre() != null) {
                sb.append("Madre:            ").append(p.getMadre().getPrimerNombre())
                  .append(" ").append(p.getMadre().getPrimerApellido()).append("\n");
            }
            areaResultado.setText(sb.toString());
        } else {
            areaResultado.setText("No se encontró ningún ciudadano con RUT: " + rut);
        }
    }
}
