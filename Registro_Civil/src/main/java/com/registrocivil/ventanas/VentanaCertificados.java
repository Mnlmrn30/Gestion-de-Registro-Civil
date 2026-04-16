package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;

public class VentanaCertificados extends JFrame {

    private GestionSistema sistema;
    private JFrame ventanaAnterior;
    private JTextField txtRut;
    private JTextArea areaCertificado;
    private JComboBox<String> cmbTipo;

    public VentanaCertificados(GestionSistema sistema, JFrame ventanaAnterior) {
        this.sistema = sistema;
        this.ventanaAnterior = ventanaAnterior;
        initComponents();
    }

    private void initComponents() {
        setTitle("Emitir Certificados");
        setSize(560, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { volver(); }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 0));
        panelPrincipal.setBackground(VentanaMenu.COLOR_FONDO);
        panelPrincipal.add(VentanaMenu.crearHeader("EMISION DE CERTIFICADOS"), BorderLayout.NORTH);

        // Panel de controles
        JPanel panelControles = new JPanel(new GridLayout(2, 1, 0, 6));
        panelControles.setBackground(Color.WHITE);
        panelControles.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JPanel filaRut = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filaRut.setBackground(Color.WHITE);
        txtRut = new JTextField(18);
        filaRut.add(new JLabel("RUT ciudadano (ej: 12345678-9):"));
        filaRut.add(txtRut);

        JPanel filaTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filaTipo.setBackground(Color.WHITE);
        cmbTipo = new JComboBox<>(new String[]{
            "1. Certificado de Nacimiento / Antecedentes Basicos",
            "2. Certificado de Matrimonio"
        });
        cmbTipo.setPreferredSize(new Dimension(330, 26));
        JButton btnEmitir = VentanaMenu.crearBoton("Emitir");
        btnEmitir.setPreferredSize(new Dimension(90, 32));
        filaTipo.add(new JLabel("Tipo:"));
        filaTipo.add(cmbTipo);
        filaTipo.add(btnEmitir);

        panelControles.add(filaRut);
        panelControles.add(filaTipo);

        // Area del certificado
        areaCertificado = new JTextArea();
        areaCertificado.setEditable(false);
        areaCertificado.setFont(new Font("Courier New", Font.PLAIN, 12));
        areaCertificado.setBackground(Color.WHITE);
        areaCertificado.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        JScrollPane scroll = new JScrollPane(areaCertificado);
        scroll.setBorder(BorderFactory.createTitledBorder("Certificado Emitido"));

        btnEmitir.addActionListener(e -> emitir());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        footer.setBackground(VentanaMenu.COLOR_FONDO);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 12, 5, 12));
        JButton btnVolver = VentanaMenu.crearBotonVolver("Volver a Gestion de Ciudadanos");
        btnVolver.addActionListener(e -> volver());
        footer.add(btnVolver);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(panelControles, BorderLayout.NORTH);
        panelCentro.add(scroll, BorderLayout.CENTER);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void volver() {
        if (ventanaAnterior != null) ventanaAnterior.setVisible(true);
        dispose();
    }

    private void emitir() {
        String rut = txtRut.getText().trim();
        if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
            JOptionPane.showMessageDialog(this, "Formato de RUT incorrecto.\nEjemplo: 12345678-9", "Error RUT", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Persona p = sistema.busquedaGlobalPersona(rut);
        String region = sistema.obtenerRegionDePersona(rut);
        if (p == null) { areaCertificado.setText("Error: No se encontro ciudadano con RUT " + rut); return; }

        int tipo = cmbTipo.getSelectedIndex();
        StringBuilder sb = new StringBuilder();
        sb.append("=======================================================\n");
        sb.append("             REPUBLICA DE CHILE                        \n");
        sb.append("   SERVICIO DE REGISTRO CIVIL E IDENTIFICACION         \n");
        sb.append("=======================================================\n");

        if (tipo == 0) {
            sb.append("           CERTIFICADO DE NACIMIENTO                  \n");
            sb.append("-------------------------------------------------------\n");
            sb.append(String.format("%-22s: %s\n",   "RUT",              p.getRut()));
            sb.append(String.format("%-22s: %s %s\n","NOMBRES",          p.getPrimerNombre(),   p.getSegundoNombre()));
            sb.append(String.format("%-22s: %s %s\n","APELLIDOS",        p.getPrimerApellido(), p.getSegundoApellido()));
            sb.append(String.format("%-22s: %d/%d/%d\n","FECHA NACIMIENTO", p.getDiaNacimiento(), p.getMesNacimiento(), p.getAñoNacimiento()));
            sb.append(String.format("%-22s: %s\n",   "SEXO",             p.getSexo()));
            sb.append(String.format("%-22s: %s\n",   "REGION",           region));
            if (p.getPadre() != null) {
                sb.append("\n--- PADRE ---\n");
                sb.append(String.format("%-22s: %s\n",   "RUT PADRE",    p.getPadre().getRut()));
                sb.append(String.format("%-22s: %s %s\n","NOMBRE PADRE", p.getPadre().getPrimerNombre(), p.getPadre().getSegundoNombre()));
            }
            if (p.getMadre() != null) {
                sb.append("\n--- MADRE ---\n");
                sb.append(String.format("%-22s: %s\n",   "RUT MADRE",    p.getMadre().getRut()));
                sb.append(String.format("%-22s: %s %s\n","NOMBRE MADRE", p.getMadre().getPrimerNombre(), p.getMadre().getSegundoNombre()));
            }
        } else {
            sb.append("           CERTIFICADO DE MATRIMONIO                  \n");
            sb.append("-------------------------------------------------------\n");
            if ("Casado/a".equals(p.getEstadoCivil()) && p.getConyuge() != null) {
                sb.append(String.format("%-24s: %s\n",   "RUT CONTRAYENTE 1",    p.getRut()));
                sb.append(String.format("%-24s: %s %s\n","NOMBRE CONTRAYENTE 1", p.getPrimerNombre(), p.getPrimerApellido()));
                sb.append("\n");
                sb.append(String.format("%-24s: %s\n",   "RUT CONTRAYENTE 2",    p.getConyuge().getRut()));
                sb.append(String.format("%-24s: %s %s\n","NOMBRE CONTRAYENTE 2", p.getConyuge().getPrimerNombre(), p.getConyuge().getPrimerApellido()));
                sb.append("\n");
                sb.append(String.format("%-24s: VIGENTE\n","ESTADO DEL VINCULO"));
            } else {
                sb.append("El ciudadano no registra un matrimonio vigente.");
            }
        }
        sb.append("=======================================================\n");
        areaCertificado.setText(sb.toString());
        areaCertificado.setCaretPosition(0);
    }
}
