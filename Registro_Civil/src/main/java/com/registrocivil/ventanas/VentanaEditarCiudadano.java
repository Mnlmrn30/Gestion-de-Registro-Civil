package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana para editar los datos de un ciudadano existente.
 */
public class VentanaEditarCiudadano extends JFrame {

    private GestionSistema sistema;
    private String[] regiones;
    private JTextField txtRutBuscar;
    private JTextField txtPrimerNombre, txtSegundoNombre, txtPrimerApellido, txtSegundoApellido;
    private JTextField txtRutPadre, txtRutMadre;
    private JComboBox<String> cmbSexo;
    private JSpinner spnDia, spnMes, spnAnio;
    private JPanel panelDatos;
    private Persona personaActual;

    public VentanaEditarCiudadano(GestionSistema sistema, String[] regiones) {
        this.sistema = sistema;
        this.regiones = regiones;
        initComponents();
    }

    private void initComponents() {
        setTitle("Editar Ciudadano");
        setSize(480, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panelPrincipal.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("EDITAR REGISTRO DE CIUDADANO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setForeground(new Color(150, 100, 30));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBusqueda.setBackground(Color.WHITE);
        txtRutBuscar = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(46, 117, 182));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        panelBusqueda.add(new JLabel("RUT a editar:"));
        panelBusqueda.add(txtRutBuscar);
        panelBusqueda.add(btnBuscar);

        // Panel de datos (inicialmente oculto)
        panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setVisible(false);

        txtPrimerNombre    = new JTextField();
        txtSegundoNombre   = new JTextField();
        txtPrimerApellido  = new JTextField();
        txtSegundoApellido = new JTextField();
        cmbSexo = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        spnDia  = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes  = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        spnAnio = new JSpinner(new SpinnerNumberModel(2000, 1900, 2026, 1));
        txtRutPadre = new JTextField();
        txtRutMadre = new JTextField();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 5, 4, 5);

        String[] etiquetas = {"Primer Nombre:", "Segundo Nombre:", "Primer Apellido:", "Segundo Apellido:",
                              "Sexo:", "Día Nac.:", "Mes Nac.:", "Año Nac.:",
                              "RUT Padre (opcional):", "RUT Madre (opcional):"};
        Component[] campos = {txtPrimerNombre, txtSegundoNombre, txtPrimerApellido, txtSegundoApellido,
                              cmbSexo, spnDia, spnMes, spnAnio, txtRutPadre, txtRutMadre};

        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0.35;
            panelDatos.add(new JLabel(etiquetas[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 0.65;
            panelDatos.add(campos[i], gbc);
        }

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(150, 100, 30));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        gbc.gridx = 0; gbc.gridy = etiquetas.length; gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 5, 5, 5);
        panelDatos.add(btnGuardar, gbc);

        btnBuscar.addActionListener(e -> buscarCiudadano());
        txtRutBuscar.addActionListener(e -> buscarCiudadano());
        btnGuardar.addActionListener(e -> guardarCambios());

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(Color.WHITE);
        panelTop.add(titulo, BorderLayout.NORTH);
        panelTop.add(panelBusqueda, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(panelDatos);
        scroll.setBorder(BorderFactory.createTitledBorder("Datos del ciudadano"));

        panelPrincipal.add(panelTop, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        add(panelPrincipal);
    }

    private void buscarCiudadano() {
        String rut = txtRutBuscar.getText().trim();
        if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        personaActual = sistema.busquedaGlobalPersona(rut);
        if (personaActual == null) {
            JOptionPane.showMessageDialog(this, "No se encontró ciudadano con RUT: " + rut, "No encontrado", JOptionPane.WARNING_MESSAGE);
            panelDatos.setVisible(false);
            return;
        }

        // Llenar campos con datos actuales
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
        revalidate();
        repaint();
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

            // Actualizar padre
            String rutPadre = txtRutPadre.getText().trim();
            if (!rutPadre.isEmpty()) {
                if (!rutPadre.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
                    JOptionPane.showMessageDialog(this, "Formato de RUT del padre incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Persona padre = sistema.busquedaGlobalPersona(rutPadre);
                if (padre != null) personaActual.setPadre(padre);
                else JOptionPane.showMessageDialog(this, "No se encontró padre con RUT: " + rutPadre, "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            // Actualizar madre
            String rutMadre = txtRutMadre.getText().trim();
            if (!rutMadre.isEmpty()) {
                if (!rutMadre.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
                    JOptionPane.showMessageDialog(this, "Formato de RUT de la madre incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Persona madre = sistema.busquedaGlobalPersona(rutMadre);
                if (madre != null) personaActual.setMadre(madre);
                else JOptionPane.showMessageDialog(this, "No se encontró madre con RUT: " + rutMadre, "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            JOptionPane.showMessageDialog(this, "¡Ciudadano actualizado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}