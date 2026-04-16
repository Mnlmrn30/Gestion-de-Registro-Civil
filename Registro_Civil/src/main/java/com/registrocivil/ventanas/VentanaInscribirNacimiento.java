package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana para inscribir un nacimiento (genera RUT automático y vincula progenitores).
 */
public class VentanaInscribirNacimiento extends JFrame {

    private GestionSistema sistema;
    private String[] regiones;

    private JComboBox<String> cmbRegion;
    private JTextField txtPrimerNombre, txtSegundoNombre;
    private JTextField txtPrimerApellido, txtSegundoApellido;
    private JTextField txtRutPadre, txtRutMadre;
    private JComboBox<String> cmbSexo;
    private JSpinner spnDia, spnMes, spnAnio;

    public VentanaInscribirNacimiento(GestionSistema sistema, String[] regiones) {
        this.sistema = sistema;
        this.regiones = regiones;
        initComponents();
    }

    private void initComponents() {
        setTitle("Inscribir Nacimiento");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titulo = new JLabel("INSCRIBIR NACIMIENTO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setForeground(new Color(30, 60, 120));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        JLabel nota = new JLabel("(El RUT será asignado automáticamente)", SwingConstants.CENTER);
        nota.setFont(new Font("Arial", Font.ITALIC, 11));
        nota.setForeground(Color.GRAY);
        gbc.gridy = 1;
        panel.add(nota, gbc);
        gbc.gridwidth = 1;

        cmbRegion        = new JComboBox<>(regiones);
        txtPrimerNombre  = new JTextField();
        txtSegundoNombre = new JTextField();
        txtPrimerApellido  = new JTextField();
        txtSegundoApellido = new JTextField();
        cmbSexo = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        spnDia  = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes  = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        spnAnio = new JSpinner(new SpinnerNumberModel(2025, 1900, 2026, 1));
        txtRutPadre = new JTextField();
        txtRutMadre = new JTextField();

        String[] etiquetas = {"Región de Nacimiento:", "Primer Nombre:", "Segundo Nombre:",
                              "Primer Apellido:", "Segundo Apellido:", "Sexo:",
                              "Día Nac.:", "Mes Nac.:", "Año Nac.:",
                              "RUT Padre (opcional):", "RUT Madre (opcional):"};
        Component[] campos = {cmbRegion, txtPrimerNombre, txtSegundoNombre,
                              txtPrimerApellido, txtSegundoApellido, cmbSexo,
                              spnDia, spnMes, spnAnio, txtRutPadre, txtRutMadre};

        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0; gbc.gridy = i + 2; gbc.weightx = 0.35;
            panel.add(new JLabel(etiquetas[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 0.65;
            panel.add(campos[i], gbc);
        }

        JButton btnRegistrar = new JButton("Registrar Nacimiento");
        btnRegistrar.setBackground(new Color(46, 117, 182));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorderPainted(false);
        gbc.gridx = 0; gbc.gridy = etiquetas.length + 2; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(btnRegistrar, gbc);

        btnRegistrar.addActionListener(e -> registrar());

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        add(scroll);
    }

    private void registrar() {
        try {
            String region    = (String) cmbRegion.getSelectedItem();
            String pNombre   = txtPrimerNombre.getText().trim();
            String sNombre   = txtSegundoNombre.getText().trim();
            String pApellido = txtPrimerApellido.getText().trim();
            String sApellido = txtSegundoApellido.getText().trim();
            String sexo      = (String) cmbSexo.getSelectedItem();
            int dia  = (int) spnDia.getValue();
            int mes  = (int) spnMes.getValue();
            int anio = (int) spnAnio.getValue();
            String rutPadre = txtRutPadre.getText().trim();
            String rutMadre = txtRutMadre.getText().trim();

            if (pNombre.isEmpty() || pApellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El primer nombre y apellido son obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar RUTs de padres si fueron ingresados
            if (!rutPadre.isEmpty() && !rutPadre.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
                JOptionPane.showMessageDialog(this, "Formato de RUT del padre incorrecto.", "Error RUT", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!rutMadre.isEmpty() && !rutMadre.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
                JOptionPane.showMessageDialog(this, "Formato de RUT de la madre incorrecto.", "Error RUT", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String rutGenerado = sistema.registrarNacimiento(region, pNombre, sNombre, pApellido, sApellido, sexo, dia, mes, anio,
                    rutPadre.isEmpty() ? null : rutPadre,
                    rutMadre.isEmpty() ? null : rutMadre);

            if (rutGenerado != null) {
                JOptionPane.showMessageDialog(this,
                    "¡Nacimiento registrado exitosamente!\n\nRUT asignado al recién nacido:\n" + rutGenerado,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar. Verifique la región.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
