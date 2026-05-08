package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

public class VentanaDefuncion extends JFrame {

    private GestionSistema sistema;
    private JFrame ventanaAnterior;
    private JTextField txtRut;

    public VentanaDefuncion(GestionSistema sistema, JFrame ventanaAnterior) {
        this.sistema = sistema;
        this.ventanaAnterior = ventanaAnterior;
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Defuncion");
        setSize(420, 270);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { volver(); }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(VentanaMenu.COLOR_FONDO);
        panelPrincipal.add(VentanaMenu.crearHeader("REGISTRAR DEFUNCION"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        txtRut = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.4;
        form.add(new JLabel("RUT del fallecido:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        form.add(txtRut, gbc);

        JLabel nota = new JLabel("Formato RUT: 12345678-9");
        nota.setFont(new Font("Arial", Font.ITALIC, 11));
        nota.setForeground(Color.GRAY);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        form.add(nota, gbc);

        JLabel advertencia = new JLabel("Esta accion marca al ciudadano como Fallecido.");
        advertencia.setFont(new Font("Arial", Font.PLAIN, 11));
        advertencia.setForeground(new Color(150, 50, 50));
        gbc.gridy = 2;
        form.add(advertencia, gbc);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        footer.setBackground(VentanaMenu.COLOR_FONDO);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 15, 5, 15));
        JButton btnVolver    = VentanaMenu.crearBotonVolver("Volver");
        JButton btnRegistrar = VentanaMenu.crearBoton("Registrar Defuncion");
        btnVolver.addActionListener(e -> volver());
        btnRegistrar.addActionListener(e -> registrar());
        this.getRootPane().setDefaultButton(btnRegistrar);
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
        String rut = txtRut.getText().trim();
        if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto.\nEjemplo: 12345678-9", "Error RUT", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Persona fallecido = sistema.busquedaGlobalPersona(rut);
        if (fallecido == null) {
            JOptionPane.showMessageDialog(this, "No se encontro ningun ciudadano con RUT: " + rut, "No encontrado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if ("Fallecido".equalsIgnoreCase(fallecido.getEstadoVital())) {
            JOptionPane.showMessageDialog(this, "Este ciudadano ya esta registrado como fallecido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmar = JOptionPane.showConfirmDialog(this,
            "Confirmar defuncion de:\n" + fallecido.getPrimerNombre() + " " + fallecido.getPrimerApellido() + "\nRUT: " + rut,
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            fallecido.setEstadoVital("Fallecido");
            StringBuilder msg = new StringBuilder("Defuncion registrada correctamente.");
            Persona conyuge = fallecido.getConyuge();
            if (conyuge != null) {
                conyuge.setEstadoCivil("Viudo/a");
                conyuge.setConyuge(null);
                msg.append("\nEl estado civil de ").append(conyuge.getPrimerNombre())
                   .append(" ").append(conyuge.getPrimerApellido()).append(" fue actualizado a Viudo/a.");
            }
            JOptionPane.showMessageDialog(this, msg.toString(), "Exito", JOptionPane.INFORMATION_MESSAGE);
            volver();
        }
    }
}