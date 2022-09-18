/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import java.net.URL;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class iReportClass
{
  private final conexion con = new conexion();
  
  public void reporte(String path)
  {
    try
    {
      URL in = getClass().getResource(path);
      JasperReport reporte = (JasperReport)JRLoader.loadObject(in);
      JasperPrint reporte_view = JasperFillManager.fillReport(reporte, new HashMap(), this.con.getConnection());
      JasperViewer.viewReport(reporte_view, false);
      
      this.con.desconectar();
    }
    catch (JRException localJRException) {}
  }
}

