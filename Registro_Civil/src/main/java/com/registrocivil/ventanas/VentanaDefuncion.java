package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana para registrar la defunción de un ciudadano.
 */
public class VentanaDefuncion extends JFrame {

    private GestionSistema sistema;
    private JTextField txtRut;

    public VentanaDefuncion(GestionSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Defunción");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        JLabel titulo = new JLabel("REGISTRAR DEFUNCIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 15));
        titulo.setForeground(new Color(150, 30, 30));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);
        gbc.gridwidth = 1;

        txtRut = new JTextField();
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.4;
        panel.add(new JLabel("RUT del fallecido:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        panel.add(txtRut, gbc);

        JButton btnRegistrar = new JButton("Registrar Defunción");
        btnRegistrar.setBackground(new Color(180, 70, 70));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorderPainted(false);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(btnRegistrar, gbc);

        btnRegistrar.addActionListener(e -> registrar());
        add(panel);
    }

    private void registrar() {
        String rut = txtRut.getText().trim();

        if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto. Ejemplo: 12345678-9", "Error RUT", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Persona fallecido = sistema.busquedaGlobalPersona(rut);
        if (fallecido == null) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún ciudadano con RUT: " + rut, "No encontrado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("Fallecido".equalsIgnoreCase(fallecido.getEstadoVital())) {
            JOptionPane.showMessageDialog(this, "Este ciudadano ya está registrado como fallecido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Confirmar defunción de:\n" + fallecido.getPrimerNombre() + " " + fallecido.getPrimerApellido() + " (RUT: " + rut + ")?",
            "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            fallecido.setEstadoVital("Fallecido");
            StringBuilder msg = new StringBuilder("Defunción registrada correctamente.");

            Persona conyuge = fallecido.getConyuge();
            if (conyuge != null) {
                conyuge.setEstadoCivil("Viudo/a");
                conyuge.setConyuge(null);
                msg.append("\nSe actualizó el estado civil de ").append(conyuge.getPrimerNombre())
                   .append(" ").append(conyuge.getPrimerApellido()).append(" a Viudo/a.");
            }

            JOptionPane.showMessageDialog(this, msg.toString(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}