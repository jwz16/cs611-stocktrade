/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package edu.bu.cs611.portfoliostocksystem;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import edu.bu.cs611.portfoliostocksystem.customer_stock_trading_system.view.CustomerStockTradingViewFrame;
import edu.bu.cs611.portfoliostocksystem.portfolio_manager.view.PortfolioMgrViewFrame;

/**
 *
 * @author sophi
 */
public class Main extends javax.swing.JFrame {

    private static Main gInstance = null;

    public static Main getInstance() {
        if (gInstance == null) {
            gInstance = new Main();
        }
        return gInstance;
    }

    /**
     * Creates new form Main
     */
    private Main() {
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

        btnCxSignIn = new javax.swing.JButton();
        btnCxSignUp = new javax.swing.JButton();
        btnMgrSignIn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Main Frame"); // NOI18N
        setResizable(false);

        btnCxSignIn.setText("Customer Sign In");
        btnCxSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCxSignInActionPerformed(evt);
            }
        });

        btnCxSignUp.setText("Customer Sign Up");

        btnMgrSignIn.setText("Manager Sign In");
        btnMgrSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMgrSignInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCxSignUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCxSignIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMgrSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(136, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addComponent(btnCxSignIn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCxSignUp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMgrSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCxSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCxSignInActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            Main.getInstance().setVisible(false);
            Main.getInstance().dispose();
            new CustomerStockTradingViewFrame().setVisible(true);
        });
    }//GEN-LAST:event_btnCxSignInActionPerformed

    private void btnMgrSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMgrSignInActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            Main.getInstance().setVisible(false);
            Main.getInstance().dispose();
            new PortfolioMgrViewFrame().setVisible(true);
        });
    }//GEN-LAST:event_btnMgrSignInActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            Main.getInstance().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCxSignIn;
    private javax.swing.JButton btnCxSignUp;
    private javax.swing.JButton btnMgrSignIn;
    // End of variables declaration//GEN-END:variables
}
