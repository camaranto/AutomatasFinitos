/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatasfinitos;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro_Acevedo
 */
public class Inicio extends javax.swing.JFrame {

    public static final ArrayList<String> OP = new ArrayList<>();
    public static ArrayList<String> Alfabeto = new ArrayList<>();
    /**
     * Creates new form Inicio
     */
    public Inicio() {
        initComponents();
        OP.add("?");
        OP.add(")");
        OP.add("(");
        OP.add("*");
        OP.add("|");
        OP.add("+");
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("AUTOMATAS FINITOS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("EXPRESION REGULAR:");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setText("CONSTRUIR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel1)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        if (!Character.isLetter(evt.getKeyChar()) && !OP.contains(evt.getKeyChar() + "")) {
         evt.consume();
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ComprobarEXP();  
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public boolean ComprobarEXP(){
        boolean sw = true;
        int i = 0;
        String EXP = jTextField1.getText();
        while(i < EXP.length() && sw) {
            if (!OP.contains(EXP.substring(i, i+1))) {
                if (!Alfabeto.contains(EXP.substring(i, i+1))) {
                    Alfabeto.add(EXP.substring(i, i+1));   
                }
            }else{
                boolean error = false;
                int Parentesis = 0;
                if (i<(EXP.length()-1)) {
                    switch(EXP.substring(i, i+1)){
                        case "|":
                            if (OP.contains(EXP.substring(i+1, i+2))) {
                                error = true;
                            }
                        break;
                        case "?":
                            if (OP.contains(EXP.substring(i+1, i+2)) && !EXP.substring(i+1, i+2).equals("(") && !EXP.substring(i+1, i+2).equals(")")) {
                                error = true;
                            }
                        break;
                        case "*":
                            if (EXP.substring(i+1, i+2).equals("+") | EXP.substring(i+1, i+2).equals("?") | EXP.substring(i+1, i+2).equals("*")) {
                                error = true;
                            }
                        break;
                        case "(":
                            Parentesis++;
                            if (OP.contains(EXP.substring(i+1, i+2))) {
                                error = true;
                            }                        
                        break;
                        case ")":            
                            Parentesis++;
                            if (EXP.substring(i+1, i+2).equals("|") | EXP.substring(i+1, i+2).equals(")")) {
                                error = true;
                            }            
                        break;
                        case "+":
                            if (EXP.substring(i+1, i+2).equals("+") | EXP.substring(i+1, i+2).equals("*") | EXP.substring(i+1, i+2).equals("?")) {
                                error = true;
                            }                         
                        break;
                    }
                }else{
                    if (EXP.substring(i, i+1).equals("+") | EXP.substring(i, i+1).equals("|") | EXP.substring(i, i+1).equals("(")) {
                        error=true;
                    }
                    if (Parentesis%2 != 0) {
                        error=true;
                    }
                }
                if (error) {
                    JOptionPane.showMessageDialog(null, "La expresion regular tiene errores lexicograficos.", "Automatas finitos", JOptionPane.WARNING_MESSAGE);
                    sw = false;
                }
            }
            i++;
        }
        return sw;
    }
    
    
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
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}