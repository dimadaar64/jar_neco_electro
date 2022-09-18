
package vistas;

import ImagenesFondo.ImagenFondo;
import Reportes.AdminReportes;
import clases_DAO.kardex_DAO;
import java.awt.Toolkit;
import java.sql.Connection;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Form_ppal extends javax.swing.JFrame {

    public Form_ppal() {
        initComponents();
        dpnEscritorio.setBorder(new ImagenFondo());
        this.setExtendedState(Form_ppal.MAXIMIZED_BOTH);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu13 = new javax.swing.JMenu();
        dpnEscritorio = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem104 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu15 = new javax.swing.JMenu();
        jMenuItem105 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        jMenu13.setText("jMenu13");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout dpnEscritorioLayout = new javax.swing.GroupLayout(dpnEscritorio);
        dpnEscritorio.setLayout(dpnEscritorioLayout);
        dpnEscritorioLayout.setHorizontalGroup(
            dpnEscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 962, Short.MAX_VALUE)
        );
        dpnEscritorioLayout.setVerticalGroup(
            dpnEscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        jMenuBar1.setToolTipText("");

        jMenu2.setText("Files");
        jMenu2.setActionCommand("F1F2");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N

        jMenuItem104.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem104.setText("Consultas sql");
        jMenuItem104.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem104ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem104);
        jMenu2.add(jSeparator1);

        jMenuItem5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuItem5.setText("CONSULTA DEPARTAMENTO Y SUBGRUPOS");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu15.setText("Graficas");
        jMenu15.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N

        jMenuItem105.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem105.setText("Precios");
        jMenuItem105.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem105ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem105);

        jMenu1.setText("Embarazadas");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem1.setForeground(new java.awt.Color(51, 153, 0));
        jMenuItem1.setText("Ingresadas");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem2.setForeground(new java.awt.Color(255, 204, 51));
        jMenuItem2.setText("Control");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem3.setForeground(new java.awt.Color(255, 0, 0));
        jMenuItem3.setText("Otro");
        jMenu1.add(jMenuItem3);

        jMenu15.add(jMenu1);

        jMenuBar1.add(jMenu15);

        jMenu3.setText("REPORTES");

        jMenuItem4.setText("LISTA PRECIOS");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem6.setText("Limpiar Existencias");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dpnEscritorio, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dpnEscritorio, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem104ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem104ActionPerformed
        Consultas_sql misDigitos=new Consultas_sql();
        dpnEscritorio.add(misDigitos);
        misDigitos.show();
        misDigitos.setLocation((Toolkit.getDefaultToolkit().getScreenSize().
            width-misDigitos.getSize().width)/2,
        (Toolkit.getDefaultToolkit().getScreenSize().height-misDigitos.getSize().height)/6);
        misDigitos.setVisible(true);
    }//GEN-LAST:event_jMenuItem104ActionPerformed

    private void jMenuItem105ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem105ActionPerformed
        Precios misDigitos=new Precios();
        dpnEscritorio.add(misDigitos);
        misDigitos.show();
        misDigitos.setLocation((Toolkit.getDefaultToolkit().getScreenSize().
            width-misDigitos.getSize().width)/2,
        (Toolkit.getDefaultToolkit().getScreenSize().height-misDigitos.getSize().height)/6);
        misDigitos.setVisible(true);
    }//GEN-LAST:event_jMenuItem105ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed


        TABLA_CHECK misDigitos=new TABLA_CHECK();
        dpnEscritorio.add(misDigitos);
        misDigitos.show();
        misDigitos.setLocation((Toolkit.getDefaultToolkit().getScreenSize().
            width-misDigitos.getSize().width)/2,
        (Toolkit.getDefaultToolkit().getScreenSize().height-misDigitos.getSize().height)/6);
        misDigitos.setVisible(true);

////        AdminReportes.startReporte("/Reportes/Lista_precios.jasper", null);
//
//        kardex_DAO obj=new kardex_DAO();
//        Connection cn=obj.conexion();
//
////        C:\Users\USUARIO\Documents\NetBeansProjects\Necoabasto_consultas_sqlServer\dist
////        String dir="/Lista_precios.jrxml";
////             conexion obj_2=new conexion();
////            Connection cn=obj_2.getConnection();
//        String dir="C:\\Users\\USUARIO\\Documents\\NetBeansProjects\\Necoabasto_consultas_sqlServer\\src\\Reportes\\Lista_precios.jrxml";
//        try {
//            JasperDesign dise=JRXmlLoader.load(dir);
//            JasperReport jr=JasperCompileManager.compileReport(dise);
//    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, null, cn);
//    JasperViewer.viewReport(jasperPrint);
//
//        } catch (JRException e) {
//            JOptionPane.showMessageDialog(jMenu1, e);
//        }

    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        Departamento_subgrupos misDigitos=new Departamento_subgrupos();
        dpnEscritorio.add(misDigitos);
        misDigitos.show();
        misDigitos.setLocation((Toolkit.getDefaultToolkit().getScreenSize().
            width-misDigitos.getSize().width)/2,
        (Toolkit.getDefaultToolkit().getScreenSize().height-misDigitos.getSize().height)/6);
        misDigitos.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        Limpiar_existencias_manual misDigitos=new Limpiar_existencias_manual();
        dpnEscritorio.add(misDigitos);
        misDigitos.show();
        misDigitos.setLocation((Toolkit.getDefaultToolkit().getScreenSize().
            width-misDigitos.getSize().width)/2,
        (Toolkit.getDefaultToolkit().getScreenSize().height-misDigitos.getSize().height)/6);
        misDigitos.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

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
            java.util.logging.Logger.getLogger(Form_ppal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Form_ppal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane dpnEscritorio;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem104;
    private javax.swing.JMenuItem jMenuItem105;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
