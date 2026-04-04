package com.registrocivil.window;

import com.registrocivil.logica.GestionSistema;
import javax.swing.JOptionPane;

public class VentanaMenu extends javax.swing.JFrame {
    private GestionSistema sistema;

    public VentanaMenu(GestionSistema sistemaRecibido) {
        initComponents();
        sistema = sistemaRecibido;
        setLocationRelativeTo(null);
        setTitle("Menu Principal - Registro Civil");
    }
    
    public VentanaMenu(){
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Titulo = new javax.swing.JLabel();
        RecienNacido = new javax.swing.JButton();
        registroGeneral = new javax.swing.JButton();
        matrimonio = new javax.swing.JButton();
        defuncion = new javax.swing.JButton();
        certificados = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Titulo.setText("SISTEMA DE REGISTRO CIVIL");

        RecienNacido.setText("Inscribir Recien Nacido");
        RecienNacido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecienNacidoActionPerformed(evt);
            }
        });

        registroGeneral.setText("Incribir Registro General");
        registroGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registroGeneralActionPerformed(evt);
            }
        });

        matrimonio.setText("Vincular Matrimonio");
        matrimonio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matrimonioActionPerformed(evt);
            }
        });

        defuncion.setText("Registrar Defuncion");

        certificados.setText("Emitir Certificados");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(155, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(Titulo)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(RecienNacido, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 171, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(registroGeneral))
                    .add(certificados)
                    .add(defuncion)
                    .add(matrimonio))
                .add(163, 163, 163))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(Titulo)
                .add(18, 18, 18)
                .add(RecienNacido)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(registroGeneral)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(matrimonio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(defuncion)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(certificados)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RecienNacidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecienNacidoActionPerformed
        JOptionPane.showMessageDialog(this, "Proximamente: Registro de Nacimiento");
    }//GEN-LAST:event_RecienNacidoActionPerformed

    private void registroGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registroGeneralActionPerformed
        VentanaPrincipal registro = new VentanaPrincipal (sistema);
        registro.setVisible(true);
    }//GEN-LAST:event_registroGeneralActionPerformed

    private void matrimonioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matrimonioActionPerformed
        JOptionPane.showMessageDialog(this, "Proximamente: Vinculo matrimonial");
    }//GEN-LAST:event_matrimonioActionPerformed
    
    private void defuncionActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String rut = JOptionPane.showInputDialog(this, "Ingrese RUT para acta de defunción:");
        if (rut != null && !rut.isEmpty()) {
            // Ejemplo buscando en una región fija por ahora
            com.registrocivil.logica.Persona p = sistema.buscarPorPersonaEnRegion("Metropolitana", rut);
            if (p != null) {
                p.setVivo(false);
                JOptionPane.showMessageDialog(this, "Defunción registrada correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "RUT no encontrado.");
            }
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaMenu(new GestionSistema()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton RecienNacido;
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton certificados;
    private javax.swing.JButton defuncion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton matrimonio;
    private javax.swing.JButton registroGeneral;
    // End of variables declaration//GEN-END:variables

}
