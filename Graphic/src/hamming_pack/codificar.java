/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hamming_pack;

/**
 *
 * @author luciana
 */
public class codificar extends javax.swing.JPanel {

    /**
     * Creates new form protect
     */
    public codificar() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bloque = new javax.swing.ButtonGroup();
        hamming = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        confirmar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        resultado = new javax.swing.JPanel();
        confirmar1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        titulo2 = new javax.swing.JLabel();
        antes = new javax.swing.JLabel();
        despues = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1300, 750));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        hamming.setBackground(new java.awt.Color(20, 13, 54));
        hamming.setPreferredSize(new java.awt.Dimension(1300, 750));

        titulo.setFont(new java.awt.Font("URW Gothic L", 1, 36)); // NOI18N
        titulo.setForeground(new java.awt.Color(241, 240, 255));
        titulo.setText("CODIFICAR");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setFont(new java.awt.Font("URW Gothic L", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(241, 240, 255));
        jLabel1.setText("BLOQUE DE ELECCION:");

        jLabel2.setFont(new java.awt.Font("URW Gothic L", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(241, 240, 255));
        jLabel2.setText("ARCHIVO: ");

        jRadioButton1.setBackground(new java.awt.Color(20, 13, 54));
        bloque.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("URW Gothic L", 1, 24)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(241, 240, 255));
        jRadioButton1.setText("  32 BITS");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setBackground(new java.awt.Color(20, 13, 54));
        bloque.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("URW Gothic L", 1, 24)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(241, 240, 255));
        jRadioButton2.setText("  2048 BITS");

        jRadioButton3.setBackground(new java.awt.Color(20, 13, 54));
        bloque.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("URW Gothic L", 1, 24)); // NOI18N
        jRadioButton3.setForeground(new java.awt.Color(241, 240, 255));
        jRadioButton3.setText("  65536 BITS");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        confirmar.setBackground(new java.awt.Color(44, 32, 106));
        confirmar.setFont(new java.awt.Font("URW Gothic L", 1, 24)); // NOI18N
        confirmar.setForeground(new java.awt.Color(241, 240, 255));
        confirmar.setText("CONFIRMAR");
        confirmar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        confirmar.setBorderPainted(false);
        confirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarActionPerformed(evt);
            }
        });

        cancelar.setBackground(new java.awt.Color(44, 32, 106));
        cancelar.setFont(new java.awt.Font("URW Gothic L", 1, 24)); // NOI18N
        cancelar.setForeground(new java.awt.Color(241, 240, 255));
        cancelar.setText("CANCELAR");
        cancelar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cancelar.setBorderPainted(false);
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout hammingLayout = new javax.swing.GroupLayout(hamming);
        hamming.setLayout(hammingLayout);
        hammingLayout.setHorizontalGroup(
            hammingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hammingLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addGroup(hammingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(hammingLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addGroup(hammingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(hammingLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(570, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hammingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(504, 504, 504))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hammingLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(confirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        hammingLayout.setVerticalGroup(
            hammingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hammingLayout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addGroup(hammingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(hammingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton1))
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addGroup(hammingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );

        add(hamming, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        resultado.setBackground(new java.awt.Color(20, 13, 54));
        resultado.setPreferredSize(new java.awt.Dimension(1300, 750));
        resultado.setLayout(null);

        confirmar1.setBackground(new java.awt.Color(44, 32, 106));
        confirmar1.setFont(new java.awt.Font("URW Gothic L", 1, 24)); // NOI18N
        confirmar1.setForeground(new java.awt.Color(241, 240, 255));
        confirmar1.setText("CONFIRMAR");
        confirmar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        confirmar1.setBorderPainted(false);
        confirmar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmar1ActionPerformed(evt);
            }
        });
        resultado.add(confirmar1);
        confirmar1.setBounds(1050, 640, 200, 60);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        resultado.add(jScrollPane1);
        jScrollPane1.setBounds(40, 160, 590, 460);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        resultado.add(jScrollPane2);
        jScrollPane2.setBounds(660, 160, 590, 460);

        titulo2.setFont(new java.awt.Font("URW Gothic L", 1, 36)); // NOI18N
        titulo2.setForeground(new java.awt.Color(241, 240, 255));
        titulo2.setText("ARCHIVO FINAL");
        resultado.add(titulo2);
        titulo2.setBounds(520, 30, 280, 50);

        antes.setFont(new java.awt.Font("URW Gothic L", 1, 24)); // NOI18N
        antes.setForeground(new java.awt.Color(241, 240, 255));
        antes.setText("ARCHIVO INGRESADO");
        resultado.add(antes);
        antes.setBounds(40, 110, 280, 50);

        despues.setFont(new java.awt.Font("URW Gothic L", 1, 24)); // NOI18N
        despues.setForeground(new java.awt.Color(241, 240, 255));
        despues.setText("ARCHIVO CODIFICADO");
        resultado.add(despues);
        despues.setBounds(660, 110, 310, 50);

        add(resultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void confirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmarActionPerformed

    private void confirmar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmar1ActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        graphic.Principal.getPrincipal().go_to(graphic.Principal.getPrincipal().getHamming());
    }//GEN-LAST:event_cancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel antes;
    private javax.swing.ButtonGroup bloque;
    private javax.swing.JButton cancelar;
    private javax.swing.JButton confirmar;
    private javax.swing.JButton confirmar1;
    private javax.swing.JLabel despues;
    private javax.swing.JPanel hamming;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JPanel resultado;
    private javax.swing.JLabel titulo;
    private javax.swing.JLabel titulo2;
    // End of variables declaration//GEN-END:variables
}
