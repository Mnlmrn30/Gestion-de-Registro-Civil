package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana para inscribir un matrimonio entre dos ciudadanos existentes.
 */
public class VentanaMatrimonio extends JFrame {

    private GestionSistema sistema;
    private JTextField txtRut1, txtRut2;

    public VentanaMatrimonio(GestionSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Inscribir Matrimonio");
        setSize(400, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        JLabel titulo = new JLabel("INSCRIBIR MATRIMONIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 15));
        titulo.setForeground(new Color(30, 60, 120));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        JLabel nota = new JLabel("Ambos ciudadanos deben estar registrados y ser solteros/viudos.", SwingConstants.CENTER);
        nota.setFont(new Font("Arial", Font.ITALIC, 10));
        nota.setForeground(Color.GRAY);
        gbc.gridy = 1;
        panel.add(nota, gbc);
        gbc.gridwidth = 1;

        txtRut1 = new JTextField();
        txtRut2 = new JTextField();

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.4;
        panel.add(new JLabel("RUT Contrayente 1:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        panel.add(txtRut1, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.4;
        panel.add(new JLabel("RUT Contrayente 2:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        panel.add(txtRut2, gbc);

        JButton btnRegistrar = new JButton("Registrar Matrimonio");
        btnRegistrar.setBackground(new Color(46, 117, 182));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorderPainted(false);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(btnRegistrar, gbc);

        btnRegistrar.addActionListener(e -> registrar());
        add(panel);
    }

    private void registrar() {
        String rut1 = txtRut1.getText().trim();
        String rut2 = txtRut2.getText().trim();

        if (!rut1.matches("^[0-9]{7,8}-[0-9Kk]{1}$") || !rut2.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto. Ejemplo: 12345678-9", "Error RUT", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = sistema.registrarMatrimonio(rut1, rut2);
        if (exito) {
            JOptionPane.showMessageDialog(this,
                "¡Matrimonio registrado exitosamente!\nEl estado civil de ambos ciudadanos ha cambiado a Casado/a.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "No se pudo registrar el matrimonio.\nPosibles causas:\n- Un RUT no existe en el sistema\n- Mismo RUT ingresado dos veces\n- Uno de los ciudadanos ya está casado",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
