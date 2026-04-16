package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana para eliminar el registro de un ciudadano del sistema.
 */
public class VentanaEliminarCiudadano extends JFrame {

    private GestionSistema sistema;
    private JTextField txtRut;

    public VentanaEliminarCiudadano(GestionSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Eliminar Registro");
        setSize(420, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        JLabel titulo = new JLabel("ELIMINAR REGISTRO DE CIUDADANO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setForeground(new Color(150, 30, 30));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        JLabel advertencia = new JLabel("⚠ Esta acción eliminará permanentemente el registro.", SwingConstants.CENTER);
        advertencia.setFont(new Font("Arial", Font.ITALIC, 10));
        advertencia.setForeground(new Color(180, 70, 0));
        gbc.gridy = 1;
        panel.add(advertencia, gbc);
        gbc.gridwidth = 1;

        txtRut = new JTextField();
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.4;
        panel.add(new JLabel("RUT ciudadano:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        panel.add(txtRut, gbc);

        JButton btnEliminar = new JButton("Eliminar Registro");
        btnEliminar.setBackground(new Color(180, 70, 70));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 13));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(btnEliminar, gbc);

        btnEliminar.addActionListener(e -> eliminar());
        add(panel);
    }

    private void eliminar() {
        String rut = txtRut.getText().trim();

        if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto. Ejemplo: 12345678-9", "Error RUT", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Persona p = sistema.busquedaGlobalPersona(rut);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún ciudadano con RUT: " + rut, "No encontrado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Confirmar eliminación de:\n" + p.getPrimerNombre() + " " + p.getPrimerApellido() + "\nRUT: " + rut + "\n\nEsta acción no se puede deshacer.",
            "⚠ Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            // Buscar región automáticamente
            String nombreRegion = "";
            for (Region r : sistema.getRegiones().values()) {
                if (r.getCiudadanos().contains(p)) {
                    nombreRegion = r.getNombre();
                    break;
                }
            }

            if (!nombreRegion.isEmpty()) {
                boolean exito = sistema.eliminarPersona(nombreRegion, rut);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Registro eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo determinar la región del ciudadano.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
