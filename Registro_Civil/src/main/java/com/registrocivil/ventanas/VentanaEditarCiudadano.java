package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

public class VentanaEditarCiudadano extends JFrame {

    private GestionSistema sistema;
    private JFrame ventanaAnterior;
    private JTextField txtRutBuscar;
    private JTextField txtPrimerNombre, txtSegundoNombre, txtPrimerApellido, txtSegundoApellido;
    private JTextField txtRutPadre, txtRutMadre;
    private JComboBox<String> cmbSexo;
    private JSpinner spnDia, spnMes, spnAnio;
    private JPanel panelDatos;
    private JButton btnGuardar;
    private Persona personaActual;

    public VentanaEditarCiudadano(GestionSistema sistema, JFrame ventanaAnterior) {
        this.sistema = sistema;
        this.ventanaAnterior = ventanaAnterior;
        initComponents();
    }

    private void initComponents() {
        setTitle("Editar Ciudadano");
        setSize(480, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { volver(); }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 0));
        panelPrincipal.setBackground(VentanaMenu.COLOR_FONDO);
        panelPrincipal.add(VentanaMenu.crearHeader("EDITAR REGISTRO DE CIUDADANO"), BorderLayout.NORTH);

        // Barra de busqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 210, 230)));
        txtRutBuscar = new JTextField(16);
        JButton btnBuscar = VentanaMenu.crearBoton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(90, 32));
        panelBusqueda.add(new JLabel("RUT a editar:"));
        panelBusqueda.add(txtRutBuscar);
        panelBusqueda.add(btnBuscar);

        // Formulario de edicion
        panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        panelDatos.setVisible(false);

        txtPrimerNombre    = new JTextField();
        txtSegundoNombre   = new JTextField();
        txtPrimerApellido  = new JTextField();
        txtSegundoApellido = new JTextField();
        cmbSexo = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        spnDia  = new JSpinner(new SpinnerNumberModel(1,    1,    31,   1));
        spnMes  = new JSpinner(new SpinnerNumberModel(1,    1,    12,   1));
        spnAnio = new JSpinner(new SpinnerNumberModel(2000, 1900, 2026, 1));
        txtRutPadre = new JTextField();
        txtRutMadre = new JTextField();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 5, 4, 5);

        String[]    labels = {"Primer Nombre:", "Segundo Nombre:", "Primer Apellido:", "Segundo Apellido:",
                              "Sexo:", "Dia Nacimiento:", "Mes Nacimiento:", "Anio Nacimiento:",
                              "RUT Padre (opcional):", "RUT Madre (opcional):"};
        Component[] campos = {txtPrimerNombre, txtSegundoNombre, txtPrimerApellido, txtSegundoApellido,
                              cmbSexo, spnDia, spnMes, spnAnio, txtRutPadre, txtRutMadre};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0.38;
            panelDatos.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 0.62;
            panelDatos.add(campos[i], gbc);
        }

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(Color.WHITE);
        panelCentro.add(panelBusqueda, BorderLayout.NORTH);
        JScrollPane scrollDatos = new JScrollPane(panelDatos);
        scrollDatos.setBorder(BorderFactory.createEmptyBorder());
        panelCentro.add(scrollDatos, BorderLayout.CENTER);

        // Footer con botones
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        footer.setBackground(VentanaMenu.COLOR_FONDO);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 15, 5, 15));
        JButton btnVolver = VentanaMenu.crearBotonVolver("Volver");
        btnGuardar = VentanaMenu.crearBoton("Guardar Cambios");
        btnGuardar.setVisible(false);
        btnVolver.addActionListener(e -> volver());
        btnGuardar.addActionListener(e -> guardarCambios());
        footer.add(btnVolver);
        footer.add(btnGuardar);

        btnBuscar.addActionListener(e -> buscarCiudadano());
        txtRutBuscar.addActionListener(e -> buscarCiudadano());

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void volver() {
        if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
        dispose();
    }

    private void buscarCiudadano() {
        String rut = txtRutBuscar.getText().trim();
        if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto.\nEjemplo: 12345678-9", "Error RUT", JOptionPane.ERROR_MESSAGE);
            return;
        }
        personaActual = sistema.busquedaGlobalPersona(rut);
        if (personaActual == null) {
            JOptionPane.showMessageDialog(this, "No se encontro ciudadano con RUT: " + rut, "No encontrado", JOptionPane.WARNING_MESSAGE);
            panelDatos.setVisible(false);
            btnGuardar.setVisible(false);
            return;
        }
        txtPrimerNombre.setText(personaActual.getPrimerNombre());
        txtSegundoNombre.setText(personaActual.getSegundoNombre());
        txtPrimerApellido.setText(personaActual.getPrimerApellido());
        txtSegundoApellido.setText(personaActual.getSegundoApellido());
        cmbSexo.setSelectedItem(personaActual.getSexo());
        spnDia.setValue(personaActual.getDiaNacimiento());
        spnMes.setValue(personaActual.getMesNacimiento());
        spnAnio.setValue(personaActual.getAñoNacimiento());
        txtRutPadre.setText(personaActual.getPadre() != null ? personaActual.getPadre().getRut() : "");
        txtRutMadre.setText(personaActual.getMadre() != null ? personaActual.getMadre().getRut() : "");
        panelDatos.setVisible(true);
        btnGuardar.setVisible(true);
        revalidate(); repaint();
    }

    private void guardarCambios() {
        if (personaActual == null) return;
        try {
            String pNombre   = txtPrimerNombre.getText().trim();
            String sNombre   = txtSegundoNombre.getText().trim();
            String pApellido = txtPrimerApellido.getText().trim();
            String sApellido = txtSegundoApellido.getText().trim();
            String sexo      = (String) cmbSexo.getSelectedItem();
            int dia  = (int) spnDia.getValue();
            int mes  = (int) spnMes.getValue();
            int anio = (int) spnAnio.getValue();

            if (pNombre.isEmpty() || pApellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El primer nombre y apellido son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            sistema.editarPersona(personaActual.getRut(), pNombre, sNombre, pApellido, sApellido, sexo, dia, mes, anio);

            String rutPadre = txtRutPadre.getText().trim();
            if (!rutPadre.isEmpty()) {
                if (!rutPadre.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
                    JOptionPane.showMessageDialog(this, "Formato de RUT del padre incorrecto.", "Error RUT", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Persona padre = sistema.busquedaGlobalPersona(rutPadre);
                if (padre != null) personaActual.setPadre(padre);
                else JOptionPane.showMessageDialog(this, "No se encontro padre con RUT: " + rutPadre, "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            String rutMadre = txtRutMadre.getText().trim();
            if (!rutMadre.isEmpty()) {
                if (!rutMadre.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
                    JOptionPane.showMessageDialog(this, "Formato de RUT de la madre incorrecto.", "Error RUT", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Persona madre = sistema.busquedaGlobalPersona(rutMadre);
                if (madre != null) personaActual.setMadre(madre);
                else JOptionPane.showMessageDialog(this, "No se encontro madre con RUT: " + rutMadre, "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            JOptionPane.showMessageDialog(this, "Ciudadano actualizado con exito.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            volver();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}