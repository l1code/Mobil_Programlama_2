/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tr.isparta.edu.soket;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author drhal
 */
public class Soket extends javax.swing.JFrame {

    public static Socket soket;
    public static ServerSocket sunucuSoket;
    public static InputStreamReader isr;
    public static BufferedReader br;
    public static String mesaj;
    
    /**
     * Creates new form Soket
     */
    public Soket() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTSonuc = new javax.swing.JTextArea();
        jTMesaj = new javax.swing.JTextField();
        jBGonder = new javax.swing.JButton();
        jBOku = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTSonuc.setColumns(20);
        jTSonuc.setRows(5);
        jScrollPane1.setViewportView(jTSonuc);

        jBGonder.setText("Gönder");
        jBGonder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGonderActionPerformed(evt);
            }
        });

        jBOku.setText("Oku");
        jBOku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOkuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTMesaj)
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jBGonder, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(jBOku, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(402, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTMesaj, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBGonder, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBOku, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(137, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBGonderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGonderActionPerformed
        // TODO add your handling code here:        
        AndroideGonder();
    }//GEN-LAST:event_jBGonderActionPerformed

    private void jBOkuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOkuActionPerformed
        // TODO add your handling code here:
        
        AndroiddenOku();
    }//GEN-LAST:event_jBOkuActionPerformed

    
    public void AndroideGonder(){
        JOptionPane.showMessageDialog(null,"Androide Gönder",mesaj,JOptionPane.PLAIN_MESSAGE);
        
        try{
         
         Socket soket=new Socket("192.168.1.35",2001);
         soket.connect(new InetSocketAddress("192.168.1.35",2001),50000);
         PrintWriter pw=new PrintWriter(soket.getOutputStream());
         pw.write(jTMesaj.getText());
         pw.flush();
         pw.close();
         soket.close();
         JOptionPane.showMessageDialog(null,"Soket Kapatıldı", mesaj,JOptionPane.PLAIN_MESSAGE);
         
        }catch(Exception ex){
            Logger.getLogger(Soket.class.getName()).log(Level.SEVERE,null,ex);
            JOptionPane.showMessageDialog(null,"hata", mesaj,JOptionPane.PLAIN_MESSAGE);
        }
        
    }
    
    
    public void AndroiddenOku(){
        try{
        sunucuSoket=new ServerSocket(2000);
            while (true) {
                soket=sunucuSoket.accept();
                isr=new InputStreamReader(soket.getInputStream());
                br=new BufferedReader(isr);
                mesaj=br.readLine();
                
                System.out.println(""+mesaj);
                
                if (jTSonuc.getText().toString().equals("")) {
                    jTSonuc.setText("Android:"+mesaj);
                }else{
                    jTSonuc.setText(jTSonuc.getText()+"\n"+mesaj);
                }
                        
            }
        }catch(Exception ex){
            Logger.getLogger(Soket.class.getName()).log(Level.SEVERE,null,ex);
        }
        
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
            java.util.logging.Logger.getLogger(Soket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Soket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Soket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Soket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Soket().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGonder;
    private javax.swing.JButton jBOku;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTMesaj;
    private javax.swing.JTextArea jTSonuc;
    // End of variables declaration//GEN-END:variables
}
