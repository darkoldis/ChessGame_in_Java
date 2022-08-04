/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Managers.FileManager;
import cz.radimkolacny.chess.MainMenuJFrame;

/**
 *
 * @author radimkolacny
 */
public class PreviousGamesJDialog extends javax.swing.JDialog {

    public PreviousGamesJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        MainMenuJFrame mainMenu = (MainMenuJFrame) parent;
        
        FileManager fileManager = mainMenu.getFileManager();
        
        initComponents();
        
        fileManager.loadPreviousGames();
        previosGamesJList.setModel(fileManager.getPreviousGames());
        setDialogProperties();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        previosGamesJList = new javax.swing.JList<>();
        backToMenuJButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Previous games");

        previosGamesJList.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        previosGamesJList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(previosGamesJList);

        backToMenuJButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        backToMenuJButton.setText("BACK TO MENU");
        backToMenuJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToMenuJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(backToMenuJButton)
                .addContainerGap(122, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backToMenuJButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setDialogProperties(){
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
    
    private void backToMenuJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToMenuJButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_backToMenuJButtonActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PreviousGamesJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backToMenuJButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> previosGamesJList;
    // End of variables declaration//GEN-END:variables
}