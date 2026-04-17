package com.registrocivil.ventanas;

import com.registrocivil.logica.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class VentanaBuscarCiudadano extends JFrame {

    private GestionSistema sistema;
    private JFrame ventanaAnterior;
    private JComboBox<String> cmbTipoBusqueda;
    private JPanel panelInputsBusqueda;
    private JTextField txtRut;
    private JTextField txtPrimerNombre;
    private JTextField txtPrimerApellido;
    
    private JTextArea areaResultado;

    public VentanaBuscarCiudadano(GestionSistema sistema, JFrame ventanaAnterior) {
        this.sistema = sistema;
        this.ventanaAnterior = ventanaAnterior;
        initComponents();
    }

    private void initComponents() {
        setTitle("Buscar Ciudadano");
        setSize(500, 500); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { volver(); }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 0));
        panelPrincipal.setBackground(VentanaMenu.COLOR_FONDO);
        panelPrincipal.add(VentanaMenu.crearHeader("BUSCAR CIUDADANO", "Búsqueda por RUT o Nombre"), BorderLayout.NORTH);

 
        JPanel panelBusquedaTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusquedaTop.setBackground(Color.WHITE);
        cmbTipoBusqueda = new JComboBox<>(new String[]{"Buscar por RUT", "Buscar por Nombre y Apellido"});
        panelBusquedaTop.add(new JLabel("Tipo de búsqueda:"));
        panelBusquedaTop.add(cmbTipoBusqueda);
        
        panelInputsBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelInputsBusqueda.setBackground(Color.WHITE);
        
        txtRut = new JTextField(15);
        txtPrimerNombre = new JTextField(10);
        txtPrimerApellido = new JTextField(10);
        
        JButton btnBuscar = VentanaMenu.crearBoton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(90, 32));
        
        panelInputsBusqueda.add(new JLabel("RUT (ej: 12345678-9):"));
        panelInputsBusqueda.add(txtRut);
        panelInputsBusqueda.add(btnBuscar);
        

        cmbTipoBusqueda.addActionListener(e -> {
            panelInputsBusqueda.removeAll();
            
            if (cmbTipoBusqueda.getSelectedIndex() == 0) {
                panelInputsBusqueda.add(new JLabel("RUT (ej: 12345678-9):"));
                panelInputsBusqueda.add(txtRut);
            } else {
                panelInputsBusqueda.add(new JLabel("Nombre:"));
                panelInputsBusqueda.add(txtPrimerNombre);
                panelInputsBusqueda.add(new JLabel("Apellido:"));
                panelInputsBusqueda.add(txtPrimerApellido);
            }
            panelInputsBusqueda.add(btnBuscar);
            
            panelInputsBusqueda.revalidate();
            panelInputsBusqueda.repaint();
        });

        JPanel panelControles = new JPanel(new BorderLayout());
        panelControles.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 210, 230)));
        panelControles.add(panelBusquedaTop, BorderLayout.NORTH);
        panelControles.add(panelInputsBusqueda, BorderLayout.CENTER);


        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaResultado.setBackground(Color.WHITE);
        areaResultado.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultado"));

        btnBuscar.addActionListener(e -> buscar());
        txtRut.addActionListener(e -> buscar());
        txtPrimerNombre.addActionListener(e -> buscar());
        txtPrimerApellido.addActionListener(e -> buscar());

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

    private void buscar() {
        areaResultado.setText(""); 
        List<Persona> listaEncontrados = new ArrayList<>(); 
        
        if (cmbTipoBusqueda.getSelectedIndex() == 0) {
            String rut = txtRut.getText().trim();
            if (!rut.matches("^[0-9]{7,8}-[0-9Kk]{1}$")) {
                areaResultado.setText("Error: Formato de RUT incorrecto.\nEjemplo: 12345678-9");
                return;
            }
            Persona p = sistema.busquedaGlobalPersona(rut);
            if (p != null) {
                listaEncontrados.add(p);
            } else {
                areaResultado.setText("No se encontro ningun ciudadano con RUT: " + rut);
                return;
            }
        } else {
            String nombre = txtPrimerNombre.getText().trim();
            String apellido = txtPrimerApellido.getText().trim();
            
            if (nombre.isEmpty() || apellido.isEmpty()) {
                areaResultado.setText("Error: Debe ingresar tanto el nombre como el apellido.");
                return;
            }
            
            listaEncontrados = sistema.busquedaGlobalPersona(nombre, apellido);
            if (listaEncontrados.isEmpty()) {
                areaResultado.setText("No se encontraron ciudadanos con el nombre: " + nombre + " " + apellido);
                return;
            }
        }
        
        StringBuilder sb = new StringBuilder();
        if (listaEncontrados.size() > 1) {
            sb.append("Se encontraron ").append(listaEncontrados.size()).append(" coincidencias:\n\n");
        }
        
        for (Persona p : listaEncontrados) {
            String region = sistema.obtenerRegionDePersona(p.getRut());
            
            sb.append("--- RESULTADO ---\n");
            sb.append(String.format("%-20s: %s\n", "RUT", p.getRut()));
            sb.append(String.format("%-20s: %s %s %s %s\n", "Nombre completo",
                p.getPrimerNombre(), p.getSegundoNombre(), p.getPrimerApellido(), p.getSegundoApellido()));
            sb.append(String.format("%-20s: %s\n", "Sexo", p.getSexo()));
            sb.append(String.format("%-20s: %d/%d/%d\n", "Fecha nacimiento",
                p.getDiaNacimiento(), p.getMesNacimiento(), p.getAñoNacimiento()));
            sb.append(String.format("%-20s: %s\n", "Region", region));
            sb.append(String.format("%-20s: %s\n", "Estado vital", p.getEstadoVital()));
            
            if (!"Fallecido".equalsIgnoreCase(p.getEstadoVital()))
                sb.append(String.format("%-20s: %s\n", "Estado civil", p.getEstadoCivil()));
                
            if (p.getConyuge() != null)
                sb.append(String.format("%-20s: %s %s (RUT: %s)\n", "Conyuge",
                    p.getConyuge().getPrimerNombre(), p.getConyuge().getPrimerApellido(), p.getConyuge().getRut()));
                    
            if (p.getPadre() != null)
                sb.append(String.format("%-20s: %s %s\n", "Padre",
                    p.getPadre().getPrimerNombre(), p.getPadre().getPrimerApellido()));
                    
            if (p.getMadre() != null)
                sb.append(String.format("%-20s: %s %s\n", "Madre",
                    p.getMadre().getPrimerNombre(), p.getMadre().getPrimerApellido()));
                    
            sb.append("\n"); 
        }
        
        areaResultado.setText(sb.toString());
        areaResultado.setCaretPosition(0);
    }
}