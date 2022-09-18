/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import Reportes.E_report;
import Reportes.E_report_cxc;
import clases_DAO.kardex_DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author USUARIO
 */
public class TABLA_CHECK extends javax.swing.JInternalFrame {

    kardex_DAO obj = new kardex_DAO();

//    String SQL ="SELECT * FROM(SELECT c.[codigo_cli],c.[cedula_cl],c.[razonsocial_cl],c.[direccion_cl]"
//            + ",c.[telefonos_cl]"
//            + ",c.[celular_cl]"
//            + ",c.[email_cl]" 
//            + ",c.[observacion10_cl]"
//            + ",s.SALDO"
//            + " FROM [necoelectro].[dbo].[clientes] AS c"
//            + " INNER JOIN (SELECT * FROM(SELECT [codigo_cl],(SUM([debito])-SUM([credito])) AS SALDO "
//            + "FROM [necoelectro].[dbo].[estadocuentaclientes] "
//            + "GROUP BY [codigo_cl]) AS SALDO) AS s "
//            + "ON c.[codigo_cli]=s.[codigo_cl] "
//            + "GROUP BY C.[codigo_cli],C.[cedula_cl],C.razonsocial_cl,c.direccion_cl,c.telefonos_cl,c.celular_cl,c.email_cl,c.observacion10_cl,s.SALDO) AS SALDO"
//            + "WHERE SALDO>0";
//    
    String SQL = "SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] WHERE existencias<>0 ORDER BY descrip_kar";

    public TABLA_CHECK() {
        initComponents();
        cargarTabla();
    }

    private void cargarTabla() {

        String sql = "SELECT * FROM(SELECT c.[codigo_cli],c.[cedula_cl],c.[razonsocial_cl],c.[direccion_cl],c.[telefonos_cl],c.[celular_cl],c.[email_cl],c.[observacion10_cl],s.SALDO"
                + " FROM [necoelectro].[dbo].[clientes] AS c"
                + " INNER JOIN (SELECT * FROM(SELECT [codigo_cl],(SUM([debito])-SUM([credito])) AS SALDO"
                + " FROM [necoelectro].[dbo].[estadocuentaclientes]"
                + " GROUP BY [codigo_cl]) AS SALDO) AS s"
                + " ON c.[codigo_cli]=s.[codigo_cl]"
                + " GROUP BY C.[codigo_cli],C.[cedula_cl],C.razonsocial_cl,c.direccion_cl,c.telefonos_cl,c.celular_cl,c.email_cl,c.observacion10_cl,s.SALDO) AS SALDO"
                + " WHERE SALDO>0 "
                + "ORDER BY razonsocial_cl;";

        tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Razon Social", "Cedula", "Direccion", "Telefono", "Email", "Saldo"
                }
        ));

        DefaultTableModel modelo = (DefaultTableModel) tb_ensayo.getModel();
        // ||
        Object[] column = new Object[modelo.getColumnCount()];

        int contador = 0;
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {

                    column[0] = rs.getString("razonsocial_cl");
                    column[1] = rs.getString("cedula_cl");
                    if (rs.getString("direccion_cl") == null) {
                        column[2] = "null";
                    } else {
                        column[2] = rs.getString("direccion_cl");
                    }

                    if (rs.getString("telefonos_cl") == null) {
//                        column[3] = "null";
                        if (rs.getString("celular_cl") == null) {
                            column[3] = "null";
                        } else {
                            column[3] = rs.getString("celular_cl");
                        }

                    } else {
                        column[3] = rs.getString("telefonos_cl");
                    }

//                    if (rs.getString("celular_cl")==null) {
//                        column[4] ="null";
//                    } else {
//                        column[4] = rs.getString("celular_cl");
//                    }
                    if (rs.getString("email_cl") == null) {
//                        column[5] = "null";
                        if (rs.getString("observacion10_cl") == null) {
                            column[4] = "null";
                        } else {
                            column[4] = rs.getString("observacion10_cl");
                        }
                    } else {
                        column[4] = rs.getString("email_cl");
                    }

//                    if (rs.getString("observacion10_cl") == null) {
//                        column[6] = "null";
//                    } else {
//                        column[6] = rs.getString("observacion10_cl");
//                    }
                    column[5] = rs.getString("SALDO");

                    modelo.addRow(column);
                }
            }
        } catch (SQLException localSQLException) {

        }
        int[] anchos = {300, 50, 50, 50, 50, 50};
        for (int i = 0; i < tb_ensayo.getColumnCount(); i++) {
            tb_ensayo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private void report() {
        List lista = new ArrayList();
        for (int i = 0; i < tb_ensayo.getRowCount(); i++) {
            E_report_cxc num = new E_report_cxc(
                    tb_ensayo.getValueAt(i, 0).toString(),
                    tb_ensayo.getValueAt(i, 1).toString(),
                    tb_ensayo.getValueAt(i, 2).toString(),
                    tb_ensayo.getValueAt(i, 3).toString(),
                    tb_ensayo.getValueAt(i, 4).toString(),
                    tb_ensayo.getValueAt(i, 5).toString()
                    
            );

            lista.add(num);
        }
        JasperReport reporte;
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile("C:/Users/USUARIO/Documents/NetBeansProjects/Necoabasto_consultas_sqlServer/src/Reportes/CXC.jasper");

            JasperPrint imprimir = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(lista));

            JasperViewer.viewReport(imprimir, false);

        } catch (Exception e) {
        }
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
        tb_ensayo = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tb_ensayo);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Eliminar filas seleccionadas");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jButton1)
                .addGap(75, 75, 75)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (tb_ensayo.getRowCount() > 150) {
            JOptionPane.showMessageDialog(null, "INFORME CON MAS DE 150 ITEMS");
        } else {
            report();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Utilidades.Con_tablas obj_2 = new Utilidades.Con_tablas();
        obj_2.eliminarFilasSeleccionadas(tb_ensayo);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_ensayo;
    // End of variables declaration//GEN-END:variables
}
