package com.registrocivil.ventanas;
 
import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;
 
/**
 * Ventana para inscribir un ciudadano con RUT manual (Registro General).
 */
public class VentanaInscribirCiudadano extends JFrame {
 
    private GestionSistema sistema;
    private String[] regiones;
 
    private JComboBox<String> cmbRegion;
    private JTextField txtRut, txtPrimerNombre, txtSegundoNombre;
    private JTextField txtPrimerApellido, txtSegundoApellido;
    private JComboBox<String> cmbSexo;
    private JSpinner spnDia, spnMes, spnAnio;
 
    public VentanaInscribirCiudadano(GestionSistema sistema, String[] regiones) {
        this.sistema = sistema;
        this.regiones = regiones;
        initComponents();
    }
 
    private void initComponents() {
        setTitle("Inscribir Ciudadano");
        setSize(450, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
 
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
 
        JLabel titulo = new JLabel("INSCRIBIR CIUDADANO (REGISTRO GENERAL)", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 13));
        titulo.setForeground(new Color(30, 60, 120));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);
        gbc.gridwidth = 1;
 
        // Campos
        cmbRegion        = new JComboBox<>(regiones);
        txtRut           = new JTextField();
        txtPrimerNombre  = new JTextField();
        txtSegundoNombre = new JTextField();
        txtPrimerApellido  = new JTextField();
        txtSegundoApellido = new JTextField();
        cmbSexo = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        spnDia  = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes  = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        spnAnio = new JSpinner(new SpinnerNumberModel(2000, 1900, 2026, 1));
 
        String[] etiquetas = {"Región:", "RUT (ej: 12345678-9):", "Primer Nombre:", "Segundo Nombre:",
                              "Primer Apellido:", "Segundo Apellido:", "Sexo:", "Día Nac.:", "Mes Nac.:", "Año Nac.:"};
        Component[] campos = {cmbRegion, txtRut, txtPrimerNombre, txtSegundoNombre,
                              txtPrimerApellido, txtSegundoApellido, cmbSexo, spnDia, spnMes, spnAnio};
 
        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0; gbc.gridy = i + 1; gbc.weightx = 0.3;
            panel.add(new JLabel(etiquetas[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 0.7;
            panel.add(campos[i], gbc);
        }
 
        // Botón inscribir
        JButton btnInscribir = new JButton("Inscribir Ciudadano");
        btnInscribir.setBackground(new Color(46, 117, 182));
        btnInscribir.setForeground(Color.WHITE);
        btnInscribir.setFont(new Font("Arial", Font.BOLD, 13));
        btnInscribir.setFocusPainted(false);
        btnInscribir.setBorderPainted(false);
        gbc.gridx = 0; gbc.gridy = etiquetas.length + 1; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(btnInscribir, gbc);
 
        btnInscribir.addActionListener(e -> inscribir());
 
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        add(scroll);
    }
 
    private void inscribir() {
        try {
            String region = (String) cmbRegion.getSelectedItem();
            String rut    = txtRut.getText().trim();
 
            if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
                JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto.\nEjemplo correcto: 12345678-9", "Error RUT", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (sistema.busquedaGlobalPersona(rut) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un ciudadano con ese RUT.", "RUT Duplicado", JOptionPane.ERROR_MESSAGE);
                return;
            }
 
            String pNombre   = txtPrimerNombre.getText().trim();
            String sNombre   = txtSegundoNombre.getText().trim();
            String pApellido = txtPrimerApellido.getText().trim();
            String sApellido = txtSegundoApellido.getText().trim();
            String sexo      = (String) cmbSexo.getSelectedItem();
            int dia  = (int) spnDia.getValue();
            int mes  = (int) spnMes.getValue();
            int anio = (int) spnAnio.getValue();
 
            if (pNombre.isEmpty() || pApellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El primer nombre y apellido son obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
 
            boolean exito = sistema.registrarPersona(region, rut, pNombre, sNombre, pApellido, sApellido, sexo, dia, mes, anio);
            if (exito) {
                JOptionPane.showMessageDialog(this, "¡Ciudadano registrado con éxito en " + region + "!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar. Verifique los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}