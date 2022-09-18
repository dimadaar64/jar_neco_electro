/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import Clases.FormatoDecimal;
import Operaciones.OperacionesTextosPlanos;
import Reportes.E_report;
import clases_DAO.kardex_DAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

public class Consultas_sql extends javax.swing.JInternalFrame {

    ArrayList<Reportes.E_report> array_limpio = new ArrayList<>();

    private static final int BOOLEAN_COLUMN = 6;

    private String capt_cod_barras = null;

    NumberFormat nf = NumberFormat.getInstance();

    kardex_DAO obj = new kardex_DAO();
    String uno = "SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] WHERE existencias<>0 ORDER BY descrip_kar";

    public Consultas_sql() {
        initComponents();
        cargarTabla(uno);
        cargar_comboBox();
        cargar_comboBox_departamento();
        btn_ver_report_table.setEnabled(false);

//        System.out.println("-->"+ret_cod_sg("CONGELADORES","CONGELADORES"));
    }

    public static void reiniciarJTable(javax.swing.JTable Tabla) {
        DefaultTableModel modelo = (DefaultTableModel) Tabla.getModel();
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }

//        TableColumnModel modCol = Tabla.getColumnModel();
//        while(modCol.getColumnCount()>0)modCol.removeColumn(modCol.getColumn(0));
    }

    private void repintar_tabla(JTable tbl_numeros_array) {
        if (tbl_numeros_array.getRowCount() > 0) {
            ///Para que se desplase al último registro de la tabla
            int row = tbl_numeros_array.getRowCount() - 1;
            Rectangle rect = tbl_numeros_array.getCellRect(row, 0, true);
            tbl_numeros_array.scrollRectToVisible(rect);
            tbl_numeros_array.clearSelection();
            tbl_numeros_array.setRowSelectionInterval(row, row);
            DefaultTableModel modelo = (DefaultTableModel) tbl_numeros_array.getModel();
            modelo.fireTableDataChanged();
        }
//        Rectangle r = tb_ensayo.getCellRect( i, 2, true);
//        jScrollPane1.getViewport().scrollRectToVisible (r);
        ///////////////////////////////////////////////////////////// 
    }

    private void repintar_tabla(JTable tbl_numeros_array, int row) {
        if (tbl_numeros_array.getRowCount() > 0) {
            ///Para que se desplase al último registro de la tabla
//            int row = tbl_numeros_array.getRowCount() - 1;
            Rectangle rect = tbl_numeros_array.getCellRect(row, 0, true);
            tbl_numeros_array.scrollRectToVisible(rect);
            tbl_numeros_array.clearSelection();
            tbl_numeros_array.setRowSelectionInterval(row, row);
            DefaultTableModel modelo = (DefaultTableModel) tbl_numeros_array.getModel();
            modelo.fireTableDataChanged();
        }
//        Rectangle r = tb_ensayo.getCellRect( i, 2, true);
//        jScrollPane1.getViewport().scrollRectToVisible (r);
        ///////////////////////////////////////////////////////////// 
    }

    private void cargarTabla(String sql) {

        tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "referencia", "descrip_kar", "activo_kar", "existencias"
                }
        ));

        DefaultTableModel modelo = (DefaultTableModel) tb_ensayo.getModel();
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
//                    column[2]=rs.getInt("activo_kar");
                    column[3] = rs.getInt("existencias");

                    modelo.addRow(column);
                }
            }
        } catch (SQLException localSQLException) {

        }
        int[] anchos = {70, 300, 10, 10};
        for (int i = 0; i < tb_ensayo.getColumnCount(); i++) {
            tb_ensayo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private void cargarTabla_con_DAT(ArrayList<Reportes.E_report> mi_list) {

        tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "referencia", "descrip_kar", "Precio 1", "Costo"
                }
        ));

        DefaultTableModel modelo = (DefaultTableModel) tb_ensayo.getModel();
        // ||
        Object[] column = new Object[modelo.getColumnCount()];

        for (int i = 0; i < mi_list.size(); i++) {
            E_report get = mi_list.get(i);

            column[0] = get.getCod();
            column[1] = get.getDescrip();
            column[2] = get.getPrecio();
            column[3] = get.getCosto();

            modelo.addRow(column);
        }

        int[] anchos = {70, 300, 10, 10};
        for (int i = 0; i < tb_ensayo.getColumnCount(); i++) {
            tb_ensayo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private void actualizar(String cod_kar, String activo) {
        String strSQL = "update [necoelectro].[dbo].[kardex] set activo_kar=" + activo + " where codigo_kar=" + cod_kar;
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            stp.execute(strSQL);

        } catch (SQLException ex) {
            Logger.getLogger(Consultas_sql.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public class CheckBoxModelListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == BOOLEAN_COLUMN) {
                TableModel model = (TableModel) e.getSource();
                String columnName = model.getColumnName(column);

                Boolean checked = (Boolean) model.getValueAt(row, column);
                String cod_kar = model.getValueAt(row, 0).toString();
                if (checked) {
                    actualizar(cod_kar, "1");
//                    System.out.println(columnName + ": " + true);
                } else {
                    actualizar(cod_kar, "0");
//                    System.out.println(columnName + ": " + false);
                }
            }
        }
    }

    private void cargar_comboBox() {

        String sql = "SELECT marca FROM [necoelectro].[dbo].[marcas] ORDER BY marca";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
//                    column[0] = rs.getString("referencia");
//                    column[1] = rs.getString("descrip_kar");
//                    column[2] = rs.getString("existencias");
                    cbo_marca.addItem(rs.getString("marca"));
//                    modelo.addRow(column);
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

    private void cargar_comboBox_subgrupo(String fil_dep) {
        String sql = "SELECT descrip_sg FROM [necoelectro].[dbo].[subgrupos] AS G "
                + "INNER JOIN [necoelectro].[dbo].[depar_kardex] AS D ON G.[codigo_dep]=D.codigo_dep "
                + "WHERE D.descrip_dep = '" + fil_dep + "' ";
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

    private void despues_de_select_cbo() {
        String select = cbo_marca.getSelectedItem().toString();

        String sql = "SELECT K.[codigo_kar],K.[referencia],K.[descrip_kar],K.precio1,K.precio3,K.[activo_kar],K.ult_costo,K.iva,K.codigo_barra,bodega.existencias,marc.marca"
                + " FROM [necoelectro].[dbo].[kardex] AS K"
                + " INNER JOIN [necoelectro].[dbo].[existencias_bodegas] AS bodega ON K.[codigo_kar]=bodega.codigo_kar"
                + " INNER JOIN [necoelectro].[dbo].[marcas] AS marc ON K.[codigo_mc]=marc.codigo_mc"
                + " WHERE marc.marca = '" + select + "' ORDER BY K.descrip_kar";

        cargarTabla_act_exis_check(sql);

    }

    private String ret_codigo_dep(String descrip_dep) {
        String codigo_de = null;
        String sql = "SELECT * FROM [necoelectro].[dbo].[depar_kardex] ";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    String descripcion_dep = rs.getString("descrip_dep");
                    if (descripcion_dep == null ? descrip_dep == null : descripcion_dep.equals(descrip_dep)) {
                        codigo_de = rs.getString("codigo_dep");

                    }
                }
            }
        } catch (SQLException localSQLException) {
        }
        return codigo_de;
    }

    private String ret_cod_sg(String descrip_sg, String descrip_dep) {
        String codigo_sg = null;
        String codigo_dep = ret_codigo_dep(descrip_dep);
        String sql = "SELECT * FROM [necoelectro].[dbo].[subgrupos] ";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    String descripcion_sg = rs.getString("descrip_sg");
                    if (descripcion_sg.equals(descrip_sg) && codigo_dep.equals(rs.getString("codigo_dep"))) {
                        codigo_sg = rs.getString("codigo_sg");
                    }
//                    String descripcion_sg=rs.getString("descrip_sg");
//                    cbo_departamento.addItem(rs.getString("descrip_dep"));
                }
            }
        } catch (SQLException localSQLException) {
        }
        return codigo_sg;
    }

    private void cargar_tab_con_sql(String codigo_sgr) {
//        String filt_dep=cbo_departamento.getSelectedItem().toString();
//        cbo_subgrupo.removeAllItems();
//        cargar_comboBox_subgrupo(filt_dep);

//        String filt_sg=cbo_subgrupo.getSelectedItem().toString();
//        String codigo_sg=ret_cod_sg(filt_sg, filt_dep);
        String sql = "SELECT K.[codigo_kar],K.[referencia],K.[descrip_kar],K.precio1,K.precio3,K.[activo_kar],K.ult_costo,K.iva,K.codigo_barra,bodega.existencias"
                + " FROM [necoelectro].[dbo].[kardex] AS K"
                + " INNER JOIN [necoelectro].[dbo].[existencias_bodegas] AS bodega"
                + " ON K.[codigo_kar]=bodega.codigo_kar"
                + " WHERE K.codigo_sgr = '" + codigo_sgr + "' ORDER BY K.descrip_kar";

        cargarTabla_act_exis_check(sql);
    }

    private void cargarTabla_todas_exist_neg(String sql) {
//       String sql="SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] WHERE existencias<0 ORDER BY descrip_kar"; 
        tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "referencia", "descrip_kar", "existencias"
                }
        ));
        DefaultTableModel modelo = (DefaultTableModel) tb_ensayo.getModel();
        Object[] column = new Object[modelo.getColumnCount()];
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    column[0] = rs.getString("referencia");
                    column[1] = rs.getString("descrip_kar");
                    column[2] = rs.getString("existencias");

                    modelo.addRow(column);
                }
            }
        } catch (SQLException localSQLException) {
        }
        int[] anchos = {25, 200, 10, 10};
        for (int i = 0; i < tb_ensayo.getColumnCount(); i++) {
            tb_ensayo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private void cargarTabla_filtrarActivos(String sql) {
        tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "cod_kar", "referencia", "descrip_kar", "precio1", "precio3", "activo_kar", "CH"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        tb_ensayo.getModel().addTableModelListener(new CheckBoxModelListener());
        ///.addTableModelListener(new CheckBoxModelListener())
        DefaultTableModel modelo = (DefaultTableModel) tb_ensayo.getModel();
        Object[] column = new Object[modelo.getColumnCount()];
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    String activo = rs.getString("activo_kar");
                    column[0] = rs.getString("codigo_kar");
                    column[1] = rs.getString("referencia");
                    column[2] = rs.getString("descrip_kar");
                    column[3] = rs.getString("precio1");
                    column[4] = rs.getString("precio3");
                    column[5] = activo;
                    column[6] = activo.equals("1");

                    modelo.addRow(column);
                }
            }
        } catch (SQLException localSQLException) {
        }
        int[] anchos = {10, 55, 400, 50, 50, 10, 5};
        for (int i = 0; i < tb_ensayo.getColumnCount(); i++) {
            tb_ensayo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    static String integerFormat(int i) {
        DecimalFormat df = new DecimalFormat("$#,###.##");
        String s = df.format(i);

        return s;

    }

    private void cargarTabla_act_exis_check(String sql) {

        FormatoDecimal myFormatter = new FormatoDecimal("#,###.##", true);

        tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "cod_kar", "referencia", "descrip_kar", "precio1", "precio3", "activo_kar", "CH", "und", "cost", "cod_barr"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        tb_ensayo.getModel().addTableModelListener(new CheckBoxModelListener());
        ///.addTableModelListener(new CheckBoxModelListener())
        DefaultTableModel modelo = (DefaultTableModel) tb_ensayo.getModel();
        Object[] column = new Object[modelo.getColumnCount()];
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    String activo = rs.getString("activo_kar");

                    int iva = rs.getInt("iva");
                    double cost = rs.getDouble("ult_costo");
                    double cost_con = cost * 1.19;

                    double exist = rs.getDouble("existencias");

                    column[0] = rs.getString("codigo_kar");
                    column[1] = rs.getString("referencia");
                    column[2] = rs.getString("descrip_kar");
                    column[3] = myFormatter.format(Double.parseDouble(rs.getString("precio1")));
                    column[4] = myFormatter.format(Double.parseDouble(rs.getString("precio3")));
                    column[5] = activo;

                    column[6] = activo.equals("1");
                    column[7] = nf.format(Math.round(exist));
//                    column[7]=(rs.getString("existencias"));
                    if (iva == 1) {
                        column[8] = nf.format(Math.round(cost_con));
                    } else {
                        column[8] = nf.format(Math.round(cost));
                    }
                    column[9] = rs.getString("codigo_barra");
//                    column[8]=myFormatter.format(Double.parseDouble(rs.getString("ult_costo")));
//                    column[8]=myFormatter.format(100000.000);
                    modelo.addRow(column);
                }
            }
        } catch (SQLException localSQLException) {
        }
        int[] anchos = {10, 55, 400, 50, 50, 10, 5, 10, 50, 100};
        for (int i = 0; i < tb_ensayo.getColumnCount(); i++) {
            tb_ensayo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private void cargarTabla_filtrarExistencias(String sql) {
        tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "referencia", "descrip_kar", "existencias"
                }
        ));
        DefaultTableModel modelo = (DefaultTableModel) tb_ensayo.getModel();
        Object[] column = new Object[modelo.getColumnCount()];
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    column[0] = rs.getString("referencia");
                    column[1] = rs.getString("descrip_kar");
                    column[2] = rs.getString("existencias");

                    modelo.addRow(column);
                }
            }
        } catch (SQLException localSQLException) {
        }
        int[] anchos = {25, 200, 10, 10};
        for (int i = 0; i < tb_ensayo.getColumnCount(); i++) {
            tb_ensayo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private void cargarTabla_filtrarExistencias_AND_ACTIVOS() {
        tb_ensayo.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "referencia", "descrip_kar", "existencias"
                }
        ));
        DefaultTableModel modelo = (DefaultTableModel) tb_ensayo.getModel();
        Object[] column = new Object[modelo.getColumnCount()];
        Connection cn = obj.conexion();

//        String sql="SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] ORDER BY descrip_kar"; 
//        String sql="SELECT E.[referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] E JOIN [necoelectro].[dbo].[kardex] K ON E.[referencia]==K.[referencia] WHERE [existencias]>0";
        String sql = "SELECT K.referencia,K.descrip_kar,K.activo_kar,B.existencias FROM dbo.kardex K JOIN dbo.existencias_bodegas B ON K.referencia=B.referencia WHERE K.activo_kar=1 ORDER BY K.descrip_kar";
//                + "FROM existencias_bodegas B JOIN kardex K ON B.referencia=K.referencia"; 

        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    String ref = rs.getString("referencia");
                    column[0] = ref;
                    column[1] = rs.getString("descrip_kar");
//                    column[2]=rs.getString("existencias");
                    column[2] = Math.round(rs.getDouble("existencias"));
//                    if (IsActivo(ref)) {
                    modelo.addRow(column);
//                    }
                }
            }
        } catch (SQLException localSQLException) {
        }
        int[] anchos = {40, 300, 10};
        for (int i = 0; i < tb_ensayo.getColumnCount(); i++) {
            tb_ensayo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private boolean IsActivo(String ref) {
        String sql = "SELECT [referencia],[activo_kar] FROM [necoelectro].[dbo].[kardex]";
        Connection cn = obj.conexion();
        try {
            Statement stp = cn.createStatement();
            try (ResultSet rs = stp.executeQuery(sql)) {
                while (rs.next()) {
                    String referencia = rs.getString("referencia");
                    String activo = rs.getString("activo_kar");
                    if ((referencia == null ? ref == null : referencia.equals(ref)) && "1".equals(activo)) {
                        return true;
                    }

                }
            }
        } catch (SQLException localSQLException) {
        }

        return false;
    }

//    private float contar_exis_total() {
//        float suma = 0;
//        for (int i = 0; i < tb_ensayo.getRowCount(); i++) {
//            String f1 = tb_ensayo.getValueAt(i, 2).toString();
//            float F1 = Float.parseFloat(f1);
//            suma = suma + F1;
//        }
//        return suma;
//    }
    private int contar_row(String cod_barr) {

        for (int i = 0; i < tb_ensayo.getRowCount(); i++) {
            String f1 = tb_ensayo.getValueAt(i, 9).toString();

            if (cod_barr == null ? f1 == null : cod_barr.equals(f1)) {
                return i;
            }

        }
        return 0;
    }

    private void report() {
        List lista = new ArrayList();
        for (int i = 0; i < tb_ensayo.getRowCount(); i++) {
            E_report num = new E_report(
                    tb_ensayo.getValueAt(i, 1).toString(),
                    tb_ensayo.getValueAt(i, 2).toString(),
                    tb_ensayo.getValueAt(i, 3).toString(),
                    tb_ensayo.getValueAt(i, 8).toString()
            );

            lista.add(num);
        }
        try {
            InputStream reporte = Consultas_sql.class.getResourceAsStream("/Reportes/" + "Lista_de_tabla.jasper");
//            JasperReport reporte;
//            reporte = (JasperReport) JRLoader.loadObjectFromFile("C:\\Users\\USUARIO\\Documentos\\NetBeansProjects\\Necoabasto_consultas_sqlServer\\Lista_de_tabla.jasper");

            JasperPrint imprimir = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(lista));

            JasperViewer.viewReport(imprimir, false);

        } catch (JRException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void report_2() {
        if (tb_ensayo.getRowCount() > 0) {
            List lista = new ArrayList();
            for (int i = 0; i < tb_ensayo.getRowCount(); i++) {
                E_report num = new E_report(
                        tb_ensayo.getValueAt(i, 0).toString(),
                        tb_ensayo.getValueAt(i, 1).toString(),
                        tb_ensayo.getValueAt(i, 2).toString(),
                        tb_ensayo.getValueAt(i, 3).toString()
                );

                lista.add(num);
            }
            try {
                InputStream reporte = Consultas_sql.class.getResourceAsStream("/Reportes/" + "Lista_de_tabla.jasper");
//            reporte = (JasperReport) JRLoader.loadObjectFromFile("C:/Users/USUARIO/OneDrive/Documents/NetBeansProjects/Necoabasto_consultas_sqlServer/src/Reportes/Lista_de_tabla.jasper");

                JasperPrint imprimir = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(lista));

                JasperViewer.viewReport(imprimir, false);

            } catch (JRException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(this, "Tabla Vacia");
        }

//        JasperReport reporte;
    }

    public void ejecutarReporte(String archivo) {
//        try {
//            // asumiendo que archivo es algo como reporte.jasper
//            // MiClase es la clase donde se encuentra este método
//            InputStream reporteInputStream = Consultas_sql.class.getResourceAsStream("/Reportes/" + archivo);
//            
//            
//            
//            JasperPrint imprimir = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(lista));
//            
//            JasperViewer jView = new JasperViewer(imprimir, false);
//            
////            JasperPrint jasperPrint = JasperFillManager.fillReport(reporteInputStream, null, conexion);
////            JasperViewer jView = new JasperViewer(jasperPrint, false);
//            jView.setVisible(true);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
//        }
    }

    private void reporte() {

        try {
            int fila;
            String datos = "";

            List Resultados = new ArrayList();
            Encapsulados.Cod_des_precio tipo;

            Resultados.clear();
            //Recorremos la Tabla
            for (fila = 0; fila < tb_ensayo.getRowCount(); fila++) {
                tipo = new Encapsulados.Cod_des_precio(String.valueOf(tb_ensayo.getValueAt(fila, 1)),
                        String.valueOf(tb_ensayo.getValueAt(fila, 2)),
                        String.valueOf(tb_ensayo.getValueAt(fila, 3)));

                Resultados.add(tipo);
            }

            Map map = new HashMap();
            JasperPrint jPrint;
            JDialog reporte = new JDialog();
            reporte.setSize(900, 700);
            reporte.setLocationRelativeTo(null);
            reporte.setTitle("Reporte Precios");
            map.put("report_1", "Reporte Ensayo");

            jPrint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("Lista_de_tabla.jasper"),
                    map, new JRBeanCollectionDataSource(Resultados));

            JRViewer jv = new JRViewer(jPrint);
            reporte.getContentPane().add(jv);
            reporte.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Consultas_sql.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void llenar_array() {
        for (int i = 0; i < tb_ensayo.getRowCount(); i++) {
            E_report num = new E_report(
                    tb_ensayo.getValueAt(i, 1).toString(),
                    tb_ensayo.getValueAt(i, 2).toString(),
                    tb_ensayo.getValueAt(i, 3).toString(),
                    tb_ensayo.getValueAt(i, 8).toString()
            );

            array_limpio.add(num);
        }
    }

    private void cargar_lista_con_table() {

        llenar_array();
    }

//    private ArrayList<E_report> capt_dat_tabla() {
//        for (int i = 0; i < tb_ensayo.getRowCount(); i++) {
//            E_report num = new E_report(
//                    tb_ensayo.getValueAt(i, 1).toString(),
//                    tb_ensayo.getValueAt(i, 2).toString(),
//                    tb_ensayo.getValueAt(i, 3).toString(),
//                    tb_ensayo.getValueAt(i, 8).toString()
//            );
//
//            array_limpio.add(num);
//        }
//        return array_limpio;
//    }
    private class pintar_cod_barras extends DefaultTableCellRenderer {

        private final Font fontePadrao = new Font("MS Sanserif", Font.BOLD, 16);
        private final Font fonte2 = new Font("Inherited", Font.PLAIN, 13);

        int contador = 0;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cell.setFont(this.fontePadrao);
            if (value instanceof String) {

                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 2; j < 10; j++) {
                        if (column == j) {
                            if (row == i) {

                                String valor = table.getValueAt(i, 9).toString();
                                String filtro = capt_cod_barras;
//                                double VALOR = Integer.parseInt(valor);
                                if (column == 9 || column == 2) {
                                    if (filtro.equals(valor)) {
                                        cell.setBackground(Color.GREEN);
                                        cell.setForeground(Color.BLACK);
                                        cell.setFont(fontePadrao);
//                                        
//                                        contador=i;
//                                        
//                                       repintar_tabla(tb_ensayo, contador); 
//                                        Rectangle r = tb_ensayo.getCellRect( i, 2, true);
//                                        jScrollPane1.getViewport().scrollRectToVisible (r);
//                                        cell.requestFocus();
                                    } else {
                                        cell.setBackground(Color.WHITE);
                                        cell.setForeground(Color.BLACK);
                                        cell.setFont(fonte2);
                                    }

//                                    Rectangle r = tb_ensayo.getCellRect( i, 2, true);
//                                        jScrollPane1.getViewport().scrollRectToVisible (r);
                                } //                                if (valor == null ? filtro == null : valor.equals(filtro)) {
                                //                                    cell.setBackground(Color.GREEN);
                                //                                    cell.setForeground(Color.BLACK);
                                //                                }
                                else {
                                    cell.setBackground(Color.WHITE);
                                    cell.setForeground(Color.BLACK);
                                    cell.setFont(fonte2);
                                }
                            }
                        }
                    }

//                    Rectangle r = tb_ensayo.getCellRect( i, 2, true);
//                                        jScrollPane1.getViewport().scrollRectToVisible (r);
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
        jPanel1 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        btn_todao_positivo = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btn_exis_act_che = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        txt_filtro = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        cbo_marca = new javax.swing.JComboBox<>();
        cbo_departamento = new javax.swing.JComboBox<>();
        cbo_subgrupo = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        txt_pintar_cod = new javax.swing.JTextField();
        lb_rows = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        btn_ver_report_table = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

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

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton6.setText("Ver Todos Los Avtivos");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        btn_todao_positivo.setText("Ver Todas las Existencias Positivas");
        btn_todao_positivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_todao_positivoActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton8.setText("Ver existencias Articulos Activos");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton2.setText(" ACTIVADOS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Existencias no ceros Derecho");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_exis_act_che.setText("act_und_ch");
        btn_exis_act_che.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exis_act_cheActionPerformed(evt);
            }
        });

        jButton9.setText("tODOS_EXIS");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        txt_filtro.setBackground(new java.awt.Color(153, 255, 204));
        txt_filtro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_filtro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_filtroActionPerformed(evt);
            }
        });

        jButton7.setText("Report");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton11.setText("MARCA");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_exis_act_che)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_exis_act_che)
                    .addComponent(jButton9)
                    .addComponent(jButton11))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jButton3.setText("NO ACTIVADOS");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("Existencias no ceros Ambos Lados");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton1.setText("Ver Todas las Existencias Negativas");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cbo_marca.setBackground(new java.awt.Color(0, 153, 153));
        cbo_marca.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo_marca.setMaximumRowCount(18);
        cbo_marca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Marca" }));
        cbo_marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_marcaActionPerformed(evt);
            }
        });

        cbo_departamento.setBackground(new java.awt.Color(0, 153, 153));
        cbo_departamento.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbo_departamento.setMaximumRowCount(18);
        cbo_departamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Departamento" }));
        cbo_departamento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_departamentoItemStateChanged(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_todao_positivo)
                            .addComponent(jButton6)
                            .addComponent(jButton8)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4))
                            .addComponent(jButton3)
                            .addComponent(jButton5)
                            .addComponent(jButton1)
                            .addComponent(cbo_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_departamento, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_subgrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(cbo_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(cbo_departamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(cbo_subgrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_todao_positivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "PINTAR CODIGO BARRAS"));

        txt_pintar_cod.setBackground(new java.awt.Color(255, 102, 102));
        txt_pintar_cod.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_pintar_cod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_pintar_codActionPerformed(evt);
            }
        });

        lb_rows.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lb_rows.setForeground(new java.awt.Color(0, 102, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lb_rows, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txt_pintar_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txt_pintar_cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_rows, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton10.setBackground(new java.awt.Color(204, 0, 0));
        jButton10.setText("ELIMINAR FILAS SELECT");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton12.setBackground(new java.awt.Color(0, 102, 51));
        jButton12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton12.setText("Agregar dato de la tabla a la Lista");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("Ver Reporte de LISTA");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton15.setText(".DAT");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        btn_ver_report_table.setText("Ver Reporte .DAT");
        btn_ver_report_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver_report_tableActionPerformed(evt);
            }
        });

        jButton17.setText("Carg Tabla .DAT");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setText("Ver Reporte Tabla");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton13)
                            .addComponent(jButton18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton17)
                            .addComponent(btn_ver_report_table))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13)
                    .addComponent(btn_ver_report_table))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jButton18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton14.setBackground(new java.awt.Color(204, 0, 0));
        jButton14.setText("Limpiar Array");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1145, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton14))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton10)
                            .addComponent(jButton14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        reiniciarJTable(tb_ensayo);
        String sql = "SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] WHERE existencias<0 ORDER BY descrip_kar";
        cargarTabla_todas_exist_neg(sql);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        reiniciarJTable(tb_ensayo);
        if (txt_filtro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Texto a Filtrar ?");
        } else {
            String filtro = txt_filtro.getText();
            String sql = "SELECT [codigo_kar],[referencia],[descrip_kar],precio1,precio3,[activo_kar],[existencias] FROM [necoelectro].[dbo].[kardex] WHERE activo_kar=1 AND descrip_kar LIKE '%" + filtro + "%' ORDER BY descrip_kar";
//            String sql="SELECT [referencia],[descrip_kar],[activo_kar],[existencias] FROM [necoelectro].[dbo].[kardex] WHERE activo_kar=1 AND descrip_kar LIKE '"+filtro+"%' ORDER BY descrip_kar";
            cargarTabla_filtrarActivos(sql);
        }
        lb_rows.setText("Rows: " + tb_ensayo.getRowCount());

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        reiniciarJTable(tb_ensayo);
        if (txt_filtro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Texto a Filtrar ?");
        } else {
            String filtro = txt_filtro.getText();
            String sql = "SELECT [codigo_kar],[referencia],[descrip_kar],precio1,precio3,[activo_kar],[existencias] FROM [necoelectro].[dbo].[kardex] WHERE activo_kar=0 AND descrip_kar LIKE '%" + filtro + "%' ORDER BY descrip_kar";
//            String sql="SELECT [referencia],[descrip_kar],[activo_kar],[existencias] FROM [necoelectro].[dbo].[kardex] WHERE activo_kar=0 AND descrip_kar LIKE '%"+filtro+"%' ORDER BY descrip_kar";
            cargarTabla_filtrarActivos(sql);
        }
        lb_rows.setText("Rows: " + tb_ensayo.getRowCount());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        reiniciarJTable(tb_ensayo);
        if (txt_filtro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Texto a Filtrar ?");
        } else {
            String filtro = txt_filtro.getText();
            String sql = "SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] WHERE existencias<>0 AND descrip_kar LIKE '" + filtro + "%' ORDER BY descrip_kar";
            cargarTabla_filtrarExistencias(sql);
        }
//        lb_rows.setText("Rows: " + tb_ensayo.getRowCount() + " --Total existencias= " + contar_exis_total());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        reiniciarJTable(tb_ensayo);
        if (txt_filtro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Texto a Filtrar ?");
        } else {
            String filtro = txt_filtro.getText();
            String sql = "SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] WHERE existencias<>0 AND descrip_kar LIKE '%" + filtro + "%' ORDER BY descrip_kar";
            cargarTabla_filtrarExistencias(sql);
        }
        lb_rows.setText("Rows: " + tb_ensayo.getRowCount());
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        reiniciarJTable(tb_ensayo);
//        if (txt_filtro.getText().isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Texto a Filtrar ?");
//        }else{
//            String filtro=txt_filtro.getText();
        String sql = "SELECT [referencia],[descrip_kar],[activo_kar],[existencias] FROM [necoelectro].[dbo].[kardex] WHERE activo_kar=1 ORDER BY descrip_kar";
        cargarTabla_filtrarActivos(sql);
//        }
        lb_rows.setText("Rows: " + tb_ensayo.getRowCount());
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btn_exis_act_cheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exis_act_cheActionPerformed
        if (txt_filtro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Texto a Filtrar ?");
        } else {
            String filtro = txt_filtro.getText();
            //String sql="SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] WHERE existencias<>0 AND descrip_kar LIKE '"+filtro+"%' ORDER BY descrip_kar";  
            String sql = "SELECT K.[codigo_kar],K.[referencia],K.[descrip_kar],K.precio1,K.precio3,K.[activo_kar],K.ult_costo,K.iva,K.codigo_barra,bodega.existencias"
                    + " FROM [necoelectro].[dbo].[kardex] AS K"
                    + " INNER JOIN [necoelectro].[dbo].[existencias_bodegas] AS bodega"
                    + " ON K.[codigo_kar]=bodega.codigo_kar"
                    + " WHERE K.descrip_kar LIKE '%" + filtro + "%' ORDER BY K.descrip_kar";
//String sql="SELECT k.codigo_kar as 'kar',k.referencia as 'kar',k.descrip_kar as 'kar',k.precio1 as 'kar',k.precio3 as 'kar',k.activo_kar as 'kar',b.codigo_kar as 'bod',b.existencias_bodegas as 'bod'"
//        + "FROM kar k JOIN bod b ON k.codigo_kar=b.codigo_kar WHERE k.descrip_kar LIKE '%\"+filtro+\"%' ORDER BY k.descrip_kar";
            cargarTabla_act_exis_check(sql);
        }
        lb_rows.setText("Rows: " + tb_ensayo.getRowCount());
//        tb_ensayo.getColumnModel().getColumn(5).setCellRenderer(new pintar_SINeXIS_EXIST());
        repintar_tabla(tb_ensayo);
    }//GEN-LAST:event_btn_exis_act_cheActionPerformed

    private void btn_todao_positivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_todao_positivoActionPerformed

        reiniciarJTable(tb_ensayo);
        String sql = "SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] WHERE existencias>0 ORDER BY descrip_kar";
        cargarTabla_todas_exist_neg(sql);
        lb_rows.setText("Rows: " + tb_ensayo.getRowCount());
    }//GEN-LAST:event_btn_todao_positivoActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        reiniciarJTable(tb_ensayo);
        cargarTabla_filtrarExistencias_AND_ACTIVOS();
        lb_rows.setText("Rows: " + tb_ensayo.getRowCount());
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        reiniciarJTable(tb_ensayo);
        if (txt_filtro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Texto a Filtrar ?");
        } else {
            String filtro = txt_filtro.getText();
            String sql = "SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] WHERE descrip_kar LIKE '%" + filtro + "%' ORDER BY descrip_kar";
//            String sql = "SELECT [referencia],[descrip_kar],precio1,precio3,[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] WHERE descrip_kar LIKE '%" + filtro + "%' ORDER BY descrip_kar";
            cargarTabla_filtrarExistencias(sql);
        }
//        lb_rows.setText("Rows: " + tb_ensayo.getRowCount() + " --Total existencias= " + contar_exis_total());
    }//GEN-LAST:event_jButton9ActionPerformed

    private void txt_filtroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_filtroActionPerformed
        if (txt_filtro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Texto a Filtrar ?");
        } else {
            String filtro = txt_filtro.getText();
            //String sql="SELECT [referencia],[descrip_kar],[existencias] FROM [necoelectro].[dbo].[existencias_bodegas] WHERE existencias<>0 AND descrip_kar LIKE '"+filtro+"%' ORDER BY descrip_kar";  
            String sql = "SELECT K.[codigo_kar],K.[referencia],K.[descrip_kar],K.precio1,K.precio3,K.[activo_kar],K.ult_costo,K.iva,K.codigo_barra,bodega.existencias"
                    + " FROM [necoelectro].[dbo].[kardex] AS K"
                    + " INNER JOIN [necoelectro].[dbo].[existencias_bodegas] AS bodega"
                    + " ON K.[codigo_kar]=bodega.codigo_kar"
                    + " WHERE K.codigo_barra = '" + filtro + "' ORDER BY K.descrip_kar";
//String sql="SELECT k.codigo_kar as 'kar',k.referencia as 'kar',k.descrip_kar as 'kar',k.precio1 as 'kar',k.precio3 as 'kar',k.activo_kar as 'kar',b.codigo_kar as 'bod',b.existencias_bodegas as 'bod'"
//        + "FROM kar k JOIN bod b ON k.codigo_kar=b.codigo_kar WHERE k.descrip_kar LIKE '%\"+filtro+\"%' ORDER BY k.descrip_kar";
//            if (sql == null) {
//                JOptionPane.showMessageDialog(null, "SIN REGISTRO");
//                System.out.println("vistas.Consultas_sql.txt_filtroActionPerformed()");
//            } else {
            cargarTabla_act_exis_check(sql);
//                repintar_tabla(tb_ensayo);
            lb_rows.setText("Rows: " + tb_ensayo.getRowCount());
//                tb_ensayo.getColumnModel().getColumn(5).setCellRenderer(new pintar_SINeXIS_EXIST());

            txt_filtro.setText("");
            txt_filtro.requestFocus();
            if (tb_ensayo.getRowCount() > 0) {
                repintar_tabla(tb_ensayo);
            }

//            }
        }
//        lb_rows.setText("Rows: "+tb_ensayo.getRowCount());
//        tb_ensayo.getColumnModel().getColumn(5).setCellRenderer(new pintar_SINeXIS_EXIST());
//        
//        txt_filtro.setText("");
//        txt_filtro.requestFocus();
    }//GEN-LAST:event_txt_filtroActionPerformed

    private void txt_pintar_codActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_pintar_codActionPerformed

        capt_cod_barras = txt_pintar_cod.getText();
        tb_ensayo.getColumnModel().getColumn(2).setCellRenderer(new pintar_cod_barras());
        tb_ensayo.getColumnModel().getColumn(9).setCellRenderer(new pintar_cod_barras());

        int fil = contar_row(capt_cod_barras);
        if (tb_ensayo.getRowCount() < 10 || fil < 10) {
            repintar_tabla(tb_ensayo, fil);
        } else {
            repintar_tabla(tb_ensayo, fil - 10);

        }
        txt_pintar_cod.setText("");
    }//GEN-LAST:event_txt_pintar_codActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
   
        if (tb_ensayo.getRowCount() > 150) {
            JOptionPane.showMessageDialog(null, "INFORME CON MAS DE 150 ITEMS");
        } else {
            if (tb_ensayo.getColumnCount()>4) {
                report();
            }else{
                JOptionPane.showMessageDialog(null, "Tabla no corresponde a este informe");
            }
            
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        Utilidades.Con_tablas obj_2 = new Utilidades.Con_tablas();
        obj_2.eliminarFilasSeleccionadas(tb_ensayo);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        reiniciarJTable(tb_ensayo);
        if (txt_filtro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Texto a Filtrar ?");
        } else {
            String filtro = txt_filtro.getText();

            String sql = "SELECT K.[codigo_kar],K.[referencia],K.[descrip_kar],K.precio1,K.precio3,K.[activo_kar],K.ult_costo,K.iva,K.codigo_barra,bodega.existencias,marc.marca"
                    + " FROM [necoelectro].[dbo].[kardex] AS K"
                    + " INNER JOIN [necoelectro].[dbo].[existencias_bodegas] AS bodega ON K.[codigo_kar]=bodega.codigo_kar"
                    + " INNER JOIN [necoelectro].[dbo].[marcas] AS marc ON K.[codigo_mc]=marc.codigo_mc"
                    + " WHERE marc.marca LIKE '%" + filtro + "%' ORDER BY K.descrip_kar";

            cargarTabla_act_exis_check(sql);
        }
//        lb_rows.setText("Rows: " + tb_ensayo.getRowCount() + " --Total existencias= " + contar_exis_total());
    }//GEN-LAST:event_jButton11ActionPerformed

    private void cbo_marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_marcaActionPerformed
        despues_de_select_cbo();
    }//GEN-LAST:event_cbo_marcaActionPerformed

    private void cbo_departamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_departamentoActionPerformed

        cbo_subgrupo.removeAllItems();
//        cbo_subgrupo.transferFocus();
//        cbo_subgrupo.removeAllItems();
        String filt_dep = cbo_departamento.getSelectedItem().toString();

        cargar_comboBox_subgrupo(filt_dep);

        ///
//        String codigo_dep=ret_codigo_dep(filt_dep);
//        String sql = "SELECT K.[codigo_kar],K.[referencia],K.[descrip_kar],K.precio1,K.precio3,K.[activo_kar],K.ult_costo,K.iva,K.codigo_barra,bodega.existencias"
//                    + " FROM [necoelectro].[dbo].[kardex] AS K"
//                    + " INNER JOIN [necoelectro].[dbo].[existencias_bodegas] AS bodega ON K.[codigo_kar]=bodega.codigo_kar"
//                    + " INNER JOIN [necoelectro].[dbo].[depar_kardex] AS D ON K.[codigo_dep]=D.codigo_dep"
//                    + " WHERE D.codigo_dep LIKE '%" + codigo_dep + "%' ORDER BY K.descrip_kar";
////            1
//            cargarTabla_act_exis_check(sql);

    }//GEN-LAST:event_cbo_departamentoActionPerformed

    private void cbo_subgrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_subgrupoActionPerformed

////        String filt_dep=cbo_departamento.getSelectedItem().toString();
////        cbo_subgrupo.removeAllItems();
////        cargar_comboBox_subgrupo(filt_dep);
//        String filt_dep=cbo_departamento.getSelectedItem().toString();
//        String filt_sg=cbo_subgrupo.getSelectedItem().toString();
        if (cbo_subgrupo.getSelectedItem() != null) {
//            System.out.println("-->"+cbo_subgrupo.getSelectedItem()+"-----"+cbo_departamento.getSelectedItem()+
//                    " Codigo_sr-->"+ret_cod_sg(cbo_subgrupo.getSelectedItem().toString(), cbo_departamento.getSelectedItem().toString()));

            cargar_tab_con_sql(ret_cod_sg(cbo_subgrupo.getSelectedItem().toString(), cbo_departamento.getSelectedItem().toString()));

        }

//        cbo_subgrupo.removeAllItems();
//        cbo_subgrupo.removeAllItems();
//        
//        cargar_tab_con_sql(filt_dep, filt_dep);
//        cargar_comboBox_subgrupo(filt_dep);
//        
//        String filt_sg=cbo_subgrupo.getSelectedItem().toString();
//        
//        String codigo_sg=ret_cod_sg(filt_sg, filt_dep);
//        
//        String sql = "SELECT K.[codigo_kar],K.[referencia],K.[descrip_kar],K.precio1,K.precio3,K.[activo_kar],K.ult_costo,K.iva,K.codigo_barra,bodega.existencias"
//                    + " FROM [necoelectro].[dbo].[kardex] AS K"
//                    + " INNER JOIN [necoelectro].[dbo].[existencias_bodegas] AS bodega"
//                    + " ON K.[codigo_kar]=bodega.codigo_kar"
//                    + " WHERE K.codigo_sgr = '" + codigo_sg + "' ORDER BY K.descrip_kar";
//            
//            cargarTabla_act_exis_check(sql);
//        filt_sg=null;
//            cbo_subgrupo.removeAllItems();
    }//GEN-LAST:event_cbo_subgrupoActionPerformed

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

    private void cbo_subgrupoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbo_subgrupoPropertyChange
//        String filt_dep=cbo_departamento.getSelectedItem().toString();
//        String filt_sg=cbo_subgrupo.getSelectedItem().toString();
//        
//        System.out.println("-->"+cbo_subgrupo.getSelectedItem());
    }//GEN-LAST:event_cbo_subgrupoPropertyChange

    private void cbo_subgrupoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbo_subgrupoMouseClicked
//        System.out.println("-->"+cbo_subgrupo.getSelectedItem());
    }//GEN-LAST:event_cbo_subgrupoMouseClicked

    private void cbo_subgrupoVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_cbo_subgrupoVetoableChange
//        System.out.println("-->"+cbo_subgrupo.getSelectedItem());
    }//GEN-LAST:event_cbo_subgrupoVetoableChange

    private void cbo_departamentoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_departamentoItemStateChanged
//       String filt_dep=cbo_departamento.getSelectedItem().toString();
//        
//        cargar_comboBox_subgrupo(filt_dep);
    }//GEN-LAST:event_cbo_departamentoItemStateChanged

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

        if (tb_ensayo.getRowCount() > 0 && tb_ensayo.getColumnCount()>5) {
            llenar_array();

            reiniciarJTable(tb_ensayo);
        } else {
            JOptionPane.showMessageDialog(this, "Tabla vacia o no corresponde al tipo de tabla. No tiene elementos para agregar");
        }


    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed

        if (array_limpio.size() > 0) {
//            JasperReport reporte;
            try {
                InputStream reporte = Consultas_sql.class.getResourceAsStream("/Reportes/" + "Lista_de_tabla.jasper");
//                reporte = (JasperReport) JRLoader.loadObjectFromFile("C:/Users/USUARIO/Documents/NetBeansProjects/Necoabasto_consultas_sqlServer/src/Reportes/Lista_de_tabla.jasper");

                JasperPrint imprimir = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(array_limpio));

                JasperViewer.viewReport(imprimir, false);

            } catch (JRException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sin Registros para mostrar");
        }

    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        array_limpio.clear();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed

        if (array_limpio.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Array para Guardar Vacio");
        } else {
            Operaciones.OperacionesTextosPlanos numerosCafeConPacha = new OperacionesTextosPlanos();
            numerosCafeConPacha.CrearGuardarArchivoDatos("lista_necoelectro", array_limpio);
        }

    }//GEN-LAST:event_jButton15ActionPerformed

    private void btn_ver_report_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver_report_tableActionPerformed
        Operaciones.OperacionesTextosPlanos obtenerNumeros = new OperacionesTextosPlanos();
        ArrayList<E_report> lista_DAT = obtenerNumeros.leerRecuperarArrayArchivoDatos("lista_necoelectro");

        if (lista_DAT.size() > 0) {
            try {
                InputStream reporte = Consultas_sql.class.getResourceAsStream("/Reportes/" + "Lista_de_tabla.jasper");
//                reporte = (JasperReport) JRLoader.loadObjectFromFile("C:/Users/USUARIO/Documents/NetBeansProjects/Necoabasto_consultas_sqlServer/src/Reportes/Lista_de_tabla.jasper");

                JasperPrint imprimir = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(lista_DAT));

                JasperViewer.viewReport(imprimir, false);

            } catch (JRException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lista Vacia");
        }
        btn_ver_report_table.setEnabled(false);

    }//GEN-LAST:event_btn_ver_report_tableActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        Operaciones.OperacionesTextosPlanos obtenerNumeros = new OperacionesTextosPlanos();
        ArrayList<E_report> lista_DAT = obtenerNumeros.leerRecuperarArrayArchivoDatos("lista_necoelectro");
        cargarTabla_con_DAT(lista_DAT);
        btn_ver_report_table.setEnabled(true);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        if (tb_ensayo.getColumnCount()<5) {
            report_2();
        }else{
            JOptionPane.showMessageDialog(this, "Datos de tabla no corresponden");
        }
        
    }//GEN-LAST:event_jButton18ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_exis_act_che;
    private javax.swing.JButton btn_todao_positivo;
    private javax.swing.JButton btn_ver_report_table;
    private javax.swing.JComboBox<String> cbo_departamento;
    private javax.swing.JComboBox<String> cbo_marca;
    private javax.swing.JComboBox<String> cbo_subgrupo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_rows;
    private javax.swing.JTable tb_ensayo;
    private javax.swing.JTextField txt_filtro;
    private javax.swing.JTextField txt_pintar_cod;
    // End of variables declaration//GEN-END:variables
}
