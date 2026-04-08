package com.registrocivil.window;

import com.registrocivil.logica.GestionSistema;
import com.registrocivil.logica.Persona;
import javax.swing.DefaultComboBoxModel;

public class VentanaPrincipal extends javax.swing.JFrame {
    private GestionSistema sistema;
    
    public VentanaPrincipal(GestionSistema sistemaRecibido) {
        initComponents();
        sistema = sistemaRecibido;
        setLocationRelativeTo(null);
        
        String[] listaRegiones = {
            "Arica y Parinacota", "Tarapaca", "Antofagasta", "Atacama", "Coquimbo",
            "Valparaiso", "Metropolitana", "O'Higgins", "Maule", "Nuble", "Biobio",
            "La Araucania", "Los Rios", "Los Lagos", "Aysen", "Magallanes"
        };
        Regiones.setModel(new DefaultComboBoxModel<>(listaRegiones));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Regiones = new javax.swing.JComboBox<>();
        Rut = new javax.swing.JTextField();
        Registrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        CajaTexto = new javax.swing.JTextArea();
        Nombre = new javax.swing.JTextField();
        Apellido = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Regiones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Regiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegionesActionPerformed(evt);
            }
        });

        Rut.setText("Rut");
        Rut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RutActionPerformed(evt);
            }
        });

        Registrar.setText("Registrar");

        CajaTexto.setColumns(20);
        CajaTexto.setRows(5);
        jScrollPane1.setViewportView(CajaTexto);

        Nombre.setText("Nombre");

        Apellido.setText("Apellido");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(Registrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addComponent(Regiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Apellido, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(Nombre, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Rut))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Rut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Registrar)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Regiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegionesActionPerformed
        String regionSeleccionada = Regiones.getSelectedItem().toString();
        String rutIngresado = Rut.getText();
        String nombreIngresado = Nombre.getText();
        String apellidoIngresado = Apellido.getText();
        
        if (rutIngresado.isEmpty() || nombreIngresado.isEmpty()){
            CajaTexto.append("ERROR: Debes ingresar al menos un RUT y Nombre. \n");
            return;
        }
        
        Persona nueva = new Persona(rutIngresado, nombreIngresado, apellidoIngresado);
        sistema.registrarNacimiento(regionSeleccionada, nueva);
        
        CajaTexto.append("Registrado: " + nombreIngresado + " en la region de " + regionSeleccionada + ".\n");
        
        Rut.setText("");
        Nombre.setText("");
        Apellido.setText("");
    }//GEN-LAST:event_RegionesActionPerformed

    private void RutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run() {
                new VentanaPrincipal(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Apellido;
    private javax.swing.JTextArea CajaTexto;
    private javax.swing.JTextField Nombre;
    private javax.swing.JComboBox<String> Regiones;
    private javax.swing.JButton Registrar;
    private javax.swing.JTextField Rut;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
