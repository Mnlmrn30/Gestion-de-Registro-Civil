package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

public class VentanaInscribirNacimiento extends JFrame {

    private GestionSistema sistema;
    private JFrame ventanaAnterior;

    private JComboBox<String> cmbRegion, cmbSexo;
    private JTextField txtPrimerNombre, txtSegundoNombre;
    private JTextField txtPrimerApellido, txtSegundoApellido;
    private JTextField txtRutPadre, txtRutMadre;
    private JTextField spnDia, spnMes, spnAnio;

    public VentanaInscribirNacimiento(GestionSistema sistema, JFrame ventanaAnterior) {
        this.sistema = sistema;
        this.ventanaAnterior = ventanaAnterior;
        initComponents();
    }

    private void initComponents() {
        setTitle("Inscribir Nacimiento");
        setSize(460, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { volver(); }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(VentanaMenu.COLOR_FONDO);
        panelPrincipal.add(VentanaMenu.crearHeader("INSCRIBIR NACIMIENTO", "El RUT sera asignado automaticamente"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        cmbRegion          = new JComboBox<>(VentanaCiudadanos.NOMBRES_REGIONES);
        txtPrimerNombre    = new JTextField();
        txtSegundoNombre   = new JTextField();
        txtPrimerApellido  = new JTextField();
        txtSegundoApellido = new JTextField();
        cmbSexo = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        spnDia = new JTextField(3);
        spnMes = new JTextField(3);
        spnAnio = new JTextField(3);
        
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFecha.setOpaque(false);
        panelFecha.add(spnDia);
        panelFecha.add(new JLabel("/"));
        panelFecha.add(spnMes);
        panelFecha.add(new JLabel("/"));
        panelFecha.add(spnAnio);
    
        txtRutPadre = new JTextField();
        txtRutMadre = new JTextField();

        String[] labels = {
            "Región de nacimiento:", "Primer Nombre:", "Segundo Nombre:",
            "Primer Apellido:", "Segundo Apellido:", "Sexo:",
            "Fecha Nacimiento:",
            "RUT Padre (opcional):", "RUT Madre (opcional):"
        };
        Component[] campos = {
            cmbRegion, txtPrimerNombre, txtSegundoNombre,
            txtPrimerApellido, txtSegundoApellido, cmbSexo,
            panelFecha, // ¡Aquí metemos el panel que creaste arriba!
            txtRutPadre, txtRutMadre
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0.4;
            form.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 0.6;
            form.add(campos[i], gbc);
        }

        JScrollPane scrollForm = new JScrollPane(form);
        scrollForm.setBorder(BorderFactory.createEmptyBorder());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        footer.setBackground(VentanaMenu.COLOR_FONDO);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 15, 5, 15));
        JButton btnVolver    = VentanaMenu.crearBotonVolver("Volver");
        JButton btnRegistrar = VentanaMenu.crearBoton("Registrar Nacimiento");
        btnVolver.addActionListener(e -> volver());
        btnRegistrar.addActionListener(e -> registrar());
        footer.add(btnVolver);
        footer.add(btnRegistrar);

        panelPrincipal.add(scrollForm, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void volver() {
        if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
        dispose();
    }

    private void registrar() {
        try {
            String region    = (String) cmbRegion.getSelectedItem();
            String pNombre   = txtPrimerNombre.getText().trim();
            String sNombre   = txtSegundoNombre.getText().trim();
            String pApellido = txtPrimerApellido.getText().trim();
            String sApellido = txtSegundoApellido.getText().trim();
            String sexo      = (String) cmbSexo.getSelectedItem();
            int dia = Integer.parseInt(spnDia.getText().trim());
            int mes = Integer.parseInt(spnMes.getText().trim());
            int anio = Integer.parseInt(spnAnio.getText().trim());;
            String rutPadre  = txtRutPadre.getText().trim();
            String rutMadre  = txtRutMadre.getText().trim();

            if (pNombre.isEmpty() || pApellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El primer nombre y apellido son obligatorios.", "Campos vacios", JOptionPane.WARNING_MESSAGE);
                return;
            }
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
                    "Nacimiento registrado exitosamente.\n\nRUT asignado al recien nacido:\n" + rutGenerado,
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
                volver();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar. Verifique los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

