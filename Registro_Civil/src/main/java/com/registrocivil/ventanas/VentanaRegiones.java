package com.registrocivil.ventanas;
 
import com.registrocivil.logica.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
 
/**
 * Ventana para consultar estadísticas y listados de regiones.
 */
public class VentanaRegiones extends JFrame {
 
    private GestionSistema sistema;
    private JTextArea areaResultado;
 
    private static final String[] NOMBRES_REGIONES = {
        "Arica y Parinacota", "Tarapaca", "Antofagasta", "Atacama",
        "Coquimbo", "Valparaiso", "Región Metropolitana", "O'Higgins",
        "Maule", "Ñuble", "Biobio", "La Araucania", "Los Rios",
        "Los Lagos", "Aysen", "Magallanes"
    };
 
    public VentanaRegiones(GestionSistema sistema) {
        this.sistema = sistema;
        initComponents();
    }
 
    private void initComponents() {
        setTitle("Gestión de Regiones");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(240, 245, 255));
 
        // Titulo
        JLabel titulo = new JLabel("GESTIÓN DE REGIONES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 17));
        titulo.setForeground(new Color(30, 60, 120));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
 
        // Panel de botones izquierda
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 5, 10));
        panelBotones.setBackground(new Color(240, 245, 255));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panelBotones.setPreferredSize(new Dimension(220, 0));
 
        JButton btnTodasRegiones  = crearBoton("Mostrar todas las regiones",  new Color(46, 117, 182));
        JButton btnListadoCiudadanos = crearBoton("Ciudadanos por región",       new Color(46, 117, 182));
        JButton btnMatrimonios     = crearBoton("Matrimonios por región",      new Color(46, 117, 182));
        JButton btnEstadisticas    = crearBoton("Estadísticas generales",      new Color(68, 140, 68));
 
        btnTodasRegiones.addActionListener(e -> mostrarTodasRegiones());
        btnListadoCiudadanos.addActionListener(e -> mostrarCiudadanosPorRegion());
        btnMatrimonios.addActionListener(e -> mostrarMatrimoniosPorRegion());
        btnEstadisticas.addActionListener(e -> mostrarEstadisticasGenerales());
 
        panelBotones.add(btnTodasRegiones);
        panelBotones.add(btnListadoCiudadanos);
        panelBotones.add(btnMatrimonios);
        panelBotones.add(btnEstadisticas);
 
        // Area de resultados
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaResultado.setBackground(new Color(250, 252, 255));
        areaResultado.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));
 
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.WEST);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
 
        add(panelPrincipal);
    }
 
    private void mostrarTodasRegiones() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== INFORMACIÓN DE TODAS LAS REGIONES ===\n\n");
        HashMap<String, Region> regiones = sistema.getRegiones();
        if (regiones.isEmpty()) {
            sb.append("No hay regiones cargadas.");
        } else {
            for (String nombre : NOMBRES_REGIONES) {
                Region r = regiones.get(nombre);
                if (r != null) {
                    int vivos = sistema.obtenerVivosPorRegion(r.getNombre());
                    int fallecidos = sistema.obtenerFallecidosPorRegion(r.getNombre());
                    sb.append("Región: ").append(r.getNombre()).append("\n");
                    sb.append("  Población total inscrita: ").append(r.getNumeroHabitantes()).append("\n");
                    sb.append("  Ciudadanos vivos:         ").append(vivos).append("\n");
                    sb.append("  Defunciones:              ").append(fallecidos).append("\n");
                    sb.append("------------------------------------------------\n");
                }
            }
        }
        areaResultado.setText(sb.toString());
    }
 
    private void mostrarCiudadanosPorRegion() {
        String regionSeleccionada = (String) JOptionPane.showInputDialog(
            this, "Seleccione la región:", "Ciudadanos por Región",
            JOptionPane.PLAIN_MESSAGE, null, NOMBRES_REGIONES, NOMBRES_REGIONES[0]
        );
        if (regionSeleccionada == null) return;
 
        Region r = sistema.getRegiones().get(regionSeleccionada);
        StringBuilder sb = new StringBuilder();
        sb.append("=== CIUDADANOS EN ").append(regionSeleccionada.toUpperCase()).append(" ===\n\n");
 
        if (r == null || r.getCiudadanos().isEmpty()) {
            sb.append("No hay ciudadanos registrados en esta región.");
        } else {
            for (Persona p : r.getCiudadanos()) {
                sb.append(p.toString()).append("\n");
            }
        }
        areaResultado.setText(sb.toString());
    }
 
    private void mostrarMatrimoniosPorRegion() {
        String regionSeleccionada = (String) JOptionPane.showInputDialog(
            this, "Seleccione la región:", "Matrimonios por Región",
            JOptionPane.PLAIN_MESSAGE, null, NOMBRES_REGIONES, NOMBRES_REGIONES[0]
        );
        if (regionSeleccionada == null) return;
 
        Region r = sistema.getRegiones().get(regionSeleccionada);
        StringBuilder sb = new StringBuilder();
        sb.append("=== MATRIMONIOS EN ").append(regionSeleccionada.toUpperCase()).append(" ===\n\n");
 
        if (r == null) {
            sb.append("Región no encontrada.");
            areaResultado.setText(sb.toString());
            return;
        }
 
        HashSet<String> procesados = new HashSet<>();
        boolean hayMatrimonios = false;
 
        for (Persona p : r.getCiudadanos()) {
            Persona conyuge = p.getConyuge();
            if (conyuge != null && !procesados.contains(p.getRut())) {
                hayMatrimonios = true;
                sb.append("MATRIMONIO:\n");
                sb.append("  ").append(p.toString()).append("\n");
                sb.append("  ").append(conyuge.toString()).append("\n");
                sb.append("------------------------------------------------\n");
                procesados.add(p.getRut());
                procesados.add(conyuge.getRut());
            }
        }
 
        if (!hayMatrimonios) sb.append("No hay matrimonios activos registrados en esta región.");
        areaResultado.setText(sb.toString());
    }
 
    private void mostrarEstadisticasGenerales() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADÍSTICAS GENERALES NACIONALES ===\n\n");
 
        int totalNacional = 0, totalVivos = 0, totalFallecidos = 0;
        int totalHombres = 0, totalMujeres = 0, otros = 0;
 
        for (String nombre : NOMBRES_REGIONES) {
            Region r = sistema.getRegiones().get(nombre);
            if (r != null && r.getNumeroHabitantes() > 0) {
                int vivos = sistema.obtenerVivosPorRegion(r.getNombre());
                int fallecidos = sistema.obtenerFallecidosPorRegion(r.getNombre());
                totalNacional += r.getNumeroHabitantes();
                totalVivos += vivos;
                totalFallecidos += fallecidos;
 
                sb.append("REGIÓN: ").append(r.getNombre().toUpperCase()).append("\n");
                sb.append("  Total inscritos: ").append(r.getNumeroHabitantes())
                  .append(" | Vivos: ").append(vivos)
                  .append(" | Fallecidos: ").append(fallecidos).append("\n");
 
                for (Persona p : r.getCiudadanos()) {
                    String sexo = p.getSexo().toLowerCase();
                    if (sexo.equals("masculino")) totalHombres++;
                    else if (sexo.equals("femenino")) totalMujeres++;
                    else otros++;
                }
            }
        }
 
        sb.append("\n=== RESUMEN NACIONAL ===\n");
        sb.append("Población total histórica: ").append(totalNacional).append("\n");
        sb.append("Ciudadanos vivos:          ").append(totalVivos).append("\n");
        sb.append("Defunciones registradas:   ").append(totalFallecidos).append("\n");
        sb.append("Total hombres:             ").append(totalHombres).append("\n");
        sb.append("Total mujeres:             ").append(totalMujeres).append("\n");
        if (otros > 0) sb.append("Otros:                     ").append(otros).append("\n");
 
        areaResultado.setText(sb.toString());
    }
 
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton("<html><center>" + texto + "</center></html>");
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 55));
        return btn;
    }
}