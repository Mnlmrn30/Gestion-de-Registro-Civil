package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

public class VentanaInscribirCiudadano extends JFrame {

    private GestionSistema sistema;
    private JFrame ventanaAnterior;

    private JComboBox<String> cmbRegion;
    private JTextField txtRut, txtPrimerNombre, txtSegundoNombre;
    private JTextField txtPrimerApellido, txtSegundoApellido;
    private JComboBox<String> cmbSexo;
    private JTextField spnDia, spnMes, spnAnio;

    public VentanaInscribirCiudadano(GestionSistema sistema, JFrame ventanaAnterior) {
        this.sistema = sistema;
        this.ventanaAnterior = ventanaAnterior;
        initComponents();
    }

    private void initComponents() {
        setTitle("Inscribir Ciudadano");
        setSize(480, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { volver(); }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(VentanaMenu.COLOR_FONDO);
        panelPrincipal.add(VentanaMenu.crearHeader("INSCRIBIR CIUDADANO", "Registro General"), BorderLayout.NORTH);

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        cmbRegion          = new JComboBox<>(VentanaCiudadanos.NOMBRES_REGIONES);
        txtRut             = new JTextField();
        txtPrimerNombre    = new JTextField();
        txtSegundoNombre   = new JTextField();
        txtPrimerApellido  = new JTextField();
        txtSegundoApellido = new JTextField();
        cmbSexo = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        spnDia = new JTextField(3);  
        spnMes = new JTextField(3);
        spnAnio = new JTextField(4);
        
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelFecha.setOpaque(false);
        panelFecha.add(spnDia);
        panelFecha.add(new JLabel("/"));
        panelFecha.add(spnMes);
        panelFecha.add(new JLabel("/"));
        panelFecha.add(spnAnio);

        String[] labels = {"Región:", "RUT (ej: 12345678-9):", "Primer Nombre:", "Segundo Nombre:",
                           "Primer Apellido:", "Segundo Apellido:", "Sexo:", "Fecha Nac. (DD/MM/AAAA):"};
        
        Component[] campos = {cmbRegion, txtRut, txtPrimerNombre, txtSegundoNombre,
                              txtPrimerApellido, txtSegundoApellido, cmbSexo, panelFecha};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0.38;
            form.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 0.62;
            form.add(campos[i], gbc);
        }

        JScrollPane scrollForm = new JScrollPane(form);
        scrollForm.setBorder(BorderFactory.createEmptyBorder());

        // Footer con botones
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        footer.setBackground(VentanaMenu.COLOR_FONDO);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 15, 5, 15));
        
        JButton btnVolver    = VentanaMenu.crearBotonVolver("Volver");
        JButton btnInscribir = VentanaMenu.crearBoton("Inscribir Ciudadano");
        
        btnVolver.addActionListener(e -> volver());
        btnInscribir.addActionListener(e -> inscribir());
        
        footer.add(btnVolver);
        footer.add(btnInscribir);
        
        panelPrincipal.add(scrollForm, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }

    private void volver() {
        if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
        dispose();
    }

    private void inscribir() {
        try {
            String region = (String) cmbRegion.getSelectedItem();
            String rut    = txtRut.getText().trim();

            if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
                JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto.\nEjemplo: 12345678-9", "Error RUT", JOptionPane.ERROR_MESSAGE);
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
            
            int dia = Integer.parseInt(spnDia.getText().trim());
            int mes = Integer.parseInt(spnMes.getText().trim());
            int anio = Integer.parseInt(spnAnio.getText().trim());

            if (pNombre.isEmpty() || pApellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El primer nombre y apellido son obligatorios.", "Campos vacios", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean exito = sistema.registrarPersona(region, rut, pNombre, sNombre, pApellido, sApellido, sexo, dia, mes, anio);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Ciudadano registrado con exito en " + region + ".", "Exito", JOptionPane.INFORMATION_MESSAGE);
                volver();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar. Verifique los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
