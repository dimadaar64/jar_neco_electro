/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.*;
 
import javax.swing.table.DefaultTableModel;
 
public class Productos {
 
    Connection cn;
 
    private int codigo;
 
    private String nombre;
 
    private int cantidad;
 
    private float precio;
 
       
    public Productos(){
 
        Conexion con=new Conexion();
 
        cn= con.conectar();
 
    }  
 
    public void LLenarDatos(DefaultTableModel modelo){
 
        try {
 
            String sql="select * from Productos";
 
            CallableStatement cmd=cn.prepareCall(sql);
 
            ResultSet rs= cmd.executeQuery();
 
           
 
            while(rs.next()){
 
                Object[] datos=new Object[4];
 
               
 
                for(int i=0;i<4;i++){
 
                    datos[i]=rs.getString(i+1);                  
 
                }
 
                modelo.addRow(datos);
 
            }
 
            cmd.close();
 
            cn.close();
 
           
 
        } catch (Exception e) {
 
            System.out.println(e.getMessage());
 
        }
 
    }  
 
    public void Agregar1 (String nombre, int cantidad, float precio){
 
        try {
 
            String sql="execute AgregarProducto ?,?,?";
 
            PreparedStatement cmd=cn.prepareCall(sql);
 
            cmd.setString(1, nombre);
 
            cmd.setInt(2, cantidad);
 
            cmd.setFloat(3, precio);
 
            cmd.execute();
 
            cmd.close();
 
            cn.close();          
 
        } catch (Exception e) {
 
            System.out.println(e.getMessage());
 
        }      
 
    }  
 
}