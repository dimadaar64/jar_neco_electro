/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import Encapsulados.Departamentos;
import clases_DAO.kardex_DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class Departamento_subgrupos extends javax.swing.JInternalFrame {

    kardex_DAO obj = new kardex_DAO();
    
    
    public Departamento_subgrupos() {
        initComponents();
        cargar_cbo_conArray_CONmODEL();
        
        cargar_comboBox_consul_subgrupo();
        
        cargarTabla_sgr_dep(orden_Departamento());
        
//        cargar_cbo_conArray();
//        cargar_comboBox_consul_dep();
//        cargar_comboBox_consul_dep();
//        cargarTabla();
        
//        System.out.println("-->"+ret_cod_sg("CONGELADORES","CONGELADORES"));
    }
    
    public void removeAllElements() {
//    if ( objects.size() > 0 ) {
//        int firstIndex = 0;
//        int lastIndex = objects.size() - 1;
//        objects.removeAllElements();
//        selectedObject = null;
//        fireIntervalRemoved(this, firstIndex, lastIndex);
//    } else {
//        selectedObject = null;
//    }
}
    
    private void cargar_comboBox_departamento(JComboBox cbo) {
        
//        cbo_departamento.removeAllItems();
        
        String sql = "SELECT descrip_dep FROM [necoelectro].[dbo].[depar_kardex] ORDER BY descrip_dep";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
//                    if (cbo.getSelectedItem()!=null) {
                        cbo.addItem(rs.getString("descrip_dep"));
//                    }
                    
                }
            }
        } catch (SQLException localSQLException) {
        }
    }
    private void cargar_comboBox_departamento() {
        
        String sql = "SELECT descrip_dep FROM [necoelectro].[dbo].[depar_kardex] ORDER BY descrip_dep";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    cbo_departamento.addItem(rs.getString("descrip_dep"));
                }
            }
        } catch (SQLException localSQLException) {
        }
    }
    
     private void cargar_comboBox_consul_subgrupo() {
        String sql = "SELECT * FROM [necoelectro].[dbo].[subgrupos] ORDER BY descrip_sg";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    cbo_consultar_sg.addItem(rs.getString("descrip_sg"));
                }
            }
        } catch (SQLException localSQLException) {
        }
        lb_item_sg.setText("ITEM SUBGRUPO-->"+cbo_consultar_sg.getItemCount());
    }
     
     private ArrayList<Departamentos> cargar_array_consul_dep() {
//        Departamentos dep; 
         ArrayList<Departamentos> list=new ArrayList();
         list.clear();
        String sql = "SELECT * FROM [necoelectro].[dbo].[depar_kardex] ORDER BY descrip_dep";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    Departamentos dep=new Departamentos();
                    dep.setDescrip_dep(rs.getString("descrip_dep"));
                    if (rs.getString("descrip_dep")!=null) {
                        list.add(dep);
                    }
                    
//                    dep depar=new dep;
//                    Deoartamente.s=rs.getString("descrip_dep");
//                    list.add(new dep.se);
                }
            }
        } catch (SQLException localSQLException) {
        }
        return list;
    }
     
     private void cargar_cbo_conArray(){
         ArrayList<Departamentos> lista=cargar_array_consul_dep();
         
         for (int i = 0; i < lista.size(); i++) {
             Departamentos get = lista.get(i);
             cbo_departamento.addItem(get.getDescrip_dep());
//             System.out.println(i+"-->"+get.getDescrip_dep());
         }
     }
     private void cargar_cbo_conArray_CONmODEL(){
         DefaultComboBoxModel kamModel=(DefaultComboBoxModel)cbo_departamento.getModel();
        kamModel.removeAllElements();
         
         ArrayList<Departamentos> lista=cargar_array_consul_dep();
         
         for (int i = 0; i < lista.size(); i++) {
             Departamentos get = lista.get(i);
             kamModel.addElement(get.getDescrip_dep());
//             cbo_departamento.addItem(get.getDescrip_dep());
//             System.out.println(i+"-->"+get.getDescrip_dep());
         }
     }
     
     

    private void cargarTabla() {
        
        String sql = "SELECT * "
                    + " FROM [necoelectro].[dbo].[kardex] AS K";
////                    + "INNER JOIN [necoelectro].[dbo].[depar_kardex] AS D ON G.[codigo_dep]=D.codigo_dep "
//                    + " ON K.[codigo_kar]=bodega.codigo_kar"
//                    + " WHERE K.codigo_sgr = '" + codigo_sgr + "' ORDER BY K.descrip_kar";

//        String sql = "SELECT * FROM [necoelectro].[dbo].[kardex]";
        
        tb_dep_subgrupos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "referencia", "descrip_kar","subgrupo","departamento"
                }
        ));

        DefaultTableModel modelo = (DefaultTableModel) tb_dep_subgrupos.getModel();
        // ||
        Object[] column = new Object[modelo.getColumnCount()];

        int contador = 0;
        Connection cn = obj.conexion();
//        this.cbo_proveedor.removeAllItems();
//        Clase_Conexion obt=new Clase_Conexion("localhost", "vivero", "root", "");
        try {
            Statement stp = cn.createStatement();
            //LIKE '" + cadenaEscrita + "%'
//            try (ResultSet rs = stp.executeQuery("SELECT [nombre1_cl],[activo_cl] FROM [necoelectro].[dbo].[clientes] WHERE activo_cl=1 ORDER BY nombre1_cl")) {
//            try (ResultSet rs = stp.executeQuery("SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas_ver1] WHERE existencias<0 ORDER BY descrip_kar")) {
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {

                    column[0] = rs.getString("referencia");
                    column[1] = rs.getString("descrip_kar");
                    column[2] = ret_descrip_sg(rs.getString("codigo_sgr"));
                    String cod_dep = ret_codigo_dep(rs.getString("codigo_sgr"));
                    column[3] =ret_descrip_dep(cod_dep);
                    
//                    column[2]=rs.getInt("activo_kar");
//                    column[3] = rs.getInt("existencias");

                    modelo.addRow(column);
                }
            }
        } catch (SQLException localSQLException) {

        }
//        int[] anchos = {70, 300, 10, 10};
//        for (int i = 0; i < tb_dep_subgrupos.getColumnCount(); i++) {
//            tb_dep_subgrupos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
//        }

    }
    
    private void cargarTabla(String filtro) {
        
        String sql = "SELECT * FROM [necoelectro].[dbo].[kardex]";
        tb_dep_subgrupos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "referencia", "descrip_kar"
                }
        ));

        DefaultTableModel modelo = (DefaultTableModel) tb_dep_subgrupos.getModel();
        // ||
        Object[] column = new Object[modelo.getColumnCount()];

        int contador = 0;
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {

                    if (filtro.equals(rs.getString("codigo_sgr"))) {
                        
                        column[0] = rs.getString("referencia");
                        column[1] = rs.getString("descrip_kar");
                        modelo.addRow(column);
                    }
                }
            }
        } catch (SQLException localSQLException) {

        }
        int[] anchos = {70, 300};
        for (int i = 0; i < tb_dep_subgrupos.getColumnCount(); i++) {
            tb_dep_subgrupos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }
    
    private String orden_Departamento(){
        String sql = "SELECT *,D.descrip_dep FROM [necoelectro].[dbo].[subgrupos] AS G "
                + "INNER JOIN [necoelectro].[dbo].[depar_kardex] AS D ON G.[codigo_dep]=D.codigo_dep "
                + "ORDER BY D.descrip_dep";
        return sql;
    }
    private String orden_Subgrupo(){
        String sql = "SELECT *,D.descrip_dep FROM [necoelectro].[dbo].[subgrupos] AS G "
                + "INNER JOIN [necoelectro].[dbo].[depar_kardex] AS D ON G.[codigo_dep]=D.codigo_dep "
                + "ORDER BY G.descrip_sg";
        return sql;
    }
    
    private void cargarTabla_sgr_dep(String sql) {
        
//        String sql = "SELECT *,D.descrip_dep FROM [necoelectro].[dbo].[subgrupos] AS G "
//                + "INNER JOIN [necoelectro].[dbo].[depar_kardex] AS D ON G.[codigo_dep]=D.codigo_dep "
//                + "ORDER BY D.descrip_dep";
        
        tb_sgr_dep.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "codigo_sg", "descrip_sg","descrip_dep"
                }
        ));

        DefaultTableModel modelo = (DefaultTableModel) tb_sgr_dep.getModel();
        // ||
        Object[] column = new Object[modelo.getColumnCount()];

        
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {

                   
                        
                        column[0] = rs.getString("codigo_sg");
                        column[1] = rs.getString("descrip_sg");
                        column[2] = rs.getString("descrip_dep");
                        modelo.addRow(column);
                   
                }
            }
        } catch (SQLException localSQLException) {

        }
        int[] anchos = {5, 130,130};
        for (int i = 0; i < tb_sgr_dep.getColumnCount(); i++) {
            tb_sgr_dep.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }
     
    private String ret_codigo_departamento(String descrip_dep){
        String codigo_dep=null;
        String sql = "SELECT * FROM [necoelectro].[dbo].[depar_kardex] ";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
//                    String cod_dep=rs.getString("codigo_de");
                    if (descrip_dep.equals(rs.getString("descrip_dep"))) {
                        codigo_dep=rs.getString("codigo_dep");
                    }
                }
            }
        } catch (SQLException localSQLException) {
        }
        return codigo_dep;
    }
    
    private String ret_cod_sg(String descrip_sg,String codigo_dep){
        String codigo_sg=null;
//        String codigo_dep=ret_codigo_dep(descrip_dep);
        String sql = "SELECT * FROM [necoelectro].[dbo].[subgrupos] ";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
//                    String descripcion_sg=rs.getString("descrip_sg");
                    if (descrip_sg.equals(rs.getString("descrip_sg")) && codigo_dep.equals(rs.getString("codigo_dep"))) {
                        codigo_sg=rs.getString("codigo_sg");
                    }
//                    String descripcion_sg=rs.getString("descrip_sg");
//                    cbo_departamento.addItem(rs.getString("descrip_dep"));
                }
            }
        } catch (SQLException localSQLException) {
        }
        return codigo_sg;
    }
  
    
    private String ret_descrip_sg(String codigo_sg){
        String descrip_sg=null;
        String sql = "SELECT * FROM [necoelectro].[dbo].[subgrupos] ";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    String cod_sg=rs.getString("codigo_sg");
                    if (cod_sg == null ? codigo_sg == null : cod_sg.equals(codigo_sg)) {
                        descrip_sg=rs.getString("descrip_sg");
                    }
                }
            }
        } catch (SQLException localSQLException) {
        }
        return descrip_sg;
    }
    private String ret_codigo_dep(String codigo_sg){
        String codigo_dep=null;
        String sql = "SELECT * FROM [necoelectro].[dbo].[subgrupos] ";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    String cod_sg=rs.getString("codigo_sg");
                    if (cod_sg == null ? codigo_sg == null : cod_sg.equals(codigo_sg)) {
                        codigo_dep=rs.getString("codigo_dep");
                    }
                }
            }
        } catch (SQLException localSQLException) {
        }
        return codigo_dep;
    }
    
    private String ret_descrip_dep(String codigo_dep){
        String descrip_dep=null;
        String sql = "SELECT * FROM [necoelectro].[dbo].[depar_kardex] ";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    String cod_dep=rs.getString("codigo_dep");
                    if (cod_dep == null ? codigo_dep == null : cod_dep.equals(codigo_dep)) {
                        descrip_dep=rs.getString("descrip_dep");
                    }
                }
            }
        } catch (SQLException localSQLException) {
        }
        return descrip_dep;
    }
    
    private void cargar_comboBox_subgrupo(String fil_dep) {
        String sql = "SELECT descrip_sg FROM [necoelectro].[dbo].[subgrupos] AS G "
                + "INNER JOIN [necoelectro].[dbo].[depar_kardex] AS D ON G.[codigo_dep]=D.codigo_dep "
                + "WHERE D.descrip_dep = '" + fil_dep + "' ORDER BY G.descrip_sg";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    cbo_subgrupo.addItem(rs.getString("descrip_sg"));
                }
            }
        } catch (SQLException localSQLException) {
        }
    }
    
   
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tb_dep_subgrupos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        cbo_departamento = new javax.swing.JComboBox<>();
        cbo_subgrupo = new javax.swing.JComboBox<>();
        lb_rows = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lb_row_dep = new javax.swing.JLabel();
        cbo_consultar_sg = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        lb_item_sg = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_sgr_dep = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        tb_dep_subgrupos.setBackground(new java.awt.Color(0, 0, 0));
        tb_dep_subgrupos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tb_dep_subgrupos.setForeground(new java.awt.Color(0, 102, 0));
        tb_dep_subgrupos.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_dep_subgrupos.setRowHeight(28);
        jScrollPane1.setViewportView(tb_dep_subgrupos);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbo_departamento.setBackground(new java.awt.Color(0, 153, 153));
        cbo_departamento.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo_departamento.setMaximumRowCount(18);
        cbo_departamento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_departamentoItemStateChanged(evt);
            }
        });
        cbo_departamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbo_departamentoMouseClicked(evt);
            }
        });
        cbo_departamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_departamentoActionPerformed(evt);
            }
        });

        cbo_subgrupo.setBackground(new java.awt.Color(0, 153, 153));
        cbo_subgrupo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo_subgrupo.setMaximumRowCount(18);
        cbo_subgrupo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_subgrupoItemStateChanged(evt);
            }
        });
        cbo_subgrupo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbo_subgrupoFocusGained(evt);
            }
        });
        cbo_subgrupo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbo_subgrupoMouseClicked(evt);
            }
        });
        cbo_subgrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_subgrupoActionPerformed(evt);
            }
        });
        cbo_subgrupo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cbo_subgrupoPropertyChange(evt);
            }
        });
        cbo_subgrupo.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                cbo_subgrupoVetoableChange(evt);
            }
        });

        lb_rows.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_rows.setForeground(new java.awt.Color(255, 0, 0));
        lb_rows.setText("jLabel1");

        jButton1.setText("RESETIAR DEPARTAMENTOS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lb_row_dep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_row_dep.setForeground(new java.awt.Color(153, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbo_subgrupo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbo_departamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_rows)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_row_dep)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbo_departamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(lb_row_dep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbo_subgrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lb_rows)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        cbo_consultar_sg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo_consultar_sg.setMaximumRowCount(15);
        cbo_consultar_sg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CONSULTAR SUBGRUPOS", " " }));
        cbo_consultar_sg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_consultar_sgActionPerformed(evt);
            }
        });

        jButton2.setText("RESETIAR SUBGRUPOS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        lb_item_sg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_item_sg.setText("jLabel1");

        tb_sgr_dep.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tb_sgr_dep);

        jButton3.setText("Orden Departamento");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Orden Subgrupo");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbo_consultar_sg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_item_sg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbo_consultar_sg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(lb_item_sg)
                            .addComponent(jButton3)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbo_departamentoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_departamentoItemStateChanged

//        cbo_subgrupo.removeAllItems();
        
        
//            cargar_comboBox_departamento();
        
//            String filt_dep=cbo_departamento.getSelectedItem().toString();
//            cargar_comboBox_subgrupo(filt_dep);
        
        
//        JOptionPane.showMessageDialog(null, "Ítem seleccionado: "+cbo_departamento.getSelectedItem());
//        if (cbo_departamento.getSelectedItem()==null) {
//            cargar_comboBox_departamento();
//            repaint();
//            System.out.println("vistas.Departamento_subgrupos.cbo_departamentoItemStateChanged()");
//        }
    }//GEN-LAST:event_cbo_departamentoItemStateChanged

    private void cbo_departamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_departamentoActionPerformed

        //tuComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        
        cbo_subgrupo.removeAllItems();
//        
//        
//            cargar_comboBox_departamento();
//        
        if (cbo_departamento.getItemCount()>0) {
            String filt_dep=cbo_departamento.getSelectedItem().toString();
            cargar_comboBox_subgrupo(filt_dep);
            if (cbo_subgrupo.getItemCount()==0) {
                Precios.reiniciarJTable(tb_dep_subgrupos);
            }
        }else{
            System.out.println("combo vacio");
        }

            
       lb_row_dep.setText("ITEMS DEPARTAMENTOS-->"+cbo_departamento.getItemCount());
            
    }//GEN-LAST:event_cbo_departamentoActionPerformed

    private void cbo_subgrupoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_subgrupoItemStateChanged

        //            System.out.println("-->"+cbo_subgrupo.getSelectedItem());

    }//GEN-LAST:event_cbo_subgrupoItemStateChanged

    private void cbo_subgrupoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbo_subgrupoFocusGained

        //        System.out.println("-->"+cbo_subgrupo.getSelectedItem());
        //        String filt_dep=cbo_departamento.getSelectedItem().toString();
        //        cbo_subgrupo.removeAllItems();
        //        cargar_comboBox_subgrupo(filt_dep);

        //        String filt_dep=cbo_departamento.getSelectedItem().toString();
        //        cbo_subgrupo.removeAllItems();
        //        cargar_comboBox_subgrupo(filt_dep);
    }//GEN-LAST:event_cbo_subgrupoFocusGained

    private void cbo_subgrupoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbo_subgrupoMouseClicked
        //        System.out.println("-->"+cbo_subgrupo.getSelectedItem());
    }//GEN-LAST:event_cbo_subgrupoMouseClicked

    private void cbo_subgrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_subgrupoActionPerformed

        if (cbo_subgrupo.getSelectedItem()!=null) {
            
            String filt_dep=cbo_departamento.getSelectedItem().toString();
                String filt_sg=cbo_subgrupo.getSelectedItem().toString();
                String codigo_sg=ret_cod_sg(filt_sg,ret_codigo_departamento(filt_dep));
                
                cargarTabla(codigo_sg);
                
                lb_rows.setText("ITEMS PRODUCTOS --> "+tb_dep_subgrupos.getRowCount());
        }

    }//GEN-LAST:event_cbo_subgrupoActionPerformed

    private void cbo_subgrupoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbo_subgrupoPropertyChange
        //        String filt_dep=cbo_departamento.getSelectedItem().toString();
        //        String filt_sg=cbo_subgrupo.getSelectedItem().toString();
        //
        //        System.out.println("-->"+cbo_subgrupo.getSelectedItem());
    }//GEN-LAST:event_cbo_subgrupoPropertyChange

    private void cbo_subgrupoVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_cbo_subgrupoVetoableChange
        //        System.out.println("-->"+cbo_subgrupo.getSelectedItem());
    }//GEN-LAST:event_cbo_subgrupoVetoableChange

    private void cbo_consultar_sgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_consultar_sgActionPerformed
      
    }//GEN-LAST:event_cbo_consultar_sgActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo_departamento.getModel();
        model.removeAllElements();
        
        cargar_comboBox_departamento();
//        cbo_departamento.removeAllItems();
//        cargar_cbo_conArray();
        
////        if (cbo_departamento.getSelectedItem().toString()==null) {
//            cargar_comboBox_departamento(cbo_departamento);
////        }
////        String filt_dep=cbo_departamento.getToolTipText();
////        if (cbo_consultar_sg.getSelectedItem().toString()!=null) {
//////            String filt_dep=cbo_departamento.getToolTipText();
//            cargar_comboBox_subgrupo(filt_dep);
//            
            cbo_departamento.requestFocus();
//            
            String filt_dep=cbo_departamento.getSelectedItem().toString();
            cargar_comboBox_subgrupo(filt_dep);
////        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cbo_consultar_sg.removeAllItems();
        cargar_comboBox_consul_subgrupo();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbo_departamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbo_departamentoMouseClicked
       cbo_subgrupo.removeAllItems();
        System.out.println("Clickeado");
        
//            cargar_comboBox_departamento();
        
            String filt_dep=cbo_departamento.getSelectedItem().toString();
            cargar_comboBox_subgrupo(filt_dep);
    }//GEN-LAST:event_cbo_departamentoMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       cargarTabla_sgr_dep(orden_Departamento());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        cargarTabla_sgr_dep(orden_Subgrupo());
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbo_consultar_sg;
    private javax.swing.JComboBox<String> cbo_departamento;
    private javax.swing.JComboBox<String> cbo_subgrupo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_item_sg;
    private javax.swing.JLabel lb_row_dep;
    private javax.swing.JLabel lb_rows;
    private javax.swing.JTable tb_dep_subgrupos;
    private javax.swing.JTable tb_sgr_dep;
    // End of variables declaration//GEN-END:variables
}
