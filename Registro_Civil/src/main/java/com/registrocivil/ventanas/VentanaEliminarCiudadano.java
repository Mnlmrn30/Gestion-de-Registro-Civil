package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

public class VentanaEliminarCiudadano extends JFrame {

    private GestionSistema sistema;
    private JFrame ventanaAnterior;
    private JTextField txtRut;

    public VentanaEliminarCiudadano(GestionSistema sistema, JFrame ventanaAnterior) {
        this.sistema = sistema;
        this.ventanaAnterior = ventanaAnterior;
        initComponents();
    }

    private void initComponents() {
        setTitle("Eliminar Registro");
        setSize(430, 290);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { volver(); }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(VentanaMenu.COLOR_FONDO);
        panelPrincipal.add(VentanaMenu.crearHeader("ELIMINAR REGISTRO DE CIUDADANO"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        txtRut = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.4;
        form.add(new JLabel("RUT ciudadano:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        form.add(txtRut, gbc);

        JLabel notaFormato = new JLabel("Formato RUT: 12345678-9");
        notaFormato.setFont(new Font("Arial", Font.ITALIC, 11));
        notaFormato.setForeground(Color.GRAY);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        form.add(notaFormato, gbc);

        JLabel advertencia = new JLabel("Se pedira confirmacion antes de eliminar el registro.");
        advertencia.setFont(new Font("Arial", Font.PLAIN, 11));
        advertencia.setForeground(new Color(130, 60, 60));
        gbc.gridy = 2;
        form.add(advertencia, gbc);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        footer.setBackground(VentanaMenu.COLOR_FONDO);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 15, 5, 15));
        JButton btnVolver    = VentanaMenu.crearBotonVolver("Volver");
        JButton btnEliminar  = VentanaMenu.crearBoton("Eliminar Registro");
        btnVolver.addActionListener(e -> volver());
        btnEliminar.addActionListener(e -> eliminar());
        footer.add(btnVolver);
        footer.add(btnEliminar);
        this.getRootPane().setDefaultButton(btnEliminar);
        panelPrincipal.add(form, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void volver() {
        if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
        dispose();
    }

    private void eliminar() {
        String rut = txtRut.getText().trim();
        if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto.\nEjemplo: 12345678-9", "Error RUT", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Persona p = sistema.busquedaGlobalPersona(rut);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "No se encontro ningun ciudadano con RUT: " + rut, "No encontrado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmar = JOptionPane.showConfirmDialog(this,
            "Confirmar eliminacion de:\n" + p.getPrimerNombre() + " " + p.getPrimerApellido()
            + "\nRUT: " + rut + "\n\nEsta accion no se puede deshacer.",
            "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            String nombreRegion = "";
            for (Region r : sistema.getRegiones().values()) {
                if (r.getCiudadanos().contains(p)) { nombreRegion = r.getNombre(); break; }
            }
            if (!nombreRegion.isEmpty()) {
                boolean exito = sistema.eliminarPersona(nombreRegion, rut);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Registro eliminado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    volver();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo determinar la region del ciudadano.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

