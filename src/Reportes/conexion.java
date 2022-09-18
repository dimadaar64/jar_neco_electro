/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class conexion
{
  static String bd = "vivero";
  static String login = "root";
  static String password = "";
//  static String url = "jdbc:mysql://localhost/" + bd;
  Connection conn = null;
  
  public conexion(){
//    try
//    {
//      Class.forName("com.mysql.jdbc.Driver");
//      
//      this.conn = DriverManager.getConnection(url, login, password);
//      if (this.conn != null) {
//        System.out.println("Conexi��n a base de datos " + bd + ". listo");
//      }
//    }
//    catch (SQLException|ClassNotFoundException e)
//    {
//      System.out.println(e);
//    }

//        Connection cn = null;
//        
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url="jdbc:sqlserver://LOCALHOST\\SQLEXPRESS;databaseName=necoelectro;user=webapp;password=770604";
            
            this.conn=DriverManager.getConnection(url);
            if (this.conn != null) {
                System.out.println("Conexi��n a base de datos " + bd + ". listo");
            }
//            
        } catch (SQLException|ClassNotFoundException e) {
//            Logger.getLogger(kardex_DAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Conexi��n a base de datos " + bd + ". listo");
        } 
    
//        return cn;
    

//    }
        
  }
  
  public Connection getConnection()
  {
    return this.conn;
  }
  
  public void desconectar()
  {
    this.conn = null;
    System.out.println("La conexion a la  base de datos " + bd + " a terminado");
  }
  
  void setAutoCommit(boolean b)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
