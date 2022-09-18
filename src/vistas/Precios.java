/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import clases_DAO.kardex_DAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;




public class Precios extends javax.swing.JInternalFrame {
    
    NumberFormat nf=NumberFormat.getInstance();
    
    //this.myFormatter.format(Double.parseDouble(rs.getString("valor"))),

   kardex_DAO obj=new kardex_DAO();
//   String uno="SELECT [referencia],[descrip_kar],[activo_kar],[existencias] FROM [necoelectro].[dbo].[kardex] WHERE activo_kar=1 AND descrip_kar LIKE '%cubierto%' ORDER BY descrip_kar";
   
    public Precios() {
        initComponents();
        cargarTabla_sql("");
    }
    
    public static void reiniciarJTable(javax.swing.JTable Tabla){
        DefaultTableModel modelo = (DefaultTableModel) Tabla.getModel();
        while(modelo.getRowCount()>0)modelo.removeRow(0);
 
//        TableColumnModel modCol = Tabla.getColumnModel();
//        while(modCol.getColumnCount()>0)modCol.removeColumn(modCol.getColumn(0));
    }
    
    private void repintar_tabla(JTable table){
        ///Para que se desplase al último registro de la tabla
          int row =  table.getRowCount () - 1;
          Rectangle rect = table.getCellRect(row, 0, true);
          table.scrollRectToVisible(rect);
          table.clearSelection();
          table.setRowSelectionInterval(row, row);
          DefaultTableModel modelo = (DefaultTableModel)table.getModel();
          modelo.fireTableDataChanged();
       ///////////////////////////////////////////////////////////// 
     }
    
    
    private void cargarTabla_sql(String sql){
       tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
               "referencia","descrip_kar","CREDITO_1","CREDITO_2","CONTADO","cost"
            }
            ));
        
        DefaultTableModel modelo=(DefaultTableModel) tb_ensayo.getModel();
        // ||
        Object[] column=new Object[modelo.getColumnCount()];
          Connection cn=obj.conexion();
        try
        {  
          Statement stp = cn.createStatement();
         
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    
                    column[0]=rs.getString("referencia");
                    column[1]=rs.getString("descrip_kar");
                    
                    double cost=rs.getDouble("ult_costo");
                    double cost_con=cost*1.19;
                    
                    double v_1=rs.getDouble("precio1");
                    double v_2=rs.getDouble("precio2");
                    double v_3=rs.getDouble("precio3");
                    
                    int iva=rs.getInt("iva");
                    
//                    String cos_1=nf.format(Math.round(cost_con));
                    
                    
                    column[2]=cal_util(cost_con, v_1)+" %"+"-->"+nf.format(Math.round(v_1));
                    column[3]=nf.format(Math.round(v_2));
                    column[4]=cal_util(cost_con, v_3)+" %"+"-->"+nf.format(Math.round(v_3));
                    if (iva==1) {
                        column[5]=nf.format(Math.round(cost_con));
                    }
                    else{
                        column[5]=nf.format(Math.round(cost));
                    }
//                    column[5]=(iva);
                    
                    modelo.addRow(column);
                }
            }
        }
        catch (SQLException localSQLException) {
            
        }
      }
    
    private void cargarTabla_Activos_NoActivos(){
       tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
               "referencia","descrip_kar","CREDITO","Activo","CONTADO","cost"
            }
            ));
        
        DefaultTableModel modelo=(DefaultTableModel) tb_ensayo.getModel();
        // ||
        Object[] column=new Object[modelo.getColumnCount()];
          Connection cn=obj.conexion();
        try
        {  
          Statement stp = cn.createStatement();
          String sql_1="SELECT referencia,descrip_kar,ult_costo,precio1,precio2,precio3,activo_kar FROM [necoelectro].[dbo].[kardex] WHERE descrip_kar LIKE '%" + txt_filtro.getText() + "%' ORDER BY descrip_kar";
         
            try (ResultSet rs = stp.executeQuery(sql_1)) {
                while (rs.next()) {
                    
                    column[0]=rs.getString("referencia");
                    
                    column[1]=rs.getString("descrip_kar");
                    
                    double cost=rs.getDouble("ult_costo");
                    double cost_con=cost*1.19;
                    
                    double v_1=rs.getDouble("precio1");
//                    double v_2=rs.getDouble("precio2");
                    double v_3=rs.getDouble("precio3");
                    
                    
                    String cos_1=nf.format(Math.round(cost_con));
                    
                    
                    column[2]=cal_util(cost_con, v_1)+" %"+"-->"+nf.format(Math.round(v_1));
//                    column[3]=nf.format(Math.round(v_2));
                    column[3]=rs.getString("activo_kar");
                    column[4]=cal_util(cost_con, v_3)+" %"+"-->"+nf.format(Math.round(v_3));
                    column[5]=(cos_1);
                    
                    modelo.addRow(column);
                }
            }
        }
        catch (SQLException localSQLException) {
            
        }
      }
    
    private String cal_util(double p_cos,double p_ven){
        double util = 0;
        if (p_ven>0) {
            util=100*(p_ven-p_cos)/p_ven;
        }
        return nf.format(Math.round(util));
    }
    
    private void renderizar(){
        int[] anchos={90,350,90,40,90,0};
        for(int i=0;i<tb_ensayo.getColumnCount();i++)            
           {
              tb_ensayo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);          
           }
        
        for (int i = 0; i < 6; i++) {
                tb_ensayo.getColumnModel().getColumn(i).setCellRenderer(new render_Columnas());
            }
        if (tb_ensayo.getRowCount()>0) {
            repintar_tabla(tb_ensayo);
        }
        
    }
    

    
//    private void cargarTabla(String filtro){
//       tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
//            new Object [][] {
//            },
//            new String [] {
//               "referencia","descrip_kar","CREDITO_1","CREDITO_2","CONTADO","cost"
//            }
//            ));
//        
//        DefaultTableModel modelo=(DefaultTableModel) tb_ensayo.getModel();
//        // ||
//        Object[] column=new Object[modelo.getColumnCount()];
//          Connection cn=obj.conexion();
//        try
//        {  
//          Statement stp = cn.createStatement();
//          //LIKE '" + cadenaEscrita + "%'
//            String sql="SELECT [referencia],[descrip_kar],[ult_costo],[precio1],[precio2],[precio3],[activo_kar] FROM [necoelectro].[dbo].[kardex] WHERE activo_kar=1 AND descrip_kar LIKE '%" + filtro + "%' ORDER BY descrip_kar";
////            try (ResultSet rs = stp.executeQuery("SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas_ver1] WHERE existencias<0 ORDER BY descrip_kar")) {
//            try (ResultSet rs = stp.executeQuery(sql)) {
//                while (rs.next()) {
//                    
//                    column[0]=rs.getString("referencia");
//                    column[1]=rs.getString("descrip_kar");
//                    
//                    double cost=rs.getDouble("ult_costo");
//                    double cost_con=cost*1.19;
//                    double v_1=rs.getDouble("precio1");
//                    double v_2=rs.getDouble("precio2");
//                    double v_3=rs.getDouble("precio3");
//                    
//                    
//                    String cos_1=nf.format(Math.round(cost_con));
//                    
//                    
//                    column[2]=cal_util(cost_con, v_1)+" %"+"-->"+nf.format(Math.round(v_1));
//                    column[3]=nf.format(Math.round(v_2));
//                    column[4]=cal_util(cost_con, v_3)+" %"+"-->"+nf.format(Math.round(v_3));
//                    column[5]=(cos_1);
//                    
//                    modelo.addRow(column);
//                }
//            }
//        }
//        catch (SQLException localSQLException) {
//            
//        }
//          int[] anchos={40,300,10,10,10,10};
//        for(int i=0;i<tb_ensayo.getColumnCount();i++)            
//           {
//              tb_ensayo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);          
//           }
//        
//        for (int i = 0; i < 6; i++) {
//                tb_ensayo.getColumnModel().getColumn(i).setCellRenderer(new render_Columnas());
//            }
//        repintar_tabla(tb_ensayo);
//        
//      }
    
//    private void cargarTabla_TODOS(String filtro){
//       tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
//            new Object [][] {
//            },
//            new String [] {
//               "referencia","descrip_kar","CREDITO_1","CREDITO_2","CONTADO","cost"
//            }
//            ));
//        
//        DefaultTableModel modelo=(DefaultTableModel) tb_ensayo.getModel();
//        // ||
//        Object[] column=new Object[modelo.getColumnCount()];
//          Connection cn=obj.conexion();
//        try
//        {  
//          Statement stp = cn.createStatement();
//          //LIKE '" + cadenaEscrita + "%'
//            String sql="SELECT [referencia],[descrip_kar],[ult_costo],[precio1],[precio2],[precio3],[activo_kar] FROM [necoelectro].[dbo].[kardex] WHERE descrip_kar LIKE '%" + filtro + "%' ORDER BY descrip_kar";
////            try (ResultSet rs = stp.executeQuery("SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas_ver1] WHERE existencias<0 ORDER BY descrip_kar")) {
//            try (ResultSet rs = stp.executeQuery(sql)) {
//                while (rs.next()) {
//                    
//                    column[0]=rs.getString("referencia");
//                    column[1]=rs.getString("descrip_kar");
//                    
//                    double cost=rs.getDouble("ult_costo");
//                    double cost_con=cost*1.19;
//                    double v_1=rs.getDouble("precio1");
//                    double v_2=rs.getDouble("precio2");
//                    double v_3=rs.getDouble("precio3");
//                    
//                    
//                    String cos_1=nf.format(Math.round(cost_con));
//                    
//                    
//                    column[2]=cal_util(cost_con, v_1)+" %"+"-->"+nf.format(Math.round(v_1));
//                    column[3]=nf.format(Math.round(v_2));
//                    column[4]=cal_util(cost_con, v_3)+" %"+"-->"+nf.format(Math.round(v_3));
//                    column[5]=(cos_1);
//                    
//                    modelo.addRow(column);
//                }
//            }
//        }
//        catch (SQLException localSQLException) {
//            
//        }
//          int[] anchos={40,300,10,10,10,10};
//        for(int i=0;i<tb_ensayo.getColumnCount();i++)            
//           {
//              tb_ensayo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);          
//           }
//        
//        for (int i = 0; i < 6; i++) {
//                tb_ensayo.getColumnModel().getColumn(i).setCellRenderer(new render_Columnas());
//            }
//        repintar_tabla(tb_ensayo);
//        
//      }
    
//    private void filtrar_al_escribir(){
//        
//        txt_filtro.addKeyListener(new java.awt.event.KeyAdapter() {
//            @Override
//            public void keyTyped(java.awt.event.KeyEvent evt) {
////                txt_filtroKeyTyped(evt);
//                JOptionPane.showMessageDialog(null, "Escucho_por texto");
//            }
//        });
//        
////        private void txt_filtroKeyTyped(java.awt.event.KeyEvent evt) {                                    
////        // TODO add your handling code here:
////    }   
//        
//    }
    
    
    private class render_Columnas extends DefaultTableCellRenderer{
     private final Font fontePadrao = new Font("MS Sanserif", Font.PLAIN, 14);     
     private final Font fonte2 = new Font("Arial", Font.BOLD, 15);   
     
     
     @Override
    public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
        JLabel cell=(JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cell.setFont(this.fontePadrao);
        if (value instanceof String) {
            for(int i=0;i<table.getRowCount();i++){
                for(int j=0;j<6;j++){
                if (row==i) {
                if (column==j) { 
                    
                    String valor=table.getValueAt(i, 5).toString();
                    String val_sin=valor.replace(".", "");
                    int VALOR=Integer.parseInt(val_sin);
                    
                    if (column>=0 && column<5 && i%2==0) {
                        cell.setBackground(Color.LIGHT_GRAY);
                        cell.setForeground(Color.BLACK);
                        cell.setFont(fontePadrao);
                    }else{
                        cell.setBackground(Color.WHITE);
                        cell.setForeground(Color.BLACK);
                        cell.setFont(fontePadrao);
                    }
                    
                    if (column==5) {
                        if (VALOR>500000) {
                           cell.setBackground(Color.LIGHT_GRAY);
                           cell.setForeground(Color.DARK_GRAY);
                           cell.setFont(fontePadrao); 
                        }
                        else{
                            cell.setBackground(Color.LIGHT_GRAY);
                            cell.setForeground(Color.BLACK);
                            cell.setFont(fontePadrao);
                        }
                    }
                }
                }    
            }
        }
        }
        
        
        
        return cell;
    }
    }
    
    
    private class render_form_alMenos_2_2 extends DefaultTableCellRenderer{
          
     private final Font fontePadrao = new Font("MS Sanserif", Font.BOLD, 12);     
     private final Font fonte2 = new Font("Arial", Font.BOLD, 12);  
     
     @Override
    public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
        JLabel cell=(JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cell.setFont(this.fontePadrao);
        if (value instanceof Double) {
            
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 2; j < table.getColumnCount(); j++) {
                    if (column==j) {
                        if (row==i) {
                            
                           String a=table.getValueAt(i, 2).toString();
                           double A=Double.parseDouble(a); 
                           if (column>=3 && column<4) {
                                if (A>500000) {   ///  ||
                                
                                cell.setBackground(Color.RED);
                                cell.setForeground(Color.WHITE);
                                cell.setFont(fonte2);
                                
                                }
                                else
                                {
                                   cell.setBackground(Color.WHITE);
                                   cell.setForeground(Color.BLACK);

                                }  
                            }
                           
                          
                            
                        }
                    }
                }
            }
        }
        return cell;
    }
} 
    
    private class comparar_Paridad_Tesla_y_Diferencias extends DefaultTableCellRenderer{
          
     private final Font fontePadrao = new Font("MS Sanserif", Font.BOLD, 12);     
     private final Font fonte2 = new Font("Arial", Font.BOLD, 12);  
     
     @Override
    public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
        JLabel cell=(JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cell.setFont(this.fontePadrao);
        if (value instanceof Double) {
            
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 2; j < table.getColumnCount(); j++) {
                    if (column==j) {
                        if (row==i) {
                            
                           
                           String tesla=table.getValueAt(i, 3).toString();
                           double TESLA=Double.parseDouble(tesla);
//                           String diferencia=table.getValueAt(i, 15).toString();
//                           int DIF=Integer.parseInt(diferencia); 
                           
//                            int fil_1=Integer.parseInt(txt_aux_1.getText());
//                            int fil_2=Integer.parseInt(txt_aux_2.getText());
//                           
                           if (column==2) {
                                if (TESLA>0) {  
                                
                                cell.setBackground(Color.DARK_GRAY);
                                cell.setForeground(Color.WHITE);
                                cell.setFont(fonte2);
                                
                                }
                                else
                                {
                                   cell.setBackground(Color.WHITE);
                                   cell.setForeground(Color.BLACK);
                                }  
                            }
                        }
                    }
                }
            }
        }
        return cell;
    }
}  
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tb_ensayo = new javax.swing.JTable();
        txt_filtro = new javax.swing.JTextField();
        lb_row = new javax.swing.JLabel();
        btn_activos_ = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_filtro_ref = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                internal_activado(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        tb_ensayo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_ensayo.setRowHeight(23);
        jScrollPane1.setViewportView(tb_ensayo);

        txt_filtro.setBackground(new java.awt.Color(102, 255, 204));
        txt_filtro.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txt_filtro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_filtroKeyTyped(evt);
            }
        });

        btn_activos_.setText("FILTRAR ACTIVOS");
        btn_activos_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_activos_ActionPerformed(evt);
            }
        });

        jButton2.setText("FILTRAR TODOS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txt_filtro_ref.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txt_filtro_ref.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_filtro_refKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_filtro_ref, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_activos_)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_row))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(lb_row))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_activos_)
                            .addComponent(jButton2)))
                    .addComponent(txt_filtro_ref))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_activos_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_activos_ActionPerformed
        if (txt_filtro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Filtro Vacio");
        }else{
            reiniciarJTable(tb_ensayo);
            String sql="SELECT [referencia],[descrip_kar],[ult_costo],[precio1],[precio2],[precio3],[activo_kar] FROM [necoelectro].[dbo].[kardex] WHERE activo_kar=1 AND descrip_kar LIKE '%" + txt_filtro.getText() + "%' ORDER BY descrip_kar";
            cargarTabla_sql(sql);
            renderizar();
        }
    }//GEN-LAST:event_btn_activos_ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (txt_filtro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Filtro Vacio");
        }else{
            reiniciarJTable(tb_ensayo);
//            String sql="SELECT [referencia],[descrip_kar],[ult_costo],[precio1],[precio2],[precio3],[activo_kar] FROM [necoelectro].[dbo].[kardex] WHERE descrip_kar LIKE '%" + txt_filtro.getText() + "%' ORDER BY descrip_kar";
            cargarTabla_Activos_NoActivos();
        }
        renderizar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_filtroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtroKeyTyped
        if (txt_filtro.getText().length()>2) {
            reiniciarJTable(tb_ensayo);
            String sql="SELECT [referencia],[descrip_kar],[ult_costo],[precio1],[precio2],[precio3],[activo_kar],[iva] FROM [necoelectro].[dbo].[kardex] WHERE activo_kar=1 AND descrip_kar LIKE '%" + txt_filtro.getText() + "%' ORDER BY descrip_kar";
            cargarTabla_sql(sql);
            renderizar();
           
        }
    }//GEN-LAST:event_txt_filtroKeyTyped

    private void internal_activado(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_internal_activado
//        JOptionPane.showMessageDialog(null, "Abri form ACTIVADO");
        txt_filtro.requestFocus();
    }//GEN-LAST:event_internal_activado

    private void txt_filtro_refKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_filtro_refKeyTyped
        
        if (txt_filtro_ref.getText().length()>1) {
            reiniciarJTable(tb_ensayo);
            String sql="SELECT [referencia],[descrip_kar],[ult_costo],[precio1],[precio2],[precio3],[activo_kar] FROM [necoelectro].[dbo].[kardex] WHERE activo_kar=1 AND referencia LIKE '%" + txt_filtro_ref.getText() + "%' ORDER BY descrip_kar";
            cargarTabla_sql(sql);
            renderizar();
           
        }
    }//GEN-LAST:event_txt_filtro_refKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_activos_;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_row;
    private javax.swing.JTable tb_ensayo;
    private javax.swing.JTextField txt_filtro;
    private javax.swing.JTextField txt_filtro_ref;
    // End of variables declaration//GEN-END:variables
}
