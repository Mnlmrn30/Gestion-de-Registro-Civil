package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

public class VentanaMatrimonio extends JFrame {

    private GestionSistema sistema;
    private JFrame ventanaAnterior;
    private JTextField txtRut1, txtRut2;
    private JComboBox<String> cmbRegion;

    public VentanaMatrimonio(GestionSistema sistema, JFrame ventanaAnterior) {
        this.sistema = sistema;
        this.ventanaAnterior = ventanaAnterior;
        initComponents();
    }

    private void initComponents() {
        setTitle("Inscribir Matrimonio");
        setSize(420, 380); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { volver(); }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(VentanaMenu.COLOR_FONDO);
        panelPrincipal.add(VentanaMenu.crearHeader("INSCRIBIR MATRIMONIO", "Registro de vínculo civil"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 15, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        txtRut1 = new JTextField();
        txtRut2 = new JTextField();
        cmbRegion = new JComboBox<>(VentanaCiudadanos.NOMBRES_REGIONES);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.4;
        form.add(new JLabel("RUT Contrayente 1:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        form.add(txtRut1, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("RUT Contrayente 2:"), gbc);
        gbc.gridx = 1;
        form.add(txtRut2, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Región del evento:"), gbc);
        gbc.gridx = 1;
        form.add(cmbRegion, gbc);

        JLabel nota = new JLabel("Formato RUT: 12345678-9");
        nota.setFont(new Font("Arial", Font.ITALIC, 11));
        nota.setForeground(Color.GRAY);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        form.add(nota, gbc);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        footer.setBackground(VentanaMenu.COLOR_FONDO);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 15, 5, 15));
        JButton btnVolver    = VentanaMenu.crearBotonVolver("Volver");
        JButton btnRegistrar = VentanaMenu.crearBoton("Registrar Matrimonio");        
        btnVolver.addActionListener(e -> volver());
        btnRegistrar.addActionListener(e -> registrar());
        footer.add(btnVolver);
        footer.add(btnRegistrar);
        panelPrincipal.add(form, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void volver() {
        if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
        dispose();
    }

    private void registrar() {
        String rut1 = txtRut1.getText().trim();
        String rut2 = txtRut2.getText().trim();
        String region = (String) cmbRegion.getSelectedItem();

        if (!rut1.matches("^[0-9]{7,8}-[0-9Kk]{1}$") || !rut2.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto.", "Error RUT", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = sistema.registrarMatrimonio(rut1, rut2, region);

        if (exito) {
            JOptionPane.showMessageDialog(this,
                "Matrimonio registrado exitosamente en " + region + ".",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            volver();
        } else {
            JOptionPane.showMessageDialog(this,
                "No se pudo registrar.\nCausas: RUTs inexistentes, misma persona o no son solteros/viudos.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}