/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class AdminReportes
{
  private static Connection conn = null;
  
  public static void startReporte(String ruta, String parametro)
  {
    try
    {
      conn = (Connection) new conexion();
      conn.setAutoCommit(false);
    }
    catch (SQLException ex)
    {
      Logger.getLogger(AdminReportes.class.getName()).log(Level.SEVERE, null, ex);
    }
    try
    {
      Map parametros = new HashMap();
      parametros.put(" ", parametro);
      
      JasperPrint muestra = JasperFillManager.fillReport(ruta, null, conn);
      JasperViewer.viewReport(muestra, false);
    }
    catch (Exception localException) {}
    try
    {
      conn.close();
    }
    catch (SQLException ex)
    {
      Logger.getLogger(AdminReportes.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
