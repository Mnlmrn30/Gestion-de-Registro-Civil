package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana para emitir certificados de nacimiento o matrimonio.
 */
public class VentanaCertificados extends JFrame {

    private GestionSistema sistema;
    private JTextField txtRut;
    private JTextArea areaCertificado;
    private JComboBox<String> cmbTipoCertificado;

    public VentanaCertificados(GestionSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }

    private void initComponents() {
        setTitle("Emitir Certificados");
        setSize(540, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("EMISIÓN DE CERTIFICADOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        titulo.setForeground(new Color(30, 60, 120));

        JPanel panelEntrada = new JPanel(new GridLayout(2, 1, 5, 8));
        panelEntrada.setBackground(Color.WHITE);
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        JPanel filaRut = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filaRut.setBackground(Color.WHITE);
        txtRut = new JTextField(18);
        filaRut.add(new JLabel("RUT ciudadano (ej: 12345678-9):"));
        filaRut.add(txtRut);

        JPanel filaTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filaTipo.setBackground(Color.WHITE);
        cmbTipoCertificado = new JComboBox<>(new String[]{
            "1. Certificado de Nacimiento / Antecedentes Básicos",
            "2. Certificado de Matrimonio"
        });
        cmbTipoCertificado.setPreferredSize(new Dimension(350, 25));
        JButton btnEmitir = new JButton("Emitir");
        btnEmitir.setBackground(new Color(68, 140, 68));
        btnEmitir.setForeground(Color.WHITE);
        btnEmitir.setFont(new Font("Arial", Font.BOLD, 12));
        btnEmitir.setFocusPainted(false);
        btnEmitir.setBorderPainted(false);
        filaTipo.add(new JLabel("Tipo:"));
        filaTipo.add(cmbTipoCertificado);
        filaTipo.add(btnEmitir);

        panelEntrada.add(filaRut);
        panelEntrada.add(filaTipo);

        areaCertificado = new JTextArea();
        areaCertificado.setEditable(false);
        areaCertificado.setFont(new Font("Courier New", Font.PLAIN, 12));
        areaCertificado.setBackground(new Color(250, 252, 255));
        areaCertificado.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scroll = new JScrollPane(areaCertificado);
        scroll.setBorder(BorderFactory.createTitledBorder("Certificado Emitido"));

        btnEmitir.addActionListener(e -> emitir());

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(panelEntrada, BorderLayout.CENTER);
        panel.add(scroll, BorderLayout.SOUTH);
        scroll.setPreferredSize(new Dimension(0, 270));

        add(panel);
    }

    private void emitir() {
        String rut = txtRut.getText().trim();

        if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto. Ejemplo: 12345678-9", "Error RUT", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Persona p = sistema.busquedaGlobalPersona(rut);
        String region = sistema.obtenerRegionDePersona(rut);

        if (p == null) {
            areaCertificado.setText("Error: No se encontró ningún ciudadano con RUT " + rut);
            return;
        }

        int tipo = cmbTipoCertificado.getSelectedIndex();
        StringBuilder sb = new StringBuilder();
        sb.append("=======================================================\n");
        sb.append("            REPÚBLICA DE CHILE                         \n");
        sb.append("   SERVICIO DE REGISTRO CIVIL E IDENTIFICACIÓN         \n");
        sb.append("=======================================================\n");

        if (tipo == 0) {
            sb.append("           CERTIFICADO DE NACIMIENTO                  \n");
            sb.append("-------------------------------------------------------\n");
            sb.append(String.format("%-20s: %s\n", "RUT", p.getRut()));
            sb.append(String.format("%-20s: %s %s\n", "NOMBRES", p.getPrimerNombre(), p.getSegundoNombre()));
            sb.append(String.format("%-20s: %s %s\n", "APELLIDOS", p.getPrimerApellido(), p.getSegundoApellido()));
            sb.append(String.format("%-20s: %d/%d/%d\n", "FECHA NACIMIENTO", p.getDiaNacimiento(), p.getMesNacimiento(), p.getAñoNacimiento()));
            sb.append(String.format("%-20s: %s\n", "SEXO", p.getSexo()));
            sb.append(String.format("%-20s: %s\n", "REGIÓN", region));
            if (p.getPadre() != null) {
                sb.append("\n--- PADRE ---\n");
                sb.append(String.format("%-20s: %s\n", "RUT PADRE", p.getPadre().getRut()));
                sb.append(String.format("%-20s: %s %s\n", "NOMBRE PADRE", p.getPadre().getPrimerNombre(), p.getPadre().getSegundoNombre()));
            }
            if (p.getMadre() != null) {
                sb.append("\n--- MADRE ---\n");
                sb.append(String.format("%-20s: %s\n", "RUT MADRE", p.getMadre().getRut()));
                sb.append(String.format("%-20s: %s %s\n", "NOMBRE MADRE", p.getMadre().getPrimerNombre(), p.getMadre().getSegundoNombre()));
            }
        } else {
            sb.append("           CERTIFICADO DE MATRIMONIO                  \n");
            sb.append("-------------------------------------------------------\n");
            if ("Casado/a".equals(p.getEstadoCivil()) && p.getConyuge() != null) {
                sb.append(String.format("%-22s: %s\n", "RUT CONTRAYENTE 1", p.getRut()));
                sb.append(String.format("%-22s: %s %s\n", "NOMBRE CONTRAYENTE 1", p.getPrimerNombre(), p.getPrimerApellido()));
                sb.append("\n");
                sb.append(String.format("%-22s: %s\n", "RUT CONTRAYENTE 2", p.getConyuge().getRut()));
                sb.append(String.format("%-22s: %s %s\n", "NOMBRE CONTRAYENTE 2", p.getConyuge().getPrimerNombre(), p.getConyuge().getPrimerApellido()));
                sb.append("\n");
                sb.append(String.format("%-22s: VIGENTE\n", "ESTADO DEL VÍNCULO"));
            } else {
                sb.append("El ciudadano no registra un matrimonio vigente.");
            }
        }
        sb.append("=======================================================\n");
        areaCertificado.setText(sb.toString());
    }
}